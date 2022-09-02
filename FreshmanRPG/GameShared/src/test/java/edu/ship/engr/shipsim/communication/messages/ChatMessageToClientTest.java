package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests a ChatMessage
 *
 * @author Andrew
 */
public class ChatMessageToClientTest
{

    /**
     * Test initialization of a chat message
     */
    @Test
    public void test()
    {
        Position p = new Position(0, 0);
        ChatMessageToClient
                msg = new ChatMessageToClient(PlayersForTest.MERLIN.getPlayerID(), 0, "Hello World!", p, ChatType.Local);
        assertEquals(PlayersForTest.MERLIN.getPlayerID(), msg.getSenderID());
        assertEquals(0, msg.getReceiverID());
        assertEquals("Hello World!", msg.getChatText());
        assertEquals(ChatType.Local, msg.getType());
        assertEquals(p, msg.getPosition());
    }

}
