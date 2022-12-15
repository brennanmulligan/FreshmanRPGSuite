package edu.ship.engr.shipsim.datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Row data gateway for the maps that a player has visited
 */
public class VisitedMapsGateway
{
    private ArrayList<String> mapsVisited = new ArrayList<>();
    private String mapName;

    /**
     * finder constructor to find the maps a player has visited.
     *
     * @param playerID the players id
     * @throws DatabaseException if error occured during database access
     */
    public VisitedMapsGateway(int playerID) throws DatabaseException
    {
        getAllVisitedMapTitles(playerID);
    }

    /**
     * adder constructor to add a map to a player
     *
     * @param playerID the players id
     * @param mapTitle the map the player has just visited
     * @throws DatabaseException if error on database access
     */
    public VisitedMapsGateway(int playerID, String mapTitle) throws DatabaseException
    {
        ServerRowDataGateway server =
                ServerRowDataGateway.findPosAndMapNameFromMapTitle(mapTitle);
        //got the mapname
        mapName = server.getMapName();
        ServerRowDataGateway server2 = new ServerRowDataGateway(mapName);
        int mapID = server2.getMapID();


        Connection connection = DatabaseManager.getSingleton().getConnection();
        String insertSql = "INSERT INTO VisitedMaps SET playerID = ?, mapID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(insertSql))
        {
            stmt.setInt(1, playerID);
            stmt.setInt(2, mapID);
            stmt.executeUpdate();
            getAllVisitedMapTitles(playerID);
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not create VisitedMaps record", e);
        }
    }

    /**
     * Create the playerID to mapID many to many table
     *
     * @throws DatabaseException if the update failed
     */
    public static void createTable() throws DatabaseException
    {
        String dropSql = "DROP TABLE IF EXISTS VisitedMaps";
        String createSql = "CREATE TABLE VisitedMaps (" + "playerID INT NOT NULL, " +
                "mapID INT NOT NULL, " +
                "FOREIGN KEY (playerID) REFERENCES Players(playerID) ON DELETE CASCADE, " +
                "FOREIGN KEY (mapID) REFERENCES Server(serverID) ON DELETE CASCADE);";

        Connection connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to delete VisitedMaps table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createSql))
        {
            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create VisitedMaps table", e);
        }
    }

    /**
     * get the maps name
     *
     * @return mapName the mapName...
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * get the maps visited
     *
     * @return mapsVisited the list of maps a player has visited
     */
    public ArrayList<String> getMaps()
    {
        return mapsVisited;
    }

    /**
     * Gets titles for all servers a player has visited
     *
     * @param playerID ID of player we want maps for
     * @throws DatabaseException if the attempt to retrieve gets an unexpected error
     */
    private void getAllVisitedMapTitles(int playerID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        String sql =
                "SELECT Server.mapTitle FROM Server INNER JOIN VisitedMaps ON Server.serverID = VisitedMaps.mapID " +
                        "WHERE playerID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql))
        {
            stmt.setInt(1, playerID);

            ArrayList<String> temp = new ArrayList<>();

            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next())
                {
                    temp.add(rs.getString("mapTitle"));
                }
            }
            mapsVisited = temp;
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Could not get all maps visited for player with id: " + playerID, e);
        }
    }
}
