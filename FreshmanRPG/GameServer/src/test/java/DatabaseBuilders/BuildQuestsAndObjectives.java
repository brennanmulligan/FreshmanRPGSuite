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
public class BuildQuestsAndObjectives
{

    private static void createObjectiveStateTable() throws DatabaseException
    {
        ObjectiveStateTableDataGateway gateway =
                (ObjectiveStateTableDataGateway) TableDataGatewayManager.getSingleton()
                        .getTableGateway("ObjectiveState");
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
        ObjectiveTableDataGatewayRDS.createTable();
        for (ObjectivesForTest objective : ObjectivesForTest.values())
        {
            ObjectiveTableDataGatewayRDS.createRow(objective.getObjectiveID(),
                    objective.getObjectiveDescription(), objective.getQuestID(),
                    objective.getExperiencePointsGained(), objective.getCompletionType(),
                    objective.getCompletionCriteria());
        }
    }

    private static void createQuestStateTable() throws DatabaseException
    {
        QuestStateTableDataGatewayRDS questStateGateway =
                (QuestStateTableDataGatewayRDS) TableDataGatewayManager.getSingleton()
                        .getTableGateway("QuestState");
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
        QuestRowDataGatewayRDS.createTable();
        for (QuestsForTest quest : QuestsForTest.values())
        {
            System.out.print(quest.getQuestID() + " ");
            new QuestRowDataGatewayRDS(quest.getQuestID(), quest.getQuestTitle(),
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
        OptionsManager.getSingleton().setUsingMocKDataSource(true);
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
