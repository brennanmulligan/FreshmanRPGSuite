package communication.handlers;

import communication.messages.PlayerJoinedMessage;
import dataDTO.VanityDTO;
import datatypes.PlayersForTest;
import datatypes.VanityType;
import model.ClientModelFacade;
import model.CommandInitializePlayer;
import model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Make sure the message is handled properly
 *
 * @author Merlin
 *
 */
public class PlayerJoinedMessageHandlerTest
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
	 * Test the type of Message that we expect
	 */
	@Test
	public void typeWeHandle()
	{
		PlayerJoinedMessageHandler h = new PlayerJoinedMessageHandler();
		assertEquals(PlayerJoinedMessage.class, h.getMessageTypeWeHandle());
	}

	/**
	 * We should add the player to the player manager
	 *
	 * @throws InterruptedException
	 *             shouldn't
	 */
	@Test
	public void test() throws InterruptedException
	{
		VanityDTO body = new VanityDTO(2, "", "", "character_clothed", VanityType.BODY);

		VanityDTO hat = new VanityDTO(-1, "", "", "hat_duck", VanityType.HAT);

		PlayerJoinedMessage msg = new PlayerJoinedMessage(PlayersForTest.JOSH.getPlayerID(), PlayersForTest.JOSH.name(),
				PlayersForTest.JOSH.getVanityItems(), PlayersForTest.JOSH.getPosition(),
				PlayersForTest.JOSH.getCrew(), PlayersForTest.JOSH.getMajor(), PlayersForTest.JOSH.getSection());
		PlayerJoinedMessageHandler handler = new PlayerJoinedMessageHandler();
		handler.process(msg);
		assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());

		CommandInitializePlayer cmd =(CommandInitializePlayer) ClientModelFacade.getSingleton().getNextCommand();

		assertEquals(PlayersForTest.JOSH.getPlayerID(), cmd.getPlayerID());
		assertEquals(PlayersForTest.JOSH.getPlayerName().toUpperCase(), cmd.getPlayerName());

		assertEquals(PlayersForTest.JOSH.getPosition(), cmd.getPosition());
		assertEquals(PlayersForTest.JOSH.getCrew(), cmd.getCrew());
		assertEquals(PlayersForTest.JOSH.getMajor(), cmd.getMajor());
		assertEquals(PlayersForTest.JOSH.getSection(), cmd.getSection());
	}

}
