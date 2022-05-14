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
	// private MessageHandlerSet secondMessageHandlerSet;
	private MessagePackerSet secondMessagePackerSet;
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
						{"TriggerBuffMessageSequenceTest", TriggerBuffMessageSequenceTest.class},
						{"TerminalTextSequenceTest", TerminalTextSequenceTest.class},
						{"ObjectSendsPopupMessageSequenceTest", ObjectSendsPopupMessageSequenceTest.class},

						//{"PlayerHasVanityItemSequenceTest",
						// PlayerHasVanityItemSequenceTest.class};
						//		{ "RecCenterGrantsDoubloonsWithBuffSequenceTest",
						//				RecCenterGrantsDoubloonsWithBuffSequenceTest.class },
						//{"TeleportationMovementSequenceTest", TeleportationMovementSequenceTest.class},
						// Terminal Text Sequence Test
						// Trigger BuffMessage Sequence Test
						{"VanityShopGetInvSequenceTest", VanityShopGetInvSequenceTest.class},});
	}

	/**
	 * @param test the description of the message protocol for a given situation
	 * @throws DatabaseException shouldn't
	 */
	public void setUpTheTest(SequenceTest test) throws DatabaseException
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

		for (ServerType serverToTest : ServerType.values())
		{
			if (testcase.getServerList().contains(serverToTest))
			{
				// TODO This is where you will have to loop through the interactions
				//  and run each one
				String result = run(serverToTest, true);
				ClientModelFacade.killThreads();
				ModelFacade.killThreads();
				assertEquals(SUCCESS_MSG, result);
			}
		}
	}

	/**
	 * @param sType   the type of server we want to run this test on
	 * @param verbose true if you want output showing the sequence of things the
	 *                test is looking at
	 * @return A message describing what happened - SUCCESS_MSG if the test passed
	 * @throws CommunicationException shouldn't
	 * @throws DatabaseException      shouldn't
	 * @throws IOException shouldn't
	 */
	public String run(ServerType sType, boolean verbose) throws CommunicationException, DatabaseException, IOException
	{
		if (verbose)
		{
			System.out.println("Starting test for " + sType);
		}
		resetCommonSingletons();
		testcase.resetNecessarySingletons();
		testcase.setUpMachines();
		setUpAccumulators(sType, testcase.getInitiatingPlayerID());

		ArrayList<MessageFlow> messages = testcase.getMessageSequence();
		initiateTheSequence(sType, messages);
		for (MessageFlow msgFlow : messages)
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
			System.out.println("Finished test for " + sType);
		}
		return SUCCESS_MSG;
	}

	private void setUpAccumulators(ServerType sType, int playerID)
	{
		stateAccumulator = new StateAccumulator(new MessagePackerSet());
		stateAccumulator.setPlayerId(playerID);
		messageHandlerSet = new MessageHandlerSet(stateAccumulator);
		secondMessagePackerSet = null;
		secondStateAccumulator = null;
		if (sType.supportsOneToManyConnections() && secondMessagePackerSet == null)
		{
			secondMessagePackerSet = new MessagePackerSet();
			secondStateAccumulator = new StateAccumulator(secondMessagePackerSet);
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
	 * There are two ways the sequence can be initiated: by the execution of a
	 * command or by sending an initial message. If the test specifies a command,
	 * execute it if we are the machine that should execute it. If the test doesn't
	 * specify a command and we are the machine that should source the first
	 * message, just ignore that message (it is there to cause things to happen on
	 * other machines)
	 *
	 * @param sType    the type of machine we are testing
	 * @param messages the sequence of messages we are supposed to execute
	 * @throws IOException shouldn't
	 */
	private void initiateTheSequence(ServerType sType, ArrayList<MessageFlow> messages) throws IOException
	{
		if (sType == testcase.getInitiatingServerType())
		{
			Command initiatingCommand = testcase.getInitiatingCommand();
			if (initiatingCommand != null)
			{
				testcase.getInitiatingCommand().execute();
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
