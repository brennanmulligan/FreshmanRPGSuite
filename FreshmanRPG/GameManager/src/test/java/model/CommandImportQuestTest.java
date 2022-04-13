package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import criteria.GameLocationDTO;
import criteria.QuestCompletionActionParameter;
import dataENUM.QuestCompletionActionType;
import datasource.DatabaseException;
import datasource.DatabaseTest;
import datasource.InvalidColumnException;
import datasource.QuestTableDataGatewayMock;
import datatypes.Position;

/**
 * Tests for quest import
 * @author Jordan Long
 *
 */
public class CommandImportQuestTest extends DatabaseTest
{

	private final String filePath = "testdata/quest.csv";


	/**
	 * Set up the test setting.
	 * @throws DatabaseException  shouldn't
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		QuestTableDataGatewayMock.getInstance().resetData();

	}


	/**
	 * Test that quests can be imported.
	 * @throws DatabaseException - shouldn't
	 * @throws InvalidColumnException - shouldn't
	 * @throws IOException - shouldn't
	 */
	@Test
	public void testImportQuests() throws DatabaseException, IOException, InvalidColumnException
	{

		File file = new File(filePath);
		String date = "04/04/2018";
		LocalDate inputDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		CommandImportQuestAdventure command = new CommandImportQuestAdventure(file, inputDate);
		boolean result = command.execute();
		if (!result)
		{
			fail();
		}

		final String questTitle = "New Test Quest";
		final String description = "Test Quest Description";
		final String triggerMapName = "theGreen.tmx";
		final int exp = 10;
		final QuestCompletionActionType completionActionType = QuestCompletionActionType.TELEPORT;
		Position p = new Position(5, 5);
		final QuestCompletionActionParameter completionActionParameter = new GameLocationDTO("theGreen.tmx", p);

		boolean found = false;

		for (QuestRecord quest : GameManagerQuestManager.getInstance().getQuests())
		{

			if (quest.getTitle() == questTitle)
			{
				found = true;
				assertEquals(quest.getTitle(), questTitle);
				assertEquals(quest.getDescription(), description);
				assertEquals(quest.getMapName(), triggerMapName);
				assertEquals(quest.getExperiencePointsGained(), exp);
				assertEquals(quest.getCompletionActionType(), completionActionType);
				assertEquals(quest.getCompletionActionParameter(), completionActionParameter);
			}
		}

		if (found)
		{
			fail();
		}

	}


	/**
	 * Test that quests can be imported.
	 * @throws DatabaseException - shouldn't
	 * @throws InvalidColumnException - shouldn't
	 * @throws IOException - shouldn't
	 */
	@Test
	public void testImportAdventures() throws DatabaseException, IOException, InvalidColumnException
	{

		File file = new File(filePath);
		String date = "04/04/2018";
		LocalDate inputDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		CommandImportQuestAdventure command = new CommandImportQuestAdventure(file, inputDate);
		boolean result = command.execute();
		if (!result)
		{
			fail();
		}

		QuestRecord quest = GameManagerQuestManager.getInstance().getQuest("New Test Quest");

		boolean found = false;

		for (AdventureRecord adventure : quest.getAdventures())
		{
			System.out.println(adventure.getAdventureDescription());
			if (adventure.getAdventureDescription() == "Adventure 1 description")
			{
				found = true;
			}
		}

		if (found)
		{
			fail();
		}

	}
}
