package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.io.Serializable;

public class PlayerPurchasedClothingMessage extends Message implements Serializable
{
    private VanityDTO vanityDTO;

    public PlayerPurchasedClothingMessage(int playerID, boolean quietMessage, VanityDTO vanityDTO)
    {
        super(playerID, quietMessage);
        this.vanityDTO = vanityDTO;
    }

    public VanityDTO getVanityDTO()
    {
        return vanityDTO;
    }
}
