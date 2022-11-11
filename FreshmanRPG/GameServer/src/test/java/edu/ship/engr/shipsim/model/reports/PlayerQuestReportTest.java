package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.QuestTestUtilities;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @author Derek
 */
@GameTest("GameServer")
public class PlayerQuestReportTest
{
    @Test
    public void testInitialization()
    {
        Player merlin = PlayerManager.getSingleton().addPlayerSilently(PlayersForTest.MERLIN.getPlayerID());
        List<ClientPlayerQuestStateDTO> questsForTest = QuestTestUtilities.getQuestsForTest();

        PlayerQuestReport report = new PlayerQuestReport(merlin, questsForTest);

        assertEquals(merlin, report.getPlayer());
        assertEquals(questsForTest, report.getQuests());
    }

    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(PlayerQuestReport.class)
                .usingGetClass() // use report.getClass().equals(other.getClass()) instead of instanceof
                .suppress(Warning.REFERENCE_EQUALITY) // suppress EqualsVerifier warning for using == instead of .equals()
                .withPrefabValues(Player.class, mock(Player.class), mock(Player.class))
                .verify();
    }
}
