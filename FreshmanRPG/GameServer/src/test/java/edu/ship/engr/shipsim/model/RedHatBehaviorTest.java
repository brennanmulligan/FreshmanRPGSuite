package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.ChatMessageToClientReport;
import edu.ship.engr.shipsim.model.reports.PlayerFinishedInitializingReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetChatManager;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the RedHatBehavior
 *
 * @author Brad Olah
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetChatManager
@ResetReportObserverConnector
public class RedHatBehaviorTest
{
    private RedHatBehavior behavior;

    /**
     * @throws DatabaseException shouldn't Set up the behavior for each test
     */
    @BeforeEach
    public void localSetUp() throws DatabaseException
    {
        behavior = new RedHatBehavior(PlayersForTest.RED_HAT.getPlayerID());
    }

    /**
     * Tests that the sorting hat will send a message when a player logs in.
     * It will ask if they are ready to be sorted if they have not already.
     * It will tell them to do the introduction quest if they have been sorted.
     */
    @Test
    public void testHatRespondsToLogin()
    {

        Player p = PlayerManager.getSingleton().addPlayer(PlayersForTest.NEWBIE.getPlayerID());
        PlayerManager.getSingleton().addPlayer(PlayersForTest.RED_HAT.getPlayerID());

        MockReportObserver mock = new MockReportObserver(ChatMessageToClientReport.class);
        p.setAppearanceType("default_player");
        assertEquals("default_player", p.getAppearanceType());

        PlayerFinishedInitializingReport testConnection = new PlayerFinishedInitializingReport(p.getPlayerID(), p.getPlayerName(), p.getAppearanceType());
        behavior.receiveReport(testConnection);

        ChatMessageToClientReport report = (ChatMessageToClientReport) mock.getReport();
        assertEquals("Newbie welcome to the game. Are you ready to be sorted?", report.getChatText());

        p.setAppearanceType("male_a");
        testConnection = new PlayerFinishedInitializingReport(p.getPlayerID(), p.getPlayerName(), p.getAppearanceType());

        behavior.receiveReport(testConnection);
        report = (ChatMessageToClientReport) mock.getReport();
        assertEquals("Welcome back Newbie. Complete the introduction quest to leave this area.", report.getChatText());
    }

}
