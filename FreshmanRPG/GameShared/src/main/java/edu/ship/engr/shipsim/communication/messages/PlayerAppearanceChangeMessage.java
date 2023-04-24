package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author rh5172
 */
public class PlayerAppearanceChangeMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 1L;

    private ArrayList<VanityDTO> vanities;

    /**
     * @param playerID ID of the player
     * @param newWearing the list of vanity objects the player is wearing
     */
    public PlayerAppearanceChangeMessage(int playerID, boolean quietMessage, ArrayList<VanityDTO> newWearing)
    {
        super(playerID, quietMessage);
        this.vanities = newWearing;
    }

    public PlayerAppearanceChangeMessage(int playerID, ArrayList<VanityDTO> newWearing)
    {
        super(playerID, false);
        this.vanities = newWearing;
    }

    public List<VanityDTO> getVanities()
    {
        return vanities;
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
        if (!super.equals(o))
        {
            return false;
        }

        PlayerAppearanceChangeMessage that = (PlayerAppearanceChangeMessage) o;

        //If they both contain the same vanityDTOs, and are both the same size they must be equal
        //This needed to be done because some orders of vanity items were different and causing issues.
        for(VanityDTO v:vanities)
        {
            if (!that.vanities.contains(v))
            {
                return false;
            }
        }
        return vanities.size() == that.vanities.size();
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + vanities.hashCode();
        return result;
    }

    /**
     * Creates a string that will be displayed by the message
     *
     * @return The message to print out what happened and what the appearanceType is now
     */
    public String toString()
    {
        String str = "PlayerAppearanceChangeMessage: player="+relevantPlayerID+", vanities: ";
        for (VanityDTO dto : vanities)
        {
            str += dto.getName() + "\n";
        }
        return str;
    }
}
