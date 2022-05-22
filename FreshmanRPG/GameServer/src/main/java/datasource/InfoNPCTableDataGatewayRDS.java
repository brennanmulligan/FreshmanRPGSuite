package datasource;

import dataDTO.InfoNPCDTO;
import dataDTO.RandomFactDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author John Lang and Noah Macminn
 */
public class InfoNPCTableDataGatewayRDS extends InfoNPCTableDataGateway
{
    static TableDataGateway getGateway()
    {
        return new InfoNPCTableDataGatewayRDS();
    }
    @Override
    public ArrayList<InfoNPCDTO> getAllInfoForNPC(int npcID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt =  connection.prepareStatement("SELECT * FROM InfoNPC");
            ResultSet result = stmt.executeQuery();

            ArrayList<InfoNPCDTO> results = new ArrayList<>();
            while (result.next())
            {
                if (result.getInt("npcID") == npcID)
                {
                    InfoNPCDTO rec = new InfoNPCDTO(result.getString("response"), result.getString("information"),
                            result.getInt("npcID"));
                    results.add(rec);
                }
            }
            return results;
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't find Player Response/Information info for infoNPC id:  " + npcID, e);
        }
    }

    /**
     * A private constructor only called by the getSingleton method
     */
    private InfoNPCTableDataGatewayRDS()
    {
        // do nothing this just explicitly makes it private
    }

    /**
     * Dump any existing table and create a new one
     *
     * @throws DatabaseException
     *             if we can't create it
     */
    public static void createTable() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt =  connection.prepareStatement(
                    "DROP TABLE IF EXISTS InfoNPC");
            stmt.executeUpdate();
            stmt.close();
            stmt =  connection.prepareStatement(
                    "Create TABLE InfoNPC (response VARCHAR(1000) NOT NULL, information VARCHAR(8000) NOT NULL, npcID INT NOT NULL," +
                                              " PRIMARY KEY (npcID, response))");
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create the RandomFacts table", e);
        }
    }

    public static void createRow(String response, String information, int npcID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt =  connection.prepareStatement(
                    "Insert INTO InfoNPC SET response = ?, information = ?, npcID = ?");
            stmt.setString(1, response);
            stmt.setString(2, information);
            stmt.setInt(3, npcID);
            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't create a random fact record for level with text " + response, e);
        }
    }


    @Override
    public void resetTableGateway()
    {

    }
}
