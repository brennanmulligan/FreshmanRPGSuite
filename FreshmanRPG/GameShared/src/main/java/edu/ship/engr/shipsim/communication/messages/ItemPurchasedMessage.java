package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * A message used in purchasing a doubloon prize
 *
 * @author Kevin Marek, Zack Semanco
 */
public class ItemPurchasedMessage implements Message, Serializable
{
    private static final long serialVersionUID = 1L;
    private final int playerID;
    private final int price;

    /**
     * @param playerID - player purchasing the item
     * @param price    - number of doubloons the item costs
     */
    public ItemPurchasedMessage(int playerID, int price)
    {
        this.playerID = playerID;
        this.price = price;
    }

    /**
     * @return the purchasing player's id
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the number of doubloons the item costs
     */
    public int getPrice()
    {
        return price;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + playerID;
        result = prime * result + price;
        return result;
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (!(obj instanceof ItemPurchasedMessage))
        {
            return false;
        }
        ItemPurchasedMessage other = (ItemPurchasedMessage) obj;
        if (playerID != other.playerID)
        {
            return false;
        }
        if (price != other.price)
        {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "ItemPurchasedMessage [playerID=" + playerID + ", price=" + price + "]";
    }
}
