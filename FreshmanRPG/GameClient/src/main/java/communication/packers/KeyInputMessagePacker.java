package communication.packers;

import java.util.ArrayList;

import communication.messages.KeyInputMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.ClientKeyInputSentReport;

/**
 * Tests functionality for a key input message packer
 * @author Ian Keefer & TJ Renninger
 */
public class KeyInputMessagePacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass() != ClientKeyInputSentReport.class)
		{
			throw new IllegalArgumentException("KeyInputPacker cannot pack messages of type " + object.getClass());
		}

		ClientKeyInputSentReport report = (ClientKeyInputSentReport) object;
		KeyInputMessage msg = new KeyInputMessage(report.getInput());

		return msg;
	}

	/**
	 *
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result =
				new ArrayList<>();
		result.add(ClientKeyInputSentReport.class);
		return result;
	}

}
