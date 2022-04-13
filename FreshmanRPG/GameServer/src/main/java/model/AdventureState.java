package model;

import datasource.DatabaseException;
import datatypes.AdventureStateEnum;
import datatypes.QuestStateEnum;
import model.reports.AdventureStateChangeReport;

/**
 * Stores the states of all the adventures for an individual player on the
 * server
 *
 * Visibility within this class is VERY important!  Only the model should be able to
 * create these objects and call methods that change their state.  However, model.reports
 * needs access to the data stored in the object, so the getters need to be public.
 *
 * @author Ryan
 *
 */
public class AdventureState
{

	private int adventureID;

	private AdventureStateEnum adventureState;

	private QuestState parentQuestState;

	private boolean needingNotification;

	/**
	 * Constructor for the instance variables.
	 *
	 * @param id : id of adventure
	 * @param state : state of adventure
	 * @param needingNotification true if the player has not been notified that
	 *            we entered this state
	 */
	protected AdventureState(int id, AdventureStateEnum state, boolean needingNotification)
	{
		this.adventureID = id;
		this.adventureState = state;
		this.needingNotification = needingNotification;
	}

	/**
	 * returns the id of the current adventure
	 *
	 * @return the id
	 */
	public int getID()
	{
		return adventureID;
	}

	/**
	 * returns the state of the current adventure.
	 *
	 * @return the state
	 */
	public AdventureStateEnum getState()
	{
		return adventureState;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + adventureID;
		result = prime * result + ((adventureState == null) ? 0 : adventureState.hashCode());
		result = prime * result + (needingNotification ? 1231 : 1237);
		result = prime * result + ((parentQuestState == null) ? 0 : parentQuestState.hashCode());
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
		AdventureState other = (AdventureState) obj;
		if (adventureID != other.adventureID)
		{
			return false;
		}
		if (adventureState != other.adventureState)
		{
			return false;
		}
		if (needingNotification != other.needingNotification)
		{
			return false;
		}
		if (parentQuestState == null)
		{
			if (other.parentQuestState != null)
			{
				return false;
			}
		}
		else if (!parentQuestState.equals(other.parentQuestState))
		{
			return false;
		}
		return true;
	}

	/**
	 * Changes the state of an adventure from hidden to pending.
	 *
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong
	 *             state
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException thrown if error occurred during quest
	 *             state change
	 */
	protected void trigger() throws IllegalAdventureChangeException, DatabaseException, IllegalQuestChangeException
	{

		changeState(AdventureStateEnum.TRIGGERED, false);
	}

	/**
	 * Change the state of the adventure from pending to complete. The adventure
	 * is complete, but we need to tell the player
	 *
	 * @throws DatabaseException if the datasource fails
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong
	 *             state
	 * @throws IllegalQuestChangeException thrown if illegal state change
	 */
	protected void complete() throws DatabaseException, IllegalAdventureChangeException, IllegalQuestChangeException
	{
		changeState(AdventureStateEnum.COMPLETED, true);
		if (parentQuestState.getStateValue() != QuestStateEnum.EXPIRED)
		{
			PlayerManager.getSingleton().getPlayerFromID(this.parentQuestState.getPlayerID())
					.addExperiencePoints(QuestManager.getSingleton()
							.getAdventure(this.parentQuestState.getID(), adventureID).getExperiencePointsGained());
		}
		this.parentQuestState.checkForFulfillmentOrFinished();

	}

	/**
	 * Tell this adventure which quest state it is contained within
	 *
	 * @param questState the parent state
	 */
	public void setParentQuest(QuestState questState)
	{
		parentQuestState = questState;
	}

	/**
	 * Does the player need to be notified about the state of this adventure?
	 *
	 * @return true if notification is required
	 */
	public boolean isNeedingNotification()
	{
		return needingNotification;
	}

	/**
	 * Changes the current states state to the given state and tells it if it
	 * needs to notify the user.
	 *
	 * @param state state to change to
	 * @param needingNotification whether to notify or not
	 * @throws IllegalAdventureChangeException thrown if changing to a wrong
	 *             state
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException thrown if queststatechange error
	 *             occurs
	 */
	protected void changeState(AdventureStateEnum state, boolean needingNotification)
			throws IllegalAdventureChangeException, DatabaseException, IllegalQuestChangeException
	{
		if (this.parentQuestState.getStateValue() == QuestStateEnum.EXPIRED)
		{
			if (!adventureIsExpiredOrCompleted())
			{
				this.adventureState = AdventureStateEnum.EXPIRED;
			}

		}
		else if ((this.adventureState.equals(AdventureStateEnum.HIDDEN) && state.equals(AdventureStateEnum.TRIGGERED))
				|| (this.adventureState.equals(AdventureStateEnum.TRIGGERED)
				&& state.equals(AdventureStateEnum.COMPLETED)))
		{
			this.adventureState = state;
			this.needingNotification = needingNotification;

			if (needingNotification == true)
			{
				AdventureRecord adventure = QuestManager.getSingleton().getAdventure(parentQuestState.getID(),
						adventureID);
				QualifiedObservableConnector.getSingleton().sendReport(
						new AdventureStateChangeReport(parentQuestState.getPlayerID(), parentQuestState.getID(),
								adventureID, adventure.getAdventureDescription(), adventureState,
								adventure.isRealLifeAdventure(), adventure.getCompletionCriteria().toString()));
			}
		}
		else
		{
			throw new IllegalAdventureChangeException(this.adventureState, state);
		}
	}

	private boolean adventureIsExpiredOrCompleted()
	{
		if (this.adventureState == AdventureStateEnum.COMPLETED || this.adventureState == AdventureStateEnum.EXPIRED)
		{
			return true;
		}
		return false;
	}

	/**
	 * Set needing notification to false
	 */
	protected void turnOffNotification()
	{
		this.needingNotification = false;
	}

}
