package model.terminal;

import java.util.ArrayList;

import datasource.DatabaseException;
import model.Player;
import model.PlayerMapper;

/**
 * @author Nathaniel, Ben and Allen
 *
 */
public class CommandTerminalTextLS extends TerminalCommand
{

	private final String terminalIdentifier = "ls";
	private final String description = "Lists maps the player has visited.";

	/**
	 * @see model.terminal.TerminalCommand#execute(int, java.lang.String)
	 */
	@Override
	public String execute(int playerID, String[] arg)
	{
		ArrayList<String> mapsVisited = new ArrayList<>();
		Player player;
		try
		{
			player = new PlayerMapper(playerID).getPlayer();
			mapsVisited = player.getPlayerVisitedMaps();
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		return formatString(mapsVisited);
	}

	/**
	 * @see model.terminal.TerminalCommand#formatString(java.lang.Object)
	 * @param generic is the ArrayList<String> of maps a player has visited
	 * @return a formated string of maps a player has visited.
	 *
	 * Because we pass an object into the method instead of a string, we have to
	 * type cast to an ArrayList of generics.  Otherwise, we get yelled at for not
	 * checking that the object is an ArrayList :(.
	 */
	@Override
	public String formatString(Object generic)
	{
		String result = "";
		ArrayList<?> maps = (ArrayList<?>) generic;
		for (Object map : maps)
		{
			result += map + ", ";
		}
		return result.substring(0, result.length() - 2);
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
		return description;
	}

}
