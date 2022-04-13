import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import datasource.AdventureRowDataGatewayMockTest;
import datasource.AdventureRowDataGatewayRDSTest;
import datasource.CSVPlayerGatewayTest;
import datasource.NPCQuestionTableDataGatewayMockTest;
import datasource.NPCQuestionTableDataGatewayRDSTest;
import datasource.QuestTableDataGatewayMockTest;
import datasource.QuestTableDataGatewayRDSTest;
import model.CommandAddAdventureTest;
import model.CommandAddPlayerTest;
import model.CommandAddQuestTest;
import model.CommandAddQuestionTest;
import model.CommandAdventureCompletedTest;
import model.CommandDeleteAdventureTest;
import model.CommandDeleteInteractableObjectTest;
import model.CommandDeletePlayerTest;
import model.CommandDeleteQuestTest;
import model.CommandDeleteQuestionTest;
import model.CommandEditAdventureTest;
import model.CommandEditInteractableObjectTest;
import model.CommandEditPlayerTest;
import model.CommandEditQuestTest;
import model.CommandGetAllInteractableObjectsTest;
import model.CommandGetAllPlayersTest;
import model.CommandGetAllQuestionsTest;
import model.CommandGetAllQuestsAndAdventuresTest;
import model.CommandImportPlayersTest;
import model.CommandImportQuestTest;
import model.CommandImportQuestionTest;
import model.CommandUpdateQuestionTest;
import model.GameManagerInteractableObjectManagerTest;
import model.GameManagerPlayerManagerTest;
import model.GameManagerQuestManagerTest;
import model.PlayerManagerTest;
import model.QuestionManagerTest;
import model.reports.AllPlayersReportTest;
import model.reports.AllQuestsAndAdventuresReportTest;
import model.reports.InvalidInputReportTest;
import model.reports.PlayerUncompletedAdventuresReportTest;
import model.reports.QuestionInfoTest;
import model.reports.QuestionListReportTest;

/**
 * All of the tests for the game managers code. Notice that the packages, and
 * classes within them, are in order by the way they show in the package
 * explorer. This is to make it easy to see if a class is missing from this
 * list. Helper classes that do not contain tests are included in the list, but
 * commented out
 *
 * @author Merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
		{
				//data source
				AdventureRowDataGatewayMockTest.class,
				AdventureRowDataGatewayRDSTest.class,
				// AdventureRowDataGatewayTest.class
				CSVPlayerGatewayTest.class,
				NPCQuestionTableDataGatewayMockTest.class,
				NPCQuestionTableDataGatewayRDSTest.class,
				// NPCQuestionTableDataGatewayTest.class,
				QuestTableDataGatewayMockTest.class,
				QuestTableDataGatewayRDSTest.class,
				// QuestTableDataGatewayTest.class,

				// model
				CommandAddAdventureTest.class,
				CommandAddPlayerTest.class,
				CommandAddQuestionTest.class,
				CommandAddQuestTest.class,
				CommandDeleteAdventureTest.class,
				CommandDeletePlayerTest.class,
				CommandDeleteQuestionTest.class,
				CommandDeleteQuestTest.class,
				CommandDeleteInteractableObjectTest.class,
				CommandEditAdventureTest.class,
				CommandEditPlayerTest.class,
				CommandEditQuestTest.class,
				CommandEditInteractableObjectTest.class,
				CommandGetAllInteractableObjectsTest.class,
				CommandGetAllPlayersTest.class,
				CommandGetAllQuestionsTest.class,
				CommandGetAllQuestsAndAdventuresTest.class,
				CommandImportPlayersTest.class,
				CommandImportQuestionTest.class,
				CommandImportQuestTest.class,
				CommandUpdateQuestionTest.class,
				CommandAdventureCompletedTest.class,
				GameManagerInteractableObjectManagerTest.class,
				GameManagerPlayerManagerTest.class,
				GameManagerQuestManagerTest.class,
				PlayerManagerTest.class,
				QuestionManagerTest.class,


				//model.reports
				AllPlayersReportTest.class,
				AllQuestsAndAdventuresReportTest.class,
				PlayerUncompletedAdventuresReportTest.class,
				QuestionInfoTest.class,
				QuestionListReportTest.class,
				InvalidInputReportTest.class,

				//ui
		})

public class AllManagerTests
{

}
