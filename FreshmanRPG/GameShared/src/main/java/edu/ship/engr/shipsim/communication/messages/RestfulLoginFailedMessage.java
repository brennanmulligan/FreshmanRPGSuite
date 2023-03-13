package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Derek
 */
public class RestfulLoginFailedMessage extends Message implements Serializable
{
    // We have this because we need it :)
    private static final long serialVersionUID = 1L;
    private final String message = "Invalid Login - Incorrect Username/Password";

    public RestfulLoginFailedMessage(boolean quietMessage)
    {
        super(0, quietMessage);
    }

    public String getMessage()
    {
        return message;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }

        if (o == null)
        {
            return false;
        }

        if (getClass() != o.getClass())
        {
            return false;
        }

        RestfulLoginFailedMessage otherMessage = (RestfulLoginFailedMessage) o;

        return Objects.equals(message, otherMessage.getMessage());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(message);
    }
}
