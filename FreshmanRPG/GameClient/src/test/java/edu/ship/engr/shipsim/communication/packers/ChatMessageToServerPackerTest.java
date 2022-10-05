package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.ChatMessageToServer;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.ChatSentReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Dave
 * <p>
 * Make sure that the ChatMessagePacker behaves properly.
 */
@GameTest("GameClient")
public class ChatMessageToServerPackerTest
{

    /**
     * Make sure that the report is properly translated into the message.
     */
    @Test
    public void testPacking()
    {
        int sender = 16;
        int receiver = 0;
        String text = "Hello world";
        Position loc = new Position(0, 0);
        ChatType type = ChatType.Local;

        ChatSentReport report = new ChatSentReport(sender, receiver, text, loc, type);
        ClientChatMessagePacker packer = new ClientChatMessagePacker();
        ChatMessageToServer msg = (ChatMessageToServer) packer.pack(report);

        assertEquals(sender, msg.getSenderID());
        assertEquals(receiver, msg.getReceiverID());
        assertEquals(text, msg.getChatText());
        assertEquals(loc, msg.getPosition());
        assertEquals(type, msg.getType());
    }

}
