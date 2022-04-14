package communication.messages;

import dataDTO.VanityDTO;

import java.io.Serializable;

public class PlayerPurchasedClothingMessage implements Message, Serializable
{
    private int playerID;
    private VanityDTO vanityDTO;

    public PlayerPurchasedClothingMessage(int playerID, VanityDTO vanityDTO)
    {
        this.playerID = playerID;
        this.vanityDTO = vanityDTO;
    }

    public int getPlayerID()
    {
        return playerID;
    }

    public VanityDTO getVanityDTO()
    {
        return vanityDTO;
    }
}
