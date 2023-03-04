package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.PlayerManager;

/**
 * @author Chris Roadcap
 * @author Denny Fleagle
 */

public class ReceiveTerminalTextReport extends SendMessageReport
{

    private int requestingPlayerID;
    private String resultText;
    private String command;

    /**
     * Constructor
     *
     * @param requestingPlayerID - the id of the player requesting the information
     * @param resultText         - the text from the terminal
     * @param command            - the name of the terminal command
     */
    public ReceiveTerminalTextReport(int requestingPlayerID, String resultText, String command)
    {
        super(requestingPlayerID, !PlayerManager.getSingleton().isNPC(requestingPlayerID));
        this.requestingPlayerID = requestingPlayerID;
        this.resultText = resultText;
        this.command = command;
    }

    /**
     * Get text from the terminal
     *
     * @return the text to send back to client
     */
    public String getResultText()
    {
        return resultText;
    }

    /**
     * Get the player id of the requesting player
     *
     * @return playerID
     */
    public int getPlayerID()
    {
        return requestingPlayerID;
    }

    /**
     * Get the name of the command from the report
     *
     * @return command - the name of the command
     */
    public String getCommand()
    {
        return this.command;
    }
}
