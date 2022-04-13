package model.reports;

import model.QualifiedObservableReport;

/**
 * @author Matthew Croft
 *
 */
public final class KnowledgePointsChangeReport implements QualifiedObservableReport
{

	/**
	 *
	 */
	public final int knowledgePoints;

	private final int playerID;

	private final int buffPool;


	/**
	 * @param playerID of the current player
	 * @param knowledgePoints of the player
	 * @param buffPool the current value of the players remaining bonus points
	 */
	public KnowledgePointsChangeReport(int playerID, int knowledgePoints, int buffPool)
	{
		this.playerID = playerID;
		this.knowledgePoints = knowledgePoints;
		this.buffPool = buffPool;
	}

	/**
	 * @return knowledgePoints
	 */
	public int getKnowledgePoints()
	{
		return knowledgePoints;
	}

	/**
	 * @return playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * gets buff pool
	 * @return buffPool
	 */
	public int getBuffPool()
	{
		return this.buffPool;
	}


	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + knowledgePoints;
		result = prime * result + playerID;
		result = prime * result + this.buffPool;
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
		KnowledgePointsChangeReport other = (KnowledgePointsChangeReport) obj;
		if (knowledgePoints != other.knowledgePoints)
		{
			return false;
		}
		if (this.buffPool != other.getBuffPool())
		{
			return false;
		}
		if (playerID != other.playerID)
		{
			return false;
		}
		return true;
	}

}
