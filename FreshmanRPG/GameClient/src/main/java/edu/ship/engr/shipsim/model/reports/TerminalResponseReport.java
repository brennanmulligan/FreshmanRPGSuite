package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * A report that handles recieving the Terminal Response
 *
 * @author Nathaniel, Allen
 */
public class TerminalResponseReport implements Report
{
    private int playerID;
    private String terminalResult;

    /**
     * @param playerID       who is getting the results of the command.
     * @param terminalResult the results of what the command has done.
     */
    public TerminalResponseReport(int playerID, String terminalResult)
    {
        this.playerID = playerID;
        this.terminalResult = terminalResult;
    }

    /**
     * @return who sent the command
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the results of the terminal command
     */
    public String getTerminalResult()
    {
        return terminalResult;
    }


    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + playerID;
        result = prime * result + ((terminalResult == null) ? 0 : terminalResult.hashCode());
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
        TerminalResponseReport other = (TerminalResponseReport) obj;
        if (playerID != other.playerID)
        {
            return false;
        }
        if (terminalResult == null)
        {
            if (other.terminalResult != null)
            {
                return false;
            }
        }
        else if (!terminalResult.equals(other.terminalResult))
        {
            return false;
        }
        return true;
    }


}
