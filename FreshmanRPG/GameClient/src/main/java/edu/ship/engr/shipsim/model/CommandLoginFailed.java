package edu.ship.engr.shipsim.model;


/**
 * @author Dave, Andy, Matt
 * <p>
 * Creates the CommandLoginFailed
 */
public class CommandLoginFailed extends Command
{

    private String message = "";


    public CommandLoginFailed()
    {

    }

    public CommandLoginFailed(String message)
    {
        this.message = message;
    }


    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        ClientPlayerManager.getSingleton().loginFailed(message);
    }

}
