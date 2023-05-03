package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.ChatMessageToClientReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

/**
 * @author merlin
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetReportObserverConnector
public class RandomFactsNPCBehaviorTest
{
    private RandomFactsNPCBehavior behavior;

    /**
     *
     */
    @BeforeEach
    public void localSetup() throws DatabaseException
    {
        behavior = new RandomFactsNPCBehavior(PlayersForTest.BIG_RED.getPlayerID());
        PlayerManager.getSingleton().addNpc(new NPC(PlayersForTest.BIG_RED.getPlayerID()));
        PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.BIG_RED.getPlayerID()).
                setPositionWithoutNotifying(PlayersForTest.BIG_RED.getPosition());
    }

    /**
     *
     */
    @Test
    public void canSpoutAFactEveryFiveSeconds()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a ChatMessageToClientReport is sent
        connector.registerObserver(observer, ChatMessageToClientReport.class);

        for (int i = 0; i < RandomFactsNPCBehavior.CHAT_DELAY_SECONDS * 100; i++)
        {
            behavior.doTimedEvent();
        }

        // since the above loop ends up sending 100 reports, make sure the observer was notified for all of them
        verify(observer, times(100)).receiveReport(any(ChatMessageToClientReport.class));
    }

}
