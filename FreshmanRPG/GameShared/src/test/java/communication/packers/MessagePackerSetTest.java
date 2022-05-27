package communication.packers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.net.ServerSocket;
import java.util.ArrayList;

import datasource.ServerSideTest;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import communication.CommunicationException;
import communication.StateAccumulator;
import communication.messages.Message;
import model.QualifiedObservableConnector;
import model.QualifiedObservableReport;
import model.reports.StubQualifiedObservableReport1;
import model.reports.StubQualifiedObservableReport2;

/**
 * Tests for the generic MessageProcessor
 *
 * @author merlin
 *
 */
public class MessagePackerSetTest extends ServerSideTest
{

	private ServerSocket servSock;

	/**
	 * reset the necessary singletons
	 */
	@Before
	public void localSetUp()
	{
		QualifiedObservableConnector.resetSingleton();
	}

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
		ArrayList<Message> result = set.pack(new StubQualifiedObservableReport1());
		assertNotNull(result);
		result = set.pack(new StubQualifiedObservableReport2());
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
		ArrayList<Message> result = set.pack(new StubQualifiedObservableReport2());
		assertEquals(2, result.size());
	}

	/**
	 * If there isn't any handler for the type of message, the list returned should be
	 * empty
	 *
	 */
	@Test
	public void noSuchHandler() throws CommunicationException
	{
		MessagePackerSet set = new MessagePackerSet();

		QualifiedObservableReport report = EasyMock.createMock(QualifiedObservableReport.class);


		ArrayList<Message> msgs = set.pack(report);
		assertEquals(0,msgs.size());

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
		StateAccumulator observer = EasyMock.createMock(StateAccumulator.class);
		observer.receiveReport(EasyMock.isA(TestReport1.class));
		EasyMock.replay(observer);

		// register the packer, hook up the observers, then our notify should
		// give the update our observer is expecting
		set.registerPacker(packer);
		set.hookUpObservationFor(observer);
		assertEquals(observer, packer.getAccumulator());
		QualifiedObservableConnector.getSingleton().sendReport(new TestReport1());

		EasyMock.verify(observer);
	}

	/**
	 * We need to destroy the MessagePackerSet packers so that on connection close
	 * they are closed as well.
	 */
	@Test
	public void destroyObservers()
	{
		MessagePackerSet set = new MessagePackerSet();
		QualifiedObservableConnector connector = QualifiedObservableConnector.getSingleton();
		StateAccumulator observer = EasyMock.createMock(StateAccumulator.class);
		connector.registerObserver(observer, TestReport1.class);
		assertEquals(2, set.getCount());
		set.destroyAllObservables(observer);
		assertEquals(0, set.getCount());
	}

	private static class TestReport1 implements QualifiedObservableReport
	{

	}

	private static class TestReport2 implements QualifiedObservableReport
	{

	}

	private static class MockMessagePacker extends MessagePacker
	{

		/**
		 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
		 */
		@Override
		public Message pack(QualifiedObservableReport object)
		{
			return EasyMock.createMock(Message.class);
		}

		/**
		 * @see communication.packers.MessagePacker#getReportTypesWePack()
		 */
		@Override
		public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
		{
			ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();

			result.add(TestReport1.class);
			result.add(TestReport2.class);
			return result;
		}

	}
}
