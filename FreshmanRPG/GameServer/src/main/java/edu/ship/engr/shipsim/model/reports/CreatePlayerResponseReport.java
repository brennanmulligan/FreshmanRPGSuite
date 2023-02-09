package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

public class CreatePlayerResponseReport implements Report
{
    private final boolean success;

    public CreatePlayerResponseReport(boolean success)
    {
        this.success = success;
    }

    public boolean getSuccess()
    {
        return success;
    }
}
