package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.model.reports.TerminalResponseReport;

/**
 * Command that is called when the server sends the result of a terminal
 * command sent by the user.
 *
 * @author Nathaniel, Allen
 */
public class CommandRecieveTerminalResponse extends Command
{

    private String terminalResult;
    private int playerID;
    private ChatType type = ChatType.Terminal;

    /**
     * @param senderID        Player who sent the terminal command.
     * @param terminalResults Result of the terminal command to be sent to the player.
     */
    public CommandRecieveTerminalResponse(int senderID, String terminalResults)
    {
        this.playerID = senderID;
        this.terminalResult = terminalResults;
    }

    /**
     * @return the message that was received the server
     */
    public String getTerminalResult()
    {
        return terminalResult;
    }

    /**
     * @return the name of the player that sent the message
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * @return the type of message
     */
    public ChatType getType()
    {
        return type;
    }

    /**
     * The ChatManager will call the sendChatToUI method with the following
     * parameters.
     */
    @Override
    boolean execute()
    {
        TerminalResponseReport report = new TerminalResponseReport(playerID, terminalResult);
        QualifiedObservableConnector.getSingleton().sendReport(report);

        return true;
    }

}
