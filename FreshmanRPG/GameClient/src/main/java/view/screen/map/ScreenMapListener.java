package view.screen.map;

import java.util.ArrayList;

import datatypes.Position;
import model.ClientPlayer;
import model.ClientPlayerManager;
import model.QualifiedObservableReport;
import model.reports.ChangeMapReport;
import model.reports.ChangePlayerAppearanceReport;
import model.reports.ClientPlayerMovedReport;
import model.reports.NewMapReport;
import model.reports.PinFailedReport;
import model.reports.PlayerConnectedToAreaServerReport;
import model.reports.PlayerDisconnectedFromAreaServerReport;
import view.player.PlayerType;
import view.screen.ScreenListener;

/**
 * Not much for now!
 *
 * @author Merlin
 *
 */
public class ScreenMapListener extends ScreenListener
{
	/**
	 *
	 */
	public ScreenMapListener()
	{
		super.setUpListening();
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 * added an if statement that only allows the playersConnectToAreaServerReport to get that Clients Player, not a new player
	 */
	@Override
	public void receiveReport(QualifiedObservableReport arg)
	{
		ScreenMap map = (ScreenMap) this.screen;

		// adds your player's sprite to this client
		if (arg.getClass().equals(ClientPlayerMovedReport.class))
		{
			ClientPlayerMovedReport report = (ClientPlayerMovedReport) arg;
			map.movePlayer(report.getID(), report.getNewPosition());
		}
		// moves the player to a new position
		else if (arg.getClass().equals(PlayerConnectedToAreaServerReport.class))
		{
			PlayerConnectedToAreaServerReport report = (PlayerConnectedToAreaServerReport) arg;
			PlayerType type = PlayerType.valueOf(report.getPlayerAppearanceType());
			Position pos = report.getPlayerPosition();
			map.addPlayer(report.getPlayerID(), type, pos, report.isThisClientsPlayer());
			if (report.isThisClientsPlayer())
			{
				map.addUIs(report.getPlayerName(), report.getCrew().getCrewAppearanceType(), report.getMajor().getMajorName());
			}
		}
		else if (arg.getClass().equals(PlayerDisconnectedFromAreaServerReport.class))
		{
			PlayerDisconnectedFromAreaServerReport report = (PlayerDisconnectedFromAreaServerReport) arg;
			map.removePlayer(report.getPlayerID());
		}
		else if (arg.getClass().equals(ChangeMapReport.class))
		{
			map.setTiledMap(null);
		}
		else if (arg.getClass().equals(NewMapReport.class))
		{
			NewMapReport report = (NewMapReport) arg;
			map.setTiledMap(report.getTiledMap());
		}
		else if (arg.getClass().equals(ChangePlayerAppearanceReport.class))
		{
			ChangePlayerAppearanceReport report = (ChangePlayerAppearanceReport) arg;
			int playerID = report.getPlayerID();

			ClientPlayer player = ClientPlayerManager.getSingleton().getPlayerFromID(playerID);
			Position pos = player.getPosition();


			PlayerType type = PlayerType.valueOf(report.getAppearanceType());
			boolean curPlayer = map.getIsPlayerCharacter(playerID);

			map.removePlayer(playerID);
			map.addPlayer(playerID, type, pos, curPlayer);
		}
	}

	/**
	 * @see view.screen.ScreenListener#getReportTypes()
	 */
	@Override
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<>();
		reportTypes.add(PlayerConnectedToAreaServerReport.class);
		reportTypes.add(PlayerDisconnectedFromAreaServerReport.class);
		reportTypes.add(ClientPlayerMovedReport.class);
		reportTypes.add(NewMapReport.class);
		reportTypes.add(ChangeMapReport.class);
		reportTypes.add(PinFailedReport.class);
		reportTypes.add(ChangePlayerAppearanceReport.class);

		return reportTypes;
	}

}
