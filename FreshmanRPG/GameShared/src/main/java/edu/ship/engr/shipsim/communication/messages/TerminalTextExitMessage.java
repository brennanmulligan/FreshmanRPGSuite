package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;
import java.util.Objects;

public class TerminalTextExitMessage extends Message implements Serializable
{

    public TerminalTextExitMessage(int playerID, boolean quietMessage)
    {
        super(playerID, quietMessage);
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
        TerminalTextExitMessage that = (TerminalTextExitMessage) o;
        return relevantPlayerID == that.relevantPlayerID;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(relevantPlayerID);
    }

    @Override
    public String toString()
    {
        return "TerminalTextExitMessage{" +
                "playerID=" + relevantPlayerID +
                '}';
    }
}

