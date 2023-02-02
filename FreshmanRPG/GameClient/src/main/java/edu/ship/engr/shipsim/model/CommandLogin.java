package edu.ship.engr.shipsim.model;


/**
 * @author Merlin
 */
public class CommandLogin extends Command
{

    private final String name;
    private final String password;

    /**
     * @param name     the player's name
     * @param password the password
     */
    public CommandLogin(String name, String password)
    {
        this.name = name;
        this.password = password;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        ClientPlayerManager p = ClientPlayerManager.getSingleton();
        p.initiateLogin(name, password);
    }

}
