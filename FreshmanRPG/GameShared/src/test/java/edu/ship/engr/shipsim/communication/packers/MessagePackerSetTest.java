package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.CommunicationException;
import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.reports.StubReport1;
import edu.ship.engr.shipsim.model.reports.StubReport2;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests for the generic MessageProcessor
 *
 * @author merlin
 */
@GameTest("GameShared")
@ResetReportObserverConnector
public class MessagePackerSetTest
{
    /**
     * Detects and registers all message packers in the same package the
     * MessagePackerSet is in. In this case, it should detect the packers that pack
     * the two stub report types. If can pack them, the packers must have been
     * detected
     *
     * @throws CommunicationException shouldn't
     */
    @Test
    public void detectsAll() throws CommunicationException
    {
        MessagePackerSet set = new MessagePackerSet();
        ArrayList<Message> result = set.pack(new StubReport1());
        assertNotNull(result);
        result = set.pack(new StubReport2());
        assertNotNull(result);
    }

    /**
     * Two of the stub packers listen to the same report
     *
     * @throws CommunicationException shouldn't
     */
    @Test
    public void canHaveTwo() throws CommunicationException
    {
        MessagePackerSet set = new MessagePackerSet();
        ArrayList<Message> result = set.pack(new StubReport2());
        assertEquals(2, result.size());
    }

    /**
     * If there isn't any handler for the type of message, the list returned should be
     * empty
     */
    @Test
    public void noSuchHandler() throws CommunicationException
    {
        MessagePackerSet set = new MessagePackerSet();
        Report report = Mockito.mock(Report.class);

        ArrayList<Message> msgs = set.pack(report);
        assertEquals(0, msgs.size());

    }

    /**
     * When we ask it to, the packer set should run through all of the packers and
     * hook up an observable to the type of report each one wants to pack
     */
    @Test
    public void hooksUpObservers()
    {
        MessagePackerSet set = new MessagePackerSet();
        MessagePacker packer = new MockMessagePacker();

        // Set up observable and observer that are interested in the report our
        // mock packer is packing
        StateAccumulator observer = Mockito.mock(StateAccumulator.class);
        observer.receiveReport(Mockito.isA(TestReport1.class));

        // register the packer, hook up the observers, then our notify should
        // give the update our observer is expecting
        set.registerPacker(packer);
        set.hookUpObservationFor(observer);
        assertEquals(observer, packer.getAccumulator());
        ReportObserverConnector.getSingleton().sendReport(new TestReport1());
    }

    /**
     * We need to destroy the MessagePackerSet packers so that on connection close
     * they are closed as well.
     */
    @Test
    public void destroyObservers()
    {
        MessagePackerSet set = new MessagePackerSet();
        ReportObserverConnector connector = ReportObserverConnector.getSingleton();
        StateAccumulator observer = Mockito.mock(StateAccumulator.class);
        connector.registerObserver(observer, TestReport1.class);
        assertEquals(2, set.getCount());
        set.destroyAllObservables(observer);
        assertEquals(0, set.getCount());
    }

    private static class TestReport1 implements Report
    {

    }

    private static class TestReport2 implements Report
    {

    }

    private static class MockMessagePacker extends MessagePacker
    {

        /**
         * @see MessagePacker#pack(Report)
         */
        @Override
        public Message pack(Report object)
        {
            return Mockito.mock(Message.class);
        }

        /**
         * @see MessagePacker#getReportTypesWePack()
         */
        @Override
        public ArrayList<Class<? extends Report>> getReportTypesWePack()
        {
            ArrayList<Class<? extends Report>> result = new ArrayList<>();

            result.add(TestReport1.class);
            result.add(TestReport2.class);
            return result;
        }

    }
}
