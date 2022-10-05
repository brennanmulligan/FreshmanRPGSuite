package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.ChatMessageToClient;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.ChatMessageToClientReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Dave
 * <p>
 * Make sure that the ChatMessagePacker behaves properly.
 */
@GameTest("GameServer")
public class ChatMessagePackerTest
{

    /**
     * Make sure that the report is properly translated into the message.
     */
    @Test
    public void testPacking()
    {
        int sender = 42;
        int receiver = 0;
        String text = "Hello world";
        Position loc = new Position(0, 0);
        ChatType type = ChatType.Local;

        ChatMessageToClientReport report = new ChatMessageToClientReport(sender, receiver,
                text, loc, type);
        ChatMessageToClientPacker packer = new ChatMessageToClientPacker();
        ChatMessageToClient msg = (ChatMessageToClient) packer.pack(report);

        assertEquals(sender, msg.getSenderID());
        assertEquals(receiver, msg.getReceiverID());
        assertEquals(text, msg.getChatText());
        assertEquals(loc, msg.getPosition());
        assertEquals(type, msg.getType());
    }

}
