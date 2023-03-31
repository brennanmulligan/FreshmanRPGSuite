package edu.ship.engr.shipsim.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A row data gateway for majors
 */
public class MajorRowDataGateway
{
    private int majorID;
    private String name;
    private Connection conn;

    /**
     * Getter for majorID
     * @return majorID
     */
    public int getMajorID()
    {
        return majorID;
    }

    /**
     * Setter for MajorID
     * @param majorID
     */
    public void setMajorID(int majorID)
    {
        this.majorID = majorID;
    }

    /**
     * Getter for the name of the major
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Setter for the name of the major
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Creates the majors table in the database
     * @throws DatabaseException
     */
    public static void createTable() throws DatabaseException
    {
        String dropSql = "DROP TABLE IF EXISTS Majors";
        String createSql = "CREATE TABLE Majors ("
                + "majorID INT NOT NULL, "
                + "Name VARCHAR(255))";

        Connection conn = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop Majors table", e);
        }

        try (PreparedStatement stmt = conn.prepareStatement(createSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create Majors table", e);
        }
    }

    /**
     * Finder constructor
     * @param majorID
     * @throws DatabaseException
     */
    public MajorRowDataGateway (int majorID) throws DatabaseException
    {
        this.majorID = majorID;
        conn = DatabaseManager.getSingleton().getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Majors WHERE MajorID = ?"))
        {
            stmt.setInt(1, this.majorID);
            try(ResultSet result = stmt.executeQuery())
            {
                result.next();
                name = result.getString("Name");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't Find a Majors with ID = " +
                    this.majorID, e);
        }
    }

    /**
     * Create constructor
     * @param majorID
     * @param name
     * @throws DatabaseException
     */
    public MajorRowDataGateway (int majorID, String name) throws DatabaseException
    {
        this.majorID = majorID;
        this.name = name;
        conn = DatabaseManager.getSingleton().getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("INSERT INTO Majors SET majorID = ?, Name = ?"))
        {
            stmt.setInt(1, majorID);
            stmt.setString(2, name);

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't Create a Major with ID = " + majorID, e);
        }
    }

    /**
     * Update the table in the database
     * @throws DatabaseException
     */
    public void persist () throws DatabaseException
    {
        conn = DatabaseManager.getSingleton().getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("UPDATE Majors SET majorID = ?, Name = ?"))
        {
            stmt.setInt(1, majorID);
            stmt.setString(2, name);

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't persist the major with ID = " +
                    majorID, e);
        }
    }

    /**
     * Delete a major from the database
     * @throws DatabaseException
     */
    public void delete () throws DatabaseException
    {
        conn = DatabaseManager.getSingleton().getConnection();
        try(PreparedStatement stmt = conn.prepareStatement("DELETE FROM Majors WHERE majorID = ?"))
        {
            stmt.setInt(1, majorID);

            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't Delete the major with ID = " +
                    majorID, e);
        }
    }
}
