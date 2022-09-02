package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerMapper;

import java.util.ArrayList;

/**
 * @author Nathaniel, Ben and Allen
 */
public class CommandTerminalTextLocations extends TerminalCommand
{

    private final String terminalIdentifier = "locations";
    private final String description = "Lists maps the player has visited.";

    /**
     *
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
     * @param generic is the ArrayList<String> of maps a player has visited
     * @return a formated string of maps a player has visited.
     * <p>
     * Because we pass an object into the method instead of a string, we have to
     * type cast to an ArrayList of generics.  Otherwise, we get yelled at for not
     * checking that the object is an ArrayList :(.
     * @see TerminalCommand#formatString(java.lang.Object)
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
     * @see TerminalCommand#getTerminalIdentifier()
     */
    @Override
    public String getTerminalIdentifier()
    {
        return terminalIdentifier;
    }

    /**
     * @see TerminalCommand#getDescription()
     */
    @Override
    public String getDescription()
    {
        return description;
    }

}
