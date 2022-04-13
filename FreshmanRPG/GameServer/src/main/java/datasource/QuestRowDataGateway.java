package datasource;

import java.sql.SQLException;
import java.util.Date;

import criteria.QuestCompletionActionParameter;
import dataENUM.QuestCompletionActionType;
import datatypes.Position;

/**
 * A row data gateway for the quest table
 *
 * @author merlin
 *
 */
public interface QuestRowDataGateway
{

	/**
	 * Used for testing - tells the mock version to reset its data back to the
	 * original state
	 */
	public void resetData();

	/**
	 * @return the unique quest ID for the row this gateway is managing
	 */
	public int getQuestID();

	/**
	 * @return the description for the quest this gateway is managing
	 */
	public String getQuestDescription();

	/**
	 *
	 * @return the name of the map that contains the trigger point for this
	 *         quest
	 */
	public String getTriggerMapName();

	/**
	 *
	 * @return the position that should trigger this quest
	 */
	public Position getTriggerPosition();

	/**
	 * @return the number of adventures you must complete to fulfill this quest
	 */
	public int getAdventuresForFulfillment();

	/**
	 * @return the number of experience points you gain when you fulfill this
	 *         quest
	 */
	int getExperiencePointsGained();

	/**
	 * @return the type of action that should be taken when this quest is
	 *         complete
	 */
	public QuestCompletionActionType getCompletionActionType();

	/**
	 * @return an object describing the details of the completion action for
	 *         this quest
	 */
	public QuestCompletionActionParameter getCompletionActionParameter();

	/**
	 *
	 * @return the title of this quest
	 */
	public String getQuestTitle();

	/**
	 * @return the first day the quest is available
	 */
	public Date getStartDate();

	/**
	 * @return the last day the quest is available
	 */
	public Date getEndDate();

	/**
	 * Store this information into the data source
	 *
	 * @throws DatabaseException if we can't persist the data to the data source
	 */
	public void persist() throws DatabaseException;

	/**
	 * @param questTitle title of the quest
	 */
	public void setQuestTitle(String questTitle);


	/**
	 * @param questDescription description of quest
	 */
	public void setQuestDescription(String questDescription);


	/**
	 * @param triggerMapName map that triggers quest
	 */
	public void setTriggerMapName(String triggerMapName);


	/**
	 * @param triggerPosition position that triggers map
	 */
	public void setTriggerPosition(Position triggerPosition);


	/**
	 * @param experiencePointsGained experience gained by completing quest
	 */
	public void setExperiencePointsGained(int experiencePointsGained);


	/**
	 * @param adventuresForFulfillment number of adventures to fulfill quest
	 */
	public void setAdventuresForFulfillment(int adventuresForFulfillment);


	/**
	 * @param completionActionParameter the action to complete quest
	 */
	public void setCompletionActionParameter(QuestCompletionActionParameter completionActionParameter);


	/**
	 * @param completionActionType type of action to complete quest
	 */
	public void setCompletionActionType(QuestCompletionActionType completionActionType);


	/**
	 * @param startDate start date of quest
	 */
	public void setStartDate(Date startDate);

	/**
	 * @param endDate end date of quest
	 */
	public void setEndDate(Date endDate);

	/**
	 * Removes the row from the table
	 * @throws DatabaseException - should only throw if removing an already deleted row or a row that does not exist`
	 * @throws SQLException - shouldn't 
	 */
	public void remove() throws DatabaseException, SQLException;
}
