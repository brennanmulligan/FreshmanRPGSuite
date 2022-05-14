import communication.handlers.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.packers.ObjectiveNotificationCompletePackerTest;
import communication.packers.AreaCollisionMessagePackerTest;
import communication.packers.ChatMessageToServerPackerTest;
import communication.packers.KeyInputPackerTest;
import communication.packers.LoginMessagePackerTest;
import communication.packers.MovementMessagePackerTest;
import communication.packers.SendTerminalTextPackerTest;
import communication.packers.TeleportationInitiationMessagePackerTest;
import model.ClientChatManagerTest;
import model.ClientPlayerManagerTest;
import model.ClientPlayerTest;
import model.CommandObjectiveNotificationCompleteTest;
import model.CommandObjectiveStateChangeTest;
import model.CommandChatMessageReceivedTest;
import model.CommandChatMessageSentTest;
import model.CommandClearModelStateTest;
import model.CommandClientMovePlayerTest;
import model.CommandDisplayTextTest;
import model.CommandHighScoreResponseTest;
import model.CommandInitializePlayerTest;
import model.CommandKeyInputSentTest;
import model.CommandDoubloonsChangedTest;
import model.CommandLoginFailedTest;
import model.CommandLoginTest;
import model.CommandNewMapTest;
import model.CommandOverwriteExperienceTest;
import model.CommandOverwriteQuestStateTest;
import model.CommandPrintObjectivesTest;
import model.CommandQuestStateChangeTest;
import model.CommandRecieveTerminalResponseTest;
import model.CommandRemovePlayerTest;
import model.CommandSendQuestStateTest;
import model.CommandUpdateFriendsListTest;
import model.MapManagerTest;
import model.ModelFacadeTest;
import model.PDFObjectiveWriterTest;
import model.ThisClientsPlayerTest;
import model.reports.ObjectiveNotificationCompleteReportTest;
import model.reports.ObjectiveStateChangeReportTest;
import model.reports.ObjectiveNeedingNotificationReportTest;
import model.reports.AreaCollisionReportTest;
import model.reports.ChangeMapReportTest;
import model.reports.ChatSentReportTest;
import model.reports.DisplayTextReportTest;
import model.reports.ExperiencePointsChangeReportTest;
import model.reports.HighScoreResponseReportTest;
import model.reports.KeyInputSentReportTest;
import model.reports.LoginFailedReportTest;
import model.reports.LoginInitiatedReportTest;
import model.reports.NewMapReportTest;
import model.reports.SendTerminalTextReportTest;
import model.reports.TerminalResponseReportTest;
import model.reports.PlayerMovedReportTest;
import model.reports.QuestStateChangeReportTest;
import model.reports.QuestStateReportTest;
import model.reports.TimeToLevelUpDeadlineTest;
import view.player.DirectionTest;
import view.player.PlayerSpriteTest;
import view.screen.ScreenListenerTest;
import view.screen.map.ScreenMapInputTest;
import view.screen.popup.ObjectiveCompleteBehaviorTest;

/**
 * All of the tests for the client code. Notice that the packages, and classes
 * within them, are in order by the way they show in the package explorer. This
 * is to make it easy to see if a class is missing from this list. Helper
 * classes that do not contain tests are included in the list, but commented out
 *
 * @author Merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
		// communication.handlers
		BuffMessageHandlerTest.class,
		ClientChatMessageHandlerTest.class,
		DisplayTextMessageHandlerTest.class,
		DoubloonsChangedMessageHandlerTest.class,
		ExperienceChangedMessageHandlerTest.class,
		HighScoreResponseHandlerTest.class,
		InitializeThisClientsPlayerMessageHandlerTest.class,
		LoginFailedMessageHandlerTest.class,
		MapFileMessageHandlerTest.class,
		ObjectiveStateChangeMessageHandlerTest.class,
		OtherPlayerMovedMessageHandlerTest.class,
		PlayerAppearanceChangeMessageHandlerTest.class,
		PlayerJoinedMessageHandlerTest.class,
		PlayerLeaveMessageHandlerTest.class,
		QuestStateChangeMessageHandlerTest.class,
		ReceiveTerminalTextMessageHandlerTest.class,
		TerminalTextExitHandlerTest.class,
		TimeToLevelUpDeadlineHandlerTest.class,
		
		// communication.packers
		AreaCollisionMessagePackerTest.class,
		ChatMessageToServerPackerTest.class,
		KeyInputPackerTest.class,
		LoginMessagePackerTest.class,
		MovementMessagePackerTest.class,
		ObjectiveNotificationCompletePackerTest.class,
		SendTerminalTextPackerTest.class,
		TeleportationInitiationMessagePackerTest.class,

		// model
		ClientChatManagerTest.class,
		ClientPlayerManagerTest.class,
		ClientPlayerTest.class,
		CommandChatMessageReceivedTest.class,
		CommandChatMessageSentTest.class,
		CommandClearModelStateTest.class,
		CommandClientMovePlayerTest.class,
		CommandDisplayTextTest.class,
		CommandDoubloonsChangedTest.class,
		CommandHighScoreResponseTest.class,
		CommandInitializePlayerTest.class,
		CommandKeyInputSentTest.class,
		CommandLoginFailedTest.class,
		CommandLoginTest.class,
		CommandNewMapTest.class,
		CommandObjectiveNotificationCompleteTest.class,
		CommandObjectiveStateChangeTest.class,
		CommandOverwriteExperienceTest.class,
		CommandOverwriteQuestStateTest.class,
		CommandPrintObjectivesTest.class,
		CommandQuestStateChangeTest.class,
		CommandRecieveTerminalResponseTest.class,
		CommandRemovePlayerTest.class,
		CommandSendQuestStateTest.class,
		CommandUpdateFriendsListTest.class,
		MapManagerTest.class,
		ModelFacadeTest.class,
		PDFObjectiveWriterTest.class,
		ThisClientsPlayerTest.class,

		// model.reports
		AreaCollisionReportTest.class,
		ChangeMapReportTest.class,
		ChatSentReportTest.class,
		DisplayTextReportTest.class,
		ExperiencePointsChangeReportTest.class,
		HighScoreResponseReportTest.class,
		KeyInputSentReportTest.class,
		LoginFailedReportTest.class,
		LoginInitiatedReportTest.class,
		NewMapReportTest.class,
		ObjectiveNeedingNotificationReportTest.class,
		ObjectiveNotificationCompleteReportTest.class,
		ObjectiveStateChangeReportTest.class,
		PlayerMovedReportTest.class,
		QuestStateChangeReportTest.class,
		QuestStateReportTest.class,
		SendTerminalTextReportTest.class,
		TerminalResponseReportTest.class,
		TimeToLevelUpDeadlineTest.class,

		// view.player
		DirectionTest.class,
		PlayerSpriteTest.class,

		// view.screen
		ScreenListenerTest.class,

		//view.screen.map
		ScreenMapInputTest.class,

		//view.screen.popup
		ObjectiveCompleteBehaviorTest.class,

})
/**
 * Runs all client tests
 * @author Merlin
 *
 */
public class AllClientTests
{
	// empty block
}
