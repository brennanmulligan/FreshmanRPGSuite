package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.*;
import edu.ship.engr.shipsim.datatypes.ObjectiveStatesForTest;
import edu.ship.engr.shipsim.datatypes.ObjectivesForTest;
import edu.ship.engr.shipsim.datatypes.QuestStatesForTest;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Quests and Objectives portion of the database
 *
 * @author Merlin
 */
public class BuildTestQuestsAndObjectives
{
    /**
     * @param args unused
     * @throws DatabaseException shouldn't
     * @throws SQLException      shouldn't
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createObjectiveTable();
        createQuestTable();
        createQuestStateTable();
        createObjectiveStateTable();
    }

    /**
     * Create a table of objectives
     */
    private static void createObjectiveTable() throws DatabaseException
    {
        System.out.println("Building the Objective Table");
        ObjectiveTableDataGateway.createTable();

        ProgressBar bar = new ProgressBar(ObjectivesForTest.values().length);
        for (ObjectivesForTest objective : ObjectivesForTest.values())
        {
            ObjectiveTableDataGateway.createRow(objective.getObjectiveID(),
                    objective.getObjectiveDescription(), objective.getQuestID(),
                    objective.getExperiencePointsGained(), objective.getCompletionType(),
                    objective.getCompletionCriteria());

            bar.update();
        }
    }

    /**
     * Create a table of quests
     */
    private static void createQuestTable() throws DatabaseException
    {
        System.out.println("Building the Quest Table");
        QuestRowDataGateway.createTable();

        ProgressBar bar = new ProgressBar(QuestsForTest.values().length);
        for (QuestsForTest quest : QuestsForTest.values())
        {
            new QuestRowDataGateway(quest.getQuestID(), quest.getQuestTitle(),
                    quest.getQuestDescription(), quest.getMapName(), quest.getPosition(),
                    quest.getExperienceGained(), quest.getObjectiveForFulfillment(),
                    quest.getCompletionActionType(), quest.getCompletionActionParameter(),
                    quest.getStartDate(), quest.getEndDate());

            bar.update();
        }
    }

    private static void createObjectiveStateTable() throws DatabaseException
    {
        System.out.println("Building the ObjectiveState Table");
        ObjectiveStateTableDataGateway gateway = ObjectiveStateTableDataGateway.getSingleton();
        gateway.createTable();

        ProgressBar bar = new ProgressBar(QuestStatesForTest.values().length);
        for (ObjectiveStatesForTest objective : ObjectiveStatesForTest.values())
        {
            gateway.createRow(objective.getPlayerID(), objective.getQuestID(),
                    objective.getObjectiveID(), objective.getState(),
                    objective.isNeedingNotification());

            bar.update();
        }
    }

    private static void createQuestStateTable() throws DatabaseException
    {
        System.out.println("Building the QuestState Table");
        QuestStateTableDataGateway questStateGateway = QuestStateTableDataGateway.getSingleton();
        questStateGateway.createTable();

        ProgressBar bar = new ProgressBar(QuestStatesForTest.values().length);
        for (QuestStatesForTest quest : QuestStatesForTest.values())
        {
            questStateGateway.createRow(quest.getPlayerID(), quest.getQuestID(),
                    quest.getState(), quest.isNeedingNotification());

            bar.update();
        }
    }
}
