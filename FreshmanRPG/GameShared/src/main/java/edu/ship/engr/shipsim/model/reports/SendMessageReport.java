package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class SendMessageReport implements Report
{
    private final int relevantPlayerID;
    private final boolean isQuiet;

    /**
     * @param relevantPlayerID the ID of the player associated with the message
     */
    public SendMessageReport(int relevantPlayerID, boolean isQuiet)
    {
        this.relevantPlayerID = relevantPlayerID;
        this.isQuiet = isQuiet;
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
    public final boolean isQuiet()
    {
        return isQuiet;
    }
}
