package model.terminal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class CommandTerminalTextDate extends TerminalCommand
{
	private final String identifier = "date";
	private final String description = "prints out the date in mm/dd/yyyy HH:mm:ss";

	/**
	 * @see model.terminal.TerminalCommand#execute(int, String)
	 */
	public String execute(int playerID, String[] arg)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MM dd HH:mm:ss yyyy");
		return sdf.format(new Date());
	}

	/**
	 * @see model.terminal.TerminalCommand#formatString(java.lang.Object)
	 */
	public String formatString(Object generic)
	{
		return null;
	}

	/**
	 * @see model.terminal.TerminalCommand#getTerminalIdentifier()
	 */
	public String getTerminalIdentifier()
	{
		return identifier;
	}

	/**
	 * @see model.terminal.TerminalCommand#getDescription()
	 */
	public String getDescription()
	{
		return description;
	}
}
