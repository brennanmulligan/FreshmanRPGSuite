package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.NPCQuestionRowDataGateway;
import model.OptionsManager;
import datatypes.NPCQuestionsForTest;

/**
 * Builds the Question portion of the database for the quizbot
 *
 * @author Merlin
 *
 */
public class BuildTestQuestions
{

	/**
	 *
	 * @param args
	 *            unused
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws SQLException
	 *             shouldn't
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton().setUsingTestDB(true);
		createQuestionTable();
	}

	/**
	 * Create a table of test questions
	 *
	 * @throws DatabaseException
	 *             if we can't build the table
	 */
	public static void createQuestionTable() throws DatabaseException
	{
		NPCQuestionRowDataGateway.createTable();
		for (NPCQuestionsForTest question : NPCQuestionsForTest.values())
		{
			new NPCQuestionRowDataGateway(question.getQ(), question.getA(), question.getStartDate(),
					question.getEndDate());
		}
	}
}
