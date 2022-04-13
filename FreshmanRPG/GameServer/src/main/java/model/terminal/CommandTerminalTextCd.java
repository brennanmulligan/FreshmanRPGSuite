package model.terminal;


import datasource.DatabaseException;
import datatypes.Position;
import model.CommandMovePlayerSilentlyAndPersist;
import model.ServerMapManager;
import model.ModelFacade;
import model.Player;
import model.PlayerMapper;

/**
 * cd teleports the person to the correct map if they have already
 * visited the given map
 * @author np5756
 *
 */
public class CommandTerminalTextCd extends TerminalCommand
{
	private final String terminalIdentifier = "cd";
	private final String description = "For every new location you visit, you can teleport to it.";

	/**
	 * Teleports the player to the given location if the player is not in that map.
	 * And only if the place was already visited.
	 */
	@Override
	public String execute(int playerID, String[] destinations)
	{
		String destination = destinations[0];

		String mapName = ServerMapManager.getSingleton().getMapNameFromMapTitle(destination);

		try
		{
			Player player = new PlayerMapper(playerID).getPlayer();
			if (!player.getPlayerVisitedMaps().contains(destination))
			{
				return "You must visit this map location before you may teleport to it.";
			}
			else if (player.getMapName().equals(mapName))
			{
				return "You cannot teleport to the map that you are currently in.";
			}
			else
			{
				Position position = ServerMapManager.getSingleton().getDefaultPositionForMap(destination);

				CommandMovePlayerSilentlyAndPersist command = new CommandMovePlayerSilentlyAndPersist(playerID, mapName, position);
				ModelFacade.getSingleton().queueCommand(command);
			}
		}
		catch (DatabaseException e)
		{

			e.printStackTrace();

		}
		return "";
	}

	/**
	 * This will not be used since we are not
	 *  printing anything in the terminal
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
	 *  the description of the command
	 */
	@Override
	public String getDescription()
	{
		return description;
	}


}
