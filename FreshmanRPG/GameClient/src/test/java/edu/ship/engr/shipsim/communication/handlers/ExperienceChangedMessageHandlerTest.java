package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ExperienceChangedMessage;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.ClientModelTestUtilities;
import edu.ship.engr.shipsim.model.CommandOverwriteExperience;
import edu.ship.engr.shipsim.model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import static org.junit.Assert.assertEquals;

/**
 * @author Ryan
 */
public class ExperienceChangedMessageHandlerTest
{

    @BeforeClass
    public static void hardReset()
    {
        OptionsManager.getSingleton().setTestMode(true);
    }

    /**
     * Reset the ModelFacade
     *
     * @throws NotBoundException     shouldn't
     * @throws AlreadyBoundException shouldn't
     */
    @Before
    public void reset() throws AlreadyBoundException, NotBoundException
    {
        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, false);
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JOHN);
    }

    /**
     * Tests that getTypeWeHandle method returns correct type.
     */
    @Test
    public void testTypeWeHandle()
    {
        ExperienceChangedMessageHandler h = new ExperienceChangedMessageHandler();
        assertEquals(ExperienceChangedMessage.class, h.getMessageTypeWeHandle());
    }

    /**
     * Testing to see if a command is queued after receiving a message
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void handleExperienceChangedMessage() throws InterruptedException
    {
        LevelRecord record = new LevelRecord("Serf", 15, 10, 7);

        ExperienceChangedMessage msg = new ExperienceChangedMessage(
                PlayersForTest.JOHN.getPlayerID(),
                PlayersForTest.JOHN.getExperiencePoints() + 2, record);
        ExperienceChangedMessageHandler h = new ExperienceChangedMessageHandler();
        h.process(msg);

        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        CommandOverwriteExperience x = (CommandOverwriteExperience) ClientModelFacade
                .getSingleton().getNextCommand();
        assertEquals(PlayersForTest.JOHN.getExperiencePoints() + 2,
                x.getExperiencePoints());
        assertEquals(record, x.getLevelRecord());
        ClientModelFacade.getSingleton().emptyQueue();
    }
}
