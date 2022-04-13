package datasource;


import criteria.AdventureCompletionCriteria;
import dataENUM.AdventureCompletionType;

/**
 * An interface for the a row data gateway for adventures
 */
public interface AdventureRowDataGateway
{
	/**
	 * Reset the mock data
	 */
	public void resetData();

	/**
	 * Return adventure ID.
	 *
	 * @return - adventure ID
	 */
	public int getAdventureId();

	/**
	 * Set adventure ID
	 * @param id to be set
	 */
	public void setAdventureId(int id);

	/**
	 * Get the adventure description
	 * @return a description of the adventure
	 */
	public String getAdventureDescription();

	/**
	 * Set the adventure description
	 * @param description to be set
	 */
	public void setAdventureDescription(String description);

	/**
	 * Return quest ID associated with this adventure.
	 *
	 * @return quest ID
	 */
	public int getQuestID();

	/**
	 * Set quest ID associated with this adventure.
	 *
	 * @param id to be set
	 */
	public void setQuestID(int id);

	/**
	 * Return experience points gained by this adventure.
	 *
	 * @return - experience points gained
	 */
	public int getExperiencePointsGained();

	/**
	 * Set experience points gained by this adventure.
	 *
	 * @param exp - experience points gained
	 */
	public void setExperiencePointsGained(int exp);

	/**
	 * Return adventure completion type.
	 *
	 * @return - adventure completion type
	 */
	public AdventureCompletionType getCompletionType();

	/**
	 * Set adventure completion type.
	 *
	 * @param completionType - adventure completion type
	 */
	public void setCompletionType(AdventureCompletionType completionType);

	/**
	 * Return adventure completion criteria.
	 *
	 * @return - adventure completion criteria
	 */
	public AdventureCompletionCriteria getCompletionCriteria();

	/**
	 * Set adventure completion criteria.
	 *
	 * @param criteria - adventure completion criteria
	 */
	public void setCompletionCriteria(AdventureCompletionCriteria criteria);

	/**
	 * Remove and adventure from the Adventures table.
	 *
	 * @throws DatabaseException - if record not found
	 */
	public void removeAdventure() throws DatabaseException;

	/**
	 * Persist stored data
	 * @throws DatabaseException - if it couldn't update
	 */
	public void persist() throws DatabaseException;
}
