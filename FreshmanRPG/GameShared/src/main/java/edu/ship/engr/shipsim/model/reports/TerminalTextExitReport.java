package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

public class TerminalTextExitReport implements Report
{

    private int playerID;

    public TerminalTextExitReport(int playerID)
    {
        this.playerID = playerID;
    }

    public int getPlayerID()
    {
        return playerID;
    }

}
