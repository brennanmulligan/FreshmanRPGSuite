package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ObjectiveTableDataGateway;
import edu.ship.engr.shipsim.datasource.QuestRowDataGateway;
import edu.ship.engr.shipsim.datatypes.ObjectivesForProduction;
import edu.ship.engr.shipsim.datatypes.QuestsForProduction;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Quests and Objectives portion of the database
 *
 * @author Merlin
 */
public class BuildProductionQuestsAndObjectives
{
    /**
     * Create a table of objectives
     */
    private static void createObjectiveTable() throws DatabaseException
    {
        ObjectiveTableDataGateway.createTable();
        for (ObjectivesForProduction objective : ObjectivesForProduction.values())
        {
            ObjectiveTableDataGateway.createRow(objective.getObjectiveID(),
                    objective.getObjectiveDescription(), objective.getQuestID(),
                    objective.getExperiencePointsGained(), objective.getCompletionType(),
                    objective.getCompletionCriteria());
        }
    }

    /**
     * Create a table of quests
     */
    private static void createQuestTable() throws DatabaseException
    {
        QuestRowDataGateway.createTable();
        for (QuestsForProduction quest : QuestsForProduction.values())
        {
            System.out.print(quest.getQuestID() + " ");
            new QuestRowDataGateway(quest.getQuestID(), quest.getQuestTitle(),
                    quest.getQuestDescription(), quest.getMapName(), quest.getPosition(),
                    quest.getExperienceGained(), quest.getObjectiveForFulfillment(),
                    quest.getCompletionActionType(), quest.getCompletionActionParameter(),
                    quest.getStartDate(), quest.getEndDate(), quest.isEasterEgg());
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
        OptionsManager.getSingleton().setUsingTestDB(false);
        System.out.println("creating objective table");
        createObjectiveTable();
        System.out.println("creating quest table");
        createQuestTable();
    }
}
