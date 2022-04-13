package model.reports;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.ClientPlayerAdventureStateDTO;
import dataDTO.ClientPlayerQuestStateDTO;
import datasource.DatabaseException;
import model.IllegalQuestChangeException;
import model.OptionsManager;
import model.Player;
import model.PlayerManager;
import model.QuestManager;
import datatypes.AdventureStatesForTest;
import datatypes.AdventuresForTest;
import datatypes.LevelsForTest;
import datatypes.PlayersForTest;
import datatypes.QuestStatesForTest;
import datatypes.QuestsForTest;

/**
 * Test the CurrentQuestStateReport
 *
 * @author Ryan, LaVonne, Olivia
 *
 */
public class UpdatePlayerInformationReportTest
{

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		QuestManager.resetSingleton();
	}

	/**
	 * Tests that we can combine a quest description and state
	 *
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void testCombineQuestDescriptionAndState() throws DatabaseException, IllegalQuestChangeException
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);
		UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(john);

		assertEquals(1, report.getPlayerID());
		assertEquals(QuestManager.getSingleton().getQuestList(john.getPlayerID()).size(),
				report.getClientPlayerQuestList().size());

		int i = 1;
		for (ClientPlayerQuestStateDTO q : report.getClientPlayerQuestList())
		{
			if (i == 1)
			{
				assertEquals(QuestsForTest.ONE_BIG_QUEST.getQuestDescription(), q.getQuestDescription());
				assertEquals(QuestStatesForTest.PLAYER1_QUEST1.getState(), q.getQuestState());
			}
			if (i == 2)
			{
				assertEquals(QuestsForTest.THE_OTHER_QUEST.getQuestDescription(), q.getQuestDescription());
				assertEquals(QuestStatesForTest.PLAYER1_QUEST2.getState(), q.getQuestState());
			}
			i++;
		}
	}

	/**
	 * Tests that we can combine a quest description and state
	 *
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void testCombineAdventureDescriptionAndState() throws DatabaseException, IllegalQuestChangeException
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);
		UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(john);

		int i = 1;
		for (ClientPlayerQuestStateDTO q : report.getClientPlayerQuestList())
		{
			ArrayList<ClientPlayerAdventureStateDTO> adventureList = q.getAdventureList();

			if (i == 1)
			{
				int j = 1;
				for (ClientPlayerAdventureStateDTO a : adventureList)
				{
					if (j == 1)
					{
						assertEquals(AdventuresForTest.QUEST1_ADVENTURE_1.getAdventureDescription(),
								a.getAdventureDescription());
						assertEquals(AdventureStatesForTest.PLAYER1_QUEST1_ADV1.getState(), a.getAdventureState());
					}
					if (j == 2)
					{
						assertEquals(AdventuresForTest.QUEST1_ADVENTURE2.getAdventureDescription(),
								a.getAdventureDescription());
						assertEquals(AdventureStatesForTest.PLAYER1_QUEST1_ADV2.getState(), a.getAdventureState());
					}
					j++;
				}

			}
			if (i == 2)
			{
				int j = 1;
				for (ClientPlayerAdventureStateDTO a : adventureList)
				{
					if (j == 1)
					{
						assertEquals(AdventuresForTest.QUEST2_ADVENTURE1.getAdventureDescription(),
								a.getAdventureDescription());
						assertEquals(AdventureStatesForTest.PLAYER1_QUEST2_ADV1.getState(), a.getAdventureState());
					}
					if (j == 2)
					{
						assertEquals(AdventuresForTest.QUEST2_ADVENTURE2.getAdventureDescription(),
								a.getAdventureDescription());
						assertEquals(AdventureStatesForTest.PLAYER1_QUEST2_ADV2.getState(), a.getAdventureState());
					}
					j++;
				}
			}
			i++;
		}
	}

	/**
	 * Tests that we can report a players experience points and their levels
	 * description and the points level requires
	 *
	 * @throws DatabaseException shouldn't
	 * @throws IllegalQuestChangeException the state changed illegally
	 */
	@Test
	public void testExperienceAndLevelPoints() throws DatabaseException, IllegalQuestChangeException
	{
		Player john = PlayerManager.getSingleton().addPlayer(1);

		UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(john);
		assertEquals(PlayersForTest.JOHN.getExperiencePoints(), report.getExperiencePts());
		assertEquals(LevelsForTest.TWO.getDescription(), report.getLevel().getDescription());
		assertEquals(LevelsForTest.TWO.getLevelUpPoints(), report.getLevel().getLevelUpPoints());

	}
}
