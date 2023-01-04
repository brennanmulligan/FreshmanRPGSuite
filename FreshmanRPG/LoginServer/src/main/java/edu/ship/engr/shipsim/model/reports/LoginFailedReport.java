package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * @author Derek
 */
public class LoginFailedReport implements Report
{
    private final String message;

    public LoginFailedReport(String message)
    {
        this.message = message;
    }

    public String getMessage()
    {
        return message;
    }
}
