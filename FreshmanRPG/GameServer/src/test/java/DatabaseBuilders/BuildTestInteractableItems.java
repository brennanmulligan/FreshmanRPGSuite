package DatabaseBuilders;

import datasource.DatabaseException;
import datasource.InteractableItemRowDataGatewayRDS;
import model.OptionsManager;
import datatypes.InteractableItemsForTest;

/**
 * Builds InteractableItemsForTest
 * @author Jake Moore, Elisabeth Ostrow
 */
public class BuildTestInteractableItems
{
	/**
	 * Main
	 * @param args unused
	 * @throws DatabaseException shouldnt
	 */
	public static void main(String[] args) throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createItemTable();
	}

	/**
	 * Create table of levels
	 * @throws DatabaseException shouldnt
	 */
	private static void createItemTable() throws DatabaseException
	{
		InteractableItemRowDataGatewayRDS.createTable();
		for (InteractableItemsForTest item : InteractableItemsForTest.values())
		{
			new InteractableItemRowDataGatewayRDS(item.getName(), item.getPosition(), item.getActionType(), item.getActionParam(), item.getMapName());
		}
	}

}
