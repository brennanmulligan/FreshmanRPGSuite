package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.datatypes.FriendStatusEnum;
import org.apache.commons.compress.utils.Lists;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FriendTableDataGateway
{
    static ArrayList<PlayerDTO> allPlayer;
    private static FriendTableDataGateway singleton;
    private Connection connect;
    private String playerName, friendName;

    private FriendTableDataGateway()
    {
        PlayerTableDataGateway playerGateway =
                PlayerTableDataGateway.getSingleton();
        try
        {
            allPlayer = playerGateway.getAllPlayers();
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * create the friend table
     *
     * @throws DatabaseException if the attempt to create the table gets an unexpected error
     */
    public static void createTable() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        @Language("MySQL")
        String dropSql = "DROP TABLE IF EXISTS Friends;";

        @Language("MySQL")
        String createSql = "CREATE TABLE Friends(playerID int NOT NULL, FRIENDID int NOT NULL, status varchar(10) NOT NULL);";

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop Friends table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create Friends table", e);
        }
    }

    public static FriendTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new FriendTableDataGateway();
        }
        return singleton;
    }

    /**
     * update the row of the player who sent the request to accepted status
     * Than call the add to add the player who accepted
     *
     * @throws DatabaseException if the attempt to update gets an unexpected error
     */
    public int accept(int playerID, String friendName) throws DatabaseException
    {

        Connection connect = DatabaseManager.getSingleton().getConnection();

        // FIXME: Hybrid Coupling... Eww
        int friendId = -1;
        try
        {

            for (PlayerDTO playerDTO : allPlayer)
            {
                //System.out.println(allPlayer.get(i).getPlayerName() + " : " + friendName);
                if (playerDTO.getPlayerName().equals(friendName))
                {
                    friendId = playerDTO.getPlayerId();
                    break;
                }
            }

            if (friendId == -1)
            {
                throw new DatabaseException("Player not found or updated");
            }
            else
            {
                try (PreparedStatement stmt = connect.prepareStatement("UPDATE Friends SET status = 'ACCEPTED' WHERE playerID = ? AND FRIENDID = ?;"))
                {
                    stmt.setInt(1, friendId);
                    stmt.setInt(2, playerID);
                    stmt.executeUpdate();

                    return friendId;
                }
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Player not found or updated", e);
        }
    }

    /**
     * create a new row in the table to add the friend
     *
     * @throws DatabaseException if the attempt to add gets an unexpected error
     */
    public void add(int playerID, String friendName, FriendStatusEnum status)
            throws DatabaseException
    {

        // FIXME: Hybrid Coupling... Eww
        int friendID = -1;
        for (PlayerDTO playerDTO : allPlayer)
        {
            if (playerDTO.getPlayerName().equals(friendName))
            {
                friendID = playerDTO.getPlayerID();
                break;
            }
        }
        if (friendID == -1)
        {
            throw new DatabaseException("Player not found or updated");
        }
        else
        {
            createRow(playerID, friendID, status);
        }
    }

    /**
     * Used to create the row in the table
     *
     * @param id       the player's id
     * @param friendID the friends player id
     * @param status   the status of their relationship
     * @throws DatabaseException if the attempt to add the relationship gets an unexpected error
     */
    public void createRow(int id, int friendID, FriendStatusEnum status)
            throws DatabaseException
    {

        String query =
                "INSERT INTO Friends " + "SET playerID = ?, FRIENDID = ?, STATUS = ?";
        this.connect = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setInt(1, id);
            stmt.setInt(2, friendID);
            stmt.setString(3, status.toString());

            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Row creation of friends was not successful", e);
        }
    }

    public ArrayList<FriendDTO> getAllFriends(int id) throws DatabaseException
    {
        this.connect = DatabaseManager.getSingleton().getConnection();
        int playerId, friendId;
        FriendStatusEnum status;
        ArrayList<FriendDTO> results = Lists.newArrayList();

        // FIXME: Extract into two separate methods... A 12 complexity is way too high for this. Try to abstract

        // Search by playerID
        try (PreparedStatement stmt = connect.prepareStatement(
                "SELECT F.playerID as PLAYERID, F.FRIENDID as FRIENDID, F.status as status" +
                        " FROM  Friends as F, Players as P WHERE F.playerID = P.playerID AND P.playerID = ?"))
        {
            stmt.setInt(1, id);

            try (ResultSet result = stmt.executeQuery())
            {
                while (result.next())
                {
                    playerId = result.getInt("PLAYERID");
                    friendId = result.getInt("FRIENDID");
                    if (result.getString("status").equals("ACCEPTED"))
                    {
                        status = FriendStatusEnum.ACCEPTED;
                    }
                    else
                    {
                        status = FriendStatusEnum.PENDING;
                    }

                    getNameFromId(playerId, friendId);

                    FriendDTO friend =
                            new FriendDTO(playerId, friendId, status, playerName, friendName);
                    results.add(friend);
                }
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to file player", e);
        }

        // Search by friendID
        try (
            //Get the records where the id passed in is the friend
            PreparedStatement stmt = connect.prepareStatement(
                    "SELECT F.playerID as PLAYERID, F.FRIENDID as FRIENDID, F.status as status" +
                            " FROM  Friends as F, Players as P WHERE F.playerID = P.playerID AND F.FRIENDID = ?;"))
        {
            stmt.setInt(1, id);

            try (ResultSet result = stmt.executeQuery())
            {
                while (result.next())
                {
                    playerId = result.getInt("FRIENDID");
                    friendId = result.getInt("PLAYERID");
                    if (result.getString("status").equals("PENDING"))
                    {
                        status = FriendStatusEnum.REQUESTED;
                    }
                    else
                    {
                        status = FriendStatusEnum.ACCEPTED;
                    }
                    getNameFromId(playerId, friendId);
                    FriendDTO friend = new FriendDTO(playerId, friendId, status, playerName, friendName);
                    results.add(friend);
                }
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to find player", e);
        }

        return results;
    }

    public int getFriendCounter(int id) throws DatabaseException
    {
        this.connect = DatabaseManager.getSingleton().getConnection();
        int friendCount = 0;

        try (PreparedStatement stmt = connect.prepareStatement(
                "SELECT COUNT(*) AS total FROM Friends WHERE status = 'ACCEPTED' AND (friendId = ? OR playerId = ?);"))
        {
            stmt.setInt(1, id);
            stmt.setInt(2, id);

            try (ResultSet result = stmt.executeQuery())
            {
                if (result.next())
                {
                    friendCount = result.getInt("total");
                }
            }
            return friendCount;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw new DatabaseException("Unable to find player");
        }
    }

    /**
     * this is for finding the player for updating friends tab list
     *
     * @param playerName the name of a player
     * @return the player's ID
     */
    public int getSpecificIDFromName(String playerName)
    {
        int playerID = 0;
        for (PlayerDTO playerDTO : allPlayer)
        {
            if (playerDTO.getPlayerName().equals(playerName))
            {
                playerID = playerDTO.getPlayerID();
            }
        }
        return playerID;
    }

    /**
     * this is for finding the player for updating friends tab list
     *
     * @param playerId the id of the player we are interested in
     * @return the name of the given player
     */
    public String getSpecificNameFromId(int playerId)
    {
        String name = "";
        for (PlayerDTO playerDTO : allPlayer)
        {
            if (playerDTO.getPlayerID() == playerId)
            {
                name = playerDTO.getPlayerName();
            }
        }
        return name;
    }

    private void getNameFromId(int playerId, int friendId)
    {
        for (PlayerDTO playerDTO : allPlayer)
        {
            if (playerDTO.getPlayerID() == playerId)
            {
                playerName = playerDTO.getPlayerName();
            }
            else if (playerDTO.getPlayerID() == friendId)
            {
                friendName = playerDTO.getPlayerName();
            }
        }
    }
}