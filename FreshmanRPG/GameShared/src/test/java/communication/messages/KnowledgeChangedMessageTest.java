package communication.messages;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import datatypes.PlayersForTest;

/**
 * @author Matthew Croft
 * @author Emily Maust
 *
 */
public class KnowledgeChangedMessageTest
{
	/**
	 * Tests that we can create ExperienceChangeMessage and sets its fields
	 */
	@Test
	public void testCreateMessage()
	{
		KnowledgeChangedMessage msg = new KnowledgeChangedMessage(PlayersForTest.JOHN.getPlayerID(),
				PlayersForTest.JOHN.getKnowledgeScore(), PlayersForTest.JOHN.getBuffPool());
		assertEquals(PlayersForTest.JOHN.getKnowledgeScore(), msg.getKnowledgePoints());
		assertEquals(PlayersForTest.JOHN.getBuffPool(), msg.getBuffPool());
	}
}
