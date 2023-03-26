package edu.ship.engr.shipsim.datasource;

import org.intellij.lang.annotations.Language;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

        @Language("MySQL") String dropSql = "DROP TABLE IF EXISTS PlayerLogins";
        @Language("MySQL") String createSql = "" +
                "CREATE TABLE PlayerLogins(" +
                "    playerID int NOT NULL," +
                "    playerName VARCHAR(30) NOT NULL," +
                "    password BLOB NOT NULL," +
                "    salt BLOB NOT NULL," +
                "    PRIMARY KEY (playerID)," +
                "    UNIQUE KEY unique_playerID (playerID)," +
                "    UNIQUE KEY unique_playerName (playerName)," +
                "    FOREIGN KEY (playerID) REFERENCES Players(playerID) ON DELETE CASCADE)";

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop PlayerLogins table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create PlayerLogins table", e);
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

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PlayerLogins WHERE playerName = ?"))
        {
            stmt.setString(1, playerName);

            try (ResultSet result = stmt.executeQuery())
            {
                result.next();
                password = result.getBytes("password");
                salt = result.getBytes("salt");
                playerID = result.getInt("playerID");
            }
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
        try (PreparedStatement stmt = connection.prepareStatement(
                "Insert INTO PlayerLogins SET playerID = ?, playerName = ?, salt = ?, password = ?",
                Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setInt(1, this.playerID);
            stmt.setString(2, this.playerName);
            stmt.setBytes(3, this.salt);
            stmt.setBytes(4, this.password);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys())
            {
                if (rs.next())
                {
                    // FIXME: Figure out what's going on here
                    playerID = rs.getInt(1);
                }
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

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM PlayerLogins WHERE playerID = ?"))
        {
            stmt.setInt(1, playerID);

            try (ResultSet result = stmt.executeQuery())
            {
                result.next();
                password = result.getBytes("password");
                salt = result.getBytes("salt");
                playerName = result.getString("playerName");
            }
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

        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE PlayerLogins SET password = ?, playerName = ?, salt = ? WHERE playerID = ?"))
        {
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

        try (PreparedStatement stmt = connection.prepareStatement("delete from PlayerLogins where playerID=?"))
        {
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