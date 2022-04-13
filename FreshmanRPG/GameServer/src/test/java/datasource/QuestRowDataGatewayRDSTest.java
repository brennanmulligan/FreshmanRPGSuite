package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import criteria.QuestCompletionActionParameter;
import dataENUM.QuestCompletionActionType;
import datatypes.Position;
import datatypes.QuestsForTest;

/**
 * Tests for the RDS version of the gateway
 *
 * @author Merlin
 *
 */
public class QuestRowDataGatewayRDSTest extends QuestRowDataGatewayTest
{

	/**
	 *
	 * @see datasource.QuestRowDataGatewayTest#findGateway(int)
	 */
	@Override
	QuestRowDataGateway findGateway(int questID) throws DatabaseException
	{
		return new QuestRowDataGatewayRDS(questID);
	}

	/**
	 * @see datasource.QuestRowDataGatewayTest#findQuestsForMapLocation(java.lang.String,
	 *      datatypes.Position)
	 */
	@Override
	ArrayList<Integer> findQuestsForMapLocation(String mapName, Position position) throws DatabaseException
	{
		return QuestRowDataGatewayRDS.findQuestsForMapLocation(mapName, position);
	}

	@Override
	int createGateway(String title, String description, String mapName, Position position, int experiencedGained,
					  int adventuresForFullfillment, QuestCompletionActionType completionActionType,
					  QuestCompletionActionParameter completionActionParameter, Date startDate, Date endDate) throws DatabaseException
	{
		QuestRowDataGatewayRDS gateway = new QuestRowDataGatewayRDS(
				title,
				description,
				mapName,
				position,
				experiencedGained,
				adventuresForFullfillment,
				completionActionType,
				completionActionParameter,
				startDate,
				endDate
		);

		return gateway.getQuestID();
	}

	/**
	 * The RowDataGateway has a creation constructor that takes the quest ID
	 *   in addition to everything else. This is used to set up the tables for tests.
	 *   This test is specifically for that constructor.
	 * @throws DatabaseException - if it fails
	 */
	@Test
	public void testCreateConstructorWithId() throws DatabaseException
	{
		QuestsForTest quest = QuestsForTest.ONE_BIG_QUEST;

		new QuestRowDataGatewayRDS(
				543,
				"Test Title",
				quest.getQuestDescription(),
				quest.getMapName(),
				quest.getPosition(),
				quest.getExperienceGained(),
				quest.getAdventuresForFulfillment(),
				quest.getCompletionActionType(),
				quest.getCompletionActionParameter(),
				quest.getStartDate(),
				quest.getEndDate()
		);

		QuestRowDataGateway gateway = findGateway(543);
		assertNotNull(gateway);
		assertEquals(543, gateway.getQuestID());
		assertEquals("Test Title", gateway.getQuestTitle());
		assertEquals(quest.getQuestDescription(), gateway.getQuestDescription());
		assertEquals(quest.getMapName(), gateway.getTriggerMapName());
		assertEquals(quest.getPosition(), gateway.getTriggerPosition());
		assertEquals(quest.getExperienceGained(), gateway.getExperiencePointsGained());
		assertEquals(quest.getAdventuresForFulfillment(), gateway.getAdventuresForFulfillment());
		assertEquals(quest.getCompletionActionType(), gateway.getCompletionActionType());
		assertEquals(quest.getCompletionActionParameter(), gateway.getCompletionActionParameter());
		assertEquals(quest.getStartDate(), gateway.getStartDate());
		assertEquals(quest.getEndDate(), gateway.getEndDate());
	}
}