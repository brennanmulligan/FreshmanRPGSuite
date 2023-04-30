package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.CommunicationException;
import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.handlers.MessageHandlerSet;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.packers.MessagePackerSet;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DatabaseManager;
import edu.ship.engr.shipsim.datasource.DuplicateNameException;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.apache.commons.compress.utils.Lists;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Can simulate the behavior of a series of messages and test to ensure that
 * incoming messages cause the correct outgoing messages to be sent.
 * Essentially, this tests to ensure that all of the participants in a operation
 * obey the message protocol associated with that operation
 *
 * @author Merlin
 */
@GameTest("GameSequenceTests")
public class RunAllSequenceTests
{
    /**
     * the message returned by the test if everything passes
     */
    public static final String SUCCESS_MSG = "Success!!";
    private StateAccumulator stateAccumulator;
    private MessageHandlerSet messageHandlerSet;
    private StateAccumulator secondStateAccumulator;

    void resetCommonSingletons()
    {
        ReportObserverConnector.resetSingleton();
        ModelFacade.resetSingleton();
        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, true);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource
    public void runTest(String className, Class<?> clazz)
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException, DatabaseException,
            ModelFacadeException, CommunicationException, IOException,
            DuplicateNameException
    {
        SequenceTest sequenceTest = (SequenceTest) clazz.getConstructor().newInstance();
        List<Interaction> interactions = sequenceTest.getInteractions();

        for (ServerType server : ServerType.values())
        {
            if (sequenceTest.getServerList().contains(server))
            {
                resetCommonSingletons();
                sequenceTest.resetNecessarySingletons();
                sequenceTest.setUpMachines();

                for (Interaction interaction : interactions)
                {
                    String result = run(server, interaction, true);
                    if (!SUCCESS_MSG.equals(result))
                    {
                        LoggerManager.getSingleton().getLogger().severe(className + " failed: " + result);

                        fail(className + " was not successful");
                    }
                }

                ClientModelFacade.killThreads();

                try
                {
                    DatabaseManager.getSingleton().rollBackAllConnections();
                }
                catch (SQLException e)
                {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static Stream<Arguments> runTest()
    {
        List<Arguments> arguments = Lists.newArrayList();

        Reflections commandClasses = new Reflections("edu.ship.engr.shipsim.sequencetests", SubTypesScanner.class);
        Set<Class<? extends SequenceTest>> cmds = commandClasses.getSubTypesOf(SequenceTest.class);

        for (Class<? extends SequenceTest> cmd : cmds)
        {
            arguments.add(Arguments.of(cmd.getSimpleName(), cmd));
        }

        return arguments.stream();
    }

    /**
     * @param sType   the type of server we want to run this test on
     * @param verbose true if you want output showing the sequence of things the
     *                test is looking at
     * @return A message describing what happened - SUCCESS_MSG if the test passed
     * @throws CommunicationException shouldn't
     * @throws IOException            shouldn't
     */
    public String run(ServerType sType, Interaction interaction, boolean verbose)
            throws CommunicationException, IOException, ModelFacadeException,
            DuplicateNameException, DatabaseException
    {
        if (verbose)
        {
            System.out.println("Starting Interaction for " + sType);
        }

        setUpAccumulators(sType, interaction.getInitiatingPlayerID(), interaction.getOtherPlayerID());

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
                    return sType.name() + " didn't send out " + message + " to " +
                            msgFlow.getDestination()
                            + ". It didn't send out anything";
                }
                if (!msgInAccumulator.equals(message))
                {
                    return sType.name() + " didn't send out " + message + " to " +
                            msgFlow.getDestination()
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
                    ModelFacadeTestHelper.waitForFacadeToProcess(10, "SequenceTests");
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

    private void setUpAccumulators(ServerType sType, int playerID, int otherPlayerID)
    {

        stateAccumulator = new StateAccumulator(new MessagePackerSet());
        stateAccumulator.setPlayerId(playerID);
        messageHandlerSet = new MessageHandlerSet(stateAccumulator);

        secondStateAccumulator = null;
        if (sType.supportsOneToManyConnections())
        {
            secondStateAccumulator = new StateAccumulator(new MessagePackerSet());
            secondStateAccumulator.setPlayerId(otherPlayerID);
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
     * @param sType       the type of machine we are testing
     * @param interaction The specific interaction that is starting
     * @throws IOException shouldn't
     */
    private void initiateTheSequence(ServerType sType, Interaction interaction)
            throws IOException, DuplicateNameException, DatabaseException
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
            ArrayList<Message> secondPendingMsgs =
                    secondStateAccumulator.getPendingMsgs();
            if (!secondPendingMsgs.isEmpty())
            {
                return "Testing " + serverType.name() +
                        ": Second accumulator had messages pending: " + secondPendingMsgs.get(0).toString();
            }
        }
        ArrayList<Message> pendingMsgs = stateAccumulator.getPendingMsgs();
        if (!pendingMsgs.isEmpty())
        {
            return "Testing " + serverType.name() +
                    ": First accumulator had messages pending";
        }
        return null;
    }


}
