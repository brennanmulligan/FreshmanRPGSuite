package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.model.PlayerManager;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Report to the client that an objective state change had occured.
 *
 * @author nk3668
 */
@EqualsAndHashCode(callSuper = true)
public final class ObjectiveStateChangeReport extends SendMessageReport
{
    @Getter private final int playerID;
    @Getter private final int questID;
    @Getter private final int objectiveID;
    @Getter private final String objectiveDescription;
    @Getter private final ObjectiveStateEnum newState;
    @Getter private final boolean realLifeObjective;
    @Getter private final String witnessTitle;

    /**
     * @param id                   players ID
     * @param questID              id of the quest
     * @param objectiveID          objectives ID to change state
     * @param objectiveDescription description of objective
     * @param newState             new state to be changed to
     * @param realLifeObjective    true if the player must complete this objective
     *                             outside of the game
     * @param witnessTitle         the title of the person who can witness completion if
     *                             this is a real life objective
     */
    public ObjectiveStateChangeReport(int id, int questID, int objectiveID, String objectiveDescription,
                                      ObjectiveStateEnum newState, boolean realLifeObjective, String witnessTitle)
    {
        super(id, !PlayerManager.getSingleton().isNPC(id));
        this.playerID = id;
        this.questID = questID;
        this.objectiveID = objectiveID;
        this.objectiveDescription = objectiveDescription;
        this.newState = newState;
        this.realLifeObjective = realLifeObjective;
        this.witnessTitle = witnessTitle;
    }
}
