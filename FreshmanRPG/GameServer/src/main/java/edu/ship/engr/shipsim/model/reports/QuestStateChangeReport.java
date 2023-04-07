package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.model.PlayerManager;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Sent when a quest changes state
 *
 * @author Merlin
 */
@EqualsAndHashCode(callSuper = true)
public final class QuestStateChangeReport extends SendMessageReport
{
    @Getter private final int questID;
    @Getter private final int playerID;
    @Getter private final String questDescription;
    @Getter private final String questTitle;
    @Getter private final QuestStateEnum newState;

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
}
