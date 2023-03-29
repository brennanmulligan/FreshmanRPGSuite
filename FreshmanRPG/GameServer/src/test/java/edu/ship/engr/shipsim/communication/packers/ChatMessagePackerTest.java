package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.ChatMessageToClient;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.reports.ChatMessageToClientReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Dave
 * <p>
 * Make sure that the ChatMessagePacker behaves properly.
 */
@GameTest("GameServer")
@ResetPlayerManager
public class ChatMessagePackerTest
{

    /**
     * Make sure that the report is properly translated into the message.
     */
    @Test
    public void testPacking()
    {
        PlayerManager.getSingleton().addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());
        int sender = PlayersForTest.MERLIN.getPlayerID();
        int receiver = PlayersForTest.MERLIN.getPlayerID();
        String text = "Hello world";
        Position loc = new Position(0, 0);
        ChatType type = ChatType.Local;

        ChatMessageToClientReport report = new ChatMessageToClientReport(sender, receiver,
                text, loc, type);
        ChatMessageToClientPacker packer = new ChatMessageToClientPacker();
        StateAccumulator sa = new StateAccumulator(null);
        packer.setAccumulator(sa);
        sa.setPlayerId(sender);
        ChatMessageToClient msg = (ChatMessageToClient) packer.pack(report);

        assertEquals(sender, msg.getSenderID());
        assertEquals(receiver, msg.getReceiverID());
        assertEquals(text, msg.getChatText());
        assertEquals(loc, msg.getPosition());
        assertEquals(type, msg.getType());
    }

}
