package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.criteria.InteractableItemActionParameter;
import edu.ship.engr.shipsim.dataDTO.InteractableItemDTO;
import edu.ship.engr.shipsim.dataENUM.InteractableItemActionType;
import edu.ship.engr.shipsim.datatypes.Position;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Row Data Gateway for Interactable Item
 *
 * @author Jake Moore, Elisabeth Ostrow
 */
public class InteractableItemRowDataGateway
{

    private final Connection connection;
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
    public InteractableItemRowDataGateway(int itemID) throws DatabaseException
    {
        this.connection = DatabaseManager.getSingleton().getConnection();
        this.itemID = itemID;
        this.findById(itemID);
    }

    /**
     * Creation Constructor
     *
     * @param name        - the name
     * @param pos         - the position
     * @param actionType  - the actionType
     * @param actionParam - the actionParam
     * @param mapName     - the mapName
     * @throws DatabaseException if creation failed or id not found
     */
    public InteractableItemRowDataGateway(String name, Position pos,
                                          InteractableItemActionType actionType,
                                          InteractableItemActionParameter actionParam,
                                          String mapName)
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
     * Creates table
     *
     * @throws DatabaseException if it cant create or drop table
     */
    public static void createTable() throws DatabaseException
    {
        String dropSql = "DROP TABLE IF EXISTS InteractableItems";
        String createSql = "CREATE TABLE InteractableItems (" +
                "itemID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(30) NOT NULL, xPosition INT NOT NULL, " +
                "yPosition INT NOT NULL, actionType INT NOT NULL, " +
                "actionParam BLOB NOT NULL, mapName VARCHAR(30) NOT NULL)";

        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop InteractableItems table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create InteractableItems table", e);
        }
    }

    public void delete() throws DatabaseException
    {
        String query = "DELETE FROM InteractableItems WHERE itemID = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query))
        {
            stmt.setInt(1, this.itemID);
            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldnt delete Item", e);
        }
    }

    public InteractableItemActionParameter getActionParam()
    {
        return this.actionParam;
    }

    public void setActionParam(InteractableItemActionParameter param)
    {
        this.actionParam = param;
    }

    public InteractableItemActionType getActionType()
    {
        return this.actionType;
    }

    public void setActionType(InteractableItemActionType action)
    {
        this.actionType = action;
    }

    public int getItemID()
    {
        return this.itemID;
    }

    public String getMapName()
    {
        return this.mapName;
    }

    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Returns the DTO of the Interactable Object
     *
     * @return the data transfer object
     * @author Jordan Long
     */
    public InteractableItemDTO getObjectInfo()
    {
        return new InteractableItemDTO(itemID, name, position, actionType, actionParam,
                mapName);
    }

    public Position getPosition()
    {
        return this.position;
    }

    public void setPosition(Position pos)
    {
        this.position = pos;
    }

    public void persist() throws DatabaseException
    {
        String query = "UPDATE InteractableItems " +
                "SET name = ?, xPosition = ?, yPosition = ?, actionType = ?, actionParam = ?, mapName = ? " +
                "WHERE itemID = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(query))
        {
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
     * Create for Creation Constructor
     *
     * @throws DatabaseException if item not created
     */
    private void create(String name, Position pos, InteractableItemActionType actionType,
                        InteractableItemActionParameter actionParam, String mapName)
            throws DatabaseException
    {
        String query = "INSERT INTO InteractableItems " +
                "SET name = ?, xPosition = ?, yPosition = ?, actionType = ?, actionParam = ?, mapName = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, name);
            stmt.setInt(2, pos.getRow());
            stmt.setInt(3, pos.getColumn());
            stmt.setInt(4, actionType.getId());
            stmt.setObject(5, actionParam);
            stmt.setString(6, mapName);

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys())
            {
                if (rs.next())
                {
                    this.itemID = rs.getInt(1);
                }
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Generated Key for itemID not found or creation of row unsuccessful",
                    e);
        }
    }

    /**
     * Finder for finder constructor
     */
    private void findById(int itemID) throws DatabaseException
    {
        String query = "SELECT * FROM InteractableItems WHERE itemID = ?";
        try (PreparedStatement stmt = this.connection.prepareStatement(query))
        {
            stmt.setInt(1, itemID);

            try (ResultSet rs = stmt.executeQuery())
            {
                rs.next();

                this.name = rs.getString("name");
                int x = rs.getInt("xPosition");
                int y = rs.getInt("yPosition");
                this.position = new Position(x, y);
                this.actionType =
                        InteractableItemActionType.findById(rs.getInt("actionType"));
                this.actionParam =
                        this.getParam(rs.getObject("actionParam"), this.actionType);
                this.mapName = rs.getString("mapName");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't find item with specified ID", e);
        }
    }

    /**
     * Converts Blob to an action param
     */
    protected InteractableItemActionParameter getParam(Object blob,
                                                       InteractableItemActionType actionType)
            throws DatabaseException
    {
        try
        {
            Class<? extends InteractableItemActionParameter> actionParameterType =
                    actionType.getActionParam();
            ByteArrayInputStream stream = new ByteArrayInputStream((byte[]) blob);
            try (ObjectInputStream ois = new ObjectInputStream(stream))
            {
                Object x = ois.readObject();

                return actionParameterType.cast(x);
            }
        }
        catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
            throw new DatabaseException(
                    "Could not convert blob to InteractableItemActionParameter");
        }
    }


}
