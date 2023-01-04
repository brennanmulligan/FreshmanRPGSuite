package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * @author Derek
 */
public final class RestfulLoginFailedReport implements Report
{
    private final String message;

    public RestfulLoginFailedReport(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
