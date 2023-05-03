package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.CrewDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A table data gateway for crews
 */
public class CrewTableDataGateway
{

    private static CrewTableDataGateway singleton;

    /**
     * A private method only called by the singleton method
     */
    private CrewTableDataGateway()
    {
        //do nothing this just explicitly makes it private
    }

    public static CrewTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new CrewTableDataGateway();
        }
        return singleton;
    }

    /**
     * For testing
     * @param singleton
     */
    public void setSingleton(CrewTableDataGateway singleton)
    {
        this.singleton = singleton;
    }

    /**
     * Retrieve all crews from the database
     * @return array list of crews DTOs
     * @throws DatabaseException
     */
    public ArrayList<CrewDTO> getAllCrews() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Crews"))
        {
            return processResultSetToCrewDTO(stmt);
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not retrieve all crews!", e);
        }
    }

    /**
     * Processes the result set we get back after we execute the query
     * @param stmt query
     * @return array list of Crew DTOs
     * @throws DatabaseException
     */
    private ArrayList<CrewDTO> processResultSetToCrewDTO(PreparedStatement stmt)
            throws DatabaseException
    {
        ArrayList<CrewDTO> resultList = new ArrayList<>();

        try(ResultSet results = stmt.executeQuery())
        {
            while (results.next())
            {
                CrewDTO dto = new CrewDTO(results.getInt("crewID"),
                        results.getString("Name"));
                resultList.add(dto);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't retrieve all crews", e);
        }
        return resultList;
    }

}
