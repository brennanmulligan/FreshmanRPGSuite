package communication.messages;

/**
 * A message representing a request to gte the vanity shop items
 * from the server
 * @author Aaron, Jake
 */
public class VanityShopInventoryRequestMessage implements Message
{
    private static final long serialVersionUID = 1L;

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        return getClass() == obj.getClass();
    }
}
