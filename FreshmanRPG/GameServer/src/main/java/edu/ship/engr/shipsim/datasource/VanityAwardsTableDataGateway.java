package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The RDS gateway for the items in the vanity awards table
 */
public class VanityAwardsTableDataGateway
{
    private static VanityAwardsTableDataGateway singleton;

    /**
     * Gets the instance of this gateway
     *
     * @return the instance
     */
    public static VanityAwardsTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new VanityAwardsTableDataGateway();
        }
        return singleton;
    }

    /**
     * Drop and re-create the VanityAwards table this gateway manages
     *
     * @throws DatabaseException shouldnt
     */
    public static void createTable() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        String dropSql = "DROP TABLE IF EXISTS VanityAwards";
        String createSql = "CREATE TABLE VanityAwards(" +
                "questID INT NOT NULL," +
                "awardID INT NOT NULL," +
                "FOREIGN KEY (questID) REFERENCES Quests(questID) ON DELETE CASCADE," +
                "FOREIGN KEY (awardID) REFERENCES VanityItems(vanityID) ON DELETE CASCADE," +
                "CONSTRAINT PK_questID_awardID PRIMARY KEY (questID, awardID));";

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop VanityAwards table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create VanityAwards table", e);
        }
    }

    /**
     * Gets all the vanity awards stored in the database
     *
     * @return a list of all the vanity awards
     * @throws DatabaseException shouldnt
     */
    public ArrayList<VanityDTO> getVanityAwards() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        ArrayList<VanityDTO> VanityAwards = new ArrayList<>();
        VanityItemsTableDataGateway vanityItemsGateway =
                VanityItemsTableDataGateway.getSingleton();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VanityAwards");
             ResultSet result = stmt.executeQuery())
        {
            while (result.next())
            {
                VanityAwards.add(vanityItemsGateway.getVanityItemByID(result.getInt("awardID")));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return VanityAwards;
    }

    /**
     * Gets all the vanity awards stored in the database
     *
     * @return a list of all the vanity awards
     * @throws DatabaseException thrown if quest ID is invalid
     */
    public ArrayList<VanityDTO> getVanityAwardsForQuest(int questID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        ArrayList<VanityDTO> VanityAwards = new ArrayList<>();
        VanityItemsTableDataGateway vanityItemsGateway =
                VanityItemsTableDataGateway.getSingleton();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM VanityAwards WHERE questID = ?"))
        {
            stmt.setInt(1, questID);

            try (ResultSet result = stmt.executeQuery())
            {
                while (result.next())
                {
                    VanityAwards.add(vanityItemsGateway.getVanityItemByID(result.getInt("awardID")));
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return VanityAwards;
    }


    /**
     * Adds a vanity award to the vanity awards list so it can be given as a quest reward
     *
     * @param awardID the ID of the vanity award to add
     * @throws DatabaseException thrown if quest or award ids are invalid
     */
    public void addVanityAward(int questID, int awardID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("INSERT INTO VanityAwards SET questID = ?, awardID = ?"))
        {
            stmt.setInt(1, questID);
            stmt.setInt(2, awardID);
            int updated = stmt.executeUpdate();
            if (updated != 1)
            {
                throw new DatabaseException("Could not add new Vanity award to database (NOT ADDED)");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not add new Vanity award to database", e);
        }
    }

    /**
     * Removes a vanity award from the vanity awards list so it cant be given out anymore
     *
     * @param awardID the id of the award to be removed
     * @throws DatabaseException thrown if quest or award ids are invalid
     */
    public void removeVanityAward(int questID, int awardID) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("DELETE FROM VanityAwards WHERE questID = ? AND awardID = ?"))
        {
            stmt.setInt(1, questID);
            stmt.setInt(2, awardID);
            int updated = stmt.executeUpdate();
            if (updated != 1)
            {
                throw new DatabaseException("Could not remove vanity award from database (NOT ADDED)");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Could not remove vanity award from database", e);
        }
    }
}
