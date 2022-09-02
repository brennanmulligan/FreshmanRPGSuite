package edu.ship.engr.shipsim.model;


/**
 * Command to reset the entire client model state
 *
 * @author Steve
 */
public class CommandClearModelState extends Command
{

    /**
     * @see Command#execute()
     */
    @Override
    boolean execute()
    {
        ClientPlayerManager.resetSingleton();
        MapManager.resetSingleton();
        return true;
    }

}
