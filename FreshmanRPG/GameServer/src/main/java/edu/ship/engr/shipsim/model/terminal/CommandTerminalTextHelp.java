package edu.ship.engr.shipsim.model.terminal;

/**
 * @author Nathaniel and Nahesha
 */
public class CommandTerminalTextHelp extends TerminalCommand
{
    private final String terminalIdentifier = "help";
    private final String description = "Displays the description of a command.";
    String commandToFind;

    /**
     * @return the formated description of the command
     */
    @Override
    public String execute(int playerID, String[] arg)
    {
        String commandToFind = arg[0];

        this.commandToFind = commandToFind;
        String data = null;
        TerminalCommand tc = null;

        try
        {
            tc = TerminalManager.getSingleton().getTerminalCommandObject(commandToFind);
        }
        catch (IllegalArgumentException | SecurityException e)
        {
            e.printStackTrace();
        }

        if (tc != null)
        {
            data = tc.getDescription();
        }
        return formatString(data);
    }

    /**
     * @return the formated description for the command we're looking for if
     * we found the command.  Otherwise return that there is no entry for that
     * input.
     */
    @Override
    public String formatString(Object generic)
    {
        if (generic != null)
        {
            String data = (String) generic;
            return "Name: " + commandToFind + " \n Description: " + data;
        }
        else if (this.commandToFind == null || this.commandToFind == "")
        {
            return buildCommandList();
        }
        return "No manual entry for " + commandToFind + ".";
    }


    /**
     * This is a temporary method to use for the OnRamping Quest.
     * This functionality will be replaced with reflection. See issue #179 in GitLab
     * (#179 - Move all TerminalTextCommands into GameShared)
     *
     * @return A print of current commands in the system
     */
    public String buildCommandList()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("The following is a list of commands, their syntax, and description:\n");
        sb.append("-- teleport <map_name> - For every new location you visit, you can " +
                "teleport to it.\n");
        sb.append("-- whois <player_name> - Find the profile for another player.\n");
        sb.append("-- friend list - Returns a list of the player's friends.\n");
        sb.append("-- friend add <player_name> - Sends a friend request to that player, can input multiple names.\n");
        sb.append("-- friend accept <player_name> - Accepts a friend request from that player, can input multiple names.\n");
        sb.append("-- locations - Lists maps the player has visited.\n");
        sb.append("-- help <command> - Displays the description of a command.\n");
        sb.append("-- who - Prints information about users who are currently logged in.\n");
        sb.append("-- whoami - Prints out information on your player.\n");
        sb.append("-- exit - Type 'exit' to logout of your account.\n");

        return sb.toString();
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
