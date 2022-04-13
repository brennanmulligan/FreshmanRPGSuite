package datasource;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dataDTO.RandomFactDTO;
import datatypes.PlayersForTest;
import datatypes.RandomFactsForTest;

/**
 * @author merlin
 *
 */
public abstract class RandomFactsTableDataGatewayTest extends DatabaseTest
{

	private RandomFactsTableDataGateway gateway;

	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
		gateway = getGateway();
	}

	/**
	 * @see datasource.DatabaseTest#tearDown()
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
	}

	/**
	 * @return the gateway the tests should be run on
	 */
	public abstract RandomFactsTableDataGateway getGateway();


	/**
	 * Make sure we can get all of the facts for a particular NPC
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void canRetrieveAllForOneNPC() throws DatabaseException
	{
		ArrayList<RandomFactDTO> result = gateway.getAllFactsForNPC(PlayersForTest.RANDOM_FACTS_NPC_1.getPlayerID());
		for (RandomFactsForTest fact : RandomFactsForTest.values())
		{
			if (fact.getNpcID() == PlayersForTest.RANDOM_FACTS_NPC_1.getPlayerID())
			{
				assertTrue(result.contains(fact.getDTO()));
			}
			else
			{
				assertFalse(result.contains(fact.getDTO()));
			}
		}
	}

	/**
	 *
	 */
	@Test
	public void isASingleton()
	{
		RandomFactsTableDataGateway x = getGateway();
		assertNotNull(x);
		assertSame(x, getGateway());
	}

}
