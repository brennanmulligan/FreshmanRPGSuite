package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

public abstract class SendMessageReport implements Report
{
    private int relevantPlayerID;

    /**
     * @param relevantPlayerID the ID of the player associated with the message
     */
    public SendMessageReport(int relevantPlayerID)
    {
        this.relevantPlayerID = relevantPlayerID;
    }

    /**
     * @return relevantPlayerID
     */
    public int getRelevantPlayerID()
    {
        return this.relevantPlayerID;
    }

    /**
     * @return isQuiet
     */
    public abstract boolean isQuiet();
}
