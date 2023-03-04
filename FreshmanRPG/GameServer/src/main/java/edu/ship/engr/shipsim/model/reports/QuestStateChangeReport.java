package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.PlayerManager;

import java.util.Objects;

/**
 * Sent when a quest changes state
 *
 * @author Merlin
 */
public final class QuestStateChangeReport extends SendMessageReport
{

    private final int questID;
    private final int playerID;
    private final String questDescription;
    private final String questTitle;
    private final QuestStateEnum newState;

    /**
     * @param playerID         the player's unique ID
     * @param questID          the quest's unique ID
     * @param questTitle       this quest's title
     * @param questDescription the description of this quest
     * @param newState         the state the quest has transitioned to for this player
     */
    public QuestStateChangeReport(int playerID, int questID, String questTitle, String questDescription,
                                  QuestStateEnum newState)
    {
        super(playerID, !PlayerManager.getSingleton().isNPC(playerID));
        this.playerID = playerID;
        this.questID = questID;
        this.questTitle = questTitle;
        this.questDescription = questDescription;
        this.newState = newState;
    }

    /**
     * @return the state the quest has moved to
     */
    public QuestStateEnum getNewState()
    {
        return newState;
    }

    /**
     * @return the player whose quest state has changed
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the description of the quest whose state has changed
     */
    public String getQuestDescription()
    {
        return questDescription;
    }

    /**
     * @return the questID that needs the report
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @return this quest's title
     */
    public String getQuestTitle()
    {
        return questTitle;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof QuestStateChangeReport that))
        {
            return false;
        }
        return getQuestID() == that.getQuestID() &&
                getPlayerID() == that.getPlayerID() &&
                Objects.equals(getQuestDescription(),
                        that.getQuestDescription()) &&
                Objects.equals(getQuestTitle(), that.getQuestTitle()) &&
                getNewState() == that.getNewState() &&
                this.getRelevantPlayerID() == that.getRelevantPlayerID() &&
                this.isQuiet() == that.isQuiet();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getQuestID(), getPlayerID(), getQuestDescription(),
                getQuestTitle(), getNewState(), getRelevantPlayerID(), isQuiet());
    }
}
