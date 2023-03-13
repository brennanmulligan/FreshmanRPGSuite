package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * @author sk5587 & ed9737 contains text that we want to send to client
 */
public class DisplayTextMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String text;

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + relevantPlayerID;
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        return result;
    }

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
        if (getClass() != obj.getClass())
        {
            return false;
        }
        DisplayTextMessage other = (DisplayTextMessage) obj;
        if (relevantPlayerID != other.relevantPlayerID)
        {
            return false;
        }
        if (text == null)
        {
            if (other.text != null)
            {
                return false;
            }
        }
        else if (!text.equals(other.text))
        {
            return false;
        }
        return true;
    }

    /**
     * @param playerID player id of the player we want to send this message to
     * @param text     text we want to send to the player
     */
    public DisplayTextMessage(int playerID, boolean quietMessage, String text)
    {
        super(playerID, quietMessage);
        this.text = text;
    }

    /**
     * Gets the text
     *
     * @return text
     */
    public String getText()
    {
        return text;
    }
}
