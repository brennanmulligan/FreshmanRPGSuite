package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.model.PlayerManager;

import java.util.Objects;

/**
 * Report to the client that an objective state change had occured.
 *
 * @author nk3668
 */
public final class ObjectiveStateChangeReport extends SendMessageReport
{
    private final int playerID;
    private final int questID;
    private final int objectiveID;
    private final String objectiveDescription;
    private final ObjectiveStateEnum newState;
    private final boolean realLifeObjective;
    private final String witnessTitle;

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

    /**
     * @return player ID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return objective ID
     */
    public int getObjectiveID()
    {
        return objectiveID;
    }

    /**
     * @return objective Description
     */
    public String getObjectiveDescription()
    {
        return objectiveDescription;
    }

    /**
     * @return new State
     */
    public ObjectiveStateEnum getNewState()
    {
        return newState;
    }

    /**
     * @return quest id
     */
    public int getQuestID()
    {
        return questID;
    }

    /**
     * @return true if the player must complete this objective in real life
     */
    public boolean isRealLifeObjective()
    {
        return realLifeObjective;
    }

    /**
     * @return if the player must complete this objective in real life, the
     * title of the person who can witness completion
     */
    public String getWitnessTitle()
    {
        return witnessTitle;
    }

    @Override
    public boolean equals(Object o)
    {

        if (this == o)
        {
            return true;
        }
        if (!(o instanceof ObjectiveStateChangeReport that))
        {
            return false;
        }
        return getPlayerID() == that.getPlayerID() &&
                getQuestID() == that.getQuestID() &&
                getObjectiveID() == that.getObjectiveID() &&
                isRealLifeObjective() == that.isRealLifeObjective() &&
                Objects.equals(getObjectiveDescription(),
                        that.getObjectiveDescription()) &&
                getNewState() == that.getNewState() &&
                Objects.equals(getWitnessTitle(), that.getWitnessTitle()) &&
                this.getRelevantPlayerID() == that.getRelevantPlayerID() &&
                this.isQuiet() == that.isQuiet();
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getPlayerID(), getQuestID(), getObjectiveID(),
                getObjectiveDescription(), getNewState(), isRealLifeObjective(),
                getWitnessTitle(), getRelevantPlayerID(), isQuiet());
    }
}
