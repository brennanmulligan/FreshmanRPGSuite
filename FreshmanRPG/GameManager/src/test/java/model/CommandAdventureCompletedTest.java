package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.AdventureStateRecordDTO;
import datasource.AdventureStateTableDataGatewayMock;
import datasource.DatabaseException;
import datatypes.AdventureStateEnum;
import datatypes.AdventureStatesForTest;

/**
 * Test the complete adventure command
 *
 */
public class CommandAdventureCompletedTest
{

	private ArrayList<AdventureStateRecordDTO> adventures;


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
	public void testAdventureCompletedCommandNewQuest() throws DatabaseException
	{
		int playerID = 1;
		int questID = 0;
		int adventureID = 0;

		CommandAdventureCompleted cm = new CommandAdventureCompleted(playerID, questID, adventureID);
		assertTrue(cm.execute());

		adventures = AdventureStateTableDataGatewayMock.getSingleton().getAdventureStates(playerID, questID);

		assertEquals(AdventureStateEnum.COMPLETED, adventures.get(adventureID).getState());

	}

	/**
	 * test that an existing adventure is updated
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testAdventureCompletedCommandExistingQuest() throws DatabaseException
	{
		AdventureStatesForTest adv = AdventureStatesForTest.PLAYER2_QUEST1_ADV3;

		CommandAdventureCompleted cm = new CommandAdventureCompleted(adv.getPlayerID(), adv.getQuestID(), adv.getAdventureID());
		assertTrue(cm.execute());

		adventures = AdventureStateTableDataGatewayMock.getSingleton().getAdventureStates(adv.getPlayerID(), adv.getQuestID());

		assertEquals(AdventureStateEnum.COMPLETED, adventures.get(adv.getAdventureID() - 1).getState());

	}
}
