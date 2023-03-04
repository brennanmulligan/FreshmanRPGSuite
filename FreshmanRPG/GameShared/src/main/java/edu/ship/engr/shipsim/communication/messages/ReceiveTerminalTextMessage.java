package edu.ship.engr.shipsim.communication.messages;

import java.io.Serializable;

/**
 * @author Chris Roadcap
 * @author Denny Fleagle
 */
public class ReceiveTerminalTextMessage extends Message implements Serializable
{
    private final String resultText;
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param resultText         The text to send back to the client
     * @param requestingPlayerID the id of the player requesting the information
     */
    public ReceiveTerminalTextMessage(int requestingPlayerID, boolean quietMessage, String resultText)
    {
        super(requestingPlayerID, quietMessage);
        this.resultText = resultText;
    }

    /**
     * Gets the list of players/maps Strings
     *
     * @return the list of players/maps Strings
     */
    public String getResultText()
    {
        return resultText;
    }

    /**
     * get the player id of the requesting player
     *
     * @return requesting player id
     */
    public int getRequestingPlayerID()
    {
        return relevantPlayerID;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + relevantPlayerID;
        result = prime * result + ((resultText == null) ? 0 : resultText.hashCode());
        return result;
    }

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
        ReceiveTerminalTextMessage other = (ReceiveTerminalTextMessage) obj;
        if (relevantPlayerID != other.relevantPlayerID)
        {
            return false;
        }
        if (resultText == null)
        {
            return other.resultText == null;
        }
        else
        {
            return resultText.equals(other.resultText);
        }
    }

    @Override
    public String toString()
    {
        return "ReceiveTerminalTextMessage{" +
                "resultText='" + resultText + '\'' +
                ", requestingPlayerID=" + relevantPlayerID +
                '}';
    }
}
