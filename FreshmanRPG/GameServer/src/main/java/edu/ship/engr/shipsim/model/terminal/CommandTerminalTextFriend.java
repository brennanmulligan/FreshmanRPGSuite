package edu.ship.engr.shipsim.model.terminal;

import java.util.HashMap;


/**
 * When 'friend' is typed in the terminal, it allows for users to access the
 * friends functionality of the MMO
 *
 * @author Zachary Semanco, Christian C.
 */

public class CommandTerminalTextFriend extends TerminalCommand
{

    private final String terminalIdentifer = "friend";
    private final String description = "Allows the user to add/accept/list friends.";

    /**
     * Holds the different argument strings and their mapped behavior class
     */
    private HashMap<String, FriendBehavior> commandTable = new HashMap<>()
    {
        private static final long serialVersionUID = 1L;

        {
            put("list", new FriendListBehavior());
            put("add", new FriendAddBehavior());
            put("accept", new FriendAcceptBehavior());
        }
    };


    /**
     * Searches for the friend argument in the HashMap
     * If the argument is found, it executes that argument
     * Else: it returns an invalid string
     *
     * @param playerID : the playerID of the player calling the command
     * @param arg      : the argument of the 'friend' command
     * @return String to print to the terminal
     */
    @Override
    public String execute(int playerID, String[] arg)
    {
        FriendBehavior result = commandTable.get(arg[0]);

        if (result == null)
        {
            return "ERROR: friend argument (" + arg[0] + ") does not exist";
        }

        String[] friends = new String[arg.length - 1];

        for (int i = 1; i < arg.length; i++)
        {
            friends[i - 1] = arg[i];
        }
        return result.execute(playerID, friends);
    }

    /**
     * @see TerminalCommand#formatString(Object)
     * Empty Method not in use by the class, required by 'parent' class
     */
    @Override
    public String formatString(Object generic)
    {
        return null;
    }

    /**
     * @see TerminalCommand#getTerminalIdentifier()
     */
    @Override
    public String getTerminalIdentifier()
    {
        return this.terminalIdentifer;
    }

    /**
     * @return the description used in man command
     */
    @Override
    public String getDescription()
    {
        return this.description;
    }

}

