package DatabaseBuilders;

import criteria.CriteriaStringDTO;
import criteria.ObjectiveCompletionCriteria;
import dataENUM.ObjectiveCompletionType;
import datasource.DatabaseException;
import datasource.ObjectiveTableDataGateway;
import datasource.QuestRowDataGateway;
import datatypes.ObjectivesForProduction;
import datatypes.QuestsForProduction;
import model.OptionsManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Builds the Quests and Objectives portion of the database
 *
 * @author Merlin
 */
public class AddProductionObjectives
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
                    quest.getStartDate(), quest.getEndDate());
        }
        System.out.println();
    }

    /**
     * @param args unused
     * @throws DatabaseException shouldn't
     * @throws SQLException      shouldn't
     */
    public static void main(String[] args) throws DatabaseException, SQLException, FileNotFoundException {
        OptionsManager.getSingleton().setUsingTestDB(false);
        System.out.println("adding objectives");
        addObjectivesFromFile("ObjectivesToAdd.csv");
 //       System.out.println("triggering the objectives");
//        createQuestTable();
    }

    private static void addObjectivesFromFile(String fileTitle) throws FileNotFoundException, DatabaseException {
        Scanner file = new Scanner(new File(fileTitle));

        while (file.hasNext()) {
            String line = file.nextLine();
            String[] parts = line.split(",");
            ObjectiveTableDataGateway.createRow(Integer.parseInt(parts[4]),"Find The real life " + parts[0] + " and scan the QR Code with the game's app",
                    Integer.parseInt(parts[3]),3, ObjectiveCompletionType.REAL_LIFE, new CriteriaStringDTO("Unused"));
        }
        file.close();
    }
}
