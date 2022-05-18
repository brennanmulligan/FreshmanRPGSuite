package communication.handlers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import datatypes.PlayersForTest;
import model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import communication.messages.ObjectiveStateChangeMessage;
import datatypes.ObjectiveStateEnum;
import model.ClientModelFacade;
import model.ClientModelTestUtilities;
import model.CommandObjectiveStateChange;

/**
 * Tests functionality of the ObjectiveStateChangeMessageHandler
 * @author Ryan
 *
 */
public class ObjectiveStateChangeMessageHandlerTest
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
	public void setUp()
	{
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, false);
	}

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
	 * @throws InterruptedException shouldn't
	 */
	@Test
	public void testMessageHandling() throws InterruptedException
	{
		ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JOHN);
		ObjectiveStateChangeMessageHandler h = new ObjectiveStateChangeMessageHandler();
		ObjectiveStateChangeMessage msg = new ObjectiveStateChangeMessage(1, 2, 3, "Big Objective", ObjectiveStateEnum.TRIGGERED, true, "Grammy");

		h.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
		CommandObjectiveStateChange cmd = (CommandObjectiveStateChange) ClientModelFacade.getSingleton().getNextCommand();
		assertEquals(2, cmd.getQuestID());
		assertEquals(3, cmd.getObjectiveID());
		assertEquals("Big Objective", cmd.getObjectiveDescription());
		assertEquals(ObjectiveStateEnum.TRIGGERED, cmd.getObjectiveState());
		assertTrue(cmd.isRealLifeObjective());
		assertEquals("Grammy", cmd.getWitnessTitle());
		ClientModelFacade.getSingleton().emptyQueue();
	}

}
