package model.terminal;

/**
 * @author Ben and Nathaniel
 * Terminal Command for testing
 */
public class StubTerminalCommand1 extends TerminalCommand
{
	private String terminalIdentifier = "stub1";

	/**
	 * @see model.terminal.TerminalCommand#execute()
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
	 * @see model.terminal.TerminalCommand#getDescription()
	 */
	@Override
	public String getDescription()
	{
		return "This is a test description";
	}
}
