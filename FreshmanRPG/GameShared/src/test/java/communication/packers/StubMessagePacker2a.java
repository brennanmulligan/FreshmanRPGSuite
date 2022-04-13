package communication.packers;

import java.util.ArrayList;

import communication.messages.Message;
import communication.messages.StubMessage1;
import model.QualifiedObservableReport;
import model.reports.StubQualifiedObservableReport1;
import model.reports.StubQualifiedObservableReport2;

/**
 * A relatively empty packer that the tests for MessagePackerSet will detect.
 * This will let us test that it detects the MessagePackers that are in its
 * package. This is also used by the StateAccumulator tests.
 *
 * @author Merlin
 *
 */
public class StubMessagePacker2a extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		return new StubMessage1();
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(StubQualifiedObservableReport1.class);
		result.add(StubQualifiedObservableReport2.class);
		return result;
	}

}
