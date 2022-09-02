package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Josh
 */
public class ChatMessageReceivedCommandTest extends ServerSideTest
{

    /**
     * Making sure that the CommandChatMessageReceived has the correct
     * information
     */
    @Test
    public void testCreateTheReportWithCorrectMessage()
    {
        String text = "Hello";
        int playerID = 42;
        int receiverID = 0;
        Position location = new Position(1, 1);
        ChatType type = ChatType.Local;

        CommandChatMessageReceived ccmr = new CommandChatMessageReceived(playerID, receiverID, text, location, type);

        assertEquals(text, ccmr.getChatText());
        assertEquals(playerID, ccmr.getSenderID());
        assertEquals(receiverID, ccmr.getReceiverID());
        assertEquals(location, ccmr.getLocation());
        assertEquals(type, ccmr.getType());
    }
}