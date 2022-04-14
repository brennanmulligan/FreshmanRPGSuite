import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import datasource.ObjectiveRowDataGatewayMockTest;
import datasource.ObjectiveRowDataGatewayRDSTest;
import datasource.CSVPlayerGatewayTest;
import datasource.NPCQuestionTableDataGatewayMockTest;
import datasource.NPCQuestionTableDataGatewayRDSTest;
import datasource.QuestTableDataGatewayMockTest;
import datasource.QuestTableDataGatewayRDSTest;
import model.CommandAddObjectiveTest;
import model.CommandAddPlayerTest;
import model.CommandAddQuestTest;
import model.CommandAddQuestionTest;
import model.CommandObjectiveCompletedTest;
import model.CommandDeleteObjectiveTest;
import model.CommandDeleteInteractableObjectTest;
import model.CommandDeletePlayerTest;
import model.CommandDeleteQuestTest;
import model.CommandDeleteQuestionTest;
import model.CommandEditObjectiveTest;
import model.CommandEditInteractableObjectTest;
import model.CommandEditPlayerTest;
import model.CommandEditQuestTest;
import model.CommandGetAllInteractableObjectsTest;
import model.CommandGetAllPlayersTest;
import model.CommandGetAllQuestionsTest;
import model.CommandGetAllQuestsAndObjectivesTest;
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
import model.reports.AllQuestsAndObjectivesReportTest;
import model.reports.InvalidInputReportTest;
import model.reports.PlayerUncompletedObjectivesReportTest;
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
				// ObjectiveRowDataGatewayTest.class
				// NPCQuestionTableDataGatewayTest.class,
				// QuestTableDataGatewayTest.class,
				CSVPlayerGatewayTest.class,
				NPCQuestionTableDataGatewayMockTest.class,
				NPCQuestionTableDataGatewayRDSTest.class,
				ObjectiveRowDataGatewayMockTest.class,
				ObjectiveRowDataGatewayRDSTest.class,
				QuestTableDataGatewayMockTest.class,
				QuestTableDataGatewayRDSTest.class,

				// model
				CommandAddObjectiveTest.class,
				CommandAddPlayerTest.class,
				CommandAddQuestTest.class,
				CommandAddQuestionTest.class,
				CommandDeleteInteractableObjectTest.class,
				CommandDeleteObjectiveTest.class,
				CommandDeletePlayerTest.class,
				CommandDeleteQuestTest.class,
				CommandDeleteQuestionTest.class,
				CommandEditInteractableObjectTest.class,
				CommandEditObjectiveTest.class,
				CommandEditPlayerTest.class,
				CommandEditQuestTest.class,
				CommandGetAllInteractableObjectsTest.class,
				CommandGetAllPlayersTest.class,
				CommandGetAllQuestionsTest.class,
				CommandGetAllQuestsAndObjectivesTest.class,
				CommandImportPlayersTest.class,
				CommandImportQuestTest.class,
				CommandImportQuestionTest.class,
				CommandObjectiveCompletedTest.class,
				CommandUpdateQuestionTest.class,
				GameManagerInteractableObjectManagerTest.class,
				GameManagerPlayerManagerTest.class,
				GameManagerQuestManagerTest.class,
				PlayerManagerTest.class,
				QuestionManagerTest.class,


				//model.reports
				AllPlayersReportTest.class,
				AllQuestsAndObjectivesReportTest.class,
				InvalidInputReportTest.class,
				PlayerUncompletedObjectivesReportTest.class,
				QuestionInfoTest.class,
				QuestionListReportTest.class,

				//ui
		})

public class AllManagerTests
{

}
