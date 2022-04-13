package communication.packers;

import java.io.IOException;
import java.util.ArrayList;

import communication.messages.MapFileMessage;
import communication.messages.Message;
import model.OptionsManager;
import model.QualifiedObservableReport;
import model.reports.PlayerConnectionReport;

/**
 * @author Merlin
 *
 */
public class MapFileMessagePacker extends MessagePacker
{
	/**
	 * The prefix for where maps are stored on the client
	 */
	public static String DIRECTORY_PREFIX = "maps/";

	/**
	 * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
	 */
	@Override
	public Message pack(QualifiedObservableReport object)
	{
		PlayerConnectionReport report = (PlayerConnectionReport) object;
		try
		{
			int playerID = report.getPlayerID();
			if (this.getAccumulator().getPlayerID() == playerID)
			{
				// send this server's map file back to the client when they
				// connect to the server
				OptionsManager optionsManager = OptionsManager.getSingleton();
				String mapName = optionsManager.getMapName();
				MapFileMessage msg = new MapFileMessage(DIRECTORY_PREFIX + mapName);
				return msg;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
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
		result.add(PlayerConnectionReport.class);
		return result;
	}

}
