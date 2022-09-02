package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.ExperienceChangedMessage;
import edu.ship.engr.shipsim.dataDTO.LevelManagerDTO;
import edu.ship.engr.shipsim.datasource.LevelRecord;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.QuestManager;
import edu.ship.engr.shipsim.model.reports.ExperienceChangedReport;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Ryan
 * <p>
 * Make sure that the ExperienceChangedMessagePacker behaves properly.
 */
public class ExperienceChangedMessagePackerTest extends ServerSideTest
{
    private StateAccumulator stateAccumulator;

    /**
     * reset the necessary singletons
     */
    @Before
    public void localSetUp()
    {
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
        ExperienceChangedMessagePacker packer = new ExperienceChangedMessagePacker();

        assertEquals(ExperienceChangedReport.class, packer.getReportTypesWePack().get(0));
    }

    /**
     * the message should contain the appropriate player's ID, experience points
     * and level object
     */
    @Test
    public void testPackedObjectIsCurrentPlayer()
    {
        LevelRecord record = LevelManagerDTO.getSingleton().getLevelForPoints(PlayersForTest.MERLIN.getExperiencePoints());
        ExperienceChangedReport report = new ExperienceChangedReport(PlayersForTest.MERLIN.getPlayerID(),
                PlayersForTest.MERLIN.getExperiencePoints(), record);
        ExperienceChangedMessagePacker packer = new ExperienceChangedMessagePacker();
        packer.setAccumulator(stateAccumulator);

        ExperienceChangedMessage msg = (ExperienceChangedMessage) packer.pack(report);

        assertEquals(PlayersForTest.MERLIN.getExperiencePoints(), msg.getExperiencePoints());
        assertEquals(record, msg.getLevel());
    }

    /**
     * If the report is not about the player we are communicating with, we
     * should ignore it
     */
    @Test
    public void ifItIsntAboutUsDontPack()
    {

        ExperienceChangedMessagePacker packer = new ExperienceChangedMessagePacker();
        packer.setAccumulator(stateAccumulator);

        ExperienceChangedReport report = new ExperienceChangedReport(PlayersForTest.JOHN.getPlayerID(),
                PlayersForTest.JOHN.getExperiencePoints(),
                LevelManagerDTO.getSingleton().getLevelForPoints(PlayersForTest.JOHN.getExperiencePoints()));
        assertNull(packer.pack(report));
    }

}
