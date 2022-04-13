package model.reports;

import model.QualifiedObservableReport;

/**
 * @author Ryan
 *
 */
public final class AdventureNotificationCompleteReport implements QualifiedObservableReport
{

	private final int playerID;
	private final int questID;
	private final int adventureID;

	/**
	 * Constructor
	 * @param playerID id of the player
	 * @param questID id of the quest
	 * @param adventureID is of the adventure
	 */
	public AdventureNotificationCompleteReport(int playerID, int questID, int adventureID)
	{
		this.playerID = playerID;
		this.questID = questID;
		this.adventureID = adventureID;
	}

	/**
	 * @return player id
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * @return quest id
	 */
	public int getQuestID()
	{
		return questID;
	}

	/**
	 * @return adventure id
	 */
	public int getAdventureID()
	{
		return adventureID;
	}

	/**)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + adventureID;
		result = prime * result + playerID;
		result = prime * result + questID;
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
		AdventureNotificationCompleteReport other = (AdventureNotificationCompleteReport) obj;
		if (adventureID != other.adventureID)
		{
			return false;
		}
		if (playerID != other.playerID)
		{
			return false;
		}
		if (questID != other.questID)
		{
			return false;
		}
		return true;
	}
}
