package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

import java.util.Objects;

public class QuestCreatedOrUpdatedReport implements Report
{
    private final boolean successful;

    public QuestCreatedOrUpdatedReport(boolean successful)
    {
        this.successful = successful;
    }

    public boolean getSuccess()
    {
        return this.successful;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        QuestCreatedOrUpdatedReport that = (QuestCreatedOrUpdatedReport) o;
        return successful == that.successful;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(successful);
    }
}
