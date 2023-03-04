package edu.ship.engr.shipsim.model.reports;

public class TerminalTextExitReport extends SendMessageReport
{

    private int playerID;

    public TerminalTextExitReport(int playerID)
    {
        super(playerID, true);
        this.playerID = playerID;
    }

    public int getPlayerID()
    {
        return playerID;
    }

}
