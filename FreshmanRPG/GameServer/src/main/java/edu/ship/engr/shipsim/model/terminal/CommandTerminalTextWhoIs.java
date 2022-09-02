package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.FindPlayerIDFromPlayerName;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerMapper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Finger command allows you to get player profile for another person
 *
 * @author Austin Smale
 * @author Nahesha Paulection
 */
public class CommandTerminalTextWhoIs extends TerminalCommand
{
    private final String terminalIdentifier = "whois";
    private final String description = "Find the profile for another player.";

    /**
     * build the player profile for the user you are looking for
     */
    @Override
    public String execute(int playerID, String[] arg)
    {
        String playerName = arg[0];
        String data = "";

        try
        {
            int playerToFindID = FindPlayerIDFromPlayerName.getPlayerID(playerName);
            Player player = new PlayerMapper(playerToFindID).getPlayer();

            data = "Name: " + player.getPlayerName() + "," +
                    "Crew: " + player.getCrew() + "," +
                    "Major: " + player.getMajor() + "," +
                    "Doubloons: " + player.getDoubloons() + "," +
                    "Current Experience: " + player.getExperiencePoints() + ",";

        }
        catch (DatabaseException e)
        {

            // Make sure that the exception we are getting is that we could not find the player
            // and not some other database exception.
            if (e.getSimpleDescription().startsWith("Couldn't find a player named"))
            {
                return "Player " + playerName + " not found.";
            }
            else
            {
                e.printStackTrace();
            }
        }

        return formatString(data);
    }

    /**
     * converts a date coming from the report as a string
     *
     * @param date Date to be converted
     * @return converted Date in string format
     */
    private String convertDateToString(Date date)
    {
        String dateString = "";
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
        dateString = sdf.format(date);
        return dateString;
    }

    /**
     * Format the date to look correct
     */
    @Override
    public String formatString(Object generic)
    {
        String data = (String) generic;
        String result = "";
        String[] tokenized = data.split(",");
        for (String token : tokenized)
        {
            result += token + "\n";
        }

        return result;
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
     * @return the description used in man command
     */
    @Override
    public String getDescription()
    {
        return description;
    }

}
