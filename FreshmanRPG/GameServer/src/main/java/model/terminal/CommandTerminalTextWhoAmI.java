package model.terminal;

import dataDTO.LevelManagerDTO;
import model.Player;
import model.PlayerManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * When the word is written into the terminal
 * the appropriate information should display
 * AKA the profile
 *
 * @author Nahesha
 */
public class CommandTerminalTextWhoAmI extends TerminalCommand
{
    private final String terminalIdentifier = "whoami";
    private final String description = "Prints out information on your player.";

    /**
     * The print outs the appropriate information
     */
    @Override
    public String execute(int playerID, String[] arg)
    {
        String data = "";
        Player player = PlayerManager.getSingleton().getPlayerFromID(playerID);

        data = "Name: " + player.getPlayerName() + "," +
                "Crew: " + player.getCrew() + "," +
                "Major: " + player.getMajor() + "," +
                "Doubloons: " + player.getDoubloons() + "," +
                "Current Experience: " + player.getExperiencePoints() + "," +
                "Experience Required For Next Level: " + LevelManagerDTO.getSingleton()
                .getLevelForPoints(player.getExperiencePoints()).getLevelUpPoints() +
                "," +
                "Level Up Deadline: " + convertDateToString(LevelManagerDTO.getSingleton()
                .getLevelForPoints(player.getExperiencePoints()).getDeadlineDate())
        ;


        return formatString(data);

    }

    /**
     * Formats the terminal information
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
     * describes the terminal command
     */
    @Override
    public String getDescription()
    {
        return description;
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
}
