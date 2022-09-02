package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.MapFileMessage;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandNewMap;
import edu.ship.engr.shipsim.model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Merlin
 */
public class MapFileMessageHandlerTest
{

    @BeforeClass
    public static void hardReset()
    {
        OptionsManager.getSingleton().setTestMode(true);
    }

    /**
     * reset the singletons and tell the model we are running headless
     */
    @Before
    public void setUp()
    {
        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, false);

    }

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
                new MapFileMessage("mct1.tmx");
        MapFileMessageHandler handler = new MapFileMessageHandler();
        handler.process(msg);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        CommandNewMap cmd = (CommandNewMap) ClientModelFacade.getSingleton().getNextCommand();
        assertTrue(cmd.getFileTitle().contains("mct1.tmx"));
        ClientModelFacade.getSingleton().emptyQueue();

    }
}
