package communication.handlers;

import static org.junit.Assert.assertEquals;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import datatypes.PlayersForTest;
import model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import communication.messages.ExperienceChangedMessage;
import datasource.LevelRecord;
import model.ClientModelFacade;
import model.ClientModelTestUtilities;
import model.CommandOverwriteExperience;

/**
 * @author Ryan
 *
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
	 * @throws NotBoundException
	 *             shouldn't
	 * @throws AlreadyBoundException
	 *             shouldn't
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
	 * @throws InterruptedException
	 *             shouldn't
	 *
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
