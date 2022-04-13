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
import sequencetests.AdventureCompletionItemInteractSequenceTest;
import sequencetests.AdventureNotificationCompleteSequenceTest;
import sequencetests.CheatCodeForBuffSequenceTest;
import sequencetests.FinishingQuestTeleportsSequenceTest;
import sequencetests.LoginBadPWSequenceTest;
import sequencetests.LoginBadPinSequenceTest;
import sequencetests.LoginBadPlayerNameSequenceTest;
import sequencetests.LoginSuccessSequenceTest;
import sequencetests.MovementBasicSequenceTest;
import sequencetests.MovementTriggerQuestSequenceTest;
import sequencetests.NoMoreBuffSequenceTest;
import sequencetests.NoMultipleBuffSequenceTest;
import sequencetests.ObjectNotInRangeSequenceTest;
import sequencetests.ObjectSendsPopupMessageSequenceTest;
import sequencetests.TeleportationMovementSequenceTest;
import sequencetests.TerminalTextSequenceTest;
import sequencetests.TriggerBuffMessageSequenceTest;

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
	private MessagePackerSet messagePackerSet;
	private StateAccumulator secondStateAccumulator;
	// private MessageHandlerSet secondMessageHandlerSet;
	private MessagePackerSet secondMessagePackerSet;
	private Class<?> testClass;

	/**
	 * @return the list of sequence tests
	 */
	@Parameters(name = "{index}: {0}")
	public static Collection<Object[]> data()
	{
		return Arrays.asList(new Object[][]
				{
						{"TriggerBuffMessageSequenceTest", TriggerBuffMessageSequenceTest.class},
						{"TerminalTextSequenceTest", TerminalTextSequenceTest.class},

						//{"TeleportationMovementSequenceTest", TeleportationMovementSequenceTest.class},
						//		{ "QuiznasiumGrantsKnowledgePointsWithBuffSequenceTest",
						//				QuiznasiumGrantsKnowledgePointsWithBuffSequenceTest.class },
						{"ObjectSendsPopupMessageSequenceTest", ObjectSendsPopupMessageSequenceTest.class},
						{"ObjectNotInRangeSequenceTest", ObjectNotInRangeSequenceTest.class},
						{"NoMultipleBuffSequenceTest", NoMultipleBuffSequenceTest.class},
						{"NoMoreBuffSequencerTest", NoMoreBuffSequenceTest.class},
						{"MovementTriggerQuestSequenceTest", MovementTriggerQuestSequenceTest.class},
						{"MovementBasicSequenceTest", MovementBasicSequenceTest.class},
						{"LoginSuccessSequenceTest", LoginSuccessSequenceTest.class},
						{"LoginBadPWSequenceTest", LoginBadPWSequenceTest.class},
						{"LoginBadPlayerNameSequenceTest", LoginBadPlayerNameSequenceTest.class},
						{"LoginBadPinSequenceTest", LoginBadPinSequenceTest.class},
						{"FinishingQuestTeleportsSequenceTest", FinishingQuestTeleportsSequenceTest.class},
						{"CheatCodeForBuffSequenceTest", CheatCodeForBuffSequenceTest.class},
						{"AdventureNotificationCompleteSequenceTest", AdventureNotificationCompleteSequenceTest.class},
						{"AdventureCompletionItemInteractSequenceTest",
								AdventureCompletionItemInteractSequenceTest.class},});
	}

	/**
	 * @param test the description of the message protocol for a given situation
	 * @throws IOException       shouldn't
	 * @throws DatabaseException shouldn't
	 */
	public void setUpTheTest(SequenceTest test) throws DatabaseException
	{
		this.testcase = test;
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		QualifiedObservableConnector.resetSingleton();
		ModelFacade.resetSingleton();
		testcase.setUpMachines();

		messagePackerSet = new MessagePackerSet();
		stateAccumulator = new StateAccumulator(messagePackerSet);
		stateAccumulator.setPlayerId(test.getInitiatingPlayerID());
		messageHandlerSet = new MessageHandlerSet(stateAccumulator);
		secondMessagePackerSet = null;
		secondStateAccumulator = null;

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

		SequenceTest testcase = (SequenceTest) testClass.getConstructor().newInstance();
		setUpTheTest(testcase);

		for (ServerType serverToTest : ServerType.values())
		{
			if (testcase.getServerList().contains(serverToTest))
			{
				String result = run(serverToTest, true);
				ClientModelFacade.killThreads();
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
		if (sType.supportsOneToManyConnections() && secondMessagePackerSet == null)
		{
			secondMessagePackerSet = new MessagePackerSet();
			secondStateAccumulator = new StateAccumulator(secondMessagePackerSet);
			// secondMessageHandlerSet = new
			// MessageHandlerSet(secondStateAccumulator);
		}
		// ModelFacade lookHere = ModelFacade.getSingleton();
		testcase.resetDataGateways();
		testcase.setUpMachines();
		purgeAccumulators();
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
		return SUCCESS_MSG;
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
