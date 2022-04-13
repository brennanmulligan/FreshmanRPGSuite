package model.reports;

import java.util.ArrayList;

import dataDTO.KnowledgePointPrizeDTO;
import model.QualifiedObservableReport;

public final class KnowledgePointPrizeListReport implements QualifiedObservableReport
{
	private final ArrayList<KnowledgePointPrizeDTO> knowledgePointPrizes;

	/**
	 * The report constructor
	 * @param knowledgePointPrizes - The List of KnowledgePointPrizes
	 */
	public KnowledgePointPrizeListReport(ArrayList<KnowledgePointPrizeDTO> knowledgePointPrizes)
	{
		this.knowledgePointPrizes = knowledgePointPrizes;
	}


	/**
	 * Gets the list of DTOs
	 * @return knowledgePointPrizes - the list of DTOs 
	 */
	public ArrayList<KnowledgePointPrizeDTO> getPrizes()
	{
		return knowledgePointPrizes;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((knowledgePointPrizes == null) ? 0 : knowledgePointPrizes.hashCode());
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
		if (!(obj instanceof KnowledgePointPrizeListReport))
		{
			return false;
		}
		KnowledgePointPrizeListReport other = (KnowledgePointPrizeListReport) obj;
		if (knowledgePointPrizes == null)
		{
			if (other.knowledgePointPrizes != null)
			{
				return false;
			}
		}
		else if (!knowledgePointPrizes.equals(other.knowledgePointPrizes))
		{
			return false;
		}
		return true;
	}


}
