package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import dataDTO.InteractableItemDTO;
import datatypes.InteractableItemsForTest;

/**
 * @author Jake Moore (I think)
 */
public abstract class InteractableItemTableDataGatewayTest extends DatabaseTest
{

	// gateway instance
	private InteractableItemTableDataGateway gateway;

	/**
	 * Make sure any static information is cleaned up between tests
	 *
	 * @throws SQLException
	 *             shouldn't
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetTableGateway();
		}
	}

	/**
	 * @return the gateway we should test
	 * @throws DatabaseException
	 *             if an error occurs
	 */
	public abstract InteractableItemTableDataGateway getGatewaySingleton() throws DatabaseException;

	/**
	 * See that retrieved gateway is always the referencing the same instance
	 *
	 * @throws DatabaseException
	 *             if an error occurs
	 */
	@Test
	public void isASingleton() throws DatabaseException
	{
		InteractableItemTableDataGateway x = getGatewaySingleton();
		InteractableItemTableDataGateway y = getGatewaySingleton();
		assertSame(x, y);
		assertNotNull(x);
	}

	/**
	 * test that we can get all interactable items in the same map
	 *
	 * @throws DatabaseException
	 *             if an error occurs
	 */
	@Test
	public void testItemsForMap() throws DatabaseException
	{
		InteractableItemTableDataGateway gateway = getGatewaySingleton();
		ArrayList<InteractableItemDTO> itemList = gateway.getItemsOnMap("quad.tmx");

		assertEquals(itemList.get(0).getId(), InteractableItemsForTest.BOOK.getItemID());
		assertEquals(itemList.get(1).getId(), InteractableItemsForTest.CHEST.getItemID());

	}

	/**
	 * Test get all items
	 *
	 * @throws DatabaseException
	 *             if an error occurs
	 */
	@Test
	public void testGetAllItems() throws DatabaseException
	{
		InteractableItemTableDataGateway gateway = getGatewaySingleton();
		ArrayList<InteractableItemDTO> itemList = gateway.getAllItems();

		assertEquals(itemList.get(0).getId(), InteractableItemsForTest.BOOK.getItemID());
		assertEquals(itemList.get(1).getId(), InteractableItemsForTest.CHEST.getItemID());
	}
}
