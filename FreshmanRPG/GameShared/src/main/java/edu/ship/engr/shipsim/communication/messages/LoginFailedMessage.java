package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * The message that goes from the main server to the client when the user
 * enterse invalid login credentials.
 *
 * @author Merlin
 */
public class LoginFailedMessage extends Message implements Serializable
{


    private static final long serialVersionUID = 1L;
    private String message = "Invalid Login - Incorrect Username/Password";

    /**
     *
     */
    public LoginFailedMessage()
    {
        super(0);

    }

    /**
     * Overloaded constructor for allowing more detailed login failed messages
     * to be sent. (ie. Same player twice)
     *
     * @param message
     */
    public LoginFailedMessage(String message, boolean quietMessage)
    {
        super(0, quietMessage);
        this.message = message;
    }


    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return message;
    }


    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result;
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
        return true;
    }


}
