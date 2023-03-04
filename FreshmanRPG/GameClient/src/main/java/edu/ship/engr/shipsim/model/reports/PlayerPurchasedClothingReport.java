package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.util.Objects;

public class PlayerPurchasedClothingReport extends SendMessageReport
{
    private final int playerID;
    private final VanityDTO vanityDTO;

    /**
     * @param playerID  the ID of the player who bought an item
     * @param vanityDTO the DTO of the vanity item that was purchased
     */
    public PlayerPurchasedClothingReport(int playerID, VanityDTO vanityDTO)
    {
        // Happens on client, thus it will always be loud
        super(0, false);
        this.playerID = playerID;
        this.vanityDTO = vanityDTO;
    }

    /**
     * @return the id of the player who bought the item
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the id of the vanity item the player bought
     */
    public VanityDTO getVanityDTO()
    {
        return vanityDTO;
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
        PlayerPurchasedClothingReport that = (PlayerPurchasedClothingReport) o;
        return (playerID == that.playerID) && (vanityDTO == that.vanityDTO);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(playerID, vanityDTO);
    }

}
