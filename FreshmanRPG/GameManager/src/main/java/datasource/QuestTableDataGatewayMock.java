package datasource;

import java.util.ArrayList;

import model.QuestRecord;
import datatypes.QuestsForTest;

/**
 * Encapsulates access to the mock Quest table.
 */
public class QuestTableDataGatewayMock implements QuestTableDataGateway
{

	private static QuestTableDataGatewayMock instance;
	private ArrayList<QuestRecord> quests;

	/**
	 * create a new object with the data from the appropriate enum
	 */
	private QuestTableDataGatewayMock()
	{
		resetData();
	}

	/**
	 * @return The gateway instance.
	 */
	public static QuestTableDataGatewayMock getInstance()
	{
		if (instance == null)
		{
			instance = new QuestTableDataGatewayMock();
		}

		return instance;
	}

	/**
	 * @see QuestTableDataGateway#resetData()
	 */
	@Override
	public void resetData()
	{
		quests = new ArrayList<>();
		for (QuestsForTest testQuest : QuestsForTest.values())
		{
			try
			{
				quests.add(new QuestRecord(testQuest.getQuestID(),
						testQuest.getQuestTitle(),
						testQuest.getQuestDescription(),
						testQuest.getMapName(),
						testQuest.getPosition(),
						AdventureTableDataGatewayMock.getSingleton().getAdventuresForQuest(testQuest.getQuestID()),
						testQuest.getExperienceGained(),
						testQuest.getAdventuresForFulfillment(),
						testQuest.getCompletionActionType(),
						testQuest.getCompletionActionParameter(),
						testQuest.getStartDate(),
						testQuest.getEndDate()));
			}
			catch (DatabaseException e)
			{
				/* Should never throw a DatabaseException, but
				 * AdventureTableDataGatewayMock#getAdventuresForQuest needs to conform
				 * to the AdventureTableDataGateway interface!
				 */
			}
		}
	}

	private void refreshAdventures() throws DatabaseException
	{
		for (QuestRecord q : quests)
		{
			q.setAdventures(AdventureTableDataGatewayMock.getSingleton().getAdventuresForQuest(q.getQuestID()));
		}
	}

	/**
	 * @see datasource.QuestTableDataGateway#getAllQuests()
	 */
	@Override
	public ArrayList<QuestRecord> getAllQuests() throws DatabaseException
	{
		refreshAdventures();
		return quests;
	}

}
