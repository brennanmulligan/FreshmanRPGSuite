package edu.ship.engr.shipsim.datasource;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Arrays;
import java.util.Random;

/**
 * The RDS implementation of the row data gateway
 *
 * @author Merlin
 */
public class PlayerLoginRowDataGateway
{
    private int playerID;
    private String playerName;
    private byte[] password;
    private byte[] salt;
    private Connection connection;

    /**
     * Drop and re-create the PlayerLogin table this gateway manages
     *
     * @throws DatabaseException if we can't talk to the RDS
     */
    public static void createPlayerLoginTable() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "DROP TABLE IF EXISTS PlayerLogins");
            stmt.executeUpdate();
            StringBuffer sql = new StringBuffer("CREATE TABLE PlayerLogins(");
            sql.append("playerID int NOT NULL, ");
            sql.append("playerName VARCHAR(30) NOT NULL,");
            sql.append("password BLOB NOT NULL,");
            sql.append("salt BLOB NOT NULL,");
            sql.append("PRIMARY KEY (playerID),");
            sql.append("FOREIGN KEY (playerID) REFERENCES Players(playerID) ");
            sql.append(" ON DELETE CASCADE)");

            stmt.executeUpdate(new String(sql));
            stmt.executeUpdate("ALTER TABLE PlayerLogins ENGINE = INNODB");
            stmt.executeUpdate("ALTER TABLE PlayerLogins ADD UNIQUE (PlayerName)");

            stmt.close();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to initialize Player Login table", e);
        }
    }

    /**
     * Finder constructor: will initialize itself by finding the appropriate row in
     * the table
     *
     * @param playerName the player we are responsible for
     * @throws DatabaseException if we cannot find the given player in the table
     */
    public PlayerLoginRowDataGateway(String playerName) throws DatabaseException
    {
        this.salt = new byte[32];

        this.connection = DatabaseManager.getSingleton().getConnection();
        this.playerName = playerName;

        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM PlayerLogins WHERE playerName = ?");
            stmt.setString(1, playerName);
            ResultSet result = stmt.executeQuery();
            result.next();
            password = result.getBytes("password");
            salt = result.getBytes("salt");
            playerID = result.getInt("playerID");

        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't find a player named " + playerName, e);
        }
    }

    /**
     * Create constructor: will create a new row in the table with the given
     * information
     *
     * @param playerID   - The player's id
     * @param playerName -The player's name
     * @param password   - The player's password
     * @throws DatabaseException if we can't create the player (can't connect or duplicate name)
     */
    public PlayerLoginRowDataGateway(int playerID, String playerName, String password) throws DatabaseException
    {
        this.salt = new byte[32];
        this.playerID = playerID;
        this.playerName = playerName;

        // This will generate a new salt and hash the password
        this.setPassword(password);

        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "Insert INTO PlayerLogins SET playerID = ?, playerName = ?, salt = ?, password = ?",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, this.playerID);
            stmt.setString(2, this.playerName);
            stmt.setBytes(3, this.salt);
            stmt.setBytes(4, this.password);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if (rs.next())
            {
                playerID = rs.getInt(1);
            }

        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't create a player record in PlayerLogins for player named " + playerName, e);
        }
    }

    /**
     * Finder constructor
     *
     * @param playerID the player's unique ID
     * @throws DatabaseException if the player isn't in the database or we can't execute the query
     */
    public PlayerLoginRowDataGateway(int playerID) throws DatabaseException
    {
        this.salt = new byte[32];
        this.connection = DatabaseManager.getSingleton().getConnection();
        this.playerID = playerID;

        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM PlayerLogins WHERE playerID = ?");
            stmt.setInt(1, playerID);
            ResultSet result = stmt.executeQuery();
            result.next();
            password = result.getBytes("password");
            salt = result.getBytes("salt");
            playerName = result.getString("playerName");

        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't find a player with ID " + playerID, e);
        }
    }

    public String getPassword()
    {
        return password.toString();
    }

    public int getPlayerID()
    {
        return playerID;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void persist() throws DatabaseException
    {
        this.connection = DatabaseManager.getSingleton().getConnection();

        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE PlayerLogins SET password = ?, playerName = ?, salt = ? WHERE playerID = ?");
            stmt.setBytes(1, password);
            stmt.setString(2, playerName);
            stmt.setBytes(3, salt);
            stmt.setInt(4, playerID);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't persist info for player with playerId:" + playerID, e);
        }
    }

    public void setPassword(String password)
    {
        Random rand = new SecureRandom();
        rand.nextBytes(this.salt);

        this.password = hashPassword(password, this.salt);
    }

    /**
     * deletes a connection based on playerID
     */
    public void deleteRow() throws DatabaseException
    {
        this.connection = DatabaseManager.getSingleton().getConnection();

        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "delete from PlayerLogins where playerID=?");
            stmt.setInt(1, this.playerID);
            stmt.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setName(String playerName)
    {
        this.playerName = playerName;
    }


    public byte[] getSalt()
    {
        return this.salt;
    }

    public void setSalt(byte[] salt)
    {
        this.salt = salt;
    }

    /**
     * @param candidate_password The password to test
     * @return True if the password is correct
     */
    public boolean checkPassword(String candidate_password)
    {
        byte[] temp = hashPassword(candidate_password, this.salt);

        return Arrays.equals(temp, this.password);
    }

    /**
     * Utility method, taken from https://www.owasp.org/index.php/Hashing_Java and
     * modified slightly for simplicity & documentation
     *
     * @param password The password to hash
     * @param salt     A randomly generated (but consistent) salt
     * @return The hashed & salted password
     */
    public static byte[] hashPassword(final String password, final byte[] salt)
    {
        try
        {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            // 10 Iterations. Higher is safer, but slower. This seems to be a good balance.
            // 256 bit keylength. The documentation indicates that is sufficient.
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 10, 256);

            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();

            return res;
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException e)
        {
            /*
             * These exceptions will be thrown if the specified encryption algorithm ('PBKDF2WithHmacSHA512'), above,
             * is not available or supported on the current platform.  This *shouldn't* be an issue unless porting to
             * a different architecture (such as ARM).
             */
            throw new RuntimeException(e);
        }
    }

}