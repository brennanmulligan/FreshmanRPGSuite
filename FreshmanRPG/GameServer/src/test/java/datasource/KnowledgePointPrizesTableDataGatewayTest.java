package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import dataDTO.KnowledgePointPrizeDTO;
import datatypes.KnowledgePointPrizesForTest;

/**
 * @author Mina Kindo, Christian Chroutamel
 *
 */
public abstract class KnowledgePointPrizesTableDataGatewayTest extends DatabaseTest
{

	// gateway instance
	private KnowledgePointPrizesTableDataGateway gateway;

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
	public abstract KnowledgePointPrizesTableDataGateway getGatewaySingleton() throws DatabaseException;

	/**
	 *
	 * @throws DatabaseException
	 */
	@Test
	public void isSingleton() throws DatabaseException
	{
		KnowledgePointPrizesTableDataGateway obj1 = getGatewaySingleton();
		KnowledgePointPrizesTableDataGateway obj2 = getGatewaySingleton();
		assertSame(obj1, obj2);
		assertNotNull(obj2);
	}

	/**
	 * Test get all prizes
	 *
	 * @throws DatabaseException
	 */
	@Test
	public void testGetAllKnowledgePrizes() throws DatabaseException
	{
		KnowledgePointPrizesTableDataGateway gateway = getGatewaySingleton();
		ArrayList<KnowledgePointPrizeDTO> itemListFromGateway = gateway.getAllKnowledgePointPrizes();
		ArrayList<KnowledgePointPrizeDTO> itemListFromEnum = new ArrayList<>();
		KnowledgePointPrizesForTest[] kpp = KnowledgePointPrizesForTest.values();

		//Make DTOs of the stuff in the enum
		for (int i = 0; i < kpp.length; i++)
		{
			itemListFromEnum.add(new KnowledgePointPrizeDTO(kpp[i].getName(), kpp[i].getCost(), kpp[i].getDescription()));
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
