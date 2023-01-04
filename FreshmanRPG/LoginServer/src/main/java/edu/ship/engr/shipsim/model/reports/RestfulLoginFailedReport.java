package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

import java.util.Objects;

/**
 * @author Derek
 */
public class RestfulLoginFailedReport implements Report
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null)
        {
            return false;
        }

        if (getClass() != o.getClass())
        {
            return false;
        }

        RestfulLoginFailedReport otherReport = (RestfulLoginFailedReport) o;

        return Objects.equals(message, otherReport.getMessage());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(message);
    }
}
