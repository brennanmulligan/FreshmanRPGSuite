package model;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import communication.CommunicationException;
import communication.StateAccumulator;
import communication.handlers.MessageHandlerSet;
import communication.messages.Message;
import communication.packers.MessagePackerSet;
import datasource.DatabaseException;
import sequencetests.*;

/**
 * Can simulate the behavior of a series of messages and test to ensure that
 * incoming messages cause the correct outgoing messages to be sent.
 * Essentially, this tests to ensure that all of the participants in a operation
 * obey the message protocol associated with that operation
 *
 * @author Merlin
 *
 */
@RunWith(Parameterized.class)
public class SequenceTestRunner
{
	/**
	 * the message returned by the test if everything passes
	 */
	public static final String SUCCESS_MSG = "Success!!";
	private SequenceTest testcase;
	private StateAccumulator stateAccumulator;
	private MessageHandlerSet messageHandlerSet;
	private StateAccumulator secondStateAccumulator;

	private final Class<?> testClass;

	/**
	 * @return the list of sequence tests
	 */
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data()
	{
		return Arrays.asList(new Object[][]
				{
						{"CdTeleportationSequenceTest",
								CdTeleportationSequenceTest.class},
						{"CheatCodeForBuffSequenceTest", CheatCodeForBuffSequenceTest.class},
						{"FinishingQuestTeleportsSequenceTest", FinishingQuestTeleportsSequenceTest.class},
						{"LoginBadPlayerNameSequenceTest", LoginBadPlayerNameSequenceTest.class},
						{"LoginBadPWSequenceTest", LoginBadPWSequenceTest.class},
						{"LoginSuccessSequenceTest", LoginSuccessSequenceTest.class},
						{"MovementBasicSequenceTest", MovementBasicSequenceTest.class},
						{"MovementTriggerQuestSequenceTest", MovementTriggerQuestSequenceTest.class},
						{"NoMultipleBuffSequenceTest", NoMultipleBuffSequenceTest.class},
						{"ObjectiveCompletionItemInteractSequenceTest",
								ObjectiveCompletionItemInteractSequenceTest.class},
						{"ObjectiveNotificationCompleteSequenceTest", ObjectiveNotificationCompleteSequenceTest.class},
						{"ObjectNotInRangeSequenceTest", ObjectNotInRangeSequenceTest.class},
						{"RecCenterGrantsDoubloonsWithBuffSequenceTest",
								RecCenterGrantsDoubloonsWithBuffSequenceTest.class },
						{"TlelportationTwiceSequenceTest",
								TeleportationTwiceSequenceTest.class },
						{"TriggerBuffMessageSequenceTest", TriggerBuffMessageSequenceTest.class},
						{"TerminalTextSequenceTest", TerminalTextSequenceTest.class},
						{"ObjectSendsPopupMessageSequenceTest", ObjectSendsPopupMessageSequenceTest.class},


							{ "RecCenterGrantsDoubloonsWithBuffSequenceTest",
										RecCenterGrantsDoubloonsWithBuffSequenceTest.class },
						{"TeleportationMovementSequenceTest", TeleportationMovementSequenceTest.class},
						// Terminal Text Sequence Test
						// Trigger BuffMessage Sequence Test
						{"VanityShopGetInvSequenceTest", VanityShopGetInvSequenceTest.class},});
	}

	/**
	 * @param test the description of the message protocol for a given situation
	 */
	public void setUpTheTest(SequenceTest test)
	{
		this.testcase = test;
		resetCommonSingletons();

	}

	private void resetCommonSingletons()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		QualifiedObservableConnector.resetSingleton();
		ModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, true);
	}

	/**
	 * @param input the string that the test should be named
	 * @param expected the class of the sequence test we should run
	 */
	public SequenceTestRunner(String input, Class<?> expected)
	{
		this.testClass = expected;
	}

	/**
	 * @throws IOException            shouldn't
	 * @throws CommunicationException shouldn't
	 * @throws IllegalAccessException shouldn't
	 * @throws InstantiationException shouldn't
	 * @throws DatabaseException      shouldn't
	 */
	@Test
	public void singleSequenceTest() throws InstantiationException, IllegalAccessException, IOException,
			CommunicationException, DatabaseException, NoSuchMethodException, InvocationTargetException
	{

		testcase = (SequenceTest) testClass.getConstructor().newInstance();
		ArrayList<Interaction> interactions = testcase.getInteractions();
		for (ServerType serverToTest : ServerType.values())
		{
			// Use this line instead of the loop if you want to debug on one server
//			ServerType serverToTest = ServerType.AREA_SERVER;
			if (testcase.getServerList().contains(serverToTest))
			{
				resetCommonSingletons();
				testcase.resetNecessarySingletons();
				testcase.setUpMachines();
				for (Interaction interaction:interactions)
				{
					String result = run(serverToTest, interaction,true);
					assertEquals(SUCCESS_MSG, result);
				}
				ClientModelFacade.killThreads();
				ModelFacade.killThreads();
			}
		}
	}

	/**
	 * @param sType   the type of server we want to run this test on
	 * @param verbose true if you want output showing the sequence of things the
	 *                test is looking at
	 * @return A message describing what happened - SUCCESS_MSG if the test passed
	 * @throws CommunicationException shouldn't
	 * @throws IOException shouldn't
	 */
	public String run(ServerType sType, Interaction interaction, boolean verbose) throws CommunicationException, IOException
	{
		if (verbose)
		{
			System.out.println("Starting Interaction for " + sType);
		}

		setUpAccumulators(sType, interaction.getInitiatingPlayerID());

		initiateTheSequence(sType, interaction);
		for (MessageFlow msgFlow : interaction.getMessageSequence())
		{
			Message message = msgFlow.getMessage();
			if (msgFlow.getSource().equals(sType) && msgFlow.isReaction())
			{
				if (verbose)
				{
					System.out.println("Checking that I sourced " + msgFlow.getMessage());
				}
				Message msgInAccumulator;
				if (msgFlow.getDestination().equals(ServerType.OTHER_CLIENT))
				{
					msgInAccumulator = secondStateAccumulator.getFirstMessage();
				}
				else
				{
					msgInAccumulator = stateAccumulator.getFirstMessage();
				}
				if (msgInAccumulator == null)
				{
					return sType.name() + " didn't send out " + message + " to " + msgFlow.getDestination()
							+ ". It didn't send out anything";
				}
				if (!msgInAccumulator.equals(message))
				{
					return sType.name() + " didn't send out " + message + " to " + msgFlow.getDestination()
							+ " Instead, I see a " + msgInAccumulator;
				}

			}
			if (msgFlow.getDestination().equals(sType))
			{
				if (verbose)
				{
					System.out.println("I am receiving " + msgFlow.getMessage());
				}
				messageHandlerSet.process(message);
				if (sType == ServerType.AREA_SERVER)
				{
					waitForCommandComplete();
				}

			}
		}
		String extraMessagesError = checkForExtraMessages(sType);
		if (extraMessagesError != null)
		{
			return extraMessagesError;
		}
		if (verbose)
		{
			System.out.println("Finished Interaction for " + sType);
		}
		return SUCCESS_MSG;
	}

	private void setUpAccumulators(ServerType sType, int playerID)
	{

		stateAccumulator = new StateAccumulator(new MessagePackerSet());
		stateAccumulator.setPlayerId(playerID);
		messageHandlerSet = new MessageHandlerSet(stateAccumulator);

		secondStateAccumulator = null;
		if (sType.supportsOneToManyConnections())
		{
			secondStateAccumulator = new StateAccumulator(new MessagePackerSet());
			// secondMessageHandlerSet = new
			// MessageHandlerSet(secondStateAccumulator);
		}
	}

	private void purgeAccumulators()
	{
		stateAccumulator.purgePendingMessages();
		if (secondStateAccumulator != null)
		{
			this.secondStateAccumulator.purgePendingMessages();
		}
	}

	/**
	 * There are two ways the interaction can be initiated: by the execution of a
	 * command or by sending an initial message. If the test specifies a command,
	 * execute it if we are the machine that should execute it. If the test doesn't
	 * specify a command and we are the machine that should source the first
	 * message, just ignore that message (it is there to cause things to happen on
	 * other machines)
	 *
	 * @param sType    the type of machine we are testing
	 * @param interaction The specific interaction that is starting
	 * @throws IOException shouldn't
	 */
	private void initiateTheSequence(ServerType sType, Interaction interaction) throws IOException
	{
		if (sType == interaction.getInitiatingServerType())
		{
			Command initiatingCommand = interaction.getInitiatingCommand();
			if (initiatingCommand != null)
			{
				interaction.getInitiatingCommand().execute();
			}
		}
	}

	private String checkForExtraMessages(ServerType serverType)
	{
		if (secondStateAccumulator != null)
		{
			ArrayList<Message> secondPendingMsgs = secondStateAccumulator.getPendingMsgs();
			if (!secondPendingMsgs.isEmpty())
			{
				return "Testing " + serverType.name() + ": Second accumulator had messages pending";
			}
		}
		ArrayList<Message> pendingMsgs = stateAccumulator.getPendingMsgs();
		if (!pendingMsgs.isEmpty())
		{
			return "Testing " + serverType.name() + ": First accumulator had messages pending";
		}
		return null;
	}

	private void waitForCommandComplete()
	{
		while (ModelFacade.getSingleton().hasCommandsPending())
		{
			try
			{
				Thread.sleep(100);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
