package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * @author bl5922 SendTerminalTextReport that gets sent from CommandSendTerminalText
 */
public class SendTerminalTextReport implements Report
{

    private int playerID = 0;
    private String terminalText;

    /**
     * @param playerID from the CommandSendTerminalText
     * @param text     from the CommandSendTerminalText
     */
    public SendTerminalTextReport(int playerID, String text)
    {
        this.playerID = playerID;
        this.terminalText = text;

    }

    /**
     * @return playerID
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return terminalText
     */
    public String getTerminalText()
    {
        return terminalText;
    }
}
