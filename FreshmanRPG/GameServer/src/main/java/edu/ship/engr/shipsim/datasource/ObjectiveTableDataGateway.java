package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.criteria.GameLocationDTO;
import edu.ship.engr.shipsim.criteria.ObjectiveCompletionCriteria;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.ObjectiveRecord;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The RDS Implementation of this gateway
 *
 * @author merlin
 */
public class ObjectiveTableDataGateway
{
    private static ObjectiveTableDataGateway singleton;

    /**
     * A private constructor only called by the getSingleton method
     */
    private ObjectiveTableDataGateway()
    {
        //do nothing, this just explicitly makes it private
    }

    /**
     * Create a new row in the table
     *
     * @param objectiveID            the unique ID of the objective
     * @param objectiveDescription   the description of the objective
     * @param questID                the quest that contains the objective
     * @param experiencePointsEarned the number of points you get when you complete
     *                               this objective
     * @param completionType         the type of action the player must do to complete this
     *                               objective
     * @param completionCriteria     the criteria for completing this objective
     * @throws DatabaseException if we can't talk to the RDS
     */
    public static void createRow(int objectiveID, String objectiveDescription,
                                 int questID, int experiencePointsEarned,
                                 ObjectiveCompletionType completionType,
                                 ObjectiveCompletionCriteria completionCriteria)
            throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "Insert INTO Objectives SET objectiveID = ?, objectiveDescription = ?, questID = ?,"
                            +
                            "experiencePointsGained = ?, completionType = ?, completionCriteria = ?");
            stmt.setInt(1, objectiveID);
            stmt.setString(2, objectiveDescription);
            stmt.setInt(3, questID);
            stmt.setInt(4, experiencePointsEarned);
            stmt.setInt(5, completionType.getID());
            stmt.setObject(6, completionCriteria);
            stmt.executeUpdate();

        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't create a objective record for objective with ID " +
                            objectiveID, e);
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
        try
        {
            PreparedStatement stmt =
                    connection.prepareStatement("DROP TABLE IF EXISTS Objectives");
            stmt.executeUpdate();
            stmt.close();

            stmt = connection.prepareStatement(
                    "Create TABLE Objectives (objectiveID INT NOT NULL, objectiveDescription VARCHAR(200), "
                            +
                            "questID INT NOT NULL, experiencePointsGained INT, completionType INT, completionCriteria BLOB, PRIMARY KEY(questID, objectiveID))");
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to create the Objective table", e);
        }
    }

    public static ObjectiveCompletionCriteria extractCompletionCriteria(
            ResultSet queryResult, ObjectiveCompletionType completionType)
            throws SQLException, DatabaseException
    {
        Class<? extends ObjectiveCompletionCriteria> completionCriteriaClass =
                completionType.getCompletionCriteriaType();
        ByteArrayInputStream baip = new ByteArrayInputStream(
                (byte[]) queryResult.getObject("completionCriteria"));
        ObjectiveCompletionCriteria completionCriteria;
        try
        {
            Object x = new ObjectInputStream(baip).readObject();
            completionCriteria = completionCriteriaClass.cast(x);
        }
        catch (ClassNotFoundException | IOException e)
        {
            throw new DatabaseException("Couldn't convert blob to completion criteria ",
                    e);
        }
        return completionCriteria;
    }

    public static ObjectiveTableDataGateway getSingleton()
    {
        if (singleton == null)
        {
            singleton = new ObjectiveTableDataGateway();
        }
        return singleton;
    }

    public ArrayList<ObjectiveRecord> findObjectivesCompletedForMapLocation(
            String mapName, Position pos) throws DatabaseException
    {
        ArrayList<ObjectiveRecord> results = new ArrayList<>();

        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM Objectives WHERE completionType = ?");
            stmt.setInt(1, ObjectiveCompletionType.MOVEMENT.getID());
            ResultSet queryResult = stmt.executeQuery();

            while (queryResult.next())
            {
                ObjectiveRecord rec = buildObjectiveRecord(queryResult);
                GameLocationDTO thisLocation =
                        (GameLocationDTO) rec.getCompletionCriteria();
                if (thisLocation.getPosition().equals(pos) &&
                        thisLocation.getMapName().equals(mapName))
                {
                    results.add(rec);
                }
            }
            return results;
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't find objectives for location at " + mapName + " " +
                            pos.toString(), e);
        }
    }

    public int getNextObjectiveID(int questID) throws DatabaseException
    {
        return getObjectivesForQuest(questID).size() + 1;
    }

    public ObjectiveRecord getObjective(int questID, int objectiveID)
            throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM Objectives WHERE questID = ? and objectiveID = ?");
            stmt.setInt(1, questID);
            stmt.setInt(2, objectiveID);
            ResultSet queryResult = stmt.executeQuery();

            if (queryResult.next())
            {
                return buildObjectiveRecord(queryResult);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't find objective " + objectiveID + " for quest ID " + questID,
                    e);
        }
        return null;
    }

    public ArrayList<ObjectiveRecord> getObjectivesForQuest(int questID)
            throws DatabaseException
    {
        Connection connection = DatabaseManager.getSingleton().getConnection();
        try
        {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM Objectives WHERE questID = ?");
            stmt.setInt(1, questID);
            ResultSet queryResult = stmt.executeQuery();

            ArrayList<ObjectiveRecord> results = new ArrayList<>();
            while (queryResult.next())
            {
                ObjectiveRecord rec = buildObjectiveRecord(queryResult);
                results.add(rec);
            }
            return results;
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Couldn't find objectives for quest ID " + questID, e);
        }
    }

    private ObjectiveRecord buildObjectiveRecord(ResultSet queryResult)
            throws DatabaseException
    {
        try
        {
            ObjectiveCompletionType completionType = ObjectiveCompletionType.findByID(
                    queryResult.getInt("completionType"));
            ObjectiveCompletionCriteria completionCriteria =
                    extractCompletionCriteria(queryResult, completionType);

            return new ObjectiveRecord(queryResult.getInt("questID"),
                    queryResult.getInt("objectiveID"),
                    queryResult.getString("objectiveDescription"), queryResult
                    .getInt("experiencePointsGained"), completionType,
                    completionCriteria);
        }
        catch (SQLException e)
        {
            throw new DatabaseException(
                    "Exception trying to parse the results of reading an objective", e);
        }
    }

}
