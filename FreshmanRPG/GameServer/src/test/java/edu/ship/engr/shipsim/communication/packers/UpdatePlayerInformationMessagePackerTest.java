package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.InitializeThisClientsPlayerMessage;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.reports.UpdatePlayerInformationReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetQuestManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Andrew
 * @author Steve
 * @author Matt
 * @author Olivia
 * @author LaVonne
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetQuestManager
public class UpdatePlayerInformationMessagePackerTest
{
    private StateAccumulator stateAccumulator;

    /**
     * reset the necessary singletons and set up an accumulator that is
     * communicating with Merlin
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
     * Test that we pack a PlayerMovedReport
     */
    @Test
    public void testReportTypeWePack()
    {
        UpdatePlayerInformationMessagePacker packer = new UpdatePlayerInformationMessagePacker();
        assertEquals(UpdatePlayerInformationReport.class, packer.getReportTypesWePack().get(0));
    }

    /**
     * If the report is not about the player we are communicating with, we
     * should ignore it
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void ifItIsntAboutUsDontPack() throws DatabaseException
    {

        UpdatePlayerInformationMessagePacker packer = new UpdatePlayerInformationMessagePacker();
        packer.setAccumulator(stateAccumulator);

        UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(
                PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.JOHN.getPlayerID()));
        assertNull(packer.pack(report));
    }

    /**
     * the message should contain the appropriate quests and objectives
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testPackedObjectIsCurrentPlayer() throws DatabaseException
    {
        Player player = PlayerManager.getSingleton().getPlayerFromID(stateAccumulator.getPlayerID());
        UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(player);
        UpdatePlayerInformationMessagePacker packer = new UpdatePlayerInformationMessagePacker();
        packer.setAccumulator(stateAccumulator);

        InitializeThisClientsPlayerMessage message = (InitializeThisClientsPlayerMessage) packer.pack(report);
        List<ClientPlayerQuestStateDTO> expected = report.getClientPlayerQuestList();
        List<ClientPlayerQuestStateDTO> actual = message.getClientPlayerQuestList();
        assertEquals(expected.size(), actual.size());
        for (ClientPlayerQuestStateDTO a : actual)
        {
            assertTrue(expected.contains(a));
        }
    }

    /**
     * Tests that we can add experience pts and levelrecord to
     * InitializeThisClientsPlayerMessage message
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testPackExperiencePtsAndLevel() throws DatabaseException
    {
        Player player = PlayerManager.getSingleton().getPlayerFromID(stateAccumulator.getPlayerID());
        UpdatePlayerInformationReport report = new UpdatePlayerInformationReport(player);
        UpdatePlayerInformationMessagePacker packer = new UpdatePlayerInformationMessagePacker();
        packer.setAccumulator(stateAccumulator);

        InitializeThisClientsPlayerMessage message = (InitializeThisClientsPlayerMessage) packer.pack(report);
        assertEquals(report.getExperiencePoints(), message.getExperiencePts());
        assertEquals(report.getLevel(), message.getLevel());
    }
}
