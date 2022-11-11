package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.QualifiedObservableReport;

/**
 * @author Derek
 */
public final class RestfulLoginFailedReport implements QualifiedObservableReport
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
