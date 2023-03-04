package edu.ship.engr.shipsim.communication;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.packers.MessagePackerSet;
import edu.ship.engr.shipsim.model.reports.StubReport1;
import edu.ship.engr.shipsim.model.reports.StubReport3;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for StateAccumulators
 *
 * @author merlin
 */
@GameTest("GameShared")
public class StateAccumulatorTest
{

    /**
     * When it initializes, it should use the given StateAccumulatorConnector to
     * register itself as an observer to all of the things it should observe
     */
    @Test
    public void initializes()
    {
        MessagePackerSet set = Mockito.mock(MessagePackerSet.class);
        set.hookUpObservationFor(Mockito.isA(StateAccumulator.class));

        new StateAccumulator(set);
    }

    /**
     * When we ask it for the pending messages, it should empty itself so we
     * don't see them again
     */
    @Test
    public void emptiesOnQuery()
    {
        MessagePackerSet packerSet = Mockito.mock(MessagePackerSet.class);
        packerSet.hookUpObservationFor(Mockito.isA(StateAccumulator.class));
        Message msg = Mockito.mock(Message.class);

        StateAccumulator accum = new StateAccumulator(packerSet);
        accum.queueMessage(msg);

        ArrayList<Message> returnedMessages = accum.getPendingMsgs();
        assertEquals(1, returnedMessages.size());
        assertEquals(0, accum.pendingMsgs.size());
    }

    /**
     * Make sure that an appropriate message gets queued when the accumulator
     * gets updated
     */
    @Test
    public void queuesOnUpdate()
    {
        MessagePackerSet packerSet = new MessagePackerSet();

        StateAccumulator accum = new StateAccumulator(packerSet);
        //Stub report 1 is a SendMessageReport so it should get sent.
        accum.receiveReport(new StubReport1());
        ArrayList<Message> pending = accum.pendingMsgs;
        assertEquals(2, pending.size());
    }

    /**
     *
     */
    @Test
    public void canDirectlyQueueMsgs()
    {
        StateAccumulator accum = new StateAccumulator(null);
        Message m = Mockito.mock(Message.class);
        accum.queueMessage(m);
        assertEquals(1, accum.pendingMsgs.size());
        assertEquals(m, accum.pendingMsgs.get(0));
    }

    /**
     * Test to ensure that we do not send Reports
     */
    @Test
    public void doesNotSendReport()
    {
        StateAccumulator accum = new StateAccumulator(null);

        //stub report 3 is just a Report not a SendMessageReport
        accum.receiveReport(new StubReport3());

        ArrayList<Message> pending = accum.pendingMsgs;

        //we should have no pending messages in this case.
        assertEquals(0, pending.size());
    }
}
