package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * @author Matthew Croft
 */
public final class DoubloonChangeReport implements Report
{

    private final int doubloons;

    /**
     * Constructor
     *
     * @param doubloons
     */
    public DoubloonChangeReport(int doubloons)
    {
        this.doubloons = doubloons;
    }


    /**
     * Getter for current doubloons
     *
     * @return doubloons
     */
    public int getDoubloons()
    {
        return doubloons;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + doubloons;
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
        DoubloonChangeReport other = (DoubloonChangeReport) obj;
        if (doubloons != other.doubloons)
        {
            return false;
        }
        return true;
    }


}
