import communication.messages.*;
import datasource.*;
import org.junit.ClassRule;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.ConnectionIncomingTest;
import communication.ConnectionListenerTest;
import communication.ConnectionManagerTest;
import communication.ConnectionOutgoingTest;
import communication.StateAccumulatorTest;
import communication.handlers.MessageHandlerSetTest;
import communication.packers.MessagePackerSetTest;
import dataDTO.ClientPlayerObjectiveTest;
import dataDTO.ClientPlayerQuestTest;
import datatypes.PositionTest;
import model.FindPlayerByIDByPlayerNameTest;
import model.MapToServerMappingTest;
import model.PlayerConnectionTest;
import model.PlayerLoginTest;
import model.QualifiedObserverConnectorTest;
import model.terminal.TerminalManagerTest;
import tmxfiles.TMXMapReaderTest;

/**
 * All of the tests for the shared code. Notice that the packages, and classes
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
				// communication
				ConnectionIncomingTest.class,
				ConnectionOutgoingTest.class,
				ConnectionListenerTest.class,
				ConnectionManagerTest.class,
				StateAccumulatorTest.class,

				// communication.handlers
				MessageHandlerSetTest.class,
				// StubMessageHandler1.class,
				// StubMessageHandler2.class,

				// communication.messages
				ObjectiveNotificationCompleteMessageTest.class,
				ObjectiveStateChangeMessageTest.class,
				RecievedTerminalTextMessageTest.class,
				AreaCollisionMessageTest.class,
				BuffMessageTest.class,
				ChatMessageToClientTest.class,
				ConnectionMessageTest.class,
				DisconnectionMessageTest.class,
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

				// communicatiaon.packers
				MessagePackerSetTest.class,
				// StubMessagePacker1,
				// StubMessagePacker2,
				// StubMessagePacker2a,

				//dataDTO
				ClientPlayerObjectiveTest.class,
				ClientPlayerQuestTest.class,

				// datasource
				DatabaseManagerTest.class,
				// DatabaseTest.class,
				LevelRecordTest.class,
				PlayerConnectionRowDataGatewayMockTest.class,
				PlayerConnectionRowDataGatewayRDSTest.class,
				// PlayerConnectionRowDataGateway,
				PlayerLoginRowDataGatewayMockTest.class,
				PlayerLoginRowDataGatewayRDSTest.class,
				// PlayerLoginDataBehaviorTest.class,
				PlayerRowDataGatewayMockTest.class,
				PlayerRowDataGatewayRDSTest.class,
				// PlayerRowDataGatewayTest.class,
				PlayerScoreRecordTest.class,
				// PlayersForTest
				ServerRowDataGatewayMockTest.class,
				ServerRowDataGatewayRDSTest.class,
				// ServerRowDataGatewayTest.class,
				// ServersForTest

				// datatypes
				PositionTest.class,

				// model
				FindPlayerByIDByPlayerNameTest.class,
				MapToServerMappingTest.class,
				PlayerConnectionTest.class,
				PlayerLoginTest.class,
				QualifiedObserverConnectorTest.class,

				// model.reports
				// StubQualifiedObservableReport1.class,
				// StubQualifiedObservableReport2.class

				//model.terminal
				TerminalManagerTest.class,

				// tmxfiles
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
		}

		;

		@Override
		protected void after()
		{

		}

		;
	};

}
