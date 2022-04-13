package model.reports;

import datatypes.QuestStateEnum;
import model.QualifiedObservableReport;

import java.util.Objects;

/**
 * Sent when a quest changes state
 *
 * @author Merlin
 */
public final class QuestStateChangeReport implements QualifiedObservableReport
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
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        QuestStateChangeReport that = (QuestStateChangeReport) o;
        return questID == that.questID && playerID == that.playerID &&
                Objects.equals(questDescription, that.questDescription) &&
                Objects.equals(questTitle, that.questTitle) && newState == that.newState;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(questID, playerID, questDescription, questTitle, newState);
    }
}
