package DatabaseBuilders;

import datasource.DatabaseException;
import datasource.InteractableItemRowDataGateway;
import datatypes.InteractableItemsForTest;
import model.OptionsManager;

/**
 * Builds InteractableItemsForTest
 * @author Jake Moore, Elisabeth Ostrow
 */
public class BuildProductionInteractableItems
{
	/**
	 * Main
	 * @param args unused
	 * @throws DatabaseException shouldnt
	 */
	public static void main(String[] args) throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingTestDB(false);
		createItemTable();
	}

	/**
	 * Create table of levels
	 * @throws DatabaseException shouldnt
	 */
	private static void createItemTable() throws DatabaseException
	{
		InteractableItemRowDataGateway.createTable();
		for (InteractableItemsForTest item : InteractableItemsForTest.values())
		{
			new InteractableItemRowDataGateway(item.getName(), item.getPosition(), item.getActionType(), item.getActionParam(), item.getMapName());
		}
	}

}
