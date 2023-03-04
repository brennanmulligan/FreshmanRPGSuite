package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * Used to log a player into a server
 *
 * @author merlin
 */
public class LoginMessage extends Message implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String playerName;
    private String password;

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
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
        LoginMessage other = (LoginMessage) obj;
        if (password == null)
        {
            if (other.password != null)
            {
                return false;
            }
        }
        else if (!password.equals(other.password))
        {
            return false;
        }
        if (playerName == null)
        {
            if (other.playerName != null)
            {
                return false;
            }
        }
        else if (!playerName.equals(other.playerName))
        {
            return false;
        }
        return true;
    }

    /**
     * @return the player name that is being logged on
     */
    public String getPlayerName()
    {
        return playerName;
    }

    /**
     * @param playerName the player's name
     * @param password   the player's password
     */
    public LoginMessage(String playerName, String password, boolean quietMessage)
    {
        super(0, quietMessage);
        this.playerName = playerName;
        this.password = password;
    }

    public LoginMessage()
    {
        super(0);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "Login Message: playerName = " + playerName + " and password = " + password;
    }

}
