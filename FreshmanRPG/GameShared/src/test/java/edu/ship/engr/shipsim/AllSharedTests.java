package edu.ship.engr.shipsim;

import edu.ship.engr.shipsim.communication.ConnectionIncomingTest;
import edu.ship.engr.shipsim.communication.ConnectionManagerTest;
import edu.ship.engr.shipsim.communication.ConnectionOutgoingTest;
import edu.ship.engr.shipsim.communication.StateAccumulatorTest;
import edu.ship.engr.shipsim.communication.handlers.MessageHandlerSetTest;
import edu.ship.engr.shipsim.communication.messages.*;
import edu.ship.engr.shipsim.communication.packers.MessagePackerSetTest;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerObjectiveTest;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestTest;
import edu.ship.engr.shipsim.datasource.*;
import edu.ship.engr.shipsim.datatypes.PositionTest;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.model.terminal.TerminalManagerTest;
import edu.ship.engr.shipsim.tmxfiles.TMXMapReaderTest;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * All of the tests for the shared code. Notice that the packages, and classes
 * within them, are in order by the way they show in the package explorer. This
 * is to make it easy to see if a class is missing from this list. Helper
 * classes that do not contain tests are included in the list, but commented out
 *
 * @author Merlin
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                // edu.ship.engr.shipsim.communication
                ConnectionIncomingTest.class,
                ConnectionOutgoingTest.class,
                ConnectionManagerTest.class,
                StateAccumulatorTest.class,

                // edu.ship.engr.shipsim.communication.handlers
                MessageHandlerSetTest.class,
                // StubMessageHandler1.class,
                // StubMessageHandler2.class,

                // edu.ship.engr.shipsim.communication.messages
                ObjectiveNotificationCompleteMessageTest.class,
                ObjectiveStateChangeMessageTest.class,
                RecievedTerminalTextMessageTest.class,
                AreaCollisionMessageTest.class,
                BuffMessageTest.class,
                ChatMessageToClientTest.class,
                ConnectionMessageTest.class,
                ExperienceChangedMessageTest.class,
                InitializeThisClientsPlayerMessageTest.class,
                KeyInputMessageTest.class,
                DoubloonsChangedMessageTest.class,
                LoginMessageTest.class,
                LoginResponseMessageTest.class,
                MapFileMessageTest.class,
                MessageStructureVerifier.class,
                MovementMessageTest.class,
                PlayerAppearanceChangeMessageTest.class,
                PlayerJoinedMessageTest.class,
                PlayerLeaveMessageTest.class,
                ObjectUnavailableMessageTest.class,
                TerminalTextExitMessageTest.class,


                // StubMessage1.class,
                // StubMessage2.class,
                TeleportationContinuationMessageTest.class,
                TeleportationInitiationMessageTest.class,
                TimeToLevelUpDeadlineTest.class,

                // edu.ship.engr.shipsim.communicatiaon.packers
                MessagePackerSetTest.class,
                // StubMessagePacker1,
                // StubMessagePacker2,
                // StubMessagePacker2a,

                // edu.ship.engr.shipsim.dataDTO
                ClientPlayerObjectiveTest.class,
                ClientPlayerQuestTest.class,

                // edu.ship.engr.shipsim.datasource
                DatabaseManagerTest.class,
                LevelRecordTest.class,
                PlayerConnectionRowDataGatewayTest.class,
                PlayerLoginRowDataGatewayTest.class,
                PlayerRowDataGatewayTest.class,
                PlayerScoreRecordTest.class,
                ServerRowDataGatewayTest.class,
                // ServersForTest

                // datatypes
                PositionTest.class,

                // edu.ship.engr.shipsim.model
                FindPlayerByIDByPlayerNameTest.class,
                MapToServerMappingTest.class,
                PlayerConnectionTest.class,
                PlayerLoginTest.class,
                QualifiedObserverConnectorTest.class,

                // edu.ship.engr.shipsim.model.reports
                // StubQualifiedObservableReport1.class,
                // StubQualifiedObservableReport2.class

                // edu.ship.engr.shipsim.model.terminal
                TerminalManagerTest.class,

                // edu.ship.engr.shipsim.tmxfiles
                TMXMapReaderTest.class,})
public class AllSharedTests
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
            DatabaseManager.getSingleton().setTesting();
            LoggerManager.createLogger("GameSharedTests");
        }

        @Override
        protected void after()
        {
        }
    };

}
