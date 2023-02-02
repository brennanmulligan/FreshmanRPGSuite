package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.ReceiveTerminalTextReport;
import edu.ship.engr.shipsim.model.terminal.TerminalCommand;
import edu.ship.engr.shipsim.model.terminal.TerminalManager;

/**
 * Command to get all connected players, sends a report (for the server packer)
 *
 * @author Kanza Amin
 */
public class CommandReceiveTerminalText extends Command
{
    private final int playerID;
    private final String terminalText;

    /**
     * @param playerID     playerID of the client requesting the list of all connected players
     * @param terminalText text from terminal
     */
    public CommandReceiveTerminalText(int playerID, String terminalText)
    {
        this.playerID = playerID;
        this.terminalText = terminalText;
    }

    /**
     * If the user only types one command, we execute the command.
     * Otherwise, if the user types "man" and one more command, we execute a man command to
     * return the description of the second command.
     */

    @Override
    void execute()
    {
        String result;
        ReceiveTerminalTextReport report;
        String[] commandArgs = terminalText.split(" ");
        String commandName = commandArgs[0];
        String[] arg = new String[commandArgs.length];
        System.arraycopy(commandArgs, 1, arg, 0, commandArgs.length - 1);

        TerminalCommand cmd = TerminalManager.getSingleton().getTerminalCommandObject(commandName);

        if (cmd == null)
        {
            result = commandArgs[0] + ": command not found";
            report = new ReceiveTerminalTextReport(playerID, result,
                    commandName);
            ReportObserverConnector.getSingleton().sendReport(report);
        }
        else
        {
            result = cmd.execute(playerID, arg);
            if (result != null && result.length() > 0)
            {
                report = new ReceiveTerminalTextReport(playerID, result,
                        cmd.getTerminalIdentifier());
                ReportObserverConnector.getSingleton().sendReport(report);
            }
        }
    }
}