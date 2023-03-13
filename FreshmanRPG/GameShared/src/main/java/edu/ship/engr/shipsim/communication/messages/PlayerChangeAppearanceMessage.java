package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Contains the id of a player and their list of new things to wear
 */
public class PlayerChangeAppearanceMessage extends Message
{
    private static final long serialVersionUID = 1L;
    private ArrayList<VanityDTO> newWearing;

    /**
     * @param playerID   the ID of the player
     * @param newWearing the list of new things to wear
     */
    public PlayerChangeAppearanceMessage(int playerID, boolean quietMessage, ArrayList<VanityDTO> newWearing)
    {
        super(playerID, quietMessage);
        this.newWearing = newWearing;
    }

    /**
     * @return the list of new things to wear
     */
    public ArrayList<VanityDTO> getNewWearing()
    {
        return newWearing;
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
        PlayerChangeAppearanceMessage that = (PlayerChangeAppearanceMessage) o;
        return Objects.equals(newWearing, that.newWearing);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(newWearing);
    }
}
