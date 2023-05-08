package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.criteria.ObjectiveCompletionCriteria;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;
import edu.ship.engr.shipsim.model.Command;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ObjectiveRowDataGateway implements Serializable
{

        private int objectiveId;
        private String objectiveDescription;
        private int questId;
        private int experiencePointsEarned;

        private ObjectiveCompletionType completionType;
        private ObjectiveCompletionCriteria completionCriteria;

        private Connection connection;

    /**
     * Finder constructor for ObjectiveRowDataGateway
     * @param objectiveId - ID of objective being found
     * @throws DatabaseException - Throws when error occurs in database
     */
    public ObjectiveRowDataGateway(int objectiveId, int questId) throws DatabaseException
    {
        this.objectiveId = objectiveId;
        this.connection = DatabaseManager.getSingleton().getConnection();
        try(PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Objectives WHERE objectiveId = ? AND questId = ?"))
        {
            stmt.setInt(1, objectiveId);
            stmt.setInt(2, questId);

            try (ResultSet result = stmt.executeQuery())
            {
                result.next();

                this.objectiveDescription = result.getString("objectiveDescription");
                this.questId = result.getInt("questID");
                this.experiencePointsEarned = result.getInt("experiencePointsGained");
                this.completionType = ObjectiveCompletionType.findByID(result.getInt("completionType"));
                this.completionCriteria = buildCompletionCriteria(result);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Couldn't find objective with that ID", e);
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

        try (PreparedStatement stmt = connection.prepareStatement("DELETE from Objectives where objectiveId = ?"))
        {
            stmt.setInt(1, objectiveId);
            stmt.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Unable to delete quest: " + objectiveId, e);
        }
    }

    private ObjectiveCompletionCriteria buildCompletionCriteria(ResultSet queryResult)
            throws SQLException
    {
        ObjectiveCompletionCriteria criteria;
        ByteArrayInputStream baip;
        try
        {
            baip = new ByteArrayInputStream((byte[]) queryResult.getObject("completionCriteria"));
        }
        catch (NullPointerException e)
        {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(baip))
        {
            Object x = ois.readObject();
            criteria = (ObjectiveCompletionCriteria) x;
        }
        catch (IOException | ClassNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        return criteria;
    }

    public int getObjectiveId()
    {
        return objectiveId;
    }

    public String getObjectiveDescription()
    {
        return objectiveDescription;
    }

    public void setObjectiveDescription(String description)
    {
        objectiveDescription = description;
    }

    public int getQuestId()
    {
        return questId;
    }

    public int getExperiencePointsEarned()
    {
        return experiencePointsEarned;
    }

    public void setExperiencePointsEarned(int experiencePointsEarned)
    {
        this.experiencePointsEarned = experiencePointsEarned;
    }

    public void setCompletionType(ObjectiveCompletionType completionType)
    {
        this.completionType = completionType;
    }

    public ObjectiveCompletionType getCompletionType()
    {
        return completionType;
    }

    public void setCompletionCriteria(ObjectiveCompletionCriteria completionCriteria)
    {
        this.completionCriteria = completionCriteria;
    }

    public ObjectiveCompletionCriteria getCompletionCriteria()
    {
        return completionCriteria;
    }

    


}
