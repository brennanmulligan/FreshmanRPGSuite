package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.ObjectiveStateChangeMessage;
import edu.ship.engr.shipsim.datatypes.ObjectiveStateEnum;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.ClientModelTestUtilities;
import edu.ship.engr.shipsim.model.CommandObjectiveStateChange;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests functionality of the ObjectiveStateChangeMessageHandler
 *
 * @author Ryan
 */
@GameTest("GameClient")
@ResetClientModelFacade(shouldClearQueue = true)
public class ObjectiveStateChangeMessageHandlerTest
{
    /**
     * Test the type of Message that we expect
     */
    @Test
    public void testTypeWeHandle()
    {
        ObjectiveStateChangeMessageHandler h = new ObjectiveStateChangeMessageHandler();
        assertEquals(ObjectiveStateChangeMessage.class, h.getMessageTypeWeHandle());
    }


    /**
     * Test that the handler messages handles the messages and creates
     * a command
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void testMessageHandling() throws InterruptedException
    {
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JOHN);
        ObjectiveStateChangeMessageHandler h = new ObjectiveStateChangeMessageHandler();
        ObjectiveStateChangeMessage msg = new ObjectiveStateChangeMessage(1, false, 2, 3, "Big Objective", ObjectiveStateEnum.TRIGGERED, true, "Grammy");

        h.process(msg);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        CommandObjectiveStateChange cmd = (CommandObjectiveStateChange) ClientModelFacade.getSingleton().getNextCommand();
        assertEquals(2, cmd.getQuestID());
        assertEquals(3, cmd.getObjectiveID());
        assertEquals("Big Objective", cmd.getObjectiveDescription());
        assertEquals(ObjectiveStateEnum.TRIGGERED, cmd.getObjectiveState());
        assertTrue(cmd.isRealLifeObjective());
        assertEquals("Grammy", cmd.getWitnessTitle());
    }

}
