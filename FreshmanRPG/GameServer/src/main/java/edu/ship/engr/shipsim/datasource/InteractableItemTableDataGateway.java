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
import java.util.ArrayList;

/**
 * Allows access to the Interactable Items Table in the DB
 *
 * @author Elisabeth Ostrow, Jake Moore
 */
public class InteractableItemTableDataGateway
{

    private static InteractableItemTableDataGateway singleton;

    // connection
    private Connection connection;

    /**
     * Private constructor does nothing
     */
    private InteractableItemTableDataGateway()
    {
    }

    public static InteractableItemTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new InteractableItemTableDataGateway();
        }
        return singleton;
    }

    /**
     * Gets all items in the database
     *
     * @return array list of all interactableitems
     */
    public ArrayList<InteractableItemDTO> getAllItems() throws DatabaseException
    {
        this.connection = DatabaseManager.getSingleton().getConnection();

        String query = "SELECT * FROM InteractableItems";

        try (PreparedStatement stmt = this.connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery())
        {
            ArrayList<InteractableItemDTO> result = new ArrayList<>();
            while (rs.next())
            {
                InteractableItemDTO item =
                        new InteractableItemDTO(rs.getInt("itemID"), rs.getString("name"),
                                new Position(rs.getInt("xPosition"),
                                        rs.getInt("yPosition")),
                                InteractableItemActionType.findById(
                                        rs.getInt("actionType")),
                                this.getParam(rs.getObject("actionParam"),
                                        InteractableItemActionType.findById(rs
                                                .getInt("actionType"))),
                                rs.getString("mapName"));
                result.add(item);
            }

            return result;
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Failed to retrieve all items", e);
        }
    }

    /**
     * gets all items on a certain map
     */
    public ArrayList<InteractableItemDTO> getItemsOnMap(String mapName)
            throws DatabaseException
    {
        this.connection = DatabaseManager.getSingleton().getConnection();

        String query = "SELECT * FROM InteractableItems WHERE mapName = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(query))
        {
            stmt.setString(1, mapName);

            try (ResultSet rs = stmt.executeQuery())
            {
                ArrayList<InteractableItemDTO> result = new ArrayList<>();
                while (rs.next())
                {
                    InteractableItemDTO item =
                            new InteractableItemDTO(rs.getInt("itemID"), rs.getString("name"),
                                    new Position(rs.getInt("xPosition"),
                                            rs.getInt("yPosition")),
                                    InteractableItemActionType.findById(
                                            rs.getInt("actionType")),
                                    this.getParam(rs.getObject("actionParam"),
                                            InteractableItemActionType.findById(rs
                                                    .getInt("actionType"))),
                                    rs.getString("mapName"));
                    result.add(item);
                }

                return result;
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't find any items", e);
        }
    }

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
            throw new DatabaseException(
                    "Could not convert blob to InteractableItemActionParameter");
        }
    }

}
