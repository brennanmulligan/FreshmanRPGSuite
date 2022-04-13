package model.terminal;

/**
 *
 * @author Nathaniel and Nahesha
 *
 */
public class CommandTerminalTextClear extends TerminalCommand
{
	private final String terminalIdentifier = "clear";
	private final String description = "Clears your screen if this is possible, including its scrollback buffer.";

	/**
	 * @see model.terminal.TerminalCommand#execute(int, String)
	 */
	@Override
	public String execute(int playerID, String[] arg)
	{
		return null;
	}

	/**
	 * @see model.terminal.TerminalCommand#formatString(java.lang.Object)
	 */
	@Override
	public String formatString(Object generic)
	{
		return null;
	}

	/**
	 * @see model.terminal.TerminalCommand#getTerminalIdentifier()
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
