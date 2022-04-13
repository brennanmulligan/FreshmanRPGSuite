package model.reports;

import datatypes.AdventureStateEnum;
import model.QualifiedObservableReport;

/**
 * @author sl6469, cody
 *
 */
public final class AdventureStateChangeReportInClient implements QualifiedObservableReport
{

	private final int adventureID;
	private final String adventureDescription;
	private final AdventureStateEnum newState;
	private final int questID;
	private final int playerID;

	/**
	 * @param playerID this player's playerID
	 * @param questID the quest this adventure is attached to
	 * @param adventureID unique adventure ID
	 * @param adventureDescription description of adventure
	 * @param newState state the adventure has moved to for this client player
	 */
	public AdventureStateChangeReportInClient(int playerID, int questID, int adventureID, String adventureDescription, AdventureStateEnum newState)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
		this.adventureDescription = adventureDescription;
		this.newState = newState;
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
		AdventureStateChangeReportInClient other = (AdventureStateChangeReportInClient) obj;
		if (adventureDescription == null)
		{
			if (other.adventureDescription != null)
			{
				return false;
			}
		}
		else if (!adventureDescription.equals(other.adventureDescription))
		{
			return false;
		}
		if (adventureID != other.adventureID)
		{
			return false;
		}
		if (newState != other.newState)
		{
			return false;
		}
		if (playerID != other.playerID)
		{
			return false;
		}
		return questID == other.questID;
	}

	/**
	 * @return the description of the adventure whose state has changed
	 */
	public String getAdventureDescription()
	{
		return adventureDescription;
	}


	/**
	 * @return the adventureID that needs the report
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**
	 * @return the state the adventure has moved to
	 */
	public AdventureStateEnum getNewState()
	{
		return newState;
	}

	/**
	 * @return the player id of the player this state is associated with
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return the id of the quest containing the adventure
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((adventureDescription == null) ? 0 : adventureDescription.hashCode());
		result = prime * result + adventureID;
		result = prime * result + ((newState == null) ? 0 : newState.hashCode());
		result = prime * result + playerID;
		result = prime * result + questID;
		return result;
	}

}
