package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.PlayerTableDataGateway;

import java.util.ArrayList;

/**
 * @author Chris Roadcap
 * @author Denny Fleagle
 */
public class CommandTerminalTextWho extends TerminalCommand
{
    private String arg;

    /**
     * Execute the command
     *
     * @return all online players and map names as a string
     */
    @Override
    public String execute(int playerID, String[] arg)
    {
        StringBuilder data = new StringBuilder();

        try
        {
            PlayerTableDataGateway gateway =
                    PlayerTableDataGateway.getSingleton();
            ArrayList<PlayerDTO> playerList = gateway.getAllOnlinePlayers();

            for (PlayerDTO player : playerList)
            {
                data.append(player.getPlayerName()).append(":")
                        .append(player.getMapName()).append(":");
            }
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
        return formatString(data.toString());
    }

    /**
     * Format the string
     *
     * @param generic the object to format
     */
    @Override
    public String formatString(Object generic)
    {
        String data = (String) generic;
        String[] tokenized = data.split(":");
        int count = 1;
        StringBuilder result = new StringBuilder();
        for (String token : tokenized)
        {
            result.append(String.format("|%-10.10s|", token));
            if (count % 2 == 0)
            {
                result.append("\n");
            }
            count++;
        }
        return result.toString();
    }

    /**
     * describes the terminal command
     */
    @Override
    public String getDescription()
    {
        return "Print information about users who are currently logged in.";
    }

    /**
     * @see TerminalCommand#getTerminalIdentifier()
     */
    @Override
    public String getTerminalIdentifier()
    {
        return "who";
    }
}
