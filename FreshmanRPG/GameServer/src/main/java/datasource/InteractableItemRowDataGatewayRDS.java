package datasource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.*;

import criteria.InteractableItemActionParameter;
import dataDTO.InteractableItemDTO;
import dataENUM.InteractableItemActionType;
import datatypes.Position;

/**
 * Row Data Gateway for Interactable Item
 *
 * @author Jake Moore, Elisabeth Ostrow
 */
public class InteractableItemRowDataGatewayRDS implements InteractableItemRowDataGateway
{

	/**
	 * Creates table
	 *
	 * @throws DatabaseException if it cant create or drop table
	 */
	public static void createTable() throws DatabaseException
	{
		String drop = "DROP TABLE IF EXISTS InteractableItems";
		String create = "CREATE TABLE InteractableItems (" + "itemID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " + "name VARCHAR(30) NOT NULL, " + "xPosition INT NOT NULL, "
				+ "yPosition INT NOT NULL, " + "actionType INT NOT NULL, " + "actionParam BLOB NOT NULL, " + "mapName VARCHAR(30) NOT NULL)";

		Connection conn = DatabaseManager.getSingleton().getConnection();

		try
		{
			// drop table
			PreparedStatement stmt;
			stmt = conn.prepareStatement(drop);
			stmt.execute();
			stmt.close();

			// create table
			stmt = conn.prepareStatement(create);
			stmt.execute();
			stmt.close();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Unable to create InteractableItem table", e);
		}
	}

	private Connection connection;

	private int itemID;
	private String name;
	private Position position;
	private InteractableItemActionType actionType;
	private InteractableItemActionParameter actionParam;
	private String mapName;

	/**
	 * Finder constructor
	 *
	 * @param itemID - the item ID
	 * @throws DatabaseException if item not found
	 */
	public InteractableItemRowDataGatewayRDS(int itemID) throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();
		this.itemID = itemID;
		this.findById(itemID);
	}

	/**
	 * Finder for finder constructor
	 *
	 * @param itemID
	 * @throws DatabaseException
	 */
	private void findById(int itemID) throws DatabaseException
	{
		String query = "SELECT * FROM InteractableItems WHERE itemID = " + itemID;
		try
		{
			PreparedStatement stmt =  this.connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			this.name = rs.getString("name");
			int x = rs.getInt("xPosition");
			int y = rs.getInt("yPosition");
			this.position = new Position(x, y);
			this.actionType = InteractableItemActionType.findById(rs.getInt("actionType"));
			this.actionParam = this.getParam(rs.getObject("actionParam"), this.actionType);
			this.mapName = rs.getString("mapName");
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldn't find item with specified ID", e);
		}
	}

	/**
	 * Creation Constructor
	 *
	 * @param name - the name
	 * @param pos - the position
	 * @param actionType - the actionType
	 * @param actionParam - the actionParam
	 * @param mapName - the mapName
	 * @throws DatabaseException if creation failed or id not found
	 */
	public InteractableItemRowDataGatewayRDS(String name, Position pos, InteractableItemActionType actionType, InteractableItemActionParameter actionParam, String mapName)
			throws DatabaseException
	{
		this.connection = DatabaseManager.getSingleton().getConnection();
		this.name = name;
		this.position = pos;
		this.actionType = actionType;
		this.actionParam = actionParam;
		this.mapName = mapName;
		this.create(name, pos, actionType, actionParam, mapName);
	}

	/**
	 * Create for Creation Constructor
	 *
	 * @param name
	 * @param pos
	 * @param actionType
	 * @param mapName
	 * @throws DatabaseException if item not created
	 */
	private void create(String name, Position pos, InteractableItemActionType actionType, InteractableItemActionParameter actionParam, String mapName) throws DatabaseException
	{
		String query = "INSERT INTO InteractableItems " + "SET name = ?, xPosition = ?, yPosition = ?, actionType = ?, actionParam = ?, mapName = ?";
		try
		{
			PreparedStatement stmt =  this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

			stmt.setString(1, name);
			stmt.setInt(2, pos.getRow());
			stmt.setInt(3, pos.getColumn());
			stmt.setInt(4, actionType.getId());
			stmt.setObject(5, actionParam);
			stmt.setString(6, mapName);

			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next())
			{
				this.itemID = rs.getInt(1);
			}
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Generated Key for itemID not found or creation of row unsuccessful", e);
		}
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see datasource.InteractableItemRowDataGateway#delete()
	 */
	@Override
	public void delete() throws DatabaseException
	{
		String query = "DELETE FROM InteractableItems WHERE itemID = ?";
		try
		{
			PreparedStatement stmt =  this.connection.prepareStatement(query);
			stmt.setInt(1, this.itemID);
			stmt.execute();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldnt delete Item", e);
		}
	}

	/**
	 * @see datasource.InteractableItemRowDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
	}

	/**
	 * @see datasource.InteractableItemRowDataGateway#persist()
	 */
	@Override
	public void persist() throws DatabaseException
	{
		String query = "UPDATE InteractableItems " + "SET name = ?, xPosition = ?, yPosition = ?, actionType = ?, actionParam = ?, mapName = ? " + "WHERE itemID = ?";

		try
		{
			PreparedStatement stmt =  this.connection.prepareStatement(query);

			stmt.setString(1, this.name);
			stmt.setInt(2, this.position.getRow());
			stmt.setInt(3, this.position.getColumn());
			stmt.setInt(4, this.actionType.getId());
			stmt.setObject(5, this.actionParam);
			stmt.setString(6, this.mapName);
			stmt.setInt(7, this.itemID);

			stmt.executeUpdate();
		}
		catch (SQLException e)
		{
			throw new DatabaseException("Couldnt persist interactable item info", e);
		}
	}

	/**
	 * @see InteractableItemRowDataGateway#getItemID()
	 */
	@Override
	public int getItemID()
	{
		return this.itemID;
	}

	/**
	 * @see InteractableItemRowDataGateway#getName()
	 */
	@Override
	public String getName()
	{
		return this.name;
	}

	/**
	 * @see InteractableItemRowDataGateway#setName(java.lang.String)
	 */
	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @see InteractableItemRowDataGateway#getPosition()
	 */
	@Override
	public Position getPosition()
	{
		return this.position;
	}

	/**
	 * @see InteractableItemRowDataGateway#setPosition(Position)
	 */
	@Override
	public void setPosition(Position pos)
	{
		this.position = pos;
	}

	/**
	 * @see datasource.InteractableItemRowDataGateway#getActionType()
	 */
	@Override
	public InteractableItemActionType getActionType()
	{
		return this.actionType;
	}

	/**
	 * @see InteractableItemRowDataGateway#setActionType(dataENUM.InteractableItemActionType)
	 */
	@Override
	public void setActionType(InteractableItemActionType action)
	{
		this.actionType = action;
	}

	/**
	 * @see datasource.InteractableItemRowDataGateway#getActionParam()
	 */
	@Override
	public InteractableItemActionParameter getActionParam()
	{
		return this.actionParam;
	}

	/**
	 * @see datasource.InteractableItemRowDataGateway#setActionParam(criteria.InteractableItemActionParameter)
	 */
	@Override
	public void setActionParam(InteractableItemActionParameter param)
	{
		this.actionParam = param;
	}

	/**
	 * @see datasource.InteractableItemRowDataGateway#getMapName()
	 */
	@Override
	public String getMapName()
	{
		return this.mapName;
	}

	/**
	 * @see datasource.InteractableItemRowDataGateway#setMapName(java.lang.String)
	 */
	@Override
	public void setMapName(String mapName)
	{
		this.mapName = mapName;
	}

	/**
	 * Converts Blob to an action param
	 *
	 * @param blob
	 * @param actionType
	 * @return InteractableItemActionParameter
	 * @throws DatabaseException
	 */
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
			e.printStackTrace();
			throw new DatabaseException("Could not convert blob to InteractableItemActionParameter");
		}
	}


	/**
	 * Returns the DTO of the Interactable Object
	 * @return the data transfer object
	 * @author Jordan Long
	 */
	public InteractableItemDTO getObjectInfo()
	{
		InteractableItemDTO dto = new InteractableItemDTO(itemID, name, position, actionType, actionParam, mapName);
		return dto;
	}


}
