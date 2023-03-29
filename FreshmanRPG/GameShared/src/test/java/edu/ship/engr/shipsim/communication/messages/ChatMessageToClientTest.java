package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Tests a ChatMessage
 *
 * @author Andrew
 */
@GameTest("GameShared")
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
                msg = new ChatMessageToClient(PlayersForTest.MERLIN.getPlayerID(), PlayersForTest.MERLIN.getPlayerID(), false, "Hello World!", p, ChatType.Local);
        assertEquals(PlayersForTest.MERLIN.getPlayerID(), msg.getSenderID());
        assertEquals(PlayersForTest.MERLIN.getPlayerID(), msg.getReceiverID());
        assertEquals("Hello World!", msg.getChatText());
        assertEquals(ChatType.Local, msg.getType());
        assertEquals(p, msg.getPosition());
        assertFalse(msg.isQuietMessage());
    }

}
