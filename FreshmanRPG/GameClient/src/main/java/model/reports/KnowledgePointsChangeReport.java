package model.reports;

import model.QualifiedObservableReport;

/**
 *
 * @author Matthew Croft
 */
public final class KnowledgePointsChangeReport implements QualifiedObservableReport
{

	private final int knowledge;

	/**
	 * Constructor
	 * @param knowledge experience points
	 */
	public KnowledgePointsChangeReport(int knowledge)
	{
		this.knowledge = knowledge;
	}


	/**
	 * Getter for current experience points
	 * @return experience points
	 */
	public int getKnowledgePoints()
	{
		return knowledge;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + knowledge;
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
		if (knowledge != other.knowledge)
		{
			return false;
		}
		return true;
	}


}
