package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.MapFileMessage;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.model.reports.PlayerConnectionReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Merlin
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetReportObserverConnector
public class MapFileMessagePackerTest
{
    /**
     * If we are notified about a player other than the one we are associated
     * with, we shouldn't pack a message
     */
    @Test
    public void ifThePlayerIsNotOnThisConnection()
    {
        PlayerManager.getSingleton().addPlayer(1);
        PlayerManager.getSingleton().addPlayer(2);
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(1);

        Player playerFromID = PlayerManager.getSingleton().getPlayerFromID(2);
        PlayerConnectionReport report = new PlayerConnectionReport(playerFromID.getPlayerID(),
                playerFromID.getPlayerName(), playerFromID.getAppearanceType(), playerFromID.getPosition(),
                playerFromID.getCrew(), playerFromID.getMajor(), playerFromID.getSection(), playerFromID.getVanityItems());
        MapFileMessagePacker packer = new MapFileMessagePacker();
        packer.setAccumulator(stateAccumulator);
        MapFileMessage msg = (MapFileMessage) packer.pack(report);
        assertNull(msg);
    }

    /**
     * If we are notified about a player that is the one we are associated with,
     * return null (no need to send this on)
     *
     * @throws DatabaseException           shouldn't
     * @throws IllegalQuestChangeException the state changed illegally
     */
    @Test
    public void ifThePlayerIsOnThisConnection() throws DatabaseException, IllegalQuestChangeException
    {
        OptionsManager.getSingleton().updateMapInformation("quad.tmx", "", 1);
        PlayerManager.getSingleton().addPlayer(1, PlayerConnection.DEFAULT_PIN);
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(1);
        MapFileMessagePacker packer = new MapFileMessagePacker();
        packer.setAccumulator(stateAccumulator);

        Player playerFromID = PlayerManager.getSingleton().getPlayerFromID(1);
        PlayerConnectionReport report = new PlayerConnectionReport(playerFromID.getPlayerID(),
                playerFromID.getPlayerName(), playerFromID.getAppearanceType(), playerFromID.getPosition(),
                playerFromID.getCrew(), playerFromID.getMajor(), playerFromID.getSection(), playerFromID.getVanityItems());
        MapFileMessage msg = (MapFileMessage) packer.pack(report);
        assertEquals("quad.tmx", msg.getMapFileName());
        OptionsManager.resetSingleton();
    }

}
