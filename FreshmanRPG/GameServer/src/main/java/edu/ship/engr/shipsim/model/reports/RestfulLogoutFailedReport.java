package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.QualifiedObservableReport;

/**
 * @author Derek
 */
public final class RestfulLogoutFailedReport implements QualifiedObservableReport
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
