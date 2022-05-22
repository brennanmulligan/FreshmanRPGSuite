package datasource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import criteria.InteractableItemActionParameter;
import dataDTO.InteractableItemDTO;
import dataENUM.InteractableItemActionType;
import datatypes.Position;

/**
 * Allows access to the Interactable Items Table in the DB
 *
 * @author Elisabeth Ostrow, Jake Moore
 */
public class InteractableItemTableDataGatewayRDS implements InteractableItemTableDataGateway
{

	static TableDataGateway getGateway()
	{
		return new DefaultItemsTableDataGatewayRDS();
	}

	// connection
	private Connection connection;

	/**
	 * Private constructor does nothing
	 */
	private InteractableItemTableDataGatewayRDS()
	{
	}

	/**
	 * Does nothing
	 */
	@Override
	public void resetTableGateway()
	{
		// Does nothing

	}

	/**
	 * gets all items on a certain map
	 */
	@Override
	public ArrayList<InteractableItemDTO> getItemsOnMap(String mapName) throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();

		String query = "SELECT * FROM InteractableItems WHERE mapName = ?";

		try
		{
			PreparedStatement stmt =  this.connection.prepareStatement(query);

			stmt.setString(1, mapName);

			ResultSet rs = stmt.executeQuery();

			ArrayList<InteractableItemDTO> result = new ArrayList<>();
			while (rs.next())
			{
				InteractableItemDTO item = new InteractableItemDTO(rs.getInt("itemID"), rs.getString("name"), new Position(rs.getInt("xPosition"), rs.getInt("yPosition")),
						InteractableItemActionType.findById(rs.getInt("actionType")), this.getParam(rs.getObject("actionParam"), InteractableItemActionType.findById(rs
						.getInt("actionType"))), rs.getString("mapName"));
				result.add(item);
			}

			return result;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find any items", e);
		}
	}

	/**
	 * Gets all items in the database
	 *
	 * @return array list of all interactableitems
	 */
	@Override
	public ArrayList<InteractableItemDTO> getAllItems() throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();

		String query = "SELECT * FROM InteractableItems";

		try
		{
			PreparedStatement stmt =  this.connection.prepareStatement(query);

			ResultSet rs = stmt.executeQuery();

			ArrayList<InteractableItemDTO> result = new ArrayList<>();
			while (rs.next())
			{
				InteractableItemDTO item = new InteractableItemDTO(rs.getInt("itemID"), rs.getString("name"), new Position(rs.getInt("xPosition"), rs.getInt("yPosition")),
						InteractableItemActionType.findById(rs.getInt("actionType")), this.getParam(rs.getObject("actionParam"), InteractableItemActionType.findById(rs
						.getInt("actionType"))), rs.getString("mapName"));
				result.add(item);
			}

			return result;
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Failed to retrieve all items", e);
		}
	}

	protected InteractableItemActionParameter getParam(Object blob, InteractableItemActionType actionType) throws DatabaseException
	{
		try
		{
			Class<? extends InteractableItemActionParameter> actionParameterType = actionType.getActionParam();
			ByteArrayInputStream stream = new ByteArrayInputStream((byte[]) blob);
			Object x = new ObjectInputStream(stream).readObject();
			return actionParameterType.cast(x);
		}
		catch (ClassNotFoundException | IOException e)
		{
			throw new DatabaseException("Could not convert blob to InteractableItemActionParameter");
		}
	}

}
