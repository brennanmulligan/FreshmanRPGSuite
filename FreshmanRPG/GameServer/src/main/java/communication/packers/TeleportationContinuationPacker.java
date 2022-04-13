package communication.packers;

import java.util.ArrayList;

import communication.messages.Message;
import communication.messages.TeleportationContinuationMessage;
import datasource.DatabaseException;
import model.MapToServerMapping;
import model.PlayerManager;
import model.QualifiedObservableReport;
import model.reports.PlayerReadyToTeleportReport;

/**
 * Progresses teleportation after the player has been moved and persisted.
 */
public class TeleportationContinuationPacker extends MessagePacker
{

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		PlayerReadyToTeleportReport report = (PlayerReadyToTeleportReport) object;
		if (this.getAccumulator().getPlayerID() == report.getPlayerID())
		{
			try
			{
				MapToServerMapping mapping = new MapToServerMapping(report.getMap());

				int newPin = PlayerManager.getSingleton().getNewPinFor(report.getPlayerID());
				return new TeleportationContinuationMessage(report.getMap(), mapping.getHostName(),
						mapping.getPortNumber(), report.getPlayerID(),
						newPin);
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
		result.add(PlayerReadyToTeleportReport.class);
		return result;
	}

}
