package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.SendTerminalTextReport;

/**
 * @author bl5922 Command thats called when someone wants all the online players
 */
public class CommandSendTerminalText extends Command
{
    private final String terminalText;

    /**
     * @param text from the terminal
     */
    public CommandSendTerminalText(String text)
    {
        this.terminalText = text;
    }

    @Override
    void execute()
    {
        SendTerminalTextReport report = new SendTerminalTextReport(
                ClientPlayerManager.getSingleton().getThisClientsPlayer().getID(), terminalText);
        ReportObserverConnector.getSingleton().sendReport(report);
    }

}
