package api.datasource;

import edu.ship.engr.shipsim.criteria.ObjectiveCompletionCriteria;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DatabaseManager;
import edu.ship.engr.shipsim.datasource.ObjectiveTableDataGateway;
import edu.ship.engr.shipsim.datasource.PlayerRowDataGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * An RDS implementation of ObjectiveRowDataGateway.
 */
public class ObjectiveRowDataGatewayRDS implements ObjectiveRowDataGateway
{
    private Connection connection;
    private int objectiveID;
    private String objectiveDescription;
    private int questId;
    private int experiencePointsGained;
    private ObjectiveCompletionType objectiveCompletionType;
    private ObjectiveCompletionCriteria objectiveCompletionCriteria;

    /**
     * Construct and initialize a new ObjectiveRowDataGatewayRDS. (Finder)
     *
     * @param questId     - quest ID objective belongs to
     * @param objectiveID - ID of this objective
     * @throws DatabaseException - if objective not found
     */
    public ObjectiveRowDataGatewayRDS(int questId, int objectiveID)
            throws DatabaseException
    {
        this.objectiveID = objectiveID;
        this.questId = questId;
        this.connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM Objectives WHERE objectiveID = ? and questID = ?"))
        {
            stmt.setInt(1, objectiveID);
            stmt.setInt(2, questId);

            try (ResultSet result = stmt.executeQuery())
            {
                result.next();
                this.objectiveDescription = result.getString("objectiveDescription");
                this.questId = result.getInt("questID");
                this.experiencePointsGained = result.getInt("experiencePointsGained");
                this.objectiveCompletionType =
                        ObjectiveCompletionType.findByID(result.getInt("completionType"));
                this.objectiveCompletionCriteria =
                        ObjectiveTableDataGateway.extractCompletionCriteria(result,
                                this.objectiveCompletionType);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't find an objective with ID " + this.objectiveID, e);
        }
    }

    /**
     * Construct and initialize a new ObjectiveRowDataGatewayRDS with an objective ID.
     * (Creator)
     *
     * @param objectiveID                 - ID of this objective
     * @param objectiveDescription        - description for objective
     * @param questId                     - quest ID objective belongs to
     * @param experiencePointsGained      - experience points gained upon completion of objective
     * @param objectiveCompletionType     - objective completion type
     * @param objectiveCompletionCriteria - objective completion criteria
     * @throws DatabaseException - if we can't talk to RDS server
     */
    public ObjectiveRowDataGatewayRDS(int objectiveID, String objectiveDescription,
                                      int questId,
                                      int experiencePointsGained,
                                      ObjectiveCompletionType objectiveCompletionType,
                                      ObjectiveCompletionCriteria objectiveCompletionCriteria)
            throws DatabaseException
    {
        this.connection = DatabaseManager.getSingleton().getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(
                "Insert INTO Objectives SET objectiveID = ?, objectiveDescription = ?, questID = ?, " +
                        "experiencePointsGained = ?, completionType = ?, completionCriteria = ?"))
        {
            stmt.setInt(1, objectiveID);
            stmt.setString(2, objectiveDescription);
            stmt.setInt(3, questId);
            stmt.setInt(4, experiencePointsGained);
            stmt.setInt(5, objectiveCompletionType.getID());
            stmt.setObject(6, objectiveCompletionCriteria);
            stmt.executeUpdate();

            this.objectiveID = objectiveID;
            this.objectiveDescription = objectiveDescription;
            this.questId = questId;
            this.experiencePointsGained = experiencePointsGained;
            this.objectiveCompletionType = objectiveCompletionType;
            this.objectiveCompletionCriteria = objectiveCompletionCriteria;

        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't create and objective record for objective with "
                            + "objective ID of " + objectiveID + " and quest ID of " +
                            questId, e);
        }
    }

    /**
     * Construct and initialize a new ObjectiveRowDataGatewayRDS. (Creator)
     *
     * @param objectiveDescription        - description for objective
     * @param questId                     - quest ID objective belongs to
     * @param experiencePointsGained      - experience points gained upon completion of objective
     * @param objectiveCompletionType     - objective completion type
     * @param objectiveCompletionCriteria - objective completion criteria
     * @throws DatabaseException - if we can't talk to RDS server
     */
    public ObjectiveRowDataGatewayRDS(String objectiveDescription, int questId,
                                      int experiencePointsGained,
                                      ObjectiveCompletionType objectiveCompletionType,
                                      ObjectiveCompletionCriteria objectiveCompletionCriteria)
            throws DatabaseException
    {
        this(ObjectiveTableDataGateway.getSingleton().getNextObjectiveID(questId),
                objectiveDescription, questId, experiencePointsGained,
                objectiveCompletionType,
                objectiveCompletionCriteria);
    }

    /**
     * @see ObjectiveRowDataGateway#getCompletionCriteria()
     */
    @Override
    public ObjectiveCompletionCriteria getCompletionCriteria()
    {
        return objectiveCompletionCriteria;
    }

    /**
     * @see ObjectiveRowDataGateway#setCompletionCriteria(ObjectiveCompletionCriteria)
     */
    @Override
    public void setCompletionCriteria(ObjectiveCompletionCriteria criteria)
    {
        this.objectiveCompletionCriteria = criteria;
    }

    /**
     * @see ObjectiveRowDataGateway#getCompletionType()
     */
    @Override
    public ObjectiveCompletionType getCompletionType()
    {
        return objectiveCompletionType;
    }

    /**
     * @see ObjectiveRowDataGateway#setCompletionType(ObjectiveCompletionType)
     */
    @Override
    public void setCompletionType(ObjectiveCompletionType completionType)
    {
        this.objectiveCompletionType = completionType;
    }

    /**
     * @see ObjectiveRowDataGateway#getExperiencePointsGained()
     */
    @Override
    public int getExperiencePointsGained()
    {
        return experiencePointsGained;
    }

    /**
     * @see ObjectiveRowDataGateway#setExperiencePointsGained(int)
     */
    @Override
    public void setExperiencePointsGained(int exp)
    {
        this.experiencePointsGained = exp;

    }

    /**
     * @see ObjectiveRowDataGateway#getObjectiveDescription()
     */
    @Override
    public String getObjectiveDescription()
    {
        return objectiveDescription;
    }

    /**
     * @see ObjectiveRowDataGateway#setObjectiveDescription(java.lang.String)
     */
    @Override
    public void setObjectiveDescription(String description)
    {
        this.objectiveDescription = description;

    }

    /**
     * @see ObjectiveRowDataGateway#getObjectiveID()
     */
    public int getObjectiveID()
    {
        return objectiveID;
    }

    /**
     * @see ObjectiveRowDataGateway#setObjectiveID(int)
     */
    @Override
    public void setObjectiveID(int id)
    {
        this.objectiveID = id;

    }

    /**
     * @see ObjectiveRowDataGateway#getQuestID()
     */
    @Override
    public int getQuestID()
    {
        return questId;
    }

    /**
     * @see ObjectiveRowDataGateway#setQuestID(int)
     */
    @Override
    public void setQuestID(int id)
    {
        this.questId = id;
    }

    /**
     * @see PlayerRowDataGateway#persist()
     */
    @Override
    public void persist() throws DatabaseException
    {

        this.connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(
                "UPDATE Objectives SET objectiveDescription = ?," +
                        "experiencePointsGained = ?, completionType = ?, completionCriteria = ? WHERE objectiveID = ? AND questID = ?");)
        {
            stmt.setString(1, objectiveDescription);
            stmt.setInt(2, experiencePointsGained);
            stmt.setInt(3, objectiveCompletionType.getID());
            stmt.setObject(4, objectiveCompletionCriteria);
            stmt.setInt(5, objectiveID);
            stmt.setInt(6, questId);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't update the objective record for objective with "
                            + "objective ID of " + objectiveID + " and quest ID of " +
                            questId, e);
        }

    }

    /**
     * @see ObjectiveRowDataGateway#removeObjective()
     */
    @Override
    public void removeObjective() throws DatabaseException
    {
        this.connection = DatabaseManager.getSingleton().getConnection();

        try (PreparedStatement stmt = connection.prepareStatement(
                "DELETE from Objectives where questID = ? and objectiveID = ?"))
        {
            stmt.setInt(1, questId);
            stmt.setInt(2, objectiveID);
            stmt.execute();
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Unable to remove objective with ID " + this.objectiveID, e);
        }
    }

    /**
     * @see ObjectiveRowDataGateway#resetData()
     */
    @Override
    public void resetData()
    {
        // do nothing
    }

}
