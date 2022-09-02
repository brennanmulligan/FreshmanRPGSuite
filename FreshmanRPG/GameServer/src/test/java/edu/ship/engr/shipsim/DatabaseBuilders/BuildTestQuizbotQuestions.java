package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.NPCQuestionRowDataGateway;
import edu.ship.engr.shipsim.datatypes.NPCQuestionsForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Question portion of the database for the quizbot
 *
 * @author Merlin
 */
public class BuildTestQuizbotQuestions
{
    /**
     * @param args unused
     * @throws DatabaseException shouldn't
     * @throws SQLException      shouldn't
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createNPCQuestionTable();
    }

    /**
     * Create a table of test questions
     *
     * @throws DatabaseException if we can't build the table
     */
    public static void createNPCQuestionTable() throws DatabaseException
    {
        System.out.println("Building the NPCQuestion Table");
        NPCQuestionRowDataGateway.createTable();

        ProgressBar bar = new ProgressBar(NPCQuestionsForTest.values().length);
        for (NPCQuestionsForTest question : NPCQuestionsForTest.values())
        {
            new NPCQuestionRowDataGateway(question.getQ(), question.getA(), question.getStartDate(), question.getEndDate());

            bar.update();
        }
    }
}
