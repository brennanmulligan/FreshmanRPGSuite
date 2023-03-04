package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.MapFileMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandNewMap;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Merlin
 */
@GameTest("GameClient")
@ResetClientModelFacade(shouldClearQueue = true)
public class MapFileMessageHandlerTest
{
    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        MapFileMessageHandler h = new MapFileMessageHandler();
        assertEquals(MapFileMessage.class, h.getMessageTypeWeHandle());
    }

    /**
     * The handler should tell the model that the new file is there. We will
     * know that happens if the model reports the change to its observers
     *
     * @throws IOException          shouldn't
     * @throws InterruptedException shouldn't
     */
    @Test
    public void tellsEngine() throws IOException, InterruptedException
    {
        MapFileMessage msg =
                new MapFileMessage(PlayersForTest.MERLIN.getPlayerID(), false, "mct1.tmx");
        MapFileMessageHandler handler = new MapFileMessageHandler();
        handler.process(msg);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        CommandNewMap cmd = (CommandNewMap) ClientModelFacade.getSingleton().getNextCommand();
        assertTrue(cmd.getFileTitle().contains("mct1.tmx"));

    }
}
