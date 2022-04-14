package datasource;


import criteria.ObjectiveCompletionCriteria;
import dataENUM.ObjectiveCompletionType;

/**
 * An interface for the a row data gateway for objectives
 */
public interface ObjectiveRowDataGateway
{
	/**
	 * Reset the mock data
	 */
	public void resetData();

	/**
	 * Return objective ID.
	 *
	 * @return - objective ID
	 */
	public int getObjectiveID();

	/**
	 * Set objective ID
	 * @param id to be set
	 */
	public void setObjectiveID(int id);

	/**
	 * Get the objective description
	 * @return a description of the objective
	 */
	public String getObjectiveDescription();

	/**
	 * Set the objective description
	 * @param description to be set
	 */
	public void setObjectiveDescription(String description);

	/**
	 * Return quest ID associated with this objective.
	 *
	 * @return quest ID
	 */
	public int getQuestID();

	/**
	 * Set quest ID associated with this objective.
	 *
	 * @param id to be set
	 */
	public void setQuestID(int id);

	/**
	 * Return experience points gained by this objective.
	 *
	 * @return - experience points gained
	 */
	public int getExperiencePointsGained();

	/**
	 * Set experience points gained by this objective.
	 *
	 * @param exp - experience points gained
	 */
	public void setExperiencePointsGained(int exp);

	/**
	 * Return objective completion type.
	 *
	 * @return - objective completion type
	 */
	public ObjectiveCompletionType getCompletionType();

	/**
	 * Set objective completion type.
	 *
	 * @param completionType - objective completion type
	 */
	public void setCompletionType(ObjectiveCompletionType completionType);

	/**
	 * Return objective completion criteria.
	 *
	 * @return - objective completion criteria
	 */
	public ObjectiveCompletionCriteria getCompletionCriteria();

	/**
	 * Set objective completion criteria.
	 *
	 * @param criteria - objective completion criteria
	 */
	public void setCompletionCriteria(ObjectiveCompletionCriteria criteria);

	/**
	 * Remove an objective from the Objectives table.
	 *
	 * @throws DatabaseException - if record not found
	 */
	public void removeObjective() throws DatabaseException;

	/**
	 * Persist stored data
	 * @throws DatabaseException - if it couldn't update
	 */
	public void persist() throws DatabaseException;
}
