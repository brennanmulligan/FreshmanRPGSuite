package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Ryan Handley and Scott Bowling
 */
public final class PlayerAppearanceChangeReport extends SendMessageReport
{
	private final int playerID;
	private final ArrayList<VanityDTO> newWearing;



	/**
	 * Sets the necessary fields
	 * @param playerID The player's ID
	 * @param newWearing The players appearance changes
	 */
	public PlayerAppearanceChangeReport(int playerID, ArrayList<VanityDTO> newWearing)
	{
        super(playerID, true);
		this.playerID = playerID;
		this.newWearing = newWearing;
	}

	/**
	 * @return the players ID
	 */
	public int getPlayerID()
	{
		return this.playerID;
	}

	/**
	 * @return The players appearance
	 */
	public ArrayList<VanityDTO> getNewWearing()
	{
		return this.newWearing;
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
		PlayerAppearanceChangeReport that = (PlayerAppearanceChangeReport) o;
		return playerID == that.playerID && Objects.equals(newWearing, that.newWearing);

    }

    @Override
    public int hashCode()
    {
        return Objects.hash();
    }
}
