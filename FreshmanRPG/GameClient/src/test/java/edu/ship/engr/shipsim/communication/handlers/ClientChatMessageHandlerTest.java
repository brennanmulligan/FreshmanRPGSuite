package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ChatMessageToClient;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandChatMessageReceivedFromServer;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Frank Schmidt
 */
@GameTest("GameClient")
@ResetClientModelFacade(shouldClearQueue = true)
public class ClientChatMessageHandlerTest
{
    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        ClientChatMessageHandler h = new ClientChatMessageHandler();
        assertEquals(ChatMessageToClient.class, h.getMessageTypeWeHandle());
    }

    /**
     * We should add a command to the ModelFacade command queue
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void test() throws InterruptedException
    {
        ClientChatMessageHandler handler = new ClientChatMessageHandler();
        ChatMessageToClient
                chat = new ChatMessageToClient(PlayersForTest.MERLIN.getPlayerID(), PlayersForTest.MERLIN.getPlayerID(), false, "message", new Position(1, 1),
                ChatType.Local);
        handler.process(chat);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        CommandChatMessageReceivedFromServer cmd =
                (CommandChatMessageReceivedFromServer) ClientModelFacade.getSingleton().getNextCommand();
        assertEquals(PlayersForTest.MERLIN.getPlayerID(), cmd.getSenderID());
        assertEquals(PlayersForTest.MERLIN.getPlayerID(), cmd.getReceiverID());
        assertEquals("message", cmd.getChatText());
        assertEquals(new Position(1, 1), cmd.getLocation());
        assertEquals(ChatType.Local, cmd.getType());
    }
}
