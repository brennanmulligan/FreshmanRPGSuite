package edu.ship.engr.shipsim.model;


import edu.ship.engr.shipsim.model.reports.LogoutReport;

/**
 * Command to the model signifying the a player has been disconnected from the
 * area server that the player is on, and no longer needs to be watched by this client
 *
 * @author nhydock
 */
public class CommandRemovePlayer extends Command
{

    private final int playerID;

    /**
     * @param id the id of the player
     */
    public CommandRemovePlayer(int id)
    {
        this.playerID = id;
    }

    /**
     * @return the ID of the player who left this area
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        if (playerID == ClientPlayerManager.getSingleton().getThisClientsPlayer().getID())
        {
            LogoutReport report = new LogoutReport();
            ReportObserverConnector.getSingleton().sendReport(report);
        }
        ClientPlayerManager.getSingleton().removePlayer(playerID);
    }

}
