package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.DoubloonsChangedMessage;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.QuestManager;
import edu.ship.engr.shipsim.model.reports.DoubloonChangeReport;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Matthew Croft
 */
public class DoubloonsChangedMessagePackerTest extends ServerSideTest
{
    private StateAccumulator stateAccumulator;

    /**
     * reset the necessary singletons
     */
    @Before
    public void localSetUp()
    {
        OptionsManager.getSingleton().setTestMode(true);
        QuestManager.resetSingleton();

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
