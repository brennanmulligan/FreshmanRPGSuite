package communication.packers;

import java.util.ArrayList;

import communication.messages.HighScoreRequestMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.HighScoreRequestReport;

/**
 * Packer for the high score request message
 * @author Ryan
 *
 */
public class HighScoreRequestPacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		return new HighScoreRequestMessage();
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result =
				new ArrayList<>();
		result.add(HighScoreRequestReport.class);
		return result;
	}

}
