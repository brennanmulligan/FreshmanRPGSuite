package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * A message for user key input.
 *
 * @author Ian Keefer & TJ Renninger
 */
public class KeyInputMessage extends Message implements Serializable
{

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((input == null) ? 0 : input.hashCode());
        return result;
    }

    /**
     * allows comparison between objects
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
        KeyInputMessage other = (KeyInputMessage) obj;
        if (input == null)
        {
            if (other.input != null)
            {
                return false;
            }
        }
        else if (!input.equals(other.input))
        {
            return false;
        }
        return true;
    }

    private static final long serialVersionUID = 1L;
    private String input;

    /**
     * @param input user key input
     */
    public KeyInputMessage(String input, boolean quietMessage)
    {
        super(0, quietMessage);
        this.input = input;
    }

    /**
     * @return user key input
     */
    public String getInput()
    {
        return input;
    }

    /**
     * @return returns a string concat of "KeyInputMessage" + the key they pressed.
     */
    @Override
    public String toString()
    {
        return "KeyInputMessage: " + input;
    }

}
