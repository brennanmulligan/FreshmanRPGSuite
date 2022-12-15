package edu.ship.engr.shipsim;

import edu.ship.engr.shipsim.communication.handlers.*;
import edu.ship.engr.shipsim.communication.packers.*;
import edu.ship.engr.shipsim.dataDTO.TestMowreyInfoDTO;
import edu.ship.engr.shipsim.dataDTO.TestPlayerDTO;
import edu.ship.engr.shipsim.datasource.*;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.model.cheatCodeBehaviors.BuffBehaviorTest;
import edu.ship.engr.shipsim.model.reports.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * All of the tests for the area servers code. Notice that the packages, and
 * classes within them, are in order by the way they show in the package
 * explorer. This is to make it easy to see if a class is missing from this
 * list. Helper classes that do not contain tests are included in the list, but
 * commented out
 *
 * @author Merlin
 */
@Suite
@SelectClasses(
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
//				// edu.ship.engr.shipsim.model
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

                // edu.ship.engr.shipsim.model.cheatCodeBehaviors
                BuffBehaviorTest.class,
                // MockCheatCodeBehavior.class, is used by tests, but isn't a test itself

                // edu.ship.engr.shipsim.model.reports
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
}
