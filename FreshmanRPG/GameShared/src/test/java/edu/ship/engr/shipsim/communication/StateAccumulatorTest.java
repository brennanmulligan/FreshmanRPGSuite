package edu.ship.engr.shipsim.communication;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.packers.MessagePackerSet;
import edu.ship.engr.shipsim.model.reports.StubQualifiedObservableReport1;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for StateAccumulators
 *
 * @author merlin
 */
public class StateAccumulatorTest
{

    /**
     * When it initializes, it should use the given StateAccumulatorConnector to
     * register itself as an observer to all of the things it should observe
     */
    @Test
    public void initializes()
    {
        MessagePackerSet packerSet = EasyMock.createMock(MessagePackerSet.class);
        packerSet.hookUpObservationFor(EasyMock.isA(StateAccumulator.class));
        EasyMock.replay(packerSet);

        new StateAccumulator(packerSet);
        EasyMock.verify(packerSet);
    }

    /**
     * When we ask it for the pending messages, it should empty itself so we
     * don't see them again
     */
    @Test
    public void emptiesOnQuery()
    {
        MessagePackerSet packerSet = EasyMock.createMock(MessagePackerSet.class);
        packerSet.hookUpObservationFor(EasyMock.isA(StateAccumulator.class));
        Message msg = EasyMock.createMock(Message.class);
        EasyMock.replay(msg);
        EasyMock.replay(packerSet);

        StateAccumulator accum = new StateAccumulator(packerSet);
        ArrayList<Message> mockMessages = new ArrayList<>();
        mockMessages.add(msg);
        accum.pendingMsgs = mockMessages;
        ArrayList<Message> returnedMessages = accum.getPendingMsgs();
        assertEquals(1, returnedMessages.size());
        assertEquals(0, accum.pendingMsgs.size());
        EasyMock.verify(packerSet);
        EasyMock.verify(msg);
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
        accum.receiveReport(new StubQualifiedObservableReport1());
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
        Message m = EasyMock.createMock(Message.class);
        accum.queueMessage(m);
        assertEquals(1, accum.pendingMsgs.size());
        assertEquals(m, accum.pendingMsgs.get(0));
    }

}
