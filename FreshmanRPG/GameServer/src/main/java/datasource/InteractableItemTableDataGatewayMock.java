package datasource;

import java.util.ArrayList;
import java.util.HashMap;

import dataDTO.InteractableItemDTO;
import datatypes.InteractableItemsForTest;

/**
 * A mock item gateway for tests
 *
 * @author Elisabeth Ostrow, Jake Moore
 */

public class InteractableItemTableDataGatewayMock extends InteractableItemTableDataGateway
{

	// singleton instance
	private static InteractableItemTableDataGatewayMock singleton = null;

	/**
	 * gets singleton instance
	 *
	 * @return singleton
	 */
	public static synchronized InteractableItemTableDataGatewayMock getInstance()
	{
		if (singleton == null)
		{
			singleton = new InteractableItemTableDataGatewayMock();
		}
		return singleton;
	}

	private InteractableItemTableDataGatewayMock()
	{
		this.resetData();
	}

	/**
	 * holds the data we are pretending to access
	 */
	private HashMap<Integer, InteractableItemDTO> mockItemTable = new HashMap<>();

	/**
	 * @see datasource.InteractableItemTableDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		this.mockItemTable = new HashMap<>();

		for (InteractableItemsForTest item : InteractableItemsForTest.values())
		{
			this.mockItemTable.put(item.getItemID(), new InteractableItemDTO(item.getItemID(), item.getName(), item.getPosition(), item.getActionType(), item.getActionParam(), item
					.getMapName()));
		}
	}

	/**
	 * @param mapName the mapName
	 * @return ArrayList<InteractableItemDTO> of items in specified map name
	 */
	@Override
	public ArrayList<InteractableItemDTO> getItemsOnMap(String mapName)
	{
		ArrayList<InteractableItemDTO> result = new ArrayList<>();
		for (InteractableItemDTO item : this.mockItemTable.values())
		{
			if (item.getMapName() == mapName)
			{
				result.add(item);
			}
		}
		return result;
	}

	/**
	 * @see datasource.InteractableItemTableDataGateway#getAllItems()
	 */
	@Override
	public ArrayList<InteractableItemDTO> getAllItems()
	{
		ArrayList<InteractableItemDTO> result = new ArrayList<>();
		result.addAll(this.mockItemTable.values());
		return result;
	}

}
