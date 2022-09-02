package edu.ship.engr.shipsim.model;

/**
 * Command to send quest state report for This Clients Player
 *
 * @author Merlin
 */
public class CommandSendQuestState extends Command
{

    /**
     * Sends CurrentQuestStateReport for ThisClientsPlayer
     *
     * @see Command#execute()
     */
    @Override
    boolean execute()
    {
        ClientPlayerManager.getSingleton().getThisClientsPlayer().sendCurrentQuestStateReport();
        return true;
    }

}
