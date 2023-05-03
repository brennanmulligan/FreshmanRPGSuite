package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.MajorDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A table data gateway for crews
 */
public class MajorTableDataGateway
{

    private static MajorTableDataGateway singleton;

    /**
     * A private method only called by the singleton method
     */
    private MajorTableDataGateway()
    {

    }

    public static MajorTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new MajorTableDataGateway();
        }
        return singleton;
    }

    /**
     * For testing
     * @param singleton
     */
    public void setSingleton(MajorTableDataGateway singleton)
    {
        this.singleton = singleton;
    }

    /**
     * Retrieve all majors from the database
     * @return array list of majors DTOs
     * @throws DatabaseException
     */
    public ArrayList<MajorDTO> getAllMajors() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Majors"))
        {
            return processResultSetToCrewDTO(stmt);
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not retrieve all majors!", e);
        }
    }

    /**
     * Processes the result set we get back after we execute the query
     * @param stmt query
     * @return array list of Major DTOs
     * @throws DatabaseException
     */
    private ArrayList<MajorDTO> processResultSetToCrewDTO(PreparedStatement stmt)
            throws DatabaseException
    {
        ArrayList<MajorDTO> resultList = new ArrayList<>();

        try(ResultSet results = stmt.executeQuery())
        {
            while (results.next())
            {
                MajorDTO dto = new MajorDTO(results.getInt("majorID"),
                        results.getString("Name"));
                resultList.add(dto);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't retrieve all majors", e);
        }
        return resultList;
    }

}
