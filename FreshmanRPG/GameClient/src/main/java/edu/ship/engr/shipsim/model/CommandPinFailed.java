package edu.ship.engr.shipsim.model;

/**
 * @author Andy and Matt
 * <p>
 * Creates the CommandPinFailed
 */
public class CommandPinFailed extends Command
{
    private final String err;

    /**
     * @param msg is the error message
     */
    public CommandPinFailed(String msg)
    {
        err = msg;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        ClientPlayerManager.getSingleton().pinFailed(err);
    }

}
