package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.criteria.QuestCompletionActionParameter;
import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.datatypes.Position;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * MySQL row data gateway to the Quest table
 *
 * @author merlin
 */
public class QuestRowDataGateway
{

    private final int questID;
    private String questTitle;
    private String questDescription;
    private String triggerMapName;
    private Position triggerPosition;
    private Connection connection;
    private int experiencePointsGained;
    private int objectivesForFulfillment;
    private QuestCompletionActionParameter completionActionParameter;
    private QuestCompletionActionType completionActionType;
    private Date startDate;
    private Date endDate;

    private boolean isEasterEgg;

    /**
     * Finder constructor
     *
     * @param questID the id of the quest this gateway will manage
     * @throws DatabaseException if we can't talk to the RDS server
     */
    public QuestRowDataGateway(int questID) throws DatabaseException
    {
        this.questID = questID;
        this.connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Quests WHERE questID = ?"))
        {
            stmt.setInt(1, questID);

            try (ResultSet result = stmt.executeQuery())
            {
                result.next();

                this.questTitle = result.getString("questTitle");
                this.questDescription = result.getString("questDescription");
                this.triggerMapName = result.getString("triggerMapName");
                this.triggerPosition = new Position(result.getInt("triggerRow"),
                        result.getInt("triggerColumn"));
                this.experiencePointsGained = result.getInt("experiencePointsGained");
                this.objectivesForFulfillment = result.getInt("objectivesForFulfillment");
                this.completionActionType = QuestCompletionActionType.findByID(
                        result.getInt("completionActionType"));
                completionActionParameter =
                        extractCompletionActionParameter(result, completionActionType);
                this.startDate = result.getDate("startDate");
                this.endDate = result.getDate("endDate");
                this.isEasterEgg = result.getBoolean("easterEgg");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't find a quest with ID " + questID, e);
        }
    }

    /**
     * Create constructor
     *
     * @param questID                   the quest's unique ID
     * @param questTitle                the quest's title
     * @param questDescription          the description of the quest
     * @param triggerMapName            the name of the map that contains the trigger
     *                                  location for this quest
     * @param triggerPosition           the coordinates of the trigger location for this
     *                                  quest
     * @param experiencePointsGained    the number of experience points gained for
     *                                  completing this quest
     * @param objectivesForFulfillment  the number of objectives this quest
     *                                  requires for fulfillment
     * @param completionActionType      the type of action that should be taken when
     *                                  this quest is completed
     * @param completionActionParameter data describing the details of the
     *                                  action taken when this quest is completed
     * @param startDate                 the first day the quest is available
     * @param endDate                   the last day the quest is available
     * @param isEasterEgg               denotes if quest is an easter egg
     * @throws DatabaseException if we can't talk to the RDS
     */
    public QuestRowDataGateway(int questID, String questTitle, String questDescription,
                               String triggerMapName, Position triggerPosition,
                               int experiencePointsGained,
                               int objectivesForFulfillment,
                               QuestCompletionActionType completionActionType,
                               QuestCompletionActionParameter completionActionParameter,
                               Date startDate, Date endDate, boolean isEasterEgg) throws DatabaseException
    {
        this.questID = questID;
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "Insert INTO Quests SET " +
                        "questID = ?, questTitle = ?, " +
                        "questDescription = ?, triggerMapname = ?, " +
                        "triggerRow = ?, triggerColumn = ?, " +
                        "experiencePointsGained = ?, objectivesForFulfillment = ?," +
                        " completionActionType = ?, completionActionParameter = ?," +
                        " startDate = ?, endDate = ?, easterEgg = ?"))
        {
            stmt.setInt(1, questID);
            stmt.setString(2, questTitle);
            stmt.setString(3, questDescription);
            stmt.setString(4, triggerMapName);
            stmt.setInt(5, triggerPosition.getRow());
            stmt.setInt(6, triggerPosition.getColumn());
            stmt.setInt(7, experiencePointsGained);
            stmt.setInt(8, objectivesForFulfillment);
            stmt.setInt(9, completionActionType.getID());
            stmt.setObject(10, completionActionParameter);
            stmt.setDate(11, new java.sql.Date(startDate.getTime()));
            stmt.setDate(12, new java.sql.Date(endDate.getTime()));
            stmt.setBoolean(13, isEasterEgg);
            stmt.executeUpdate();

            this.questDescription = questDescription;
            this.questTitle = questTitle;

        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't create a quest record for quest with ID " + questID, e);
        }
    }

    /**
     * @param title                     The quest title.
     * @param description               the description of the quest
     * @param mapName                   the name of the map that contains the trigger
     *                                  location for this quest
     * @param position                  the coordinates of the trigger location for this
     *                                  quest
     * @param experienceGained          the number of experience points gained for
     *                                  completing this quest
     * @param objectivesForFulfillment the number of objectives this quest
     *                                  requires for fulfillment
     * @param completionActionType      the type of action that should be taken when
     *                                  this quest is completed
     * @param completionActionParameter data describing the details of the
     *                                  action taken when this quest is completed
     * @param startDate                 the first day the quest is available
     * @param endDate                   the last day the quest is available
     * @throws DatabaseException if we can't talk to the RDS
     */
    public QuestRowDataGateway(String title, String description, String mapName,
                               Position position, int experienceGained,
                               int objectivesForFulfillment,
                               QuestCompletionActionType completionActionType,
                               QuestCompletionActionParameter completionActionParameter,
                               Date startDate, Date endDate, Boolean isEasterEgg) throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "Insert INTO Quests SET " +
                        "questTitle = ?, questDescription = ?, " +
                        "triggerMapname = ?, triggerRow = ?, triggerColumn = ?, " +
                        "experiencePointsGained = ?, objectivesForFulfillment = ?, " +
                        "completionActionType = ?, completionActionParameter = ?, " +
                        "startDate = ?, endDate = ?, easterEgg = ?",
                Statement.RETURN_GENERATED_KEYS))
        {
            stmt.setString(1, title);
            stmt.setString(2, description);
            stmt.setString(3, mapName);
            stmt.setInt(4, position.getRow());
            stmt.setInt(5, position.getColumn());
            stmt.setInt(6, experienceGained);
            stmt.setInt(7, objectivesForFulfillment);
            stmt.setInt(8, completionActionType.getID());
            stmt.setObject(9, completionActionParameter);
            stmt.setDate(10, new java.sql.Date(startDate.getTime()));
            stmt.setDate(11, new java.sql.Date(endDate.getTime()));
            stmt.setBoolean(12, isEasterEgg);
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys())
            {
                if (generatedKeys.next())
                {
                    this.questID = generatedKeys.getInt(1);
                }
                else
                {
                    throw new DatabaseException("No quest id was generated.");
                }
            }

            this.questDescription = description;
            this.questTitle = title;

        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't create a quest record.", e);
        }
    }

    /**
     * Drop the table if it exists and re-create it empty
     *
     * @throws DatabaseException shouldn't
     */
    public static void createTable() throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();

        String dropSql = "DROP TABLE IF EXISTS VanityAwards, Quests";
        String createSql = "Create TABLE Quests (questID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, questTitle VARCHAR(40) UNIQUE,questDescription VARCHAR(200), triggerMapName VARCHAR(80)," +
                " triggerRow INT, triggerColumn INT, experiencePointsGained INT, objectivesForFulfillment INT, " +
                " completionActionType INT, completionActionParameter BLOB, startDate DATE, endDate DATE," +
                " easterEgg TINYINT DEFAULT 0)";

        try (PreparedStatement stmt = connection.prepareStatement(dropSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to drop Quests table", e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(createSql))
        {
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create Quests table", e);
        }
    }

    public static QuestCompletionActionParameter extractCompletionActionParameter(
            ResultSet result, QuestCompletionActionType completionActionType)
            throws SQLException, DatabaseException
    {
        Class<? extends QuestCompletionActionParameter> completionActionParameterType =
                completionActionType.getCompletionActionParameterType();
        QuestCompletionActionParameter completionActionParameter;
        if (completionActionType != QuestCompletionActionType.NO_ACTION)
        {

            ByteArrayInputStream baip;
            try
            {
                baip = new ByteArrayInputStream(
                        (byte[]) result.getObject("completionActionParameter"));
            }
            catch (NullPointerException ex)
            {
                return null;
            }

            try (ObjectInputStream ois = new ObjectInputStream(baip))
            {
                Object x = ois.readObject();
                completionActionParameter = completionActionParameterType.cast(x);
            }
            catch (ClassNotFoundException | IOException e)
            {
                throw new DatabaseException(
                        "Couldn't convert blob to completion action parameter ", e);
            }
        }
        else
        {
            completionActionParameter = null;
        }
        return completionActionParameter;
    }

    /**
     * Get the ID of the quest with that title
     *
     * @param questTitle
     * @return the quest ID
     * @throws DatabaseException if we can't talk to the RDS data source
     */
    public static int findIDFromTitle(String questTitle)
            throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT questID FROM Quests WHERE questTitle = ?"))
        {
            stmt.setString(1, questTitle);

            try (ResultSet result = stmt.executeQuery())
            {
                result.next();
                return result.getInt("questID");
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("No Quest ID found from the Quest Title", e);
        }
    }

        /**
     * Get the IDs of the quests that are supposed to trigger at a specified map
     * location
     *
     * @param mapName  the name of the map
     * @param position the position on the map
     * @return the quest IDs
     * @throws DatabaseException if we can't talk to the RDS data source
     */
    public static ArrayList<Integer> findQuestsForMapLocation(String mapName, Position position)
            throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT questID FROM Quests WHERE triggerMapName = ? and triggerRow = ? and triggerColumn = ?"))
        {
            stmt.setString(1, mapName);
            stmt.setInt(2, position.getRow());
            stmt.setInt(3, position.getColumn());

            try (ResultSet result = stmt.executeQuery())
            {
                ArrayList<Integer> results = new ArrayList<>();

                while (result.next())
                {
                    results.add(result.getInt("questID"));
                }

                return results;
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't find a quests for map named " + mapName + " and position " +
                            position, e);
        }
    }

    public QuestCompletionActionParameter getCompletionActionParameter()
    {
        return completionActionParameter;
    }

    public void setCompletionActionParameter(
            QuestCompletionActionParameter completionActionParameter)
    {
        this.completionActionParameter = completionActionParameter;
    }

    public QuestCompletionActionType getCompletionActionType()
    {
        return completionActionType;
    }

    public void setCompletionActionType(QuestCompletionActionType completionActionType)
    {
        this.completionActionType = completionActionType;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public void setEasterEgg(boolean isEasterEgg)
    {
        this.isEasterEgg = isEasterEgg;
    }

    public boolean isEasterEgg()
    {
        return isEasterEgg;
    }

    public int getExperiencePointsGained()
    {
        return experiencePointsGained;
    }

    public void setExperiencePointsGained(int experiencePointsGained)
    {
        this.experiencePointsGained = experiencePointsGained;
    }

    public int getObjectivesForFulfillment()
    {
        return objectivesForFulfillment;
    }

    public void setObjectivesForFulfillment(int objectivesForFulfillment)
    {
        this.objectivesForFulfillment = objectivesForFulfillment;
    }

    public String getQuestDescription()
    {
        return questDescription;
    }

    public void setQuestDescription(String questDescription)
    {
        this.questDescription = questDescription;
    }

    public int getQuestID()
    {
        return questID;
    }

    public String getQuestTitle()
    {
        return questTitle;
    }

    public void setQuestTitle(String questTitle)
    {
        this.questTitle = questTitle;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public String getTriggerMapName()
    {
        return triggerMapName;
    }

    public void setTriggerMapName(String triggerMapName)
    {
        this.triggerMapName = triggerMapName;
    }

    public Position getTriggerPosition()
    {
        return triggerPosition;
    }

    public void setTriggerPosition(Position triggerPosition)
    {
        this.triggerPosition = triggerPosition;
    }

    public void persist() throws DatabaseException
    {
        this.connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE Quests SET questTitle = ?, questDescription = ?, triggerMapName = ?, " +
                        "triggerRow = ?, triggerColumn = ?, experiencePointsGained = ?, " +
                        "objectivesForFulfillment = ?, completionActionType = ?, " +
                        "completionActionParameter = ?, startDate = ?, endDate = ?, easterEgg = ? WHERE questID = ?"))
        {
            stmt.setString(1, questTitle);
            stmt.setString(2, questDescription);
            stmt.setString(3, triggerMapName);
            stmt.setInt(4, triggerPosition.getRow());
            stmt.setInt(5, triggerPosition.getColumn());
            stmt.setInt(6, experiencePointsGained);
            stmt.setInt(7, objectivesForFulfillment);
            stmt.setInt(8, completionActionType.getID());
            stmt.setObject(9, completionActionParameter);
            stmt.setDate(10, new java.sql.Date(startDate.getTime()));
            stmt.setDate(11, new java.sql.Date(endDate.getTime()));
            stmt.setBoolean(12, isEasterEgg);
            stmt.setInt(13, questID);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {

            throw new DatabaseException(
                    "Couldn't persist info for quest with ID " + questID, e);
        }

    }

    /**
     * Removes quest from table
     *
     * @throws DatabaseException will if the quest is not found
     * @throws SQLException,     shouldn't
     */
    public void remove() throws DatabaseException
    {
        this.connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement("DELETE from Quests where questID = ?"))
        {
            stmt.setInt(1, questID);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to delete quest: " + questID, e);
        }
    }
}
