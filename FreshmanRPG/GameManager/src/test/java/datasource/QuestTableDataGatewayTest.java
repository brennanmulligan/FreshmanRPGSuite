package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.ObjectiveRecord;
import model.QuestRecord;
import datatypes.QuestsForTest;

/**
 * Common tests for QuestTableDataGateway.
 */
public abstract class QuestTableDataGatewayTest extends DatabaseTest
{
	/**
	 * @throws DatabaseException shouldn't
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
	}

	/**
	 * Make sure any static information is cleaned up between tests.
	 *
	 * @throws DatabaseException data source exception
	 */
	@After
	public void cleanup() throws DatabaseException
	{
		getGatewaySingleton().resetData();
	}

	/**
	 * Returns an instance of QuestTableDataGateway.
	 *
	 * @return an instance of QuestTableDataGateway
	 */
	public abstract QuestTableDataGateway getGatewaySingleton();

	/**
	 * Should be a singleton.
	 */
	@Test
	public void testIsASingleton()
	{
		QuestTableDataGateway x = getGatewaySingleton();
		QuestTableDataGateway y = getGatewaySingleton();
		assertSame(x, y);
		assertNotNull(x);
	}

	/**
	 * Ensures that the gateways are able to retrieve all quests.
	 *
	 * @throws DatabaseException - problem reading Quests table
	 */
	@Test
	public void testGetAllQuests() throws DatabaseException
	{
		QuestTableDataGateway gateway = getGatewaySingleton();
		assertQuestListMatchesTestData(gateway.getAllQuests());
	}

	private void assertQuestListMatchesTestData(List<QuestRecord> actual)
	{
		List<QuestsForTest> expected = Arrays.asList(QuestsForTest.values());
		assertEquals(expected.size(), actual.size());

		for (int i = 0; i < expected.size(); i++)
		{
			assertQuestsAreEqual(expected.get(i), actual.get(i));
		}
	}

	private void assertQuestsAreEqual(QuestsForTest expected, QuestRecord actual)
	{
		assertQuestObjectiveListMatchesTestData(actual);
		assertEquals(expected.getQuestID(), actual.getQuestID());
		assertEquals(expected.getQuestTitle(), actual.getTitle());
		assertEquals(expected.getQuestDescription(), actual.getDescription());
		assertEquals(expected.getMapName(), actual.getMapName());
		assertEquals(expected.getPosition(), actual.getPos());
		assertEquals(expected.getExperienceGained(), actual.getExperiencePointsGained());
		assertEquals(expected.getObjectiveForFulfillment(), actual.getObjectivesForFulfillment());
		assertEquals(expected.getCompletionActionType(), actual.getCompletionActionType());
		assertEquals(expected.getStartDate(), actual.getStartDate());
		assertEquals(expected.getEndDate(), actual.getEndDate());
	}

	private void assertQuestObjectiveListMatchesTestData(QuestRecord quest)
	{
		List<ObjectiveRecord> expected = new ArrayList<>();

		try
		{
			expected = ObjectiveTableDataGatewayMock.getSingleton().getObjectivesForQuest(quest.getQuestID());
		}
		catch (DatabaseException e)
		{
		}

		List<ObjectiveRecord> actual = quest.getObjectives();
		assertEquals(expected.size(), actual.size());

		for (int i = 0; i < expected.size(); i++)
		{
			assertObjectivesAreEqual(expected.get(i), actual.get(i));
		}
	}

	private void assertObjectivesAreEqual(ObjectiveRecord expected, ObjectiveRecord actual)
	{
		assertEquals(expected.getObjectiveID(), actual.getObjectiveID());
		assertEquals(expected.getObjectiveDescription(), actual.getObjectiveDescription());
		assertEquals(expected.getQuestID(), actual.getQuestID());
		assertEquals(expected.getExperiencePointsGained(), actual.getExperiencePointsGained());
	}

}
