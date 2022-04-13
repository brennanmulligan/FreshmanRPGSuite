package communication.messages;

import java.io.Serializable;
import java.util.Objects;

public class TerminalTextExitMessage implements Message, Serializable
{

    private int playerID;

    public TerminalTextExitMessage(int playerID)
    {
        this.playerID = playerID;
    }

    public int getPlayerID()
    {
        return playerID;
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
        return playerID == that.playerID;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(playerID);
    }

    @Override
    public String toString()
    {
        return "TerminalTextExitMessage{" +
                "playerID=" + playerID +
                '}';
    }
}

