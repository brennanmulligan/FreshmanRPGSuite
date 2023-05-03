package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Derek
 */
public class RestfulLoginMessage extends Message implements Serializable
{
    // We have this because we need it :)
    private static final long serialVersionUID = 1L;

    public final String playerName;
    public final String password;

    public RestfulLoginMessage(String playerName, String password, boolean quietMessage)
    {
        super(0, quietMessage);
        this.playerName = playerName;
        this.password = password;
    }


    public String getPlayerName()
    {
        return playerName;
    }

    public String getPassword()
    {
        return password;
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

        RestfulLoginMessage otherMessage = (RestfulLoginMessage) o;

        return Objects.equals(playerName, otherMessage.getPlayerName()) && Objects.equals(password, otherMessage.getPassword());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(playerName, password);
    }
}
