package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.RandomFactsTableDataGateway;
import model.OptionsManager;
import datatypes.RandomFactsForTest;

/**
 * Builds the Question portion of the database for the quizbot
 *
 * @author Merlin
 *
 */
public class BuildTestRandomFacts
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
		createRandomFactTable();
	}

	/**
	 * Create a table of random Facts
	 *
	 * @throws DatabaseException
	 *             if we can't build the table
	 */
	public static void createRandomFactTable() throws DatabaseException
	{
		RandomFactsTableDataGateway.createTable();
		for (RandomFactsForTest fact : RandomFactsForTest.values())
		{
			RandomFactsTableDataGateway.createRow(fact.getFactText(), fact.getNpcID(), fact.getStartDate(),
					fact.getEndDate());
		}
	}
}
