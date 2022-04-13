package model.terminal;

import java.util.ArrayList;

import dataDTO.PlayerDTO;
import datasource.DatabaseException;
import datasource.PlayerTableDataGatewayRDS;
import model.terminal.TerminalCommand;

/**
 *
 * @author Chris Roadcap
 * @author Denny Fleagle 
 *
 */
public class CommandTerminalTextWho extends TerminalCommand
{
	private final String terminalIdentifier = "who";
	private final String description = "Print information about users who are currently logged in.";
	private String arg;

	/**
	 * Execute the command
	 * @return all online players and map names as a string
	 */
	@Override
	public String execute(int playerID, String[] arg)
	{
		String data = "";

		try
		{
			ArrayList<PlayerDTO> playerList = PlayerTableDataGatewayRDS.getSingleton().retrieveAllOnlinePlayers();

			for (PlayerDTO player : playerList)
			{
				data += player.getPlayerName() + ":" + player.getMapName() + ":";
			}
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return formatString(data);
	}

	/**
	 * Format the string
	 * @param generic the object to format
	 */
	@Override
	public String formatString(Object generic)
	{
		String data = (String) generic;
		String[] tokenized = data.split(":");
		int count = 1;
		String result = "";
		for (String token : tokenized)
		{
			result += String.format("|%-10.10s|", token);
			if (count % 2 == 0)
			{
				result += "\n";
			}
			count++;
		}
		return result;
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
