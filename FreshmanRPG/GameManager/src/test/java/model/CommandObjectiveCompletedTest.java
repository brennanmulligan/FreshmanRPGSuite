package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.ObjectiveStateRecordDTO;
import datasource.ObjectiveStateTableDataGatewayMock;
import datasource.DatabaseException;
import datatypes.ObjectiveStateEnum;
import datatypes.ObjectiveStatesForTest;

/**
 * Test the complete objective command
 *
 */
public class CommandObjectiveCompletedTest
{

	private ArrayList<ObjectiveStateRecordDTO> objectives;


	/**
	 * First make sure the DatabaseTest.setUp() is called
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * test that a new state is added
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testObjectiveCompletedCommandNewQuest() throws DatabaseException
	{
		int playerID = 1;
		int questID = 0;
		int objectiveID = 0;

		CommandObjectiveCompleted cm = new CommandObjectiveCompleted(playerID, questID, objectiveID);
		assertTrue(cm.execute());

		objectives = ObjectiveStateTableDataGatewayMock.getSingleton().getObjectiveStates(playerID, questID);

		assertEquals(ObjectiveStateEnum.COMPLETED, objectives.get(objectiveID).getState());

	}

	/**
	 * test that an existing objective is updated
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testObjectiveCompletedCommandExistingQuest() throws DatabaseException
	{
		ObjectiveStatesForTest adv = ObjectiveStatesForTest.PLAYER2_QUEST1_ADV3;

		CommandObjectiveCompleted cm = new CommandObjectiveCompleted(adv.getPlayerID(), adv.getQuestID(), adv.getObjectiveID());
		assertTrue(cm.execute());

		objectives = ObjectiveStateTableDataGatewayMock.getSingleton().getObjectiveStates(adv.getPlayerID(), adv.getQuestID());

		assertEquals(ObjectiveStateEnum.COMPLETED, objectives.get(adv.getObjectiveID() - 1).getState());

	}
}
