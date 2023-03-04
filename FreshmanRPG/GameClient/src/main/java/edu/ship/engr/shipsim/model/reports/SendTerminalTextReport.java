package edu.ship.engr.shipsim.model.reports;

/**
 * @author bl5922 SendTerminalTextReport that gets sent from CommandSendTerminalText
 */
public class SendTerminalTextReport extends SendMessageReport
{

    private int playerID = 0;
    private String terminalText;

    /**
     * @param playerID from the CommandSendTerminalText
     * @param text     from the CommandSendTerminalText
     */
    public SendTerminalTextReport(int playerID, String text)
    {
        // Happens on client, thus it will always be loud
        super(0, false);
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
