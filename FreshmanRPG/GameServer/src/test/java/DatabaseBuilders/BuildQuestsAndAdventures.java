package DatabaseBuilders;

import java.sql.SQLException;

import datasource.AdventureStateTableDataGatewayRDS;
import datasource.AdventureTableDataGatewayRDS;
import datasource.DatabaseException;
import datasource.QuestRowDataGatewayRDS;
import datasource.QuestStateTableDataGatewayRDS;
import model.OptionsManager;
import datatypes.AdventureStatesForTest;
import datatypes.AdventuresForTest;
import datatypes.QuestStatesForTest;
import datatypes.QuestsForTest;

/**
 * Builds the Quests and Adventures portion of the database
 *
 * @author Merlin
 *
 */
public class BuildQuestsAndAdventures
{

	/**
	 *
	 * @param args unused
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		OptionsManager.getSingleton().setUsingTestDB(true);
		System.out.println("creating adventure table");
		createAdventureTable();
		System.out.println("creating quest table");
		createQuestTable();
		System.out.println("creating quest state table");
		createQuestStateTable();
		System.out.println("creating adventure state table");
		createAdventureStateTable();
	}

	private static void createQuestStateTable() throws DatabaseException
	{
		QuestStateTableDataGatewayRDS.getSingleton().createTable();
		for (QuestStatesForTest quest : QuestStatesForTest.values())
		{
			QuestStateTableDataGatewayRDS.getSingleton().createRow(quest.getPlayerID(), quest.getQuestID(),
					quest.getState(), quest.isNeedingNotification());
		}
	}

	private static void createAdventureStateTable() throws DatabaseException
	{
		AdventureStateTableDataGatewayRDS.getSingleton().createTable();
		for (AdventureStatesForTest adventure : AdventureStatesForTest.values())
		{
			AdventureStateTableDataGatewayRDS.getSingleton().createRow(adventure.getPlayerID(), adventure.getQuestID(),
					adventure.getAdventureID(), adventure.getState(), adventure.isNeedingNotification());
		}
	}

	/**
	 * Create a table of quests
	 *
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createQuestTable() throws SQLException, DatabaseException
	{
		QuestRowDataGatewayRDS.createTable();
		for (QuestsForTest quest : QuestsForTest.values())
		{
			System.out.print(quest.getQuestID() + " ");
			new QuestRowDataGatewayRDS(quest.getQuestID(), quest.getQuestTitle(), quest.getQuestDescription(),
					quest.getMapName(), quest.getPosition(), quest.getExperienceGained(),
					quest.getAdventuresForFulfillment(), quest.getCompletionActionType(),
					quest.getCompletionActionParameter(), quest.getStartDate(), quest.getEndDate());
			;
		}
	}

	/**
	 * Create a table of adventures
	 *
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createAdventureTable() throws SQLException, DatabaseException
	{
		AdventureTableDataGatewayRDS.createTable();
		for (AdventuresForTest adventure : AdventuresForTest.values())
		{
			AdventureTableDataGatewayRDS.createRow(adventure.getAdventureID(), adventure.getAdventureDescription(),
					adventure.getQuestID(), adventure.getExperiencePointsGained(), adventure.getCompletionType(),
					adventure.getCompletionCriteria());
		}
	}
}
