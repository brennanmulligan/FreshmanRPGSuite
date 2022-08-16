
import communication.packers.*;
import dataDTO.TestMowreyInfoDTO;
import datasource.*;
import model.*;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.handlers.ObjectiveNotificationCompleteMessageHandlerTest;
import communication.handlers.ChatMessageToServerHandlerTest;
import communication.handlers.ConnectMessageHandlerTest;
import communication.handlers.HighScoreRequestMessageHandlerTest;
import communication.handlers.KeyInputMessageHandlerTest;
import communication.handlers.MovementMessageHandlerTest;
import communication.handlers.SendTerminalTextMessageHandlerTest;
import communication.handlers.TeleportationInitiationHandlerTest;
import dataDTO.TestPlayerDTO;
import model.cheatCodeBehaviors.BuffBehaviorTest;
import model.reports.AddExistingPlayerReportTest;
import model.reports.ObjectiveStateChangeReportTest;
import model.reports.ReceiveTerminalTextReportTest;
import model.reports.ExperienceChangedReportTest;
import model.reports.InteractableObjectBuffReportTest;
import model.reports.InteractableObjectTextReportTest;
import model.reports.KeyInputRecievedReportTest;
import model.reports.DoubloonsChangeReportTest;
import model.reports.ObjectInRangeReportTest;
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
				ObjectiveNotificationCompleteMessageHandlerTest.class,
				ChatMessageToServerHandlerTest.class,
				ConnectMessageHandlerTest.class,
				HighScoreRequestMessageHandlerTest.class,
				KeyInputMessageHandlerTest.class,
				MovementMessageHandlerTest.class,
				SendTerminalTextMessageHandlerTest.class,
				TeleportationInitiationHandlerTest.class,

//				// communication.packers
				ObjectiveStateChangeMessagePackerTest.class,
				ReceiveTerminalTextMessagePackerTest.class,
				ChatMessagePackerTest.class,
				ExperienceChangedMessagePackerTest.class,
				InteractionDeniedMessagePackerTest.class,
				InteractableObjectBuffMessagePackerTest.class,
				DoubloonsChangedMessagePackerTest.class,
				MapFileMessagePackerTest.class,
				MovementMessagePackerTest.class,
				PlayerJoinedMessagePackerTest.class,
				QuestStateChangeMessagePackerTest.class,
				TeleportationContinuationPackerTest.class,
				TeleportOnQuestCompletionPackerTest.class,
				UpdatePlayerInformationMessagePackerTest.class,
				TerminalTextExitPackerTest.class,

//				// dataDTO
				TestMowreyInfoDTO.class,
				TestPlayerDTO.class,

				// dataSource
				DefaultItemsTableDataGatewayTest.class,
				DoubloonPrizesTableDataGatewayTest.class,
				FriendTableDataGatewayTest.class,
				InteractableItemRowDataGatewayTest.class,
				LevelTableDataGatewayTest.class,
				NPCQuestionRowDataGatewayTest.class,
				NPCRowDataGatewayTest.class,
				ObjectiveRecordTest.class,
				ObjectiveStateTableDataGatewayTest.class,
				ObjectiveTableDataGatewayTest.class,
				PlayerRowDataGatewayTest.class,
				QuestRowDataGatewayTest.class,
				QuestStateTableDataGatewayTest.class,
				RandomFactsTableDataGatewayTest.class,
				VanityAwardsTableDataGatewayTest.class,
				VanityInventoryTableDataGatewayTest.class,
				VanityItemsTableDataGatewayTest.class,
				VanityShopTableDataGatewayTest.class,
				VisitedMapsGatewayTest.class,
//
//				// model
				ObjectiveStateTest.class,
				ChatManagerTest.class,
				ChatMessageReceivedCommandTest.class,
				CheatCodeManagerTest.class,
				CommandAddPlayerTest.class,
				CommandObjectiveNotificationCompleteTest.class,
				CommandKeyInputMessageReceivedTest.class,
				CommandMovePlayerToAnotherMapAndPersistTest.class,
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
				ObjectiveStateChangeReportTest.class,
				ReceiveTerminalTextReportTest.class,
				ExperienceChangedReportTest.class,
				InteractableObjectTextReportTest.class,
				InteractableObjectBuffReportTest.class,
				KeyInputRecievedReportTest.class,
				DoubloonsChangeReportTest.class,
				ObjectInRangeReportTest.class,
				//PlayerAppearanceChangeReportTest.class,
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
			LoggerManager.createLogger("ServerTests");
		}


		@Override
		protected void after()
		{

		}

	};
}
