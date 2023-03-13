package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * @author bl5922 Message that contains the players ID and is packed by the
 * GetOnlinePlayersPacker
 */
public class SendTerminalTextMessage extends Message implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String terminalText;

    /**
     * @param playerID     for the client player
     * @param terminalText command from terminal
     */
    public SendTerminalTextMessage(int playerID, boolean quietMessage, String terminalText)
    {
        super(playerID, quietMessage);
        this.terminalText = terminalText;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + relevantPlayerID;
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
        SendTerminalTextMessage other = (SendTerminalTextMessage) obj;
        if (relevantPlayerID != other.relevantPlayerID)
        {
            return false;
        }
        return true;
    }

    /**
     * @return getTerminalText
     */
    public String getTerminalText()
    {
        return terminalText;
    }

    @Override
    public String toString()
    {
        return "SendTerminalTextMessage{" +
                "playerID=" + relevantPlayerID +
                ", terminalText='" + terminalText + '\'' +
                '}';
    }
}
