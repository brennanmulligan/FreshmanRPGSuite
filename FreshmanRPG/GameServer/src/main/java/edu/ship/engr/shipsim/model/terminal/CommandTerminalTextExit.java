package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.model.CommandRemovePlayer;
import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.reports.TerminalTextExitReport;


/**
 * Client Command for logging player out via terminal text.
 * Send a report that is packed and sent to a client side handler
 * in order to switch UI.
 * <p>
 * Also called the SERVER SIDE CommandRemovePlayer, that is responsible
 * for removing a player from the database.
 * <p>
 * Authors: John G., Ian L.
 */
public class CommandTerminalTextExit extends TerminalCommand
{
    private final String terminalIdentifier = "exit";
    private final String description = "Logs the player off.";

    /**
     * Command executes and logs player out
     *
     * @param playerID - ID of the player to logout
     */
    @Override
    public String execute(int playerID, String[] arg)
    {

        TerminalTextExitReport report = new TerminalTextExitReport(playerID);
        ReportObserverConnector.getSingleton().sendReport(report);

        CommandRemovePlayer cmd = new CommandRemovePlayer(playerID);
        ModelFacade.getSingleton().queueCommand(cmd);

        return null;
    }

    /**
     * Not needed for this command
     */
    @Override
    public String formatString(Object generic)
    {
        // Not needed for this command
        return null;
    }

    /**
     * returns the terminal identifier string of this command;
     */
    @Override
    public String getTerminalIdentifier()
    {
        return terminalIdentifier;
    }

    /**
     * describes the terminal command
     */
    @Override
    public String getDescription()
    {
        return description;
    }
}
