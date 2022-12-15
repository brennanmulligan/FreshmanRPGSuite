package api.model;

import java.util.Objects;

/**
 * Objective Authentication Object
 *
 * @author Joel
 */
public class ObjectiveRequest
{

    private double longitude;
    private double latitude;
    private int questID;
    private int objectiveID;
    private int playerID;

    /**
     * Create an ObjectiveAuthentication
     *
     * @param longitude   coordinate from where request was made
     * @param latitude    coordinate from where request was made
     * @param questID     quest id number
     * @param objectiveID objective (previously objective) id number
     * @param playerID    player id number
     */
    public ObjectiveRequest(double longitude, double latitude, int questID, int objectiveID, int playerID)
    {
        this.longitude = longitude;
        this.latitude = latitude;
        this.questID = questID;
        this.objectiveID = objectiveID;
        this.playerID = playerID;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public int getQuestID()
    {
        return questID;
    }

    public int getObjectiveID()
    {
        return objectiveID;
    }

    public int getPlayerID()
    {
        return playerID;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public void setQuestID(int questID)
    {
        this.questID = questID;
    }

    public void setObjectiveID(int objectiveID)
    {
        this.objectiveID = objectiveID;
    }

    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }

    @Override
    public String toString()
    {
        return "ObjectiveAuthentication{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", questID=" + questID +
                ", objectiveID=" + objectiveID +
                ", playerID=" + playerID +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        ObjectiveRequest that = (ObjectiveRequest) o;
        return Double.compare(that.getLongitude(), getLongitude()) == 0 && Double.compare(that.getLatitude(), getLatitude()) == 0 && getQuestID() == that.getQuestID() && getObjectiveID() == that.getObjectiveID() && getPlayerID() == that.getPlayerID();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getLongitude(), getLatitude(), getQuestID(), getObjectiveID(), getPlayerID());
    }
}


