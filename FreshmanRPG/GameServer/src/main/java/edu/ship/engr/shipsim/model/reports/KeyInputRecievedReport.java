package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;

/**
 * @author Ian Keefer
 * @author TJ Renninger
 */
public class KeyInputRecievedReport implements Report
{

    private final String input;
    private final int playerId;

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

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }

        KeyInputRecievedReport that = (KeyInputRecievedReport) o;

        if (playerId != that.playerId)
        {
            return false;
        }
        return input.equals(that.input);
    }

    @Override
    public int hashCode()
    {
        int result = input.hashCode();
        result = 31 * result + playerId;
        return result;
    }
}
