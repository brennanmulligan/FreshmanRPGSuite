package communication.messages;

import dataDTO.VanityDTO;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Contains the id of a player and their list of new things to wear
 */
public class PlayerChangeAppearanceMessage implements Message
{
    private static final long serialVersionUID = 1L;
    private int playerID;
    private ArrayList<VanityDTO> newWearing;

    /**
     * @param playerID the ID of the player
     * @param newWearing the list of new things to wear
     */
    public PlayerChangeAppearanceMessage(int playerID, ArrayList<VanityDTO> newWearing)
    {
        this.playerID = playerID;
        this.newWearing = newWearing;
    }

    /**
     * @return the list of new things to wear
     */
    public ArrayList<VanityDTO> getNewWearing()
    {
        return newWearing;
    }

    /**
     * @return the ID of the player
     */
    public int getPlayerID()
    {
        return playerID;
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
