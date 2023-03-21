package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.CriteriaIntegerDTO;
import edu.ship.engr.shipsim.criteria.CriteriaStringDTO;
import edu.ship.engr.shipsim.criteria.NPCResponseDTO;
import edu.ship.engr.shipsim.criteria.ObjectiveCompletionCriteria;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;
import edu.ship.engr.shipsim.datasource.*;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.reports.*;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Retrieves the list of quest and objectives from the database and sends them
 * to the PlayerManager?
 *
 * @author lavonne
 */
public class QuestManager implements ReportObserver
{
    private static QuestManager singleton;
    private final ObjectiveTableDataGateway objectiveGateway;
    private final HashMap<Integer, ArrayList<QuestState>> questStates;
    private final FriendTableDataGateway friendGateway;

    private QuestManager()
    {
        ReportObserverConnector.getSingleton()
                .registerObserver(this, PlayerMovedReport.class);
        ReportObserverConnector.getSingleton()
                .registerObserver(this, PlayerLeaveReport.class);
        ReportObserverConnector.getSingleton()
                .registerObserver(this, KeyInputRecievedReport.class);
        ReportObserverConnector.getSingleton()
                .registerObserver(this, ChatMessageReceivedReport.class);
        ReportObserverConnector.getSingleton()
                .registerObserver(this, DoubloonChangeReport.class);
        ReportObserverConnector.getSingleton()
                .registerObserver(this, PlayerConnectionReport.class);
        ReportObserverConnector.getSingleton()
                .registerObserver(this, InteractableObjectBuffReport.class);
        ReportObserverConnector.getSingleton()
                .registerObserver(this, InteractableObjectTextReport.class);
        ReportObserverConnector.getSingleton()
                .registerObserver(this, FriendConnectionReceivedReport.class);
        ReportObserverConnector.getSingleton()
                .registerObserver(this, ReceiveTerminalTextReport.class);

        questStates = new HashMap<>();
        this.friendGateway = FriendTableDataGateway.getSingleton();
        this.objectiveGateway = ObjectiveTableDataGateway.getSingleton();
    }

    /**
     * The method returns a singleton of QuestManager
     *
     * @return the only QuestManager in the system
     */
    public synchronized static QuestManager getSingleton()
    {
        if (singleton == null)
        {
            singleton = new QuestManager();
        }

        return singleton;
    }

    /**
     * Reset the singleton to null
     */
    public static void resetSingleton()
    {
        OptionsManager.getSingleton().assertTestMode();
        if (singleton != null)
        {
            singleton = null;
        }
    }

    /**
     * Add a quest to the player's questList
     *
     * @param playerID the player we are adding the quest id
     * @param quest    the quest being added
     */
    public void addQuestState(int playerID, QuestState quest)
    {
        ArrayList<QuestState> questStateList;
        if (!questStates.containsKey(playerID))
        {
            questStateList = new ArrayList<>();
            questStates.put(playerID, questStateList);
        }
        else
        {
            questStateList = questStates.get(playerID);
        }
        questStateList.add(quest);
        quest.setPlayerID(playerID);
    }

    /**
     * Get the objective state from the id's and set the objective state to
     * complete
     * if it is pending
     *
     * @param playerID    the id of the player
     * @param questID     the id of the quest
     * @param objectiveID the id of the objective
     * @throws DatabaseException               shouldn't
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong
     *                                         state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     */
    public void completeObjective(int playerID, int questID, int objectiveID)
            throws DatabaseException, IllegalObjectiveChangeException,
            IllegalQuestChangeException
    {
        ObjectiveState objectiveStateByID =
                getObjectiveStateByID(playerID, questID, objectiveID);


        if (objectiveStateByID != null)
        {

            objectiveStateByID.complete();
        }

    }

    /**
     * If quest state is not null, finish
     *
     * @param playerID the player ID
     * @param questID  the quest ID
     * @throws IllegalQuestChangeException thrown if illegal state change
     * @throws DatabaseException           shouldn't
     */
    public void completeQuest(int playerID, int questID)
            throws IllegalQuestChangeException, DatabaseException
    {
        QuestState qs = getQuestStateByID(playerID, questID);
        if (qs != null)
        {
            qs.complete();
        }
    }

    /**
     * Get the information about a specific objective
     *
     * @param questID     the quest that contains the objective
     * @param objectiveID the objective ID within that quest
     * @return the requested details
     * @throws DatabaseException if the data source can't respond well
     */
    public ObjectiveRecord getObjective(int questID, int objectiveID)
            throws DatabaseException
    {
        return this.objectiveGateway.getObjective(questID, objectiveID);
    }

    /**
     * @param questID        the id of the quest we're checking
     * @param reportPlayerId the player's id
     * @return the list of points objectives ready for completion
     */
    public ArrayList<ObjectiveRecord> getObjectivesByDoubloons(int questID,
                                                               int reportPlayerId)
    {
        ArrayList<ObjectiveRecord> questObjectives;
        ArrayList<ObjectiveRecord> pointsObjectives = new ArrayList<>();

        try
        {
            questObjectives =
                    QuestManager.getSingleton().getQuest(questID).getObjectives();
            if (questObjectives.size() != 0)
            {
                for (ObjectiveRecord A : questObjectives)
                {
                    ObjectiveState currentState = QuestManager.getSingleton()
                            .getObjectiveStateByID(reportPlayerId, questID,
                                    A.getObjectiveID());

                    if (A.getCompletionType() == ObjectiveCompletionType.DOUBLOONS &&
                            currentState != null &&
                            currentState.getState() == ObjectiveStateEnum.TRIGGERED)
                    {
                        pointsObjectives.add(A);
                    }
                }
            }
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }

        return pointsObjectives;
    }

    /**
     * See if there are any objectives related to friends
     *
     * @param questID        the quest we are looking for
     * @param reportPlayerId the player we are looking for
     * @return a list of objectives that are related to friends and are
     * active for this
     * player
     */
    public ArrayList<ObjectiveRecord> getObjectivesByFriend(int questID,
                                                            int reportPlayerId)
    {
        ArrayList<ObjectiveRecord> questObjectives;
        ArrayList<ObjectiveRecord> pointsObjectives = new ArrayList<>();

        try
        {
            questObjectives =
                    QuestManager.getSingleton().getQuest(questID).getObjectives();
            if (questObjectives.size() != 0)
            {
                for (ObjectiveRecord A : questObjectives)
                {
                    ObjectiveState currentState = QuestManager.getSingleton()
                            .getObjectiveStateByID(reportPlayerId, questID,
                                    A.getObjectiveID());

                    if (A.getCompletionType() == ObjectiveCompletionType.FRIENDS &&
                            currentState != null &&
                            currentState.getState() == ObjectiveStateEnum.TRIGGERED)
                    {
                        pointsObjectives.add(A);
                    }
                }
            }
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }

        return pointsObjectives;
    }

    /**
     * Returns a list of ObjectiveRecord objects based on completion at
     * specified
     * map and position
     *
     * @param pos     - the position of the objective
     * @param mapName - the map that the objective is on
     * @return an array list of ObjectiveRecords at this position
     * @throws DatabaseException shouldn't
     */
    public ArrayList<ObjectiveRecord> getObjectivesByPosition(Position pos,
                                                              String mapName)
            throws DatabaseException
    {
        return objectiveGateway.findObjectivesCompletedForMapLocation(mapName
                , pos);
    }

    /**
     * @param questID        - the id of the quest we're checking
     * @param reportPlayerID - the player's id
     * @return questObjectives - all objectives that have completionType chat
     */
    public ArrayList<ObjectiveRecord> getObjectivesByTerminal(int questID,
                                                              int reportPlayerID)
    {
        ArrayList<ObjectiveRecord> questObjectives = new ArrayList<>();
        try
        {

            for (ObjectiveRecord AR : getQuest(questID).getObjectives())
            {
                ObjectiveState objectiveState =
                        getObjectiveStateByID(reportPlayerID, questID,
                                AR.getObjectiveID());
                if (objectiveState != null)
                {
                    ObjectiveStateEnum currentObjectivesState =
                            objectiveState.getState();
                    if (ObjectiveStateEnum.TRIGGERED == currentObjectivesState)
                    {
                        if (AR.getCompletionType() == ObjectiveCompletionType.TERMINAL)
                        {
                            questObjectives.add(AR);
                        }
                    }
                }
            }
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }

        return questObjectives;
    }

    /**
     * Gets pending chat objectives of chatCompletionType
     *
     * @param questID            the quest to get objectives for
     * @param reportPlayerID     the player who sent the message
     * @param chatCompletionType the chat completion type
     * @return ArrayList of ObjectiveRecords of chat completion type
     */
    public ArrayList<ObjectiveRecord> getPendingChatObjectives(int questID,
                                                               int reportPlayerID,
                                                               ObjectiveCompletionType chatCompletionType)
    {
        ArrayList<ObjectiveRecord> questObjectives = new ArrayList<>();
        try
        {

            for (ObjectiveRecord AR : getQuest(questID).getObjectives())
            {
                ObjectiveState objectiveState =
                        getObjectiveStateByID(reportPlayerID, questID,
                                AR.getObjectiveID());
                if (objectiveState != null)
                {
                    ObjectiveStateEnum currentObjectivesState =
                            objectiveState.getState();
                    if (ObjectiveStateEnum.TRIGGERED == currentObjectivesState)
                    {
                        if (AR.getCompletionType() == chatCompletionType)
                        {
                            questObjectives.add(AR);
                        }
                    }
                }
            }
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }

        return questObjectives;
    }

    /**
     * returns all ObjectiveRecords which the type is chat
     *
     * @param questID        the quest to get objectives for
     * @param reportPlayerID the player who sent a chat message
     * @return all objectives that have completionType chat
     */
    public ArrayList<ObjectiveRecord> getPendingChatObjectives(int questID,
                                                               int reportPlayerID)
    {
        ArrayList<ObjectiveRecord> questObjectives = new ArrayList<>();
        try
        {

            for (ObjectiveRecord AR : getQuest(questID).getObjectives())
            {
                ObjectiveState objectiveState =
                        getObjectiveStateByID(reportPlayerID, questID,
                                AR.getObjectiveID());
                if (objectiveState != null)
                {
                    ObjectiveStateEnum currentObjectivesState =
                            objectiveState.getState();
                    if (ObjectiveStateEnum.TRIGGERED == currentObjectivesState)
                    {
                        if (AR.getCompletionType() == ObjectiveCompletionType.CHAT)
                        {
                            questObjectives.add(AR);
                        }
                    }
                }
            }
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }

        return questObjectives;
    }

    /**
     * Gets a specific quest from the database. Creates a Quest and passes it
     * on the
     * player mapper?
     *
     * @param questID the quest to retrieve
     * @return quest the retrieved request
     * @throws DatabaseException throw an exception if the quest id isn't found
     */
    public QuestRecord getQuest(int questID) throws DatabaseException
    {

        QuestRowDataGateway questGateway;
        questGateway = new QuestRowDataGateway(questID);

        return new QuestRecord(questGateway.getQuestID(),
                questGateway.getQuestTitle(),
                questGateway.getQuestDescription(),
                questGateway.getTriggerMapName(),
                questGateway.getTriggerPosition(),
                objectiveGateway.getObjectivesForQuest(questID),
                questGateway.getExperiencePointsGained(),
                questGateway.getObjectivesForFulfillment(),
                questGateway.getCompletionActionType(),
                questGateway.getCompletionActionParameter(),
                questGateway.getStartDate(),
                questGateway.getEndDate(),
                questGateway.getIsEasterEgg());
    }

    /**
     * Get a list of all of the current quest states for a given player
     *
     * @param playerID the player's id
     * @return the states
     */
    public ArrayList<QuestState> getQuestList(int playerID)
    {
        return questStates.get(playerID);
    }

    /**
     * Return the quests that are associated with a certain map and position
     *
     * @param pos     the position of the quest
     * @param mapName the map that the quest is on
     * @return an array list of questIDs
     * @throws DatabaseException shouldn't
     */
    public ArrayList<Integer> getQuestsByPosition(Position pos, String mapName)
            throws DatabaseException
    {
        return QuestRowDataGateway.findQuestsForMapLocation(mapName, pos);
    }

    /**
     * method to handle friends
     *
     * @param report a FriendConnectionReceivedReport
     */
    public void handleFriends(Report report)
    {
        FriendConnectionReceivedReport friendship =
                (FriendConnectionReceivedReport) report;
        int senderID = friendship.getSenderID();
        int receiverID = friendship.getReceiverID();

        //check objectives for the sender
        ArrayList<QuestState> questStates = singleton.getQuestList(senderID);

        ArrayList<ObjectiveRecord> objectivesForCompletion = new ArrayList<>();

        for (QuestState q : questStates)
        {
            objectivesForCompletion.addAll(
                    getObjectivesByFriend(q.getID(), q.getPlayerID()));
        }

        try
        {
            if (objectivesForCompletion.size() != 0)
            {
                for (ObjectiveRecord a : objectivesForCompletion)
                {
                    CriteriaIntegerDTO criteria =
                            (CriteriaIntegerDTO) a.getCompletionCriteria();

                    if (criteria.getTarget() == friendGateway.getFriendCounter(senderID))
                    {
                        singleton.completeObjective(senderID, a.getQuestID(),
                                a.getObjectiveID());
                    }
                }
            }
        }
        catch (DatabaseException | IllegalObjectiveChangeException |
               IllegalQuestChangeException e)
        {
            System.out.println("player does not exist or could not find " +
                    "friends");
            e.printStackTrace();
        }

        //check objectives for the receiver
        ArrayList<QuestState> questStates2 = singleton.getQuestList(receiverID);

        ArrayList<ObjectiveRecord> objectivesForCompletion2 = new ArrayList<>();

        for (QuestState q : questStates2)
        {
            objectivesForCompletion2.addAll(
                    getObjectivesByFriend(q.getID(), q.getPlayerID()));
        }

        try
        {
            if (objectivesForCompletion2.size() != 0)
            {
                for (ObjectiveRecord a : objectivesForCompletion2)
                {
                    CriteriaIntegerDTO criteria2 =
                            (CriteriaIntegerDTO) a.getCompletionCriteria();

                    if (criteria2.getTarget() ==
                            friendGateway.getFriendCounter(receiverID))
                    {
                        singleton.completeObjective(receiverID, a.getQuestID(),
                                a.getObjectiveID());
                    }
                }
            }
        }
        catch (DatabaseException | IllegalObjectiveChangeException |
               IllegalQuestChangeException e)
        {
            System.out.println("player does not exist or could not find " +
                    "friends");
            e.printStackTrace();
        }
    }

    /**
     * @see ReportObserver#receiveReport(Report)
     */
    @Override
    public void receiveReport(Report report)
    {
        if (report.getClass() == PlayerMovedReport.class)
        {
            handlePlayerMovement(report);
        }
        else if (report.getClass() == PlayerLeaveReport.class)
        {
            PlayerLeaveReport myReport = (PlayerLeaveReport) report;
            removeQuestStatesForPlayer(myReport.getPlayerID());
        }
        else if (report.getClass() == KeyInputRecievedReport.class)
        {
            handlePlayerInput(report);
        }
        else if (report.getClass() == ChatMessageReceivedReport.class)
        {
            handlePlayerChatCriteriaCompletion(report);
            handlePlayerChatReceivedCriteriaCompletion(report);
        }
        else if (report.getClass() == DoubloonChangeReport.class)
        {
            handleDoubloonsChanged(report);
        }
        else if (report.getClass() == PlayerConnectionReport.class)
        {
            PlayerConnectionReport myReport =
                    (PlayerConnectionReport) report;
            try
            {
                triggerQuestsForPosition(myReport.getPosition(),
                        OptionsManager.getSingleton().getMapFileTitle(),
                        myReport.getPlayerID());
                completeObjectivesForPosition(myReport.getPosition(),
                        OptionsManager.getSingleton().getMapFileTitle(),
                        myReport.getPlayerID());
            }
            catch (DatabaseException |
                   IllegalObjectiveChangeException |
                   IllegalQuestChangeException e)
            {
                e.printStackTrace();
            }
        }
        else if (report.getClass() == InteractableObjectBuffReport.class)
        {
            handleInteractableObjectBuffReport(report);

        }
        else if (report.getClass() == InteractableObjectTextReport.class)
        {
            handleInteractableObjectTextReport(report);

        }
        else if (report.getClass() == FriendConnectionReceivedReport.class)
        {

            handleFriends(report);

        }
        else if (report.getClass() == ReceiveTerminalTextReport.class)
        {
            handleReceiveTerminalTextReport(report);

        }

    }

    /**
     * Remove all of the quest states for a given player
     *
     * @param playerID the player we are removing
     */
    public void removeQuestStatesForPlayer(int playerID)
    {
        questStates.remove(playerID);
    }

    /**
     * Trigger a quest for a given player
     *
     * @param playerID the player
     * @param questID  the quest to be triggered
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong
     *                                         state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     * @throws DatabaseException               shouldn't
     */
    public void triggerQuest(int playerID, int questID)
            throws IllegalObjectiveChangeException, IllegalQuestChangeException,
            DatabaseException
    {
        QuestState qs = getQuestStateByID(playerID, questID);
        Date now = Calendar.getInstance().getTime();
        QuestRecord q = getQuest(questID);

        if ((qs != null) && (qs.getStateValue() == QuestStateEnum.AVAILABLE))
        {
            if (now.after(q.getStartDate()) && now.before(q.getEndDate()))
            {
                qs.trigger();

            }
        }
    }

    /**
     * Set needing notification to false
     *
     * @param playerID    the id of the player
     * @param questID     the id of the quest
     * @param objectiveID the id of the objective
     * @throws DatabaseException               shouldn't
     * @throws IllegalObjectiveChangeException thrown if changing to a wrong
     *                                         state
     * @throws IllegalQuestChangeException     thrown if illegal state change
     */
    public void turnOffNotification(int playerID, int questID, int objectiveID)
            throws DatabaseException, IllegalObjectiveChangeException,
            IllegalQuestChangeException
    {
        ObjectiveState x = getObjectiveStateByID(playerID, questID,
                objectiveID);
        if (x != null)
        {
            x.turnOffNotification();
        }
    }

    /**
     * Called when the user no longer needs to be notified about the current
     * state
     * of a quest
     *
     * @param playerID the player
     * @param questID  the quest that should be quieted
     */
    public void turnOffQuestNotification(int playerID, int questID)
    {
        QuestState qs = getQuestStateByID(playerID, questID);
        qs.setNeedingNotification(false);
    }

    /**
     * checks if objective meets chat criteria and completes it if it does
     *
     * @param reportPlayerID player who sent chat message
     * @param q              quest to get objective for
     * @throws PlayerNotFoundException if the player in the report is not
     *                                 logged in
     */
    private void checkAllChatObjectivesForCompletion(int reportPlayerID,
                                                     ChatType type,
                                                     QuestState q)
            throws PlayerNotFoundException
    {
        PlayerManager PM = PlayerManager.getSingleton();
        for (ObjectiveRecord a : getPendingChatObjectives(q.getID(),
                reportPlayerID,
                ObjectiveCompletionType.CHAT))
        {
            ObjectiveCompletionCriteria criteria = a.getCompletionCriteria();
            if (criteria.getClass().equals(CriteriaStringDTO.class))
            {
                CriteriaStringDTO castCrit = (CriteriaStringDTO) criteria;
                try
                {
                    Player npc = PM.getPlayerFromID(
                            PM.getPlayerIDFromPlayerName(castCrit.getString()));
                    if ((npc != null) && (type != ChatType.Local) || PM.getPlayerFromID(reportPlayerID)
                            .canReceiveLocalMessage(npc.getPlayerPosition()))
                    {
                        try
                        {
                            QuestManager.getSingleton()
                                    .completeObjective(reportPlayerID,
                                            q.getID(),
                                            a.getObjectiveID());

                        }
                        catch (DatabaseException |
                               IllegalObjectiveChangeException |
                               IllegalQuestChangeException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                catch (PlayerNotFoundException e)
                {
                    // the player that would match this criteria isn't on
                    // this server
                    // so there's no way this objective would complete
                }


            }
        }
    }

    /**
     * Checks if the received chat report meets the criteria to complete the
     * quest
     */
    private void checkAllChatReceivedObjectivesForCompletion(int reportPlayerID,
                                                             int reportNPCID,
                                                             String reportChat,
                                                             QuestState q)
            throws PlayerNotFoundException
    {
        PlayerManager PM = PlayerManager.getSingleton();
        for (ObjectiveRecord a : getPendingChatObjectives(q.getID(),
                reportPlayerID,
                ObjectiveCompletionType.CHAT_RECEIVED))
        {
            ObjectiveCompletionCriteria criteria = a.getCompletionCriteria();
            if (criteria.getClass().equals(NPCResponseDTO.class))
            {
                NPCResponseDTO castCrit = (NPCResponseDTO) criteria;
                Player npc = PM.getPlayerFromID(reportNPCID);
                Player player = PM.getPlayerFromID(reportPlayerID);
                if (player.canReceiveLocalMessage(npc.getPlayerPosition()))
                {
                    if (reportChat.equals(castCrit.getResponse()) &&
                            PM.getPlayerIDFromPlayerName(castCrit.getNPCName()) ==
                                    reportNPCID)
                    {
                        try
                        {
                            QuestManager.getSingleton()
                                    .completeObjective(reportPlayerID,
                                            q.getID(),
                                            a.getObjectiveID());

                        }
                        catch (DatabaseException |
                               IllegalObjectiveChangeException |
                               IllegalQuestChangeException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private void completeObjectivesForPosition(Position position,
                                               String mapName,
                                               int playerID)
            throws DatabaseException, IllegalObjectiveChangeException,
            IllegalQuestChangeException
    {
        ArrayList<ObjectiveRecord> objectives =
                getObjectivesByPosition(position, mapName);
        for (ObjectiveRecord a : objectives)
        {
            ObjectiveState objectiveStateByID =
                    getObjectiveStateByID(playerID, a.getQuestID(),
                            a.getObjectiveID());
            if (objectiveStateByID != null &&
                    objectiveStateByID.getState() != ObjectiveStateEnum.COMPLETED)
            {
                this.completeObjective(playerID, a.getQuestID(),
                        a.getObjectiveID());
            }
        }
    }

    private ArrayList<ObjectiveRecord> getObjectivesByInteractableObject(int questID,
                                                                         int reportPlayerId)
    {
        ArrayList<ObjectiveRecord> questObjectives;
        ArrayList<ObjectiveRecord> pointsObjectives = new ArrayList<>();

        try
        {
            questObjectives =
                    QuestManager.getSingleton().getQuest(questID).getObjectives();
            if (questObjectives.size() != 0)
            {
                for (ObjectiveRecord A : questObjectives)
                {
                    ObjectiveState currentState = QuestManager.getSingleton()
                            .getObjectiveStateByID(reportPlayerId, questID,
                                    A.getObjectiveID());

                    if (A.getCompletionType() == ObjectiveCompletionType.INTERACT &&
                            currentState != null &&
                            currentState.getState() == ObjectiveStateEnum.TRIGGERED)
                    {
                        pointsObjectives.add(A);
                    }
                }
            }
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }

        return pointsObjectives;
    }

    private void handleInteractableObjectBuffReport(Report report)
    {
        InteractableObjectBuffReport myReport =
                (InteractableObjectBuffReport) report;
        PlayerManager.getSingleton();

        ArrayList<QuestState> questStates =
                singleton.getQuestList(myReport.getPlayerID());

        ArrayList<ObjectiveRecord> objectivesForCompletion = new ArrayList<>();

        for (QuestState q : questStates)
        {
            objectivesForCompletion.addAll(
                    getObjectivesByInteractableObject(q.getID(),
                            q.getPlayerID()));
        }

        try
        {
            if (objectivesForCompletion.size() != 0)
            {
                for (ObjectiveRecord a : objectivesForCompletion)
                {
                    CriteriaIntegerDTO criteria =
                            (CriteriaIntegerDTO) a.getCompletionCriteria();

                    if (criteria.getTarget() == InteractObjectManager.getSingleton()
                            .objectInRange(myReport.getPlayerID()))
                    {
                        singleton.completeObjective(myReport.getPlayerID(),
                                a.getQuestID(), a.getObjectiveID());
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void handleInteractableObjectTextReport(Report report)
    {
        InteractableObjectTextReport myReport =
                (InteractableObjectTextReport) report;
        PlayerManager.getSingleton();

        ArrayList<QuestState> questStates =
                singleton.getQuestList(myReport.getPlayerID());

        ArrayList<ObjectiveRecord> objectivesForCompletion = new ArrayList<>();

        for (QuestState q : questStates)
        {
            objectivesForCompletion.addAll(
                    getObjectivesByInteractableObject(q.getID(),
                            q.getPlayerID()));
        }

        try
        {
            if (objectivesForCompletion.size() != 0)
            {
                for (ObjectiveRecord a : objectivesForCompletion)
                {
                    CriteriaIntegerDTO criteria =
                            (CriteriaIntegerDTO) a.getCompletionCriteria();

                    if (criteria.getTarget() == InteractObjectManager.getSingleton()
                            .objectInRange(myReport.getPlayerID()))
                    {
                        singleton.completeObjective(myReport.getPlayerID(),
                                a.getQuestID(), a.getObjectiveID());
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Will listen for SendChatMessageReports and check to see if they help
     * complete
     * any of the current objective that a player is doing Objective must be off
     * ObjectiveCompletion type chat and the players must be within a certain
     * distance of each other
     */
    private void handlePlayerChatCriteriaCompletion(Report report)
    {
        ChatMessageReceivedReport myReport = (ChatMessageReceivedReport) report;
        try
        {
            int reportPlayerID = myReport.getSenderID();
            ArrayList<QuestState> questStateList =
                    singleton.getQuestList(reportPlayerID);

            if (questStateList != null)
            {
                for (QuestState q : questStateList)
                {
                    checkAllChatObjectivesForCompletion(reportPlayerID,
                            myReport.getType(), q);
                }
            }
        }
        catch (PlayerNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Check all chat reports that are received from an NPC and passes along
     * the report
     *
     * @param report a ChatMessageReceivedReport
     */
    private void handlePlayerChatReceivedCriteriaCompletion(
            Report report)
    {
        ChatMessageReceivedReport myReport = (ChatMessageReceivedReport) report;
        try
        {
            int reportNPCID = myReport.getSenderID();
            int reportPlayerID = myReport.getReceiverID();
            String reportChat = myReport.getChatText();
            ArrayList<QuestState> questStateList = getQuestList(reportPlayerID);

            if (questStateList != null)
            {
                for (QuestState q : questStateList)
                {
                    checkAllChatReceivedObjectivesForCompletion(reportPlayerID,
                            reportNPCID, reportChat, q);
                }
            }
        }
        catch (PlayerNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Iterates through all quests and objectives and validates if it's the
     * correct
     * criteria for keyboard input.
     *
     * @param report a KeyInputRecievedReport
     */
    private void handlePlayerInput(Report report)
    {
        KeyInputRecievedReport myReport = (KeyInputRecievedReport) report;
        ArrayList<QuestState> questStates =
                singleton.getQuestList(myReport.getPlayerId());
        if (questStates != null)
        {
            for (QuestState qs : questStates)
            {
                for (ObjectiveState as : qs.getObjectiveList())
                {
                    validateInputCriteriaForObjectives(myReport.getInput(),
                            myReport.getPlayerId(), qs.getID(), as.getID());
                }
            }
        }
    }

    private void handlePlayerMovement(Report report)
    {
        PlayerMovedReport myReport = (PlayerMovedReport) report;
        Position position = myReport.getNewPosition();
        String mapName = myReport.getMapName();
        int playerID = myReport.getPlayerID();
        try
        {
            triggerQuestsForPosition(position, mapName, playerID);

            completeObjectivesForPosition(position, mapName, playerID);

        }
        catch (DatabaseException | IllegalObjectiveChangeException |
               IllegalQuestChangeException e)
        {
            e.printStackTrace();
        }
    }

    private void triggerQuestsForPosition(Position position, String mapName,
                                          int playerID)
            throws DatabaseException, IllegalObjectiveChangeException,
            IllegalQuestChangeException
    {
        QuestManager qm = QuestManager.getSingleton();
        ArrayList<Integer> questIDs;

        questIDs = qm.getQuestsByPosition(position, mapName);

        for (Integer q : questIDs)
        {
            this.triggerQuest(playerID, q);
        }
    }

    /**
     * Validates if an input string matches the criteria string.
     */
    private void validateInputCriteriaForObjectives(String input, int playerId,
                                                    int questId,
                                                    int objectiveId)
    {
        try
        {
            ObjectiveRecord ar = getObjective(questId, objectiveId);
            ObjectiveState objectiveStateByID =
                    singleton.getObjectiveStateByID(playerId, questId,
                            objectiveId);
            if (ar.getCompletionType() == ObjectiveCompletionType.KEYSTROKE &&
                    (objectiveStateByID != null) &&
                    (objectiveStateByID.getState() == ObjectiveStateEnum.TRIGGERED))
            {
                CriteriaStringDTO cs =
                        (CriteriaStringDTO) ar.getCompletionCriteria();
                if (cs.getString().equalsIgnoreCase(input))
                {
                    singleton.completeObjective(playerId, questId, objectiveId);
                }
            }
        }
        catch (Exception e)
        {
            LoggerManager.getSingleton().getLogger()
                    .info("Exception getting objective " + objectiveId +
                            " for quest " + questId);
            e.printStackTrace();
        }
    }

    /**
     * See if gaining doubloons completed any objectives
     *
     * @param report A DoubloonChangeReport
     */
    protected void handleDoubloonsChanged(Report report)
    {
        DoubloonChangeReport myReport = (DoubloonChangeReport) report;
        PlayerManager PM = PlayerManager.getSingleton();

        ArrayList<QuestState> questStates =
                singleton.getQuestList(myReport.getPlayerID());

        ArrayList<ObjectiveRecord> objectivesForCompletion = new ArrayList<>();

        for (QuestState q : questStates)
        {
            objectivesForCompletion.addAll(
                    getObjectivesByDoubloons(q.getID(), q.getPlayerID()));
        }

        try
        {
            if (objectivesForCompletion.size() != 0)
            {
                for (ObjectiveRecord a : objectivesForCompletion)
                {
                    CriteriaIntegerDTO criteria =
                            (CriteriaIntegerDTO) a.getCompletionCriteria();

                    if (criteria.getTarget() <=
                            PM.getPlayerFromID(myReport.getPlayerID()).getQuizScore())
                    {
                        singleton.completeObjective(myReport.getPlayerID(),
                                a.getQuestID(), a.getObjectiveID());
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Will listen for ReceiveTerminalTextReport and check to see if they
     * help complete
     * any of the current objective that a player is doing Objective must be of
     * ObjectiveCompletion type terminal
     */
    protected void handleReceiveTerminalTextReport(Report report)
    {
        ReceiveTerminalTextReport myReport = (ReceiveTerminalTextReport) report;

        ArrayList<QuestState> questStates =
                singleton.getQuestList(myReport.getPlayerID());

        ArrayList<ObjectiveRecord> objectivesForCompletion = new ArrayList<>();

        if (questStates != null)
        {
            for (QuestState q : questStates)
            {
                objectivesForCompletion.addAll(
                        getObjectivesByTerminal(q.getID(), q.getPlayerID()));
            }

            try
            {
                if (objectivesForCompletion.size() != 0)
                {
                    for (ObjectiveRecord a : objectivesForCompletion)
                    {
                        CriteriaStringDTO criteria =
                                (CriteriaStringDTO) a.getCompletionCriteria();

                        if (criteria.getString().equals(myReport.getCommand()))
                        {
                            singleton.completeObjective(myReport.getPlayerID(),
                                    a.getQuestID(), a.getObjectiveID());
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Go through the questList and get the state of the quest based on the id
     *
     * @param playerID the id of the player
     * @param questID  the id of the quest we are interested in
     * @return the state of the quest
     */
    QuestState getQuestStateByID(int playerID, int questID)
    {

        ArrayList<QuestState> questStateList = singleton.getQuestList(playerID);
        QuestRecord quest;
        try
        {
            quest = QuestManager.getSingleton().getQuest(questID);
        }
        catch (DatabaseException e)
        {
            // this should never happen . . .
            return null;
        }

        QuestState qState = null;

        if (questStateList != null)
        {
            for (QuestState q : questStateList)
            {
                if (q.getID() == questID)
                {
                    qState = q;
                }
            }
        }
        if (qState == null)
        {
            Date now = Calendar.getInstance().getTime();
            Date startDate = quest.getStartDate();
            if (startDate != null && now.after(startDate))
            {
                qState =
                        new QuestState(playerID, questID,
                                QuestStateEnum.AVAILABLE, true);
                try
                {
                    quest = getQuest(questID);
                    ArrayList<ObjectiveState> advStates = new ArrayList<>();
                    for (ObjectiveRecord objective : quest.getObjectives())
                    {
                        advStates.add(new ObjectiveState(objective.getObjectiveID(),
                                ObjectiveStateEnum.HIDDEN, false));
                    }
                    qState.addObjectives(advStates);
                    addQuestState(playerID, qState);
                }
                catch (DatabaseException e)
                {
                    e.printStackTrace();
                }

            }
        }
        if (quest != null && qState != null)
        {
            Date now = Calendar.getInstance().getTime();
            if (now.after(quest.getEndDate()) &&
                    qState.getStateValue() != QuestStateEnum.EXPIRED &&
                    qState.getStateValue() != QuestStateEnum.COMPLETED)
            {
                try
                {
                    qState.changeState(QuestStateEnum.EXPIRED,
                            qState.isNeedingNotification());
                }
                catch (IllegalQuestChangeException | DatabaseException e)
                {
                    e.printStackTrace();
                }
            }
        }

        return qState;
    }

    /**
     * Use the id's of the player quest and objective to find a specified
     * objective
     * state
     *
     * @param playerID    the id of the player
     * @param questID     the id of the quest
     * @param objectiveID the id of the objective
     * @return the state of the objectivenew Grego
     */
    ObjectiveState getObjectiveStateByID(int playerID, int questID,
                                         int objectiveID)
    {

        ArrayList<QuestState> questStateList = questStates.get(playerID);
        if (questStateList == null)
        {
            return null;
        }
        for (QuestState q : questStateList)
        {

            if (q.getID() == questID)
            {

                ArrayList<ObjectiveState> objectiveList = q.getObjectiveList();

                for (ObjectiveState a : objectiveList)
                {
                    if (a.getID() == objectiveID)
                    {

                        return a;
                    }
                }

            }
        }

        return null;
    }

    @NotNull
    private List<ClientPlayerObjectiveStateDTO> getObjectives(@NotNull QuestRecord questRecord, @NotNull QuestState questState)
    {
        List<ClientPlayerObjectiveStateDTO> objectives = Lists.newArrayList();

        for (ObjectiveState objectiveState : questState.getObjectiveList())
        {
            ObjectiveRecord objective = questRecord.getObjectiveID(objectiveState.getID());

            ClientPlayerObjectiveStateDTO dto = new ClientPlayerObjectiveStateDTO(objectiveState.getID(),
                    objective.getObjectiveDescription(),
                    objective.getExperiencePointsGained(),
                    objectiveState.getState(),
                    objectiveState.isNeedingNotification(),
                    objective.isRealLifeObjective(),
                    objective.getCompletionCriteria().toString(),
                    questState.getStateValue());

            objectives.add(dto);
        }

        return objectives;
    }

    @NotNull
    public List<ClientPlayerQuestStateDTO> getQuests(Player player) throws DatabaseException
    {
        List<ClientPlayerQuestStateDTO> quests = Lists.newArrayList();

        List<QuestState> questList = getQuestList(player.getPlayerID());
        if (questList != null)
        {
            for (QuestState questState : questList)
            {
                QuestRecord record = getQuest(questState.getID());

                ClientPlayerQuestStateDTO dto = new ClientPlayerQuestStateDTO(record.getQuestID(),
                        record.getTitle(),
                        record.getDescription(),
                        questState.getStateValue(),
                        record.getExperiencePointsGained(),
                        record.getObjectivesForFulfillment(),
                        questState.isNeedingNotification(),
                        record.getEndDate(),
                        record.isEasterEgg());

                dto.setObjectives(getObjectives(record, questState));

                quests.add(dto);
            }
        }

        return quests;
    }
}
