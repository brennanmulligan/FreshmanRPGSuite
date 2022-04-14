package model.reports;

import java.util.ArrayList;

import dataDTO.DoubloonPrizeDTO;
import model.QualifiedObservableReport;

/**
 *
 * @author Andrew M, Christian C
 *
 * This is the doubloon prize report
 *
 */
public class DoubloonPrizeReport implements QualifiedObservableReport
{


	private ArrayList<DoubloonPrizeDTO> doubloonPrizes = new ArrayList<>();
	private int playerID;

	/**
	 * The report constructor
	 * @param doubloonPrizes
	 */
	public DoubloonPrizeReport(int playerID, ArrayList<DoubloonPrizeDTO> doubloonPrizes)
	{
		this.doubloonPrizes = doubloonPrizes;
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
	public ArrayList<DoubloonPrizeDTO> getPrizes()
	{
		return doubloonPrizes;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((doubloonPrizes == null) ? 0 : doubloonPrizes.hashCode());
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
		DoubloonPrizeReport other = (DoubloonPrizeReport) obj;
		if (doubloonPrizes == null)
		{
			if (other.doubloonPrizes != null)
			{
				return false;
			}
		}
		else if (!doubloonPrizes.equals(other.doubloonPrizes))
		{
			return false;
		}
		return true;
	}
}
