package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.DoubloonChangeReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

/**
 * @author Andrew Stake
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetReportObserverConnector
public class CommandItemPurchasedTest
{
    /**
     * Makes sure the points are deducted from the player
     */
    @Test
    public void testPointDeduction()
    {
        int startingScore = 100;
        int price = 50;

        PlayerManager.getSingleton().addPlayer(1);
        Player p = PlayerManager.getSingleton().getPlayerFromID(1);
        p.setDoubloons(startingScore);

        assertEquals(startingScore, p.getQuizScore());

        CommandItemPurchased cmd = new CommandItemPurchased(1, price);
        cmd.execute();

        assertEquals(startingScore - price, p.getQuizScore());
    }

    /**
     * Tests that this command will trigger a report to be generated in player
     */
    @Test
    public void testNotifyObservers()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a DoubloonChangeReport is sent
        connector.registerObserver(observer, DoubloonChangeReport.class);

        PlayersForTest testPlayer = PlayersForTest.MERLIN;

        PlayerManager.getSingleton().addPlayer(testPlayer.getPlayerID());

        CommandItemPurchased commandItemPurchased = new CommandItemPurchased(testPlayer.getPlayerID(), 50);
        commandItemPurchased.execute();

        verify(observer, times(1)).receiveReport(any(DoubloonChangeReport.class));
    }
}
