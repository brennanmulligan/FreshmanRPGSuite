package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.QualifiedObservableReport;

public class TerminalTextExitReport implements QualifiedObservableReport
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
