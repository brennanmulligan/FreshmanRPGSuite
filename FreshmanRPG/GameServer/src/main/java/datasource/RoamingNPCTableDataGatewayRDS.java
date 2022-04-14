package datasource;

import dataDTO.RoamingNPCPathsDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoamingNPCTableDataGatewayRDS extends RoamingNPCTableDataGateway
{

    private static RoamingNPCTableDataGatewayRDS singleton;

    public static synchronized RoamingNPCTableDataGatewayRDS getSingleton()
    {
        if (singleton == null)
        {
            singleton = new RoamingNPCTableDataGatewayRDS();
        }
        return singleton;
    }

    public static void createTable() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt =  connection.prepareStatement(
                    "DROP TABLE IF EXISTS RoamingNPC");
            stmt.executeUpdate();
            stmt.close();
            stmt =  connection.prepareStatement(
                    "Create TABLE RoamingNPC (path VARCHAR(1000) NOT NULL, npcID INT NOT NULL, PRIMARY KEY(npcID, path))");
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create the RoamingNPC table", e);
        }
    }

    @Override
    public ArrayList<RoamingNPCPathsDTO> getAllPathsForNPC(int npcID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt =  connection.prepareStatement("SELECT * FROM RoamingNPC");
            ResultSet result = stmt.executeQuery();

            ArrayList<RoamingNPCPathsDTO> results = new ArrayList<>();
            while (result.next())
            {
                if (result.getInt("npcID") == npcID)
                {
                    RoamingNPCPathsDTO rec = new RoamingNPCPathsDTO(result.getString("path"), result.getInt("npcID"));
                    results.add(rec);
                }
            }
            return results;
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't find Roaming Paths", e);
        }
    }


    public static void createRow(String path, int npcID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt =  connection.prepareStatement(
                    "Insert INTO RoamingNPC SET path = ?, npcID = ?");
            stmt.setString(1, path);
            stmt.setInt(2, npcID);
            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't create a roaming path record for level with text " + path, e);
        }
    }
}
