import communication.handlers.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.packers.AdventureNotificationCompletePackerTest;
import communication.packers.AreaCollisionMessagePackerTest;
import communication.packers.ChatMessagePackerTest;
import communication.packers.KeyInputPackerTest;
import communication.packers.LoginMessagePackerTest;
import communication.packers.MovementMessagePackerTest;
import communication.packers.SendTerminalTextPackerTest;
import communication.packers.TeleportationInitiationMessagePackerTest;
import model.ChatManagerTest;
import model.ClientPlayerManagerTest;
import model.ClientPlayerTest;
import model.CommandAdventureNotificationCompleteTest;
import model.CommandAdventureStateChangeTest;
import model.CommandChatMessageReceivedTest;
import model.CommandChatMessageSentTest;
import model.CommandClearModelStateTest;
import model.CommandClientMovePlayerTest;
import model.CommandDisplayTextTest;
import model.CommandHighScoreResponseTest;
import model.CommandInitializePlayerTest;
import model.CommandKeyInputSentTest;
import model.CommandKnowledgePointsChangedTest;
import model.CommandLoginFailedTest;
import model.CommandLoginTest;
import model.CommandNewMapTest;
import model.CommandOverwriteExperienceTest;
import model.CommandOverwriteQuestStateTest;
import model.CommandPrintAdventuresTest;
import model.CommandQuestStateChangeTest;
import model.CommandRecieveTerminalResponseTest;
import model.CommandRemovePlayerTest;
import model.CommandSendQuestStateTest;
import model.CommandUpdateFriendsListTest;
import model.MapManagerTest;
import model.ModelFacadeTest;
import model.PDFAdventureWriterTest;
import model.ThisClientsPlayerTest;
import model.reports.AdventureNotificationCompleteReportTest;
import model.reports.AdventureStateChangeReportTest;
import model.reports.AdventuresNeedingNotificationReportTest;
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
import view.screen.popup.AdventureCompleteBehaviorTest;

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
		AdventureStateChangeMessageHandlerTest.class,
		ReceiveTerminalTextMessageHandlerTest.class,
		BuffMessageHandlerTest.class,
		ChatMessageHandlerTest.class,
		DisplayTextMessageHandlerTest.class,
		ExperienceChangedMessageHandlerTest.class,
		HighScoreResponseHandlerTest.class,
		InitializeThisClientsPlayerMessageHandlerTest.class,
		KnowledgeChangedMessageHandlerTest.class,
		LoginFailedMessageHandlerTest.class,
		MapFileMessageHandlerTest.class,
		OtherPlayerMovedMessageHandlerTest.class,
		PlayerAppearanceChangeMessageHandlerTest.class,
		PlayerLeaveMessageHandlerTest.class,
		PlayerJoinedMessageHandlerTest.class,
		TimeToLevelUpDeadlineHandlerTest.class,
		QuestStateChangeMessageHandlerTest.class,
		TerminalTextExitHandlerTest.class,
		
		// communication.packers
		AdventureNotificationCompletePackerTest.class,
		AreaCollisionMessagePackerTest.class,
		ChatMessagePackerTest.class,
		KeyInputPackerTest.class,
		LoginMessagePackerTest.class,
		MovementMessagePackerTest.class,
		SendTerminalTextPackerTest.class,
		TeleportationInitiationMessagePackerTest.class,

		// model
		ChatManagerTest.class,
		ClientPlayerManagerTest.class,
		ClientPlayerTest.class,
		CommandAdventureNotificationCompleteTest.class,
		CommandAdventureStateChangeTest.class,
		CommandChatMessageReceivedTest.class,
		CommandChatMessageSentTest.class,
		CommandClearModelStateTest.class,
		CommandClientMovePlayerTest.class,
		CommandHighScoreResponseTest.class,
		CommandInitializePlayerTest.class,
		CommandKeyInputSentTest.class,
		CommandKnowledgePointsChangedTest.class,
		CommandLoginFailedTest.class,
		CommandLoginTest.class,
		CommandNewMapTest.class,
		CommandOverwriteExperienceTest.class,
		CommandOverwriteQuestStateTest.class,
		CommandPrintAdventuresTest.class,
		CommandRecieveTerminalResponseTest.class,
		CommandQuestStateChangeTest.class,
		CommandRemovePlayerTest.class,
		CommandSendQuestStateTest.class,
		CommandUpdateFriendsListTest.class,
		CommandDisplayTextTest.class,
		MapManagerTest.class,
		ModelFacadeTest.class,
		PDFAdventureWriterTest.class,
		ThisClientsPlayerTest.class,

		// model.reports
		AdventureNotificationCompleteReportTest.class,
		AdventuresNeedingNotificationReportTest.class,
		AdventureStateChangeReportTest.class,
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
		SendTerminalTextReportTest.class,
		PlayerMovedReportTest.class,
		QuestStateChangeReportTest.class,
		QuestStateReportTest.class,
		TimeToLevelUpDeadlineTest.class,
		TerminalResponseReportTest.class,

		// view.player
		DirectionTest.class,
		PlayerSpriteTest.class,

		// view.screen
		ScreenListenerTest.class,

		//view.screen.map
		ScreenMapInputTest.class,

		//view.screen.popup
		AdventureCompleteBehaviorTest.class,

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
