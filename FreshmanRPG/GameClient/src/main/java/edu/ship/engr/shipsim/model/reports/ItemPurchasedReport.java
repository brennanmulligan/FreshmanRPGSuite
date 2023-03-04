package edu.ship.engr.shipsim.model.reports;

/**
 * Contains information that allows a doubloon prize to be purchased
 *
 * @author Kevin Marek
 */
public class ItemPurchasedReport extends SendMessageReport
{
    private final int playerID;
    private final int price;

    /**
     * @param playerID - player purchasing the item
     * @param price    - number of doubloons item costs
     */
    public ItemPurchasedReport(int playerID, int price)
    {
        // Happens on client, thus it will always be loud
        super(0, false);
        this.playerID = playerID;
        this.price = price;
    }

    /**
     * @return ID of player purchasing item
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return number of doubloons the item costs
     */
    public int getPrice()
    {
        return price;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public final int hashCode()
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
    public final boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof ItemPurchasedReport))
        {
            return false;
        }
        ItemPurchasedReport other = (ItemPurchasedReport) obj;
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

}
