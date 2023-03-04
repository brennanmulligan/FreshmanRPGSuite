package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ExperienceChangedMessage;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.ClientModelTestUtilities;
import edu.ship.engr.shipsim.model.CommandOverwriteExperience;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Ryan
 */
@GameTest("GameClient")
@ResetClientModelFacade(shouldClearQueue = true)
public class ExperienceChangedMessageHandlerTest
{
    @BeforeEach
    public void setup()
    {
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
                PlayersForTest.JOHN.getPlayerID(), false,
                PlayersForTest.JOHN.getExperiencePoints() + 2, record);
        ExperienceChangedMessageHandler h = new ExperienceChangedMessageHandler();
        h.process(msg);

        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        CommandOverwriteExperience x = (CommandOverwriteExperience) ClientModelFacade
                .getSingleton().getNextCommand();
        assertEquals(PlayersForTest.JOHN.getExperiencePoints() + 2,
                x.getExperiencePoints());
        assertEquals(record, x.getLevelRecord());
    }
}
