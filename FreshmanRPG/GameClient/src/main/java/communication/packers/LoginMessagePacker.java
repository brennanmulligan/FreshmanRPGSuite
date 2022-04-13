package communication.packers;

import java.util.ArrayList;

import communication.messages.LoginMessage;
import communication.messages.Message;
import model.QualifiedObservableReport;
import model.reports.LoginInitiatedReport;

/**
 * Takes the information given to us when MovementNotifier updates and
 * translates it to the appropriate MovementMessage. It turns out this is pretty
 * trivial since the MovementNotifier pushes a MovementMessage
 *
 * @author merlin
 *
 */
public class LoginMessagePacker extends MessagePacker
{
	/**
	 *
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass() != LoginInitiatedReport.class)
		{
			throw new IllegalArgumentException(
					"LoginMessagePacker cannot pack messages of type "
							+ object.getClass());
		}
		LoginInitiatedReport report = (LoginInitiatedReport) object;
		Message msg = new LoginMessage(report.getPlayerName(), report.getPassword());
		return msg;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result =
				new ArrayList<>();
		result.add(LoginInitiatedReport.class);
		return result;
	}

}
