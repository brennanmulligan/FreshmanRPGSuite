package edu.ship.engr.shipsim.model.cheatCodeBehaviors;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.Player;
import edu.ship.engr.shipsim.model.PlayerManager;
import edu.ship.engr.shipsim.model.ReportObserver;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.reports.InteractableObjectBuffReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests the cheat code behavior that gives a player more buff
 *
 * @author merlin
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetReportObserverConnector
public class BuffBehaviorTest
{
    /**
     * The player should get the buff if they have typed in the correct chat message
     */
    @Test
    public void testGivesOnCorrectMessage()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if an InteractableObjectBuffReport is sent
        connector.registerObserver(observer, InteractableObjectBuffReport.class);

        // add a player to the manager and set its buff pool to 0
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setBuffPool(0);

        // set up the behavior and give it a cheat
        // that can send out an InteractableObjectBuffReport
        BuffBehavior behavior = new BuffBehavior();
        behavior.giveCheat(PlayersForTest.MERLIN.getPlayerID(), BuffBehavior.text);

        // verify that the player was given the buff
        assertEquals(BuffBehavior.BUFF_VALUE, player.getBuffPool());

        // set up the report to expect
        InteractableObjectBuffReport expectedReport = new InteractableObjectBuffReport(PlayersForTest.MERLIN.getPlayerID(), BuffBehavior.BUFF_VALUE);

        // verify that the observer received an InteractableObjectBuffReport
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * If they have existing buff, their buff should max at the cheat code's value
     */
    @Test
    public void testAddBuffOnCorrectMessage()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if an InteractableObjectBuffReport is sent
        connector.registerObserver(observer, InteractableObjectBuffReport.class);

        // add a player to the manager and set its buff pool to 0
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setBuffPool(3);

        // set up the report to expect
        // since the player's buff pool is 3, the amount added would be the base value - 3
        InteractableObjectBuffReport expectedReport = new InteractableObjectBuffReport(player.getPlayerID(), BuffBehavior.BUFF_VALUE - 3);

        // set up the behavior and give it a cheat
        // that can send out an InteractableObjectBuffReport
        BuffBehavior behavior = new BuffBehavior();
        behavior.giveCheat(player.getPlayerID(), BuffBehavior.text);

        // verify that the player is maxed at the buff value
        assertEquals(BuffBehavior.BUFF_VALUE, player.getBuffPool());

        // verify that the observer received an InteractableObjectBuffReport
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }

    /**
     * If they have existing buff that is more than the cheat code's value , their
     * buff should be unchanged
     */
    @Test
    public void testDontOverMaxBuffOnCorrectMessage()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if an InteractableObjectBuffReport is sent
        connector.registerObserver(observer, InteractableObjectBuffReport.class);

        // add a player to the manager and set its buff pool to 0
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setBuffPool(BuffBehavior.BUFF_VALUE + 3);

        // set up the behavior and give it a cheat
        // that can send out an InteractableObjectBuffReport
        BuffBehavior behavior = new BuffBehavior();
        behavior.giveCheat(player.getPlayerID(), BuffBehavior.text);

        // verify that the player is still at their original buff value
        assertEquals(BuffBehavior.BUFF_VALUE + 3, player.getBuffPool());

        // verify that the observer never received an InteractableObjectBuffReport
        verify(observer, never()).receiveReport(any(InteractableObjectBuffReport.class));
    }

    /**
     * If they typed in the wrong value, nothing should happen
     */
    @Test
    public void testDoesntGiveBuffOnIncorrectMessage()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if an InteractableObjectBuffReport is sent
        connector.registerObserver(observer, InteractableObjectBuffReport.class);

        // add a player to the manager and set its buff pool to 0
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setBuffPool(3);

        // set up the behavior and give it a cheat
        // that can send out an InteractableObjectBuffReport
        BuffBehavior behavior = new BuffBehavior();
        behavior.giveCheat(player.getPlayerID(), BuffBehavior.text + "z");

        // verify that the player is maxed at the buff value
        assertEquals(3, player.getBuffPool());

        // verify that the observer never received an InteractableObjectBuffReport
        verify(observer, never()).receiveReport(any(InteractableObjectBuffReport.class));
    }
}
