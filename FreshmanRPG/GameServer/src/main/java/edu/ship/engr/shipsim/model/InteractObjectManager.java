package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.CriteriaIntegerDTO;
import edu.ship.engr.shipsim.criteria.CriteriaStringDTO;
import edu.ship.engr.shipsim.criteria.InteractableItemActionParameter;
import edu.ship.engr.shipsim.criteria.QuestListCompletionParameter;
import edu.ship.engr.shipsim.dataDTO.InteractableItemDTO;
import edu.ship.engr.shipsim.dataENUM.InteractableItemActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.InteractableItemRowDataGateway;
import edu.ship.engr.shipsim.datasource.InteractableItemTableDataGateway;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.InteractableObjectBuffReport;
import edu.ship.engr.shipsim.model.reports.InteractableObjectTextReport;
import edu.ship.engr.shipsim.model.reports.InteractionDeniedReport;
import edu.ship.engr.shipsim.model.reports.KeyInputRecievedReport;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * InteractObjectManager checks the player position against other objects'
 * locations in the DB If the player is near the objects, report will be sent
 * out
 *
 * @author Andy, Truc, and Emmanuel
 */
public class InteractObjectManager implements ReportObserver
{
    private static final int WIDTH = 2;
    private static final int HEIGHT = 2;
    private static InteractObjectManager singleton;
    private static InteractableItemTableDataGateway gateway;

    /**
     * Protected for singleton
     */
    private InteractObjectManager()
    {
        gateway =
                InteractableItemTableDataGateway.getSingleton();

        ReportObserverConnector.getSingleton()
                .registerObserver(this, KeyInputRecievedReport.class);
    }

    /**
     * @return singleton instance of this manager
     */
    public synchronized static InteractObjectManager getSingleton()
    {
        if (singleton == null)
        {
            singleton = new InteractObjectManager();
        }
        return singleton;
    }

    /**
     * Reset the singleton for testing purpose
     */
    public static void resetSingleton()
    {
        OptionsManager.getSingleton().assertTestMode();
        if (singleton != null)
        {
            ReportObserverConnector.getSingleton()
                    .unregisterObserver(singleton, KeyInputRecievedReport.class);
            singleton = null;
        }
    }

    /**
     * Checks whether the player is in the object locations on the map or not
     *
     * @param playerId the player whose location we're checking
     * @return the object id of what is in range, -1 if nothing
     * @throws DatabaseException shouldn't
     *                           <p>
     *                           edited by: Elisabeth Ostrow
     */
    public int objectInRange(int playerId) throws DatabaseException
    {
        // Gets player position from id
        Player playerObject = PlayerManager.getSingleton().getPlayerFromID(playerId);
        if (playerObject == null)
        {
            return -1;
        }
        Position playerPosition = playerObject.getPosition();

        int x = playerPosition.getRow();
        int y = playerPosition.getColumn();

        // Creates a rectangle around player
        Rectangle playerRec = new Rectangle(x, y, WIDTH, HEIGHT);

        // Gets all objects on the same map from DB
        ArrayList<InteractableItemDTO> items =
                gateway.getItemsOnMap(playerObject.getMapName());

        // Checks whether the player is in the object range
        for (InteractableItemDTO item : items)
        {
            Position p = item.getPosition();

            Rectangle objRec = new Rectangle(p.getRow(), p.getColumn(), WIDTH, HEIGHT);
            boolean isInObjectRange = playerRec.intersects(objRec);
            if (isInObjectRange)
            {
                return item.getId();
            }
        }
        return -1;
    }

    /**
     * handle a received report execute the appropriate item action if necessary
     * <p>
     * edited by: Elisabeth Ostrow
     */
    @Override
    public void receiveReport(Report report)
    {
        if (report.getClass().equals(KeyInputRecievedReport.class))
        {
            try
            {
                KeyInputRecievedReport rpt = (KeyInputRecievedReport) report;
                if (rpt.getInput().equalsIgnoreCase("E"))
                {
                    int playerID = rpt.getPlayerId();
                    int objectID = objectInRange(playerID);
                    if (objectID >= 0)
                    {
                        execute(playerID, objectID);
                    }
                    // otherwise do nothing
                }
            }
            catch (DatabaseException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Responsible for executing interaction with specific objects
     *
     * @param playerId - the player who is interacting with the object
     * @param itemId   - the itemId from the database that the player is interacting with
     * @return whether or not execution was "successful"
     * @author Adam Pine, Jacob Knight
     * <p>
     * <p>
     * edited by Stephen Clabaugh and Aaron Gerber
     */
    protected boolean execute(int playerId, int itemId)
    {
        try
        {
            InteractableItemRowDataGateway rowGateway;
            rowGateway = new InteractableItemRowDataGateway(itemId);
            InteractableItemActionType type = rowGateway.getActionType();
            InteractableItemActionParameter param = rowGateway.getActionParam();
            if (type != null)
            {
                switch (type)
                {
                    case NO_ACTION:
                        return true;
                    case MESSAGE:
                        CriteriaStringDTO txt = (CriteriaStringDTO) param;
                        InteractableObjectTextReport textReport =
                                new InteractableObjectTextReport(playerId,
                                        txt.getString());
                        ReportObserverConnector.getSingleton()
                                .sendReport(textReport);
                        return true;
                    case BOARD:
                        CriteriaStringDTO txt1 = (CriteriaStringDTO) param;
                        InteractableObjectTextReport textReport1 =
                                new InteractableObjectTextReport(playerId,
                                        txt1.getString());
                        ReportObserverConnector.getSingleton()
                                .sendReport(textReport1);
                        return true;
                    case BUFF:
                        Player p = PlayerManager.getSingleton().getPlayerFromID(playerId);
                        if (p.getBuffPool() == 0)
                        {
                            CriteriaIntegerDTO buffPool = (CriteriaIntegerDTO) param;
                            PlayerManager.getSingleton().getPlayerFromID(playerId)
                                    .setBuffPool(buffPool.getTarget());
                            ReportObserverConnector.getSingleton().sendReport(
                                    new InteractableObjectBuffReport(playerId,
                                            buffPool.getTarget()));
                        }
                        else
                        {
                            ReportObserverConnector.getSingleton()
                                    .sendReport(new InteractionDeniedReport(playerId));
                        }
                        return true;
                    case QUEST_TRIGGER:
                        String paramString = param.toString();
                        paramString = paramString.replace("Criteria String: ", "");
                        List<String> arrayList =
                                new ArrayList<>(Arrays.asList(paramString.split(",")));
                        ArrayList<Integer> questList = new ArrayList<>();

                        for (String questID : arrayList)
                        {
                            System.out.println(questID);
                            questList.add(Integer.parseInt(questID.trim()));
                        }

                        QuestListCompletionParameter ql =
                                new QuestListCompletionParameter(
                                        questList); // Quests to trigger
                        for (Integer questID : ql.getQuestIDs()) // For every quest in QuestLIstCompletionParameter
                        {
                            try
                            {
                                QuestManager.getSingleton()
                                        .triggerQuest(playerId, questID);
                            }
                            catch (IllegalQuestChangeException |
                                   IllegalObjectiveChangeException e)
                            {
                                return false;
                            }
                        }
                        return true;
                    default:
                        return false;
                }
            }
        }
        catch (DatabaseException | NullPointerException e)
        {
            return false;
        }
        return false;
    }

}
