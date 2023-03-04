package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rh5172
 */
public class PlayerAppearanceChangeMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 1L;

    private ArrayList<VanityDTO> vanities;



    /**
     * @param playerID ID of the player
     * @param vanities the list of vanity objects the player is wearing
     */
    public PlayerAppearanceChangeMessage(int playerID, boolean quietMessage, VanityDTO bodyDTO, VanityDTO hatDTO)
    {
        super(playerID, quietMessage);
        this.vanities = new ArrayList<>(vanities);

    }

    public List<VanityDTO> getVanities()
    {
        return vanities;
    }

    /**
     * Creates a string that will be displayed by the message
     *
     * @return The message to print out what happened and what the appearanceType is now
     */
    public String toString()
    {
        return "Appearance Change: Appearance changed to: " + "AHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH fix this";
    }


}
