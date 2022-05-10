package DatabaseBuilders;

import java.sql.SQLException;

import datasource.ObjectiveStateTableDataGatewayRDS;
import datasource.ObjectiveTableDataGatewayRDS;
import datasource.DatabaseException;
import datasource.QuestRowDataGatewayRDS;
import datasource.QuestStateTableDataGatewayRDS;
import model.OptionsManager;
import datatypes.ObjectiveStatesForTest;
import datatypes.ObjectivesForTest;
import datatypes.QuestStatesForTest;
import datatypes.QuestsForTest;

/**
 * Builds the Quests and Objectives portion of the database
 *
 * @author Merlin
 *
 */
public class BuildQuestsAndObjectives
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
		System.out.println("creating objective table");
		createObjectiveTable();
		System.out.println("creating quest table");
		createQuestTable();
		System.out.println("creating quest state table");
		createQuestStateTable();
		System.out.println("creating objective state table");
		createObjectiveStateTable();
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

	private static void createObjectiveStateTable() throws DatabaseException
	{
		ObjectiveStateTableDataGatewayRDS.getSingleton().createTable();
		for (ObjectiveStatesForTest objective : ObjectiveStatesForTest.values())
		{
			ObjectiveStateTableDataGatewayRDS.getSingleton().createRow(objective.getPlayerID(), objective.getQuestID(),
					objective.getObjectiveID(), objective.getState(), objective.isNeedingNotification());
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
					quest.getObjectiveForFulfillment(), quest.getCompletionActionType(),
					quest.getCompletionActionParameter(), quest.getStartDate(), quest.getEndDate());
			;
		}
		System.out.println();
	}

	/**
	 * Create a table of objectives
	 *
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createObjectiveTable() throws SQLException, DatabaseException
	{
		ObjectiveTableDataGatewayRDS.createTable();
		for (ObjectivesForTest objective : ObjectivesForTest.values())
		{
			ObjectiveTableDataGatewayRDS.createRow(objective.getObjectiveID(), objective.getObjectiveDescription(),
					objective.getQuestID(), objective.getExperiencePointsGained(), objective.getCompletionType(),
					objective.getCompletionCriteria());
		}
	}
}
