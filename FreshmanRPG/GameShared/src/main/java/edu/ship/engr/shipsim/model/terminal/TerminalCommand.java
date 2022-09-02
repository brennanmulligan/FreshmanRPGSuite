package edu.ship.engr.shipsim.model.terminal;

/**
 * @author Chris Roadcap
 * @author Denny Fleagle
 */
public abstract class TerminalCommand
{
    /**
     * perform the action associated with this command
     *
     * @param playerID for the commands that need it
     * @param arg      the argument to be passed tothe command
     * @return string associated with command
     */
    public abstract String execute(int playerID, String[] arg);

    /**
     * format the string
     *
     * @param generic generic object
     * @return formatted string
     */
    public abstract String formatString(Object generic);

    /**
     * @return the string identifier for all the commands
     */
    public abstract String getTerminalIdentifier();

    /**
     * @return the description of the terminal command
     */
    public abstract String getDescription();

}
 