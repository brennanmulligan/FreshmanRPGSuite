package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * @author Ian Keefer
 * @author TJ Renninger
 */
public class KeyInputRecievedReport implements Report
{

    private String input;
    private int playerId;

    /**
     * @param input    the key that is pressed
     * @param playerId user id
     */
    public KeyInputRecievedReport(String input, int playerId)
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
}
