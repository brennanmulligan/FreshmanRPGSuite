package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.criteria.QuestCompletionActionParameter;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ObjectiveTableDataGateway;
import edu.ship.engr.shipsim.datasource.QuestRowDataGateway;
import edu.ship.engr.shipsim.datasource.QuestTableDataGateway;
import edu.ship.engr.shipsim.datatypes.Position;

import java.util.ArrayList;
import java.util.Date;

/**
 * Manages retrieving and persisting all of the data associated with Quests
 *
 * @author Scott Bucher
 */
public class QuestMapper
{
    private final QuestRowDataGateway questGateway;
    private final ObjectiveTableDataGateway objectiveTableDataGateway;
    protected QuestRecord questRecord;

    /**
     * Finder constructor
     *
     * @param questId the quest's unique ID
     * @throws DatabaseException if we can't find the given quest
     */
    public QuestMapper(int questId) throws DatabaseException
    {
        this.questGateway = new QuestRowDataGateway(questId);
        this.objectiveTableDataGateway =
                ObjectiveTableDataGateway.getSingleton();

        this.questRecord = new QuestRecord(this.questGateway.getQuestID(),
                this.questGateway.getQuestTitle(),
                this.questGateway.getQuestDescription(),
                this.questGateway.getTriggerMapName(),
                this.questGateway.getTriggerPosition(),
                objectiveTableDataGateway.getObjectivesForQuest(
                        this.questGateway.getQuestID()),
                this.questGateway.getExperiencePointsGained(),
                this.questGateway.getObjectivesForFulfillment(),
                this.questGateway.getCompletionActionType(),
                this.questGateway.getCompletionActionParameter(),
                this.questGateway.getStartDate(),
                this.questGateway.getEndDate(),
                this.questGateway.isEasterEgg());
    }

    /**
     * Creation constructor.
     *
     * @param questTitle                the title of the quest
     * @param questDescription          the description of the quest
     * @param mapName                   the name of the map that triggers the
     *                                 quest
     * @param position                  the position on the map that triggers
     *                                 the quest
     * @param objectives                the objectives associated with the quest
     * @param objectivesForFulfillment  the objectives that must be fulfilled
     *                                 to complete the quest
     * @param experiencePointsGained    the amount of experience points
     *                                  gained upon completion of the quest
     * @param completionActionType      the type of action that is performed
     *                                  upon completion of the quest
     * @param completionActionParameter the parameter for the action that is
     *                                  performed upon completion of the quest
     * @param startDate                 the date that the quest becomes
     *                                  available
     * @param endDate                   the date that the quest expires
     * @param isEasterEgg               if the quest is an easter egg
     * @throws DatabaseException if we can't create the quest
     */
    public QuestMapper(String questTitle, String questDescription,
                       String mapName, Position position,
                       ArrayList<ObjectiveRecord> objectives,
                       int objectivesForFulfillment, int experiencePointsGained,
                       QuestCompletionActionType completionActionType,
                       QuestCompletionActionParameter completionActionParameter,
                       Date startDate, Date endDate, boolean isEasterEgg)
            throws DatabaseException
    {
        this.questGateway =
                new QuestRowDataGateway(questTitle, questDescription, mapName,
                        position,
                        experiencePointsGained, objectivesForFulfillment,
                        completionActionType, completionActionParameter,
                        startDate, endDate, isEasterEgg);

        this.questRecord = new QuestRecord(this.questGateway.getQuestID(),
                questTitle,
                questDescription,
                mapName,
                position,
                objectives,
                experiencePointsGained,
                objectivesForFulfillment,
                completionActionType,
                completionActionParameter,
                startDate,
                endDate,
                isEasterEgg);

        // update all the quest ids in the objective list to use the new quest id
        for (ObjectiveRecord objective : this.questRecord.getObjectives())
        {
            objective.setQuestID(this.questRecord.getQuestID());
        }

        this.objectiveTableDataGateway =
                ObjectiveTableDataGateway.getSingleton();
        this.objectiveTableDataGateway.upsertObjectives(this.questRecord);
    }

    /**
     * Returns a list of all the quests.
     *
     * @return list of all quests
     */
    public static ArrayList<QuestRecord> getAllQuests()
    {
        QuestTableDataGateway gateway = QuestTableDataGateway.getSingleton();

        ObjectiveTableDataGateway objectiveGateway =
                ObjectiveTableDataGateway.getSingleton();

        try
        {
            ArrayList<QuestRecord> quests = gateway.getAllQuests();

            for (QuestRecord quest : quests)
            {
                quest.setObjectives(objectiveGateway.getObjectivesForQuest(
                        quest.getQuestID()));
            }

            return quests;
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Get the player that this Mapper is responsible for
     *
     * @return the player this mapper manages
     */
    public QuestRecord getQuest()
    {
        return questRecord;
    }

    /**
     * Remove the quest
     *
     * @throws DatabaseException - shouldn't
     */
    public void remove() throws DatabaseException
    {
        this.questGateway.remove();
    }

    /**
     * Persist the current state of the quest into the data source
     *
     * @throws DatabaseException if we can't complete the write
     */
    protected void persist() throws DatabaseException
    {
        this.questGateway.setQuestTitle(this.questRecord.getTitle());
        this.questGateway.setQuestDescription(
                this.questRecord.getDescription());
        this.questGateway.setTriggerMapName(
                this.questRecord.getTriggerMapName());
        this.questGateway.setTriggerPosition(this.questRecord.getPos());
        this.questGateway.setExperiencePointsGained(
                this.questRecord.getExperiencePointsGained());
        this.questGateway.setObjectivesForFulfillment(
                this.questRecord.getObjectivesForFulfillment());
        this.questGateway.setCompletionActionType(
                this.questRecord.getCompletionActionType());
        this.questGateway.setCompletionActionParameter(
                this.questRecord.getCompletionActionParameter());
        this.questGateway.setStartDate(this.questRecord.getStartDate());
        this.questGateway.setEndDate(this.questRecord.getEndDate());
        this.questGateway.setEasterEgg(this.questRecord.isEasterEgg());
        this.questGateway.persist();

        this.objectiveTableDataGateway.upsertObjectives(this.questRecord);
    }
}
