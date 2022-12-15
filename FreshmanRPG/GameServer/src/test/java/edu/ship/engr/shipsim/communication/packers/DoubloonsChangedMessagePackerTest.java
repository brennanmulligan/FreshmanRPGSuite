package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.DoubloonsChangedMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.reports.DoubloonChangeReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Matthew Croft
 */
@GameTest("GameServer")
@ResetQuestManager
public class DoubloonsChangedMessagePackerTest
{
    private StateAccumulator stateAccumulator;

    /**
     * reset the necessary singletons
     */
    @BeforeEach
    public void localSetUp()
    {
        PlayerManager playerManager = PlayerManager.getSingleton();
        playerManager.addPlayer(PlayersForTest.JOHN.getPlayerID());
        playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());
        stateAccumulator = new StateAccumulator(null);
        stateAccumulator.setPlayerId(PlayersForTest.MERLIN.getPlayerID());
    }

    /**
     *
     */
    @Test
    public void testReportWePack()
    {
        DoubloonChangePacker packer = new DoubloonChangePacker();

        assertEquals(DoubloonChangeReport.class, packer.getReportTypesWePack().get(0));
    }

    /**
     * the message should contain the appropriate player's ID, experience points
     * and level object
     */
    @Test
    public void testPackedObjectIsCurrentPlayer()
    {
        DoubloonChangeReport report = new DoubloonChangeReport(PlayersForTest.MERLIN.getPlayerID(),
                PlayersForTest.MERLIN.getDoubloons(), PlayersForTest.MERLIN.getBuffPool());
        DoubloonChangePacker packer = new DoubloonChangePacker();
        packer.setAccumulator(stateAccumulator);

        DoubloonsChangedMessage msg = (DoubloonsChangedMessage) packer.pack(report);

        assertEquals(PlayersForTest.MERLIN.getDoubloons(), msg.getDoubloons());
    }

    /**
     * If the report is not about the player we are communicating with, we
     * should ignore it
     */
    @Test
    public void ifItIsntAboutUsDontPack()
    {

        DoubloonChangePacker packer = new DoubloonChangePacker();
        packer.setAccumulator(stateAccumulator);

        DoubloonChangeReport report = new DoubloonChangeReport(PlayersForTest.JOHN.getPlayerID(),
                PlayersForTest.JOHN.getDoubloons(), PlayersForTest.MERLIN.getBuffPool());
        assertNull(packer.pack(report));
    }
}
