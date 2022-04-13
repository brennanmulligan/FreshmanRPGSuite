package communication.packers;

import java.util.ArrayList;

import communication.messages.Message;
import communication.messages.TeleportationContinuationMessage;
import datasource.DatabaseException;
import model.PlayerManager;
import model.QualifiedObservableReport;
import model.reports.TeleportOnQuestCompletionReport;

/**
 * Packer telling the client to teleport on quest completion
 *
 * @author Zach Thompson, Chris Hershey, Abdul
 *
 */
public class TeleportOnQuestCompletionPacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		if (object.getClass().equals(TeleportOnQuestCompletionReport.class))
		{
			TeleportOnQuestCompletionReport report = (TeleportOnQuestCompletionReport) object;
			try
			{
				TeleportationContinuationMessage msg = new TeleportationContinuationMessage(
						report.getLocation().getMapName(), report.getHostName(), report.getPortNumber(),
						report.getPlayerID(), PlayerManager.getSingleton().getNewPinFor(report.getPlayerID()));
				return msg;
			}
			catch (DatabaseException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @see communication.packers.MessagePacker#getReportTypesWePack()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
		result.add(TeleportOnQuestCompletionReport.class);
		return result;
	}

}
