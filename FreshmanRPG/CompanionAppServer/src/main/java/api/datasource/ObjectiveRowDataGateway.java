package api.datasource;

import edu.ship.engr.shipsim.criteria.ObjectiveCompletionCriteria;
import edu.ship.engr.shipsim.dataENUM.ObjectiveCompletionType;
import edu.ship.engr.shipsim.datasource.DatabaseException;

/**
 * An interface for the a row data gateway for objectives
 */
public interface ObjectiveRowDataGateway
{
    /**
     * Reset the mock data
     */
    void resetData();

    /**
     * Return objective ID.
     *
     * @return - objective ID
     */
    int getObjectiveID();

    /**
     * Set objective ID
     *
     * @param id to be set
     */
    void setObjectiveID(int id);

    /**
     * Get the objective description
     *
     * @return a description of the objective
     */
    String getObjectiveDescription();

    /**
     * Set the objective description
     *
     * @param description to be set
     */
    void setObjectiveDescription(String description);

    /**
     * Return quest ID associated with this objective.
     *
     * @return quest ID
     */
    int getQuestID();

    /**
     * Set quest ID associated with this objective.
     *
     * @param id to be set
     */
    void setQuestID(int id);

    /**
     * Return experience points gained by this objective.
     *
     * @return - experience points gained
     */
    int getExperiencePointsGained();

    /**
     * Set experience points gained by this objective.
     *
     * @param exp - experience points gained
     */
    void setExperiencePointsGained(int exp);

    /**
     * Return objective completion type.
     *
     * @return - objective completion type
     */
    ObjectiveCompletionType getCompletionType();

    /**
     * Set objective completion type.
     *
     * @param completionType - objective completion type
     */
    void setCompletionType(ObjectiveCompletionType completionType);

    /**
     * Return objective completion criteria.
     *
     * @return - objective completion criteria
     */
    ObjectiveCompletionCriteria getCompletionCriteria();

    /**
     * Set objective completion criteria.
     *
     * @param criteria - objective completion criteria
     */
    void setCompletionCriteria(ObjectiveCompletionCriteria criteria);

    /**
     * Remove an objective from the Objectives table.
     *
     * @throws DatabaseException - if record not found
     */
    void removeObjective() throws DatabaseException;

    /**
     * Persist stored data
     *
     * @throws DatabaseException - if it couldn't update
     */
    void persist() throws DatabaseException;
}
