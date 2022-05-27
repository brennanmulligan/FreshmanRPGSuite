package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import datasource.ServerSideTest;
import org.junit.Before;
import org.junit.Test;

import datatypes.ObjectiveStatesForTest;
import datatypes.PlayersForTest;

/**
 * @author Ryan
 *
 */
public class CommandObjectiveNotificationCompleteTest extends ServerSideTest
{
	/**
	 *
	 */
	@Before
	public void localSetup()
	{
		PlayerManager.resetSingleton();
	}

	/**
	 * Test getters and init
	 */
	@Test
	public void testInit()
	{
		CommandObjectiveNotificationComplete cmd = new CommandObjectiveNotificationComplete(1, 2, 3);

		assertEquals(1, cmd.getPlayerID());
		assertEquals(2, cmd.getQuestID());
		assertEquals(3, cmd.getObjectiveID());
	}

	@Test
	public void testExecute()
	{
		int playerID = PlayersForTest.MERLIN.getPlayerID();
		int questID = 4;
		int objectiveID = ObjectiveStatesForTest.PLAYER2_QUEST4_ADV2.getObjectiveID();

		PlayerManager.getSingleton().addPlayer(playerID);

		CommandObjectiveNotificationComplete cmd = new CommandObjectiveNotificationComplete(playerID, questID,
				objectiveID);
		cmd.execute();

		assertFalse(QuestManager.getSingleton().getObjectiveStateByID(playerID, questID, objectiveID)
				.isNeedingNotification());
	}

}
