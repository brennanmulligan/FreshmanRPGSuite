package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.RandomFactsTableDataGateway;
import edu.ship.engr.shipsim.datatypes.RandomFactsForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Question portion of the database for the quizbot
 *
 * @author Merlin
 */
public class BuildTestRandomFacts
{
    /**
     * @param args unused
     * @throws DatabaseException shouldn't
     * @throws SQLException      shouldn't
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createRandomFactTable();
    }

    /**
     * Create a table of random Facts
     *
     * @throws DatabaseException if we can't build the table
     */
    public static void createRandomFactTable() throws DatabaseException
    {
        System.out.println("Building the RandomFact Table");
        RandomFactsTableDataGateway.createTable();

        ProgressBar bar = new ProgressBar(RandomFactsForTest.values().length);
        for (RandomFactsForTest fact : RandomFactsForTest.values())
        {
            RandomFactsTableDataGateway.createRow(fact.getFactText(), fact.getNpcID(), fact.getStartDate(), fact.getEndDate());

            bar.update();
        }
    }
}
