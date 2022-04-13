package model.reports;

import java.util.ArrayList;

import dataDTO.KnowledgePointPrizeDTO;
import datasource.KnowledgePointPrizesTableDataGatewayRDS;
import model.QualifiedObservableReport;

/**
 *
 * @author Andrew M, Christian C
 *
 * This is the knowledge point prize report
 *
 */
public class KnowledgePointPrizeReport implements QualifiedObservableReport
{


	private ArrayList<KnowledgePointPrizeDTO> knowledgePointPrizes = new ArrayList<>();
	private int playerID;

	/**
	 * The report constructor
	 * @param knowledgePointPrizes
	 */
	public KnowledgePointPrizeReport(int playerID, ArrayList<KnowledgePointPrizeDTO> knowledgePointPrizes)
	{
		this.knowledgePointPrizes = knowledgePointPrizes;
		this.playerID = playerID;
	}

	/**
	 * Gets the player ID
	 * @return playerID
	 */
	public int getPlayerID()
	{
		return playerID;
	}


	/**
	 * Gets the list of DTOs
	 * @return list
	 */
	public ArrayList<KnowledgePointPrizeDTO> getPrizes()
	{
		return knowledgePointPrizes;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((knowledgePointPrizes == null) ? 0 : knowledgePointPrizes.hashCode());
		return result;
	}

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
		KnowledgePointPrizeReport other = (KnowledgePointPrizeReport) obj;
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
