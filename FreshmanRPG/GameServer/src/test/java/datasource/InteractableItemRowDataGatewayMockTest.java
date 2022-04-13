package datasource;

import criteria.InteractableItemActionParameter;
import dataENUM.InteractableItemActionType;
import datatypes.Position;

/**
 * Tests for InteractableItemGatewayMock
 * @author Elisabeth Ostrow, Jake Moore
 */
public class InteractableItemRowDataGatewayMockTest extends InteractableItemRowDataGatewayTest
{
	/**
	 * Finder Constructor
	 * @param itemID
	 * @return InteractableItemGateway
	 * @throws DatabaseException
	 */
	@Override
	InteractableItemRowDataGateway findGateway(int itemID) throws DatabaseException
	{
		return new InteractableItemRowDataGatewayMock(itemID);
	}

	/**
	 * Creation Constructor
	 * @param name
	 * @param pos
	 * @param action
	 * @return InteractableItemGateway
	 */
	@Override
	InteractableItemRowDataGateway createGateway(String name, Position pos, InteractableItemActionType actionType, InteractableItemActionParameter param, String mapName)
	{
		return new InteractableItemRowDataGatewayMock(name, pos, actionType, param, mapName);
	}

}
