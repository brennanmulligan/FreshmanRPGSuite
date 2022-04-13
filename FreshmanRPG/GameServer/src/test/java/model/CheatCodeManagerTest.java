package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import datatypes.ChatType;
import model.cheatCodeBehaviors.MockCheatCodeBehavior;
import datatypes.PlayersForTest;

/**
 * Tests for the Server's CheatCodeManager
 *
 * @author merlin
 *
 */
public class CheatCodeManagerTest
{

	private CheatCodeManager mgr;

	/**
	 * Start fresh for each test
	 */
	@Before
	public void reset()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		mgr = new CheatCodeManager();
	}

	/**
	 * It needs to find all of the cheat behaviors (including our mock one for
	 * testing)
	 */
	@Test
	public void registersBehaviors()
	{
		assertTrue(mgr.hasCheatCodeBehavior(MockCheatCodeBehavior.class));
	}

	/**
	 * Makes sure that behaviors are fired for every chat type
	 */
	@Test
	public void tellsBehaviorToFireOnAllChatTypes()
	{
		PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		for (ChatType type : ChatType.values())
		{
			tellsBehaviorToFireIfChatTypeIs(type);
		}
	}

	private void tellsBehaviorToFireIfChatTypeIs(ChatType type)
	{
		MockCheatCodeBehavior.gaveCheat = false;
		assertTrue(mgr.handleChatTextForCheatBehaviors(PlayersForTest.MERLIN.getPlayerID(), MockCheatCodeBehavior.CHAT_TEXT));
		assertTrue(MockCheatCodeBehavior.gaveCheat);
	}

	/**
	 * If the string isn't a valid cheat code, we should get a false return value
	 */
	@Test
	public void returnsFalseIfNotACheatCode()
	{
		MockCheatCodeBehavior.gaveCheat = false;
		assertFalse(mgr.handleChatTextForCheatBehaviors(PlayersForTest.MERLIN.getPlayerID(), MockCheatCodeBehavior.CHAT_TEXT + 'Z'));
	}
}
