package model;

import model.reports.ReceiveTerminalTextReport;
import model.terminal.TerminalCommand;
import model.terminal.TerminalManager;

/**
 * Command to get all connected players, sends a report (for the server packer)
 * @author Kanza Amin
 *
 */
public class CommandReceiveTerminalText extends Command
{
	private int playerID;
	private String terminalText;

	/**
	 * @param playerID playerID of the client requesting the list of all connected players
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
	 * @return
	 */

	@Override
	boolean execute()
	{
		String result = "";
		ReceiveTerminalTextReport report;
		String[] commandArgs = terminalText.split(" ");
		String commandName = commandArgs[0];
		String[] arg = new String[commandArgs.length];
		for (int i = 1; i < commandArgs.length; i++)
		{
			arg[i - 1] = commandArgs[i];
		}

		TerminalCommand cmd = TerminalManager.getSingleton().getTerminalCommandObject(commandName);

		if (cmd == null)
		{
			result = commandArgs[0] + ": command not found";
			report = new ReceiveTerminalTextReport(playerID, result,
					commandName);
			QualifiedObservableConnector.getSingleton().sendReport(report);
		}
		else
		{
			result = cmd.execute(playerID, arg);
			report = new ReceiveTerminalTextReport(playerID, result,
					cmd.getTerminalIdentifier());
			QualifiedObservableConnector.getSingleton().sendReport(report);
		}
		return true;
	}
}