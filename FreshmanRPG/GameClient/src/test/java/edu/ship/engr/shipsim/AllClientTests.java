package edu.ship.engr.shipsim;

import edu.ship.engr.shipsim.communication.handlers.*;
import edu.ship.engr.shipsim.communication.packers.*;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.model.reports.*;
import edu.ship.engr.shipsim.view.player.DirectionTest;
import edu.ship.engr.shipsim.view.player.PlayerSpriteTest;
import edu.ship.engr.shipsim.view.screen.ScreenListenerTest;
import edu.ship.engr.shipsim.view.screen.map.ScreenMapInputTest;
import edu.ship.engr.shipsim.view.screen.popup.ObjectiveCompleteBehaviorTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * All of the tests for the client code. Notice that the packages, and classes
 * within them, are in order by the way they show in the package explorer. This
 * is to make it easy to see if a class is missing from this list. Helper
 * classes that do not contain tests are included in the list, but commented out
 *
 * @author Merlin
 */
@Suite
@SelectClasses(
        {
                // edu.ship.engr.shipsim.communication.handlers
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

                // edu.ship.engr.shipsim.communication.packers
                AreaCollisionMessagePackerTest.class,
                ChatMessageToServerPackerTest.class,
                KeyInputPackerTest.class,
                LoginMessagePackerTest.class,
                MovementMessagePackerTest.class,
                ObjectiveNotificationCompletePackerTest.class,
                SendTerminalTextPackerTest.class,
                TeleportationInitiationMessagePackerTest.class,

                // edu.ship.engr.shipsim.model
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

                // edu.ship.engr.shipsim.model.reports
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

                // edu.ship.engr.shipsim.view.player
                DirectionTest.class,
                PlayerSpriteTest.class,

                // edu.ship.engr.shipsim.view.screen
                ScreenListenerTest.class,

                // edu.ship.engr.shipsim.view.screen.map
                ScreenMapInputTest.class,

                // edu.ship.engr.shipsim.view.screen.popup
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
