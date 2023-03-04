package edu.ship.engr.shipsim.model.reports;

import java.util.Objects;

/**
 * @author Derek
 */
public class RestfulLoginServerFailedReport extends SendMessageReport
{

    private final String message;

    public RestfulLoginServerFailedReport(String message)
    {
        super(0, false);
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

        RestfulLoginServerFailedReport
                otherReport = (RestfulLoginServerFailedReport) o;

        return Objects.equals(message, otherReport.getMessage());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(message);
    }
}
