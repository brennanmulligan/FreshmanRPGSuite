package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.io.Serializable;

public class PlayerPurchasedClothingMessage extends Message implements Serializable
{
    private VanityDTO vanityDTO;

    public PlayerPurchasedClothingMessage(int playerID, VanityDTO vanityDTO)
    {
        super(playerID);
        this.vanityDTO = vanityDTO;
    }

    public VanityDTO getVanityDTO()
    {
        return vanityDTO;
    }
}
