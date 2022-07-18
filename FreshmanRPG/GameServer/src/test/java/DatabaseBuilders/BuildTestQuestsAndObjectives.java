package DatabaseBuilders;

import datasource.*;
import datatypes.ObjectiveStatesForTest;
import datatypes.ObjectivesForTest;
import datatypes.QuestStatesForTest;
import datatypes.QuestsForTest;
import model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Quests and Objectives portion of the database
 *
 * @author Merlin
 */
public class BuildTestQuestsAndObjectives
{

    private static void createObjectiveStateTable() throws DatabaseException
    {
        ObjectiveStateTableDataGateway gateway =
                ObjectiveStateTableDataGateway.getSingleton();
        gateway.createTable();
        for (ObjectiveStatesForTest objective : ObjectiveStatesForTest.values())
        {
            gateway.createRow(objective.getPlayerID(), objective.getQuestID(),
                    objective.getObjectiveID(), objective.getState(),
                    objective.isNeedingNotification());
        }
    }

    /**
     * Create a table of objectives
     */
    private static void createObjectiveTable() throws DatabaseException
    {
        ObjectiveTableDataGateway.createTable();
        for (ObjectivesForTest objective : ObjectivesForTest.values())
        {
            ObjectiveTableDataGateway.createRow(objective.getObjectiveID(),
                    objective.getObjectiveDescription(), objective.getQuestID(),
                    objective.getExperiencePointsGained(), objective.getCompletionType(),
                    objective.getCompletionCriteria());
        }
    }

    private static void createQuestStateTable() throws DatabaseException
    {
        QuestStateTableDataGateway questStateGateway =
                QuestStateTableDataGateway.getSingleton();
        questStateGateway.createTable();
        for (QuestStatesForTest quest : QuestStatesForTest.values())
        {
            questStateGateway.createRow(quest.getPlayerID(), quest.getQuestID(),
                    quest.getState(), quest.isNeedingNotification());
        }
    }

    /**
     * Create a table of quests
     */
    private static void createQuestTable() throws DatabaseException
    {
        QuestRowDataGateway.createTable();
        for (QuestsForTest quest : QuestsForTest.values())
        {
            System.out.print(quest.getQuestID() + " ");
            new QuestRowDataGateway(quest.getQuestID(), quest.getQuestTitle(),
                    quest.getQuestDescription(), quest.getMapName(), quest.getPosition(),
                    quest.getExperienceGained(), quest.getObjectiveForFulfillment(),
                    quest.getCompletionActionType(), quest.getCompletionActionParameter(),
                    quest.getStartDate(), quest.getEndDate());
        }
        System.out.println();
    }

    /**
     * @param args unused
     * @throws DatabaseException shouldn't
     * @throws SQLException      shouldn't
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        System.out.println("creating objective table");
        createObjectiveTable();
        System.out.println("creating quest table");
        createQuestTable();
        System.out.println("creating quest state table");
        createQuestStateTable();
        System.out.println("creating objective state table");
        createObjectiveStateTable();
    }
}
