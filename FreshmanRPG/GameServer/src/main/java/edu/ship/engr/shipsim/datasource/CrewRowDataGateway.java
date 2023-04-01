package edu.ship.engr.shipsim.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A row data gateway for crews
 */
public class CrewRowDataGateway
{
    private int crewID;
    private String name;
    private Connection conn;

    /**
     * Getter for crewID
     * @return crewID
     */
    public int getCrewID()
    {
        return crewID;
    }

    /**
     * Setter for CrewID
     * @param crewID
     */
    public void setCrewID(int crewID)
    {
        this.crewID = crewID;
    }

    /**
     * Getter for the name of the crew
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter for the name of the crew
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Creates the crews table in the database
     * @throws DatabaseException
     */
    public static void createTable() throws DatabaseException
    {
        String dropSql = "DROP TABLE IF EXISTS Crews";
        String createSql = "CREATE TABLE Crews ("
                + "crewID INT NOT NULL, "
                + "Name VARCHAR(255))";

        Connection conn = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop Crews table", e);
        }

        try (PreparedStatement stmt = conn.prepareStatement(createSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create Crews table", e);
        }
    }

    /**
     * Finder constructor
     * @param crewID
     * @throws DatabaseException
     */
    public CrewRowDataGateway (int crewID) throws DatabaseException
    {
        this.crewID = crewID;
        conn = DatabaseManager.getSingleton().getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Crews WHERE CrewID = ?"))
        {
            stmt.setInt(1, crewID);
            try(ResultSet result = stmt.executeQuery())
            {
                result.next();
                name = result.getString("Name");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't Find a Crew with ID = " + crewID, e);
        }
    }

    /**
     * Create constructor
     * @param crewID
     * @param name
     * @throws DatabaseException
     */
    public CrewRowDataGateway (int crewID, String name) throws DatabaseException
    {
        this.crewID = crewID;
        this.name = name;
        conn = DatabaseManager.getSingleton().getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO Crews SET CrewID = ?, Name = ?"))
        {
            stmt.setInt(1, crewID);
            stmt.setString(2, name);

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't Create a Crew with ID = " + crewID, e);
        }
    }

    /**
     * Update the table in the database
     * @throws DatabaseException
     */
    public void persist () throws DatabaseException
    {
        conn = DatabaseManager.getSingleton().getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE Crews SET crewID = ?, Name = ?"))
        {
            stmt.setInt(1, crewID);
            stmt.setString(2, name);

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't persist the Crew with ID = " + crewID, e);
        }
    }

    /**
     * Delete a crew from the database
     * @throws DatabaseException
     */
    public void delete () throws DatabaseException
    {
        conn = DatabaseManager.getSingleton().getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM Crews WHERE crewID = ?"))
        {
            stmt.setInt(1, crewID);

            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't Delete the Crew with ID = " + crewID, e);
        }
    }
}
