package edu.ship.engr.shipsim.view.screen.map;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.ClientPlayer;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.ReportObserver;
import edu.ship.engr.shipsim.model.reports.*;
import edu.ship.engr.shipsim.view.screen.ScreenListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Not much for now!
 *
 * @author Merlin
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
     * @see ReportObserver#receiveReport(Report)
     * added an if statement that only allows the playersConnectToAreaServerReport to get that Clients Player, not a new player
     */
    @Override
    public void receiveReport(Report arg)
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
            List<VanityDTO> vanities = report.getVanities();

            Position pos = report.getPlayerPosition();

            map.addPlayer(report.getPlayerID(), vanities, pos, report.isThisClientsPlayer());
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

            List<VanityDTO> vanities = report.getVanities();
            boolean curPlayer = map.getIsPlayerCharacter(playerID);

            map.removePlayer(playerID);
            map.addPlayer(playerID, vanities, pos, curPlayer);
        }
    }

    /**
     * @see ScreenListener#getReportTypes()
     */
    @Override
    public ArrayList<Class<? extends Report>> getReportTypes()
    {
        ArrayList<Class<? extends Report>> reportTypes = new ArrayList<>();
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
