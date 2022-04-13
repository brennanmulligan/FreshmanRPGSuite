package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.InteractableItemDTO;
import datasource.DatabaseException;
import datasource.InteractableItemTableDataGatewayMock;
import datatypes.InteractableItemsForTest;

/**
 * Tests for the GameManagerInteractableObjectManager class
 *
 * @author Ben Uleau and Abe Loscher
 */
public class GameManagerInteractableObjectManagerTest
{
	/**
	 * Resets the data of any changes that occured
	 */
	@Before
	public void setUp()
	{
		GameManagerInteractableObjectManager.resetSingleton();
	}

	/**
	 * Test that the manager correctly initialized
	 */
	@Test
	public void testInteractableObjectManagerIntitialization()
	{
		GameManagerInteractableObjectManager manager = GameManagerInteractableObjectManager.getInstance();
		assertNotNull(manager);
	}

	/**
	 * Tests that we can get the full list of interactable objects from the database
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testGetAllInteractableObjects() throws DatabaseException
	{
		ArrayList<InteractableItemDTO> list = GameManagerInteractableObjectManager.getInstance().getObjects();

		ArrayList<InteractableItemDTO> gatewayList = InteractableItemTableDataGatewayMock.getInstance().getAllItems();

		/*
		 * assertEquals(InteractableItemsForTest.values().length, list.size());
		 * InteractableItemDTO dto = list.get(InteractableItemsForTest.BOOK.ordinal());
		 * assertEquals(InteractableItemsForTest.BOOK.getItemID(), dto.getId());
		 * assertEquals(InteractableItemsForTest.BOOK.getActionType(),
		 * dto.getActionType());
		 * assertEquals(InteractableItemsForTest.BOOK.getActionParam(),
		 * dto.getActionParam()); assertEquals(InteractableItemsForTest.BOOK.getName(),
		 * dto.getName()); assertEquals(InteractableItemsForTest.BOOK.getMapName(),
		 * dto.getMapName());
		 */

		assertEquals(gatewayList.size(), list.size());
		InteractableItemDTO dto = list.get(InteractableItemsForTest.BOOK.ordinal());
		assertEquals(InteractableItemsForTest.BOOK.getItemID(), dto.getId());
		assertEquals(InteractableItemsForTest.BOOK.getActionType(), dto.getActionType());
		assertEquals(InteractableItemsForTest.BOOK.getActionParam(), dto.getActionParam());
		assertEquals(InteractableItemsForTest.BOOK.getName(), dto.getName());
		assertEquals(InteractableItemsForTest.BOOK.getMapName(), dto.getMapName());

	}
}
