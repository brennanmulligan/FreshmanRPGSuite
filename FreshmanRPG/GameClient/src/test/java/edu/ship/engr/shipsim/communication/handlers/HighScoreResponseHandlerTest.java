package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.HighScoreResponseMessage;
import edu.ship.engr.shipsim.datatypes.PlayerScoreRecord;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandHighScoreResponse;
import edu.ship.engr.shipsim.model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author nk3668
 */
public class HighScoreResponseHandlerTest
{
    @BeforeClass
    public static void hardReset()
    {
        OptionsManager.getSingleton().setTestMode(true);
    }

    /**
     * Reset the ModelFacade
     */
    @Before
    public void reset()
    {
        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, false);
    }

    /**
     *
     */
    @Test
    public void testTypeWeHandle()
    {
        HighScoreResponseMessageHandler h = new HighScoreResponseMessageHandler();
        assertEquals(HighScoreResponseMessage.class, h.getMessageTypeWeHandle());
    }

    /**
     * Tests that we queue the message to the facade.
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void handleHighScoreResponseMessage() throws InterruptedException
    {
        reset();

        ArrayList<PlayerScoreRecord> list = new ArrayList<>();
        list.add(new PlayerScoreRecord("Ethan", 0));
        list.add(new PlayerScoreRecord("Weaver", 3));
        list.add(new PlayerScoreRecord("Merlin", 9001));
        list.add(new PlayerScoreRecord("Nate", 984257));

        HighScoreResponseMessage msg = new HighScoreResponseMessage(list);
        HighScoreResponseMessageHandler h = new HighScoreResponseMessageHandler();
        h.process(msg);

        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        CommandHighScoreResponse cmd = (CommandHighScoreResponse) ClientModelFacade.getSingleton().getNextCommand();
        assertEquals(list, cmd.getScoreRecord());
        ClientModelFacade.getSingleton().emptyQueue();
        reset();
    }
}
