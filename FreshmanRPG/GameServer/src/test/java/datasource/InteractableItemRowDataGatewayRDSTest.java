package datasource;

import org.junit.Test;

import criteria.InteractableItemActionParameter;
import dataENUM.InteractableItemActionType;
import datatypes.Position;
import datatypes.InteractableItemsForTest;

/**
 * Tests for InteractableItemGatewayRDS
 *
 * @author Jake Moore, Lis Ostrow
 */
public class InteractableItemRowDataGatewayRDSTest extends InteractableItemRowDataGatewayTest
{
	@Override
	InteractableItemRowDataGateway findGateway(int itemID) throws DatabaseException
	{
		return new InteractableItemRowDataGatewayRDS(itemID);
	}

	@Override
	InteractableItemRowDataGateway createGateway(String name, Position pos, InteractableItemActionType actionType, InteractableItemActionParameter actionParam, String mapName)
			throws DatabaseException
	{
		return new InteractableItemRowDataGatewayRDS(name, pos, actionType, actionParam, mapName);
	}

	/**
	 * @throws DatabaseException if an error happened
	 */
	@Test(expected = DatabaseException.class)
	public void testDeletesCorrectItem() throws DatabaseException
	{
		InteractableItemRowDataGateway firstItem = this.findGateway(InteractableItemsForTest.BOOK.getItemID());
		firstItem.delete();
		this.findGateway(InteractableItemsForTest.BOOK.getItemID());
	}

}
