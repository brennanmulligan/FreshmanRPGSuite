package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.apache.commons.compress.utils.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * @author Ryan C and Adam W
 */
@GameTest("GameClient")
public class PlayerConnectedToAreaServerReportTest
{
    @Test
    public void testInitialization()
    {
        PlayersForTest player = PlayersForTest.MERLIN;

        List<VanityDTO> vanities = Lists.newArrayList();
        VanityDTO mockedVanity = mock(VanityDTO.class);
        vanities.add(mockedVanity);

        PlayerConnectedToAreaServerReport report = new PlayerConnectedToAreaServerReport(
                player.getPlayerID(),
                player.getPlayerName(),
                player.getPosition(),
                player.getCrew(),
                player.getMajor(),
                true,
                vanities
        );

        assertEquals(player.getPlayerID(), report.getPlayerID());
        assertEquals(player.getPlayerName(), report.getPlayerName());
        assertEquals(player.getPosition(), report.getPlayerPosition());
        assertEquals(player.getCrew(), report.getCrew());
        assertEquals(player.getMajor(), report.getMajor());
        assertTrue(report.isThisClientsPlayer());
        assertEquals(vanities, report.getVanities());
    }

    /**
     * Test for equals contract
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(PlayerConnectedToAreaServerReport.class).withRedefinedSuperclass().verify();
    }
}
