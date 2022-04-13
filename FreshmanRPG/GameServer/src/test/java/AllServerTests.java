
import communication.packers.*;
import model.*;
import model.terminal.CommandTerminalTextExit;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.handlers.AdventureNotificationCompleteMessageHandlerTest;
import communication.handlers.ChatMessageHandlerTest;
import communication.handlers.ConnectMessageHandlerTest;
import communication.handlers.DisconnectMessageHandlerTest;
import communication.handlers.HighScoreRequestMessageHandlerTest;
import communication.handlers.KeyInputMessageHandlerTest;
import communication.handlers.MovementMessageHandlerTest;
import communication.handlers.SendTerminalTextMessageHandlerTest;
import communication.handlers.TeleportationInitiationHandlerTest;
import dataDTO.TestPlayerDTO;
import datasource.AdventureRecordTest;
import datasource.AdventureStateTableDataGatewayMockTest;
import datasource.AdventureStateTableDataGatewayRDSTest;
import datasource.AdventureTableDataGatewayMockTest;
import datasource.AdventureTableDataGatewayRDSTest;
import datasource.LevelTableDataGatewayMockTest;
import datasource.LevelTableDataGatewayRDSTest;
import datasource.NPCQuestionRowDataGatewayMockTest;
import datasource.NPCQuestionRowDataGatewayRDSTest;
import datasource.NPCRowDataGatewayMockTest;
import datasource.NPCRowDataGatewayRDSTest;
import datasource.PlayerRowDataGatewayMockTest;
import datasource.PlayerRowDataGatewayRDSTest;
import datasource.PlayerTableDataGatewayMockTest;
import datasource.PlayerTableDataGatewayRDSTest;
import datasource.QuestRowDataGatewayMockTest;
import datasource.QuestRowDataGatewayRDSTest;
import datasource.QuestStateTableDataGatewayMockTest;
import datasource.QuestStateTableDataGatewayRDSTest;
import datasource.RandomFactsTableDataGatewayMockTest;
import datasource.RandomFactsTableDataGatewayRDSTest;
import datasource.VisitedMapsGatewayMockTest;
import datasource.VisitedMapsGatewayRDSTest;
import model.cheatCodeBehaviors.BuffBehaviorTest;
import model.reports.AddExistingPlayerReportTest;
import model.reports.AdventureStateChangeReportTest;
import model.reports.ReceiveTerminalTextReportTest;
import model.reports.ExperienceChangedReportTest;
import model.reports.InteractableObjectBuffReportTest;
import model.reports.InteractableObjectTextReportTest;
import model.reports.KeyInputRecievedReportTest;
import model.reports.KnowledgeChangeReportTest;
import model.reports.ObjectInRangeReportTest;
import model.reports.PlayerAppearanceChangeReportTest;
import model.reports.PlayerConnectionReportTest;
import model.reports.PlayerFinishedInitializingReportTest;
import model.reports.PlayerMovedReportTest;
import model.reports.QuestStateChangeReportTest;
import model.reports.SendChatMessageReportTest;
import model.reports.TeleportOnQuestCompletionReportTest;
import model.reports.TimeToLevelUpDeadlineTest;
import model.reports.UpdatePlayerInformationReportTest;

/**
 * All of the tests for the area servers code. Notice that the packages, and
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
				// communication.handlers
				AdventureNotificationCompleteMessageHandlerTest.class,
				ChatMessageHandlerTest.class,
				ConnectMessageHandlerTest.class,
				DisconnectMessageHandlerTest.class,
				HighScoreRequestMessageHandlerTest.class,
				KeyInputMessageHandlerTest.class,
				MovementMessageHandlerTest.class,
				SendTerminalTextMessageHandlerTest.class,
				TeleportationInitiationHandlerTest.class,

				// communication.packers
				AdventureStateChangeMessagePackerTest.class,
				ReceiveTerminalTextMessagePackerTest.class,
				ChatMessagePackerTest.class,
				ExperienceChangedMessagePackerTest.class,
				InteractionDeniedMessagePackerTest.class,
				InteractableObjectBuffMessagePackerTest.class,
				KnowledgeChangedMessagePackerTest.class,
				MapFileMessagePackerTest.class,
				MovementMessagePackerTest.class,
				PlayerAppearanceChangePackerTest.class,
				PlayerJoinedMessagePackerTest.class,
				QuestStateChangeMessagePackerTest.class,
				TeleportationContinuationPackerTest.class,
				TeleportOnQuestCompletionPackerTest.class,
				UpdatePlayerInformationMessagePackerTest.class,
				TerminalTextExitPackerTest.class,

				// dataDTO
				TestPlayerDTO.class,

				// dataSource
				AdventureRecordTest.class,
				AdventureStateTableDataGatewayMockTest.class,
				AdventureStateTableDataGatewayRDSTest.class,
				// AdventureStateTableDataGatewayTest.class,
				AdventureTableDataGatewayMockTest.class,
				AdventureTableDataGatewayRDSTest.class,
				// AdventureTableDataGateway.class
				LevelTableDataGatewayMockTest.class,
				LevelTableDataGatewayRDSTest.class,
				// LevelTableDataGatewayTest.class,
				NPCQuestionRowDataGatewayMockTest.class,
				NPCQuestionRowDataGatewayRDSTest.class,
				// NPCQuestionRowDataGatewayTest.class,
				NPCRowDataGatewayMockTest.class,
				NPCRowDataGatewayRDSTest.class,
				// NPCRowDataGatewayTest.class,
				PlayerTableDataGatewayMockTest.class,
				PlayerTableDataGatewayRDSTest.class,
				// PlayerRowDataGatewayTest.class,
				QuestRowDataGatewayMockTest.class,
				QuestRowDataGatewayRDSTest.class,
				// QuestRowDataGatewayTest.class,
				QuestStateTableDataGatewayMockTest.class,
				QuestStateTableDataGatewayRDSTest.class,
				// QuestStateTableDataGatewayTest.class,
				RandomFactsTableDataGatewayMockTest.class,
				RandomFactsTableDataGatewayRDSTest.class,
//		RandomFactsTableDataGatewayTest.class,
				VisitedMapsGatewayMockTest.class,
				VisitedMapsGatewayRDSTest.class,

				// model
				AdventureStateTest.class,
				ChatManagerTest.class,
				ChatMessageReceivedCommandTest.class,
				CheatCodeManagerTest.class,
				CommandAddPlayerTest.class,
				CommandAdventureNotificationCompleteTest.class,
				CommandKeyInputMessageReceivedTest.class,
				CommandMovePlayerSilentlyAndPersistTest.class,
				CommandMovePlayerSilentlyTest.class,
				CommandMovePlayerTest.class,
				CommandPersistPlayerTest.class,
				CommandRemovePlayerTest.class,
				CommandTerminalTextExitTest.class,
				InteractObjectManagerTest.class,
				LevelManagerTest.class,
				NPCMapperTest.class,
				NPCQuestionTest.class,
				NPCTest.class,
				OptionsManagerTest.class,
				PlayerManagerTest.class,
				PlayerMapperTest.class,
				PlayerTest.class,
				QuestManagerTest.class,
				QuestStateTest.class,
				QuestTest.class,
				QuizBotBehaviorTest.class,
				RedHatBehaviorTest.class,
				InteractObjectManagerTest.class,
				TutorBehaviorTest.class,

				// model.cheatCodeBehaviors
				BuffBehaviorTest.class,
				// MockCheatCodeBehavior.class, is used by tests, but isn't a test itself

				// model.reports
				AddExistingPlayerReportTest.class,
				AdventureStateChangeReportTest.class,
				ReceiveTerminalTextReportTest.class,
				ExperienceChangedReportTest.class,
				InteractableObjectTextReportTest.class,
				InteractableObjectBuffReportTest.class,
				KeyInputRecievedReportTest.class,
				KnowledgeChangeReportTest.class,
				ObjectInRangeReportTest.class,
				PlayerAppearanceChangeReportTest.class,
				PlayerConnectionReportTest.class,
				PlayerFinishedInitializingReportTest.class,
				PlayerMovedReportTest.class,
				QuestStateChangeReportTest.class,
				SendChatMessageReportTest.class,
				TeleportOnQuestCompletionReportTest.class,
				TimeToLevelUpDeadlineTest.class,
				UpdatePlayerInformationReportTest.class,
		})

public class AllServerTests
{
	/**
	 * Make sure we default all of the PINs at the beginning of running the
	 * tests so that none will be expired
	 */
	@ClassRule
	public static ExternalResource testRule = new ExternalResource()
	{
		@Override
		protected void before() throws Throwable
		{
			PlayerConnectionTest.defaultAllPins();
		}

		;

		@Override
		protected void after()
		{

		}

		;
	};
}
