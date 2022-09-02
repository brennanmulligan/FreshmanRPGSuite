package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.model.reports.DoubloonChangeReport;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author Andrew Stake
 */
public class CommandItemPurchasedTest extends ServerSideTest
{
    /**
     * Reset PlayerManager
     */
    @Before
    public void localSetup()
    {
        PlayerManager.resetSingleton();
        QualifiedObservableConnector.resetSingleton();

        OptionsManager.getSingleton().setTestMode(true);
    }

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
        PlayerManager.getSingleton().addPlayer(4);
        QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
        QualifiedObservableConnector.getSingleton().registerObserver(obs, DoubloonChangeReport.class);
        obs.receiveReport(EasyMock.anyObject(DoubloonChangeReport.class));
        EasyMock.replay(obs);

        CommandItemPurchased cmd = new CommandItemPurchased(4, 50);
        cmd.execute();

        EasyMock.verify(obs);
    }

    /**
     * Tests that the command reports false when an invalid player id is used
     */
    @Test
    public void testInvalidPlayer()
    {
        int price = 50;

        CommandItemPurchased cmd = new CommandItemPurchased(-1, price);
        assertFalse(cmd.execute());
    }

}
