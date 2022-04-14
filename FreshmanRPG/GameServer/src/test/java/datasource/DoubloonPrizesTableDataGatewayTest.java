package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import dataDTO.DoubloonPrizeDTO;
import datatypes.DoubloonPrizesForTest;

/**
 * @author Mina Kindo, Christian Chroutamel
 *
 */
public abstract class DoubloonPrizesTableDataGatewayTest extends DatabaseTest
{

	// gateway instance
	private DoubloonPrizesTableDataGateway gateway;

	/**
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 *
	 * @return singleton
	 * @throws DatabaseException
	 */
	public abstract DoubloonPrizesTableDataGateway getGatewaySingleton() throws DatabaseException;

	/**
	 *
	 * @throws DatabaseException
	 */
	@Test
	public void isSingleton() throws DatabaseException
	{
		DoubloonPrizesTableDataGateway obj1 = getGatewaySingleton();
		DoubloonPrizesTableDataGateway obj2 = getGatewaySingleton();
		assertSame(obj1, obj2);
		assertNotNull(obj2);
	}

	/**
	 * Test get all prizes
	 *
	 * @throws DatabaseException
	 */
	@Test
	public void testGetAllDoubloonPrizes() throws DatabaseException
	{
		DoubloonPrizesTableDataGateway gateway = getGatewaySingleton();
		ArrayList<DoubloonPrizeDTO> itemListFromGateway = gateway.getAllDoubloonPrizes();
		ArrayList<DoubloonPrizeDTO> itemListFromEnum = new ArrayList<>();
		DoubloonPrizesForTest[] prizes = DoubloonPrizesForTest.values();

		//Make DTOs of the stuff in the enum
		for (int i = 0; i < prizes.length; i++)
		{
			itemListFromEnum.add(new DoubloonPrizeDTO(prizes[i].getName(), prizes[i].getCost(), prizes[i].getDescription()));
		}

		//Tests that the gateway has the same amount of values as in the data source
		assertEquals(itemListFromEnum.size(), itemListFromGateway.size());

		//make sure the DTO list we get from the gateway has the correct information
		for (int i = 0; i < itemListFromGateway.size(); i++)
		{
			assertTrue(itemListFromGateway.contains(itemListFromEnum.get(i)));
		}
	}
}
