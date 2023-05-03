package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.PlayerJoinedMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.reports.AddExistingPlayerReport;
import edu.ship.engr.shipsim.model.reports.PlayerConnectionReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Merlin
 */
@GameTest("GameServer")
@ResetPlayerManager
public class PlayerJoinedMessagePackerTest
{
    /**
     * Checks that existing players are notified when a player is added
     */
    @Test
    public void ifThePlayerIsNotOnThisConnection()
    {
        PlayerManager playerManager = PlayerManager.getSingleton();
        playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(PlayersForTest.MERLIN.getPlayerID());
        playerManager.addPlayer(PlayersForTest.JOHN.getPlayerID());

        Player playerFromID = playerManager.getPlayerFromID(PlayersForTest.JOHN.getPlayerID());
        PlayerConnectionReport report = new PlayerConnectionReport(playerFromID.getPlayerID(),
                playerFromID.getPlayerName(), playerFromID.getAppearanceType(), playerFromID.getPosition(),
                playerFromID.getCrew(), playerFromID.getMajor(), playerFromID.getSection(), playerFromID.getVanityItems());
        ServerPlayerJoinedMessagePacker packer = new ServerPlayerJoinedMessagePacker();
        packer.setAccumulator(stateAccumulator);
        PlayerJoinedMessage msg = (PlayerJoinedMessage) packer.pack(report);
        assertEquals(PlayersForTest.JOHN.getPlayerName(), msg.getPlayerName());


// Functionality was removed... will add it back later :)
//		String bodyID = report.getVanity().get(0).getTextureName();
//
//		assertEquals(bodyID, msg.getBodyID());
        assertEquals(PlayersForTest.JOHN.getPlayerID(), msg.getRelevantPlayerID());
        assertEquals(PlayersForTest.JOHN.getPosition(), msg.getPosition());
        assertEquals(PlayersForTest.JOHN.getCrew(), msg.getCrew());
        assertEquals(PlayersForTest.JOHN.getSection(), msg.getSection());
    }

    /**
     * When a player logs it, we get an AddExistingPlayerReport for each player
     * on the server. If the message is targeted at our accumulator, we should
     * pack a PlayerJoinedMessage
     */
    @Test
    public void addNotifiesAboutExistingPlayer()
    {
        PlayerManager playerManager = PlayerManager.getSingleton();
        playerManager.addPlayer(PlayersForTest.JOHN.getPlayerID());
        playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(PlayersForTest.MERLIN.getPlayerID());

        AddExistingPlayerReport report = new AddExistingPlayerReport(PlayersForTest.MERLIN.getPlayerID(),
                PlayersForTest.JOHN.getPlayerID(), PlayersForTest.JOHN.getPlayerName(),
                PlayersForTest.JOHN.getAppearanceType(), PlayersForTest.JOHN.getPosition(),
                PlayersForTest.JOHN.getCrew(), PlayersForTest.JOHN.getMajor(), PlayersForTest.JOHN.getSection(), new ArrayList<>());
        ServerPlayerJoinedMessagePacker packer = new ServerPlayerJoinedMessagePacker();
        packer.setAccumulator(stateAccumulator);
        PlayerJoinedMessage msg = (PlayerJoinedMessage) packer.pack(report);
        assertEquals(PlayersForTest.JOHN.getPlayerName(), msg.getPlayerName());
        assertEquals(PlayersForTest.JOHN.getPlayerID(), msg.getRelevantPlayerID());
        assertEquals(PlayersForTest.JOHN.getPosition(), msg.getPosition());
        assertEquals(PlayersForTest.JOHN.getCrew(), msg.getCrew());
        assertEquals(PlayersForTest.JOHN.getSection(), msg.getSection());

    }

    /**
     * Add existing player reports should only be sent by the accumulator that
     * is talking to the recipient player
     */
    @Test
    public void ignoresExistingPlayerWhenNotMine()
    {
        PlayerManager playerManager = PlayerManager.getSingleton();
        playerManager.addPlayer(PlayersForTest.JOHN.getPlayerID());
        playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());
        StateAccumulator stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(PlayersForTest.JOHN.getPlayerID());

        AddExistingPlayerReport report = new AddExistingPlayerReport(PlayersForTest.MERLIN.getPlayerID(),
                PlayersForTest.JOHN.getPlayerID(), PlayersForTest.JOHN.getPlayerName(),
                PlayersForTest.JOHN.getAppearanceType(), PlayersForTest.JOHN.getPosition(),
                PlayersForTest.JOHN.getCrew(), PlayersForTest.JOHN.getMajor(), PlayersForTest.JOHN.getSection(), new ArrayList<>());
        ServerPlayerJoinedMessagePacker packer = new ServerPlayerJoinedMessagePacker();
        packer.setAccumulator(stateAccumulator);
        PlayerJoinedMessage msg = (PlayerJoinedMessage) packer.pack(report);
        assertNull(msg);
    }
}
