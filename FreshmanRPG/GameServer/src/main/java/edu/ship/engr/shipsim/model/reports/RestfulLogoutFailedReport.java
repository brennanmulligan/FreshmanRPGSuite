package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * @author Derek
 */
public final class RestfulLogoutFailedReport implements Report
{
    private final String message;

    public RestfulLogoutFailedReport(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
