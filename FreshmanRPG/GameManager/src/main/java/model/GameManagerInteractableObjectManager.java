package model;

import java.util.ArrayList;

import criteria.InteractableItemActionParameter;
import dataDTO.InteractableItemDTO;
import dataENUM.InteractableItemActionType;
import datasource.DatabaseException;
import datasource.InteractableItemRowDataGateway;
import datasource.InteractableItemRowDataGatewayMock;
import datasource.InteractableItemRowDataGatewayRDS;
import datasource.InteractableItemTableDataGateway;
import datasource.InteractableItemTableDataGatewayMock;
import datasource.InteractableItemTableDataGatewayRDS;
import datatypes.Position;
import model.reports.ObjectListReport;

/**
 * This manager handles operations pertaining to data on interactable objects
 * @author Ben Uleau, Abe Loscher, Chris Boyer
 *
 */
public class GameManagerInteractableObjectManager
{

	private static GameManagerInteractableObjectManager instance;

	/**
	 * @return the only instance of this in the system
	 */
	protected static GameManagerInteractableObjectManager getInstance()
	{
		if (instance == null)
		{
			instance = new GameManagerInteractableObjectManager();
		}

		return instance;
	}

	private GameManagerInteractableObjectManager()
	{
	}

	/**
	 * for testing purposes
	 */
	static void resetSingleton()
	{
		instance = null;
	}

	/**
	 * @return the list of objects we are managing
	 * @throws DatabaseException shouldn't
	 */
	protected ArrayList<InteractableItemDTO> getObjects() throws DatabaseException
	{
		return getItemTableDataGateway().getAllItems();
	}


	private InteractableItemTableDataGateway getItemTableDataGateway() throws DatabaseException
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return InteractableItemTableDataGatewayMock.getInstance();
		}
		return InteractableItemTableDataGatewayRDS.getInstance();
	}

	/**
	 * @return the number of objects we are managing
	 * @throws DatabaseException if we can't access the db
	 */
	protected int getNumberOfObjects() throws DatabaseException
	{
		return getObjects().size();
	}

	/**
	 * Add a new object to the db
	 * @param name the object's name
	 * @param mapName the map the object is on
	 * @param position the row/column the object is in
	 * @param actionType the type of action that should occur when the object is touched
	 * @param actionParam details the action that will occur when the object is touch
	 * @return a description of the object
	 * @throws DatabaseException if we can't store it
	 */
	protected InteractableItemDTO addObject(String name, String mapName, Position position,
											InteractableItemActionType actionType, InteractableItemActionParameter actionParam) throws DatabaseException
	{
		InteractableItemDTO dto = null;
		boolean alreadyPresent = false;

		ArrayList<InteractableItemDTO> objects = this.getObjects();

		for (InteractableItemDTO o : objects)
		{
			if (o.equals(new InteractableItemDTO(name, position, actionType, actionParam, mapName)))
			{
				alreadyPresent = true;
			}
		}
		if (!alreadyPresent)
		{
			InteractableItemRowDataGatewayRDS gateway = new InteractableItemRowDataGatewayRDS(name, position, actionType, actionParam, mapName);
			dto = gateway.getObjectInfo();
			objects.add(dto);
		}

		refreshObjectList();
		return dto;
	}


	/**
	 * Resends the list of objects
	 * @throws DatabaseException shouldn't
	 */
	public void refreshObjectList() throws DatabaseException
	{
		ArrayList<InteractableItemDTO> list = this.getObjects();
		ObjectListReport report = new ObjectListReport(list);
		QualifiedObservableConnector.getSingleton().sendReport(report);
	}

	/**
	 * deletes an interactable object based on its ID 
	 * @param ID - id of the objects
	 * @return - true or false 
	 * @throws DatabaseException- shouldn't
	 */
	public boolean removeObjects(int ID) throws DatabaseException
	{
		try
		{
			InteractableItemRowDataGateway itemGateway = getItemRowDataGateway(ID);
			itemGateway.delete();
			refreshObjectList();
			return true;
		}
		catch (DatabaseException e)
		{
			return false;
		}

	}

	private InteractableItemRowDataGateway getItemRowDataGateway(int id) throws DatabaseException
	{
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			return new InteractableItemRowDataGatewayMock(id);
		}
		return new InteractableItemRowDataGatewayRDS(id);
	}

	/**
	 * Edit an interactable item
	 * @param dto - a dto that holds the information for an interactable object
	 * @return true if update was successful
	 */
	protected boolean updateItem(InteractableItemDTO dto)
	{
		try
		{
			InteractableItemRowDataGateway itemGateway = getItemRowDataGateway(dto.getId());
			itemGateway.setActionParam(dto.getActionParam());
			itemGateway.setActionType(dto.getActionType());
			itemGateway.setMapName(dto.getMapName());
			itemGateway.setName(dto.getName());
			itemGateway.setPosition(dto.getPosition());
			itemGateway.persist();

			ArrayList<InteractableItemDTO> list = this.getObjects();

			QualifiedObservableConnector.getSingleton().sendReport(new ObjectListReport(list));
			return true;
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
			return false;
		}
	}

}
