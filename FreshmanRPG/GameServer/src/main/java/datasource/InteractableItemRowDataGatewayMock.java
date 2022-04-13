package datasource;

import java.util.HashMap;

import criteria.InteractableItemActionParameter;
import dataENUM.InteractableItemActionType;
import datatypes.InteractableItemsForTest;
import datatypes.Position;

/**
 * Mock for row data gateway
 * @author Jake Moore, Lis Ostrow
 */
public class InteractableItemRowDataGatewayMock implements InteractableItemRowDataGateway
{
	/**
	 * Row Object
	 * @author Jake Moore, Lis Ostrow
	 */
	private class MockInteractableItemRow
	{
		String name;
		Position position;
		InteractableItemActionType actionType;
		InteractableItemActionParameter actionParam;
		String mapName;

		/**
		 * Constructor
		 * @param name - the name of the item
		 * @param row - the row of the item
		 * @param col - the col of the item
		 * @param action - the actionType of the item
		 * @param actionParam - the actionParameter of the item
		 * @param mapName - the map the item exists on
		 */
		public MockInteractableItemRow(String name, int row, int col, InteractableItemActionType action, InteractableItemActionParameter actionParam, String mapName)
		{
			this.name = name;
			this.position = new Position(row, col);
			this.actionType = action;
			this.actionParam = actionParam;
			this.mapName = mapName;
		}
	}

	/**
	 * Instance Vars
	 */
	private int itemID;
	private MockInteractableItemRow interactableItem;
	private static HashMap<Integer, MockInteractableItemRow> mockItemTable;

	/**
	 * Finder Constructor
	 * @param itemID - the id of the item
	 * @throws DatabaseException - if there was a database error
	 */
	public InteractableItemRowDataGatewayMock(int itemID) throws DatabaseException
	{
		this.itemID = itemID;

		if (mockItemTable == null)
		{
			this.resetData();
		}

		interactableItem = mockItemTable.get(itemID);
		if (interactableItem == null)
		{
			throw new DatabaseException("Item not found for id " + itemID);
		}
	}

	/**
	 * Creation Constructor
	 * @param name - the name of the item
	 * @param pos - the position of the item
	 * @param action - the action of the item
	 * @param actionParam - the actionParameter of the item
	 * @param mapName - the name of the map which the item is located
	 */
	public InteractableItemRowDataGatewayMock(String name, Position pos, InteractableItemActionType action, InteractableItemActionParameter actionParam, String mapName)
	{
		if (mockItemTable == null)
		{
			this.resetData();
		}
		this.interactableItem = new MockInteractableItemRow(name, pos.getRow(), pos.getColumn(), action, actionParam, mapName);
		persist();
	}

	/**
	 * Gets item id
	 */
	@Override
	public int getItemID()
	{
		return this.itemID;
	}

	/**
	 * Gets name
	 */
	@Override
	public String getName()
	{
		return this.interactableItem.name;
	}

	/**
	 * Sets name
	 */
	@Override
	public void setName(String name)
	{
		this.interactableItem.name = name;
	}

	/**
	 * Gets position
	 */
	@Override
	public Position getPosition()
	{
		return this.interactableItem.position;
	}

	/**
	 * Sets position
	 */
	@Override
	public void setPosition(Position pos)
	{
		this.interactableItem.position = pos;
	}

	/**
	 * Gets action
	 * @return actionType
	 */
	@Override
	public InteractableItemActionType getActionType()
	{
		return this.interactableItem.actionType;
	}

	/**
	 * Sets action
	 * @param action - the actionType the item should become.
	 */
	@Override
	public void setActionType(InteractableItemActionType action)
	{
		this.interactableItem.actionType = action;
	}

	/**
	 * Gets map name
	 * @return mapName
	 */
	@Override
	public String getMapName()
	{
		return this.interactableItem.mapName;
	}

	/**
	 * Sets map name
	 * @param mapName - the name of the map
	 */
	@Override
	public void setMapName(String mapName)
	{
		this.interactableItem.mapName = mapName;
	}

	/**
	 * Deletes item
	 */
	@Override
	public void delete()
	{
		mockItemTable.remove(this.itemID);
	}

	/**
	 * Resets data
	 */
	@Override
	public void resetData()
	{
		mockItemTable = new HashMap<>();
		for (InteractableItemsForTest p : InteractableItemsForTest.values())
		{
			mockItemTable.put(p.getItemID(), new MockInteractableItemRow(p.getName(), p.getPosition().getRow(), p.getPosition().getColumn(), p.getActionType(), p.getActionParam(), p.getMapName()));
		}
	}

	/**
	 * persists to database
	 */
	@Override
	public void persist()
	{
		mockItemTable.put(this.itemID, interactableItem);
	}

	/**
	 * Gets the actionParam
	 * @return actionParam
	 */
	@Override
	public InteractableItemActionParameter getActionParam()
	{
		return this.interactableItem.actionParam;
	}

	/**
	 * Sets the actionParam
	 */
	@Override
	public void setActionParam(InteractableItemActionParameter param)
	{
		this.interactableItem.actionParam = param;
	}


}
