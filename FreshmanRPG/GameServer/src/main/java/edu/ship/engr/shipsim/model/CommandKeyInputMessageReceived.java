package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.KeyInputRecievedReport;

/**
 * Command of receiving a key input message
 *
 * @author Ian Keefer & TJ Renninger
 */
public class CommandKeyInputMessageReceived extends Command
{

    private final String input;
    private final int playerId;

    /**
     * @param input    user key input
     * @param playerId user id
     */
    public CommandKeyInputMessageReceived(String input, int playerId)
    {
        this.input = input;
        this.playerId = playerId;
    }

    /**
     * @return user key input
     */
    public String getInput()
    {
        return input;
    }

    /**
     * @return user id
     */
    public int getPlayerId()
    {
        return playerId;
    }

    @Override
    void execute()
    {
        KeyInputRecievedReport report = new KeyInputRecievedReport(input, playerId);
        ReportObserverConnector.getSingleton().sendReport(report);
    }

}
