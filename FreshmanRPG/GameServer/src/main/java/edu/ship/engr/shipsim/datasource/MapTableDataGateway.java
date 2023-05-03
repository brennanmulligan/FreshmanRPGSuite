package edu.ship.engr.shipsim.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MapTableDataGateway
{
    private static MapTableDataGateway singleton;

    private MapTableDataGateway()
    {

    }

    /**
     * Retrieves singleton instance
     *
     * @return instance of gateway
     */
    public static MapTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new MapTableDataGateway();
        }
        return singleton;
    }

    public void setSingleton(MapTableDataGateway singleton)
    {
        this.singleton = singleton;
    }

    public ArrayList<String> getMapNames() throws DatabaseException
    {
        ArrayList<String> data = new ArrayList<>();

        try
        {
            Connection connection =
                    DatabaseManager.getSingleton().getConnection();

            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT mapName FROM Server ORDER BY serverID"))
            {
                try (ResultSet rs = stmt.executeQuery())
                {
                    while (rs.next())
                    {
                        data.add(rs.getString("mapName"));
                    }
                }


            }
            catch (SQLException ex)
            {
                throw new DatabaseException(
                        "Unable to get all map names from the database", ex);
            }

        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }

        return data;
    }

}
