package edu.ship.engr.shipsim;

import edu.ship.engr.shipsim.communication.handlers.*;
import edu.ship.engr.shipsim.communication.packers.*;
import edu.ship.engr.shipsim.dataDTO.TestMowreyInfoDTO;
import edu.ship.engr.shipsim.dataDTO.TestPlayerDTO;
import edu.ship.engr.shipsim.datasource.*;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.model.cheatCodeBehaviors.BuffBehaviorTest;
import edu.ship.engr.shipsim.model.reports.*;
import edu.ship.engr.shipsim.restfulcommunication.controllers.TestObjectiveController;
import edu.ship.engr.shipsim.restfulcommunication.controllers.TestPlayerController;
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
                ChatMessageToServerHandlerTest.class,
                ConnectMessageHandlerTest.class,
                HighScoreRequestMessageHandlerTest.class,
                KeyInputMessageHandlerTest.class,
                MovementMessageHandlerTest.class,
                ObjectiveNotificationCompleteMessageHandlerTest.class,
                SendTerminalTextMessageHandlerTest.class,
                TeleportationInitiationHandlerTest.class,

//				// communication.packers
                ChatMessagePackerTest.class,
                DoubloonsChangedMessagePackerTest.class,
                ExperienceChangedMessagePackerTest.class,
                InteractableObjectBuffMessagePackerTest.class,
                InteractionDeniedMessagePackerTest.class,
                MapFileMessagePackerTest.class,
                MovementMessagePackerTest.class,
                ObjectiveStateChangeMessagePackerTest.class,
                PlayerJoinedMessagePackerTest.class,
                QuestStateChangeMessagePackerTest.class,
                ReceiveTerminalTextMessagePackerTest.class,
                TeleportOnQuestCompletionPackerTest.class,
                TeleportationContinuationPackerTest.class,
                TerminalTextExitPackerTest.class,
                UpdatePlayerInformationMessagePackerTest.class,

//				// dataDTO
                TestMowreyInfoDTO.class,
                TestPlayerDTO.class,

                // dataSource
                CrewRowDataGatewayTest.class,
                CrewTableDataGatewayTest.class,
                DefaultItemsTableDataGatewayTest.class,
                DoubloonPrizesTableDataGatewayTest.class,
                FriendTableDataGatewayTest.class,
                InteractableItemRowDataGatewayTest.class,
                LevelTableDataGatewayTest.class,
                MajorRowDataGatewayTest.class,
                MajorTableDataGatewayTest.class,
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
                ChatManagerTest.class,
                ChatMessageReceivedCommandTest.class,
                CheatCodeManagerTest.class,
                CommandAddPlayerTest.class,
                CommandCreatePlayerTest.class,
                CommandKeyInputMessageReceivedTest.class,
                CommandMovePlayerSilentlyTest.class,
                CommandMovePlayerTest.class,
                CommandMovePlayerToAnotherMapAndPersistTest.class,
                CommandObjectiveNotificationCompleteTest.class,
                CommandPersistPlayerTest.class,
                CommandRemovePlayerTest.class,
                CommandTerminalTextExitTest.class,
                InteractObjectManagerTest.class,
                InteractObjectManagerTest.class,
                LevelManagerTest.class,
                NPCMapperTest.class,
                NPCQuestionTest.class,
                NPCTest.class,
                ObjectiveStateTest.class,
                OptionsManagerTest.class,
                PlayerManagerTest.class,
                PlayerMapperTest.class,
                PlayerTest.class,
                QuestManagerTest.class,
                QuestStateTest.class,
                QuestTest.class,
                QuizBotBehaviorTest.class,
                RedHatBehaviorTest.class,
                TutorBehaviorTest.class,

                // edu.ship.engr.shipsim.model.cheatCodeBehaviors
                BuffBehaviorTest.class,
                // MockCheatCodeBehavior.class, is used by tests, but isn't a test itself

                // edu.ship.engr.shipsim.model.reports
                //PlayerAppearanceChangeReportTest.class,
                AddExistingPlayerReportTest.class,
                DoubloonsChangeReportTest.class,
                ExperienceChangedReportTest.class,
                InteractableObjectBuffReportTest.class,
                InteractableObjectTextReportTest.class,
                KeyInputRecievedReportTest.class,
                ObjectInRangeReportTest.class,
                ObjectiveStateChangeReportTest.class,
                PlayerConnectionReportTest.class,
                PlayerFinishedInitializingReportTest.class,
                PlayerMovedReportTest.class,
                QuestStateChangeReportTest.class,
                ReceiveTerminalTextReportTest.class,
                SendChatMessageReportTest.class,
                TeleportOnQuestCompletionReportTest.class,
                TimeToLevelUpDeadlineTest.class,
                UpdatePlayerInformationReportTest.class,

                // restfulcommunication.controllers
                TestObjectiveController.class,
                TestPlayerController.class
        })

public class AllServerTests
{
}
