package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;
import edu.ship.engr.shipsim.model.Report;

import java.util.ArrayList;

public final class DoubloonPrizeListReport implements Report
{
    private final ArrayList<DoubloonPrizeDTO> doubloonPrizes;

    /**
     * The report constructor
     *
     * @param DoubloonPrizes - The List of DoubloonPrizes
     */
    public DoubloonPrizeListReport(ArrayList<DoubloonPrizeDTO> DoubloonPrizes)
    {
        this.doubloonPrizes = DoubloonPrizes;
    }


    /**
     * Gets the list of DTOs
     *
     * @return doubloonPrizes - the list of DTOs
     */
    public ArrayList<DoubloonPrizeDTO> getPrizes()
    {
        return doubloonPrizes;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((doubloonPrizes == null) ? 0 : doubloonPrizes.hashCode());
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
        if (!(obj instanceof DoubloonPrizeListReport))
        {
            return false;
        }
        DoubloonPrizeListReport other = (DoubloonPrizeListReport) obj;
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
