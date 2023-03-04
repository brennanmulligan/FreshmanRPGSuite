package edu.ship.engr.shipsim.model.reports;

/**
 * @author Ryan
 */
public final class QuestNotificationCompleteReport extends SendMessageReport
{

    private final int playerID;
    private final int questID;

    /**
     * Constructor
     *
     * @param playerID id of the player
     * @param questID  id of the quest
     */
    public QuestNotificationCompleteReport(int playerID, int questID)
    {
        // Happens on client, thus it will always be loud
        super(0, false);

        this.playerID = playerID;
        this.questID = questID;
    }

    /**
     * @return player id
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return quest id
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + playerID;
        result = prime * result + questID;
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        QuestNotificationCompleteReport other = (QuestNotificationCompleteReport) obj;
        if (playerID != other.playerID)
        {
            return false;
        }
        if (questID != other.questID)
        {
            return false;
        }
        return true;
    }


}
