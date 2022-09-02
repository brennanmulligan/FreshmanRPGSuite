package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.RandomFactDTO;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.ChatMessageReceivedReport;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * @author merlin
 */
public class RandomFactsNPCBehaviorTest extends ServerSideTest
{
    private RandomFactsNPCBehavior behavior;

    /**
     *
     */
    @Before
    public void localSetup()
    {
        behavior = new RandomFactsNPCBehavior(PlayersForTest.BIG_RED.getPlayerID());
        PlayerManager.getSingleton().addNpc(new NPC(PlayersForTest.BIG_RED.getPlayerID()));
        PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.BIG_RED.getPlayerID()).
                setPlayerPositionWithoutNotifying(PlayersForTest.BIG_RED.getPosition());
    }

    /**
     *
     */
    @Test
    public void canSpoutAFactEveryFiveSeconds()
    {
        QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
        ArrayList<Capture<ChatMessageReceivedReport>> messages = new ArrayList<>();
        for (int i = 0; i < 100; i++)
        {
            messages.add(new Capture<>());
            obs.receiveReport(EasyMock.and(EasyMock.capture(messages.get(i)), EasyMock.isA(ChatMessageReceivedReport.class)));
        }
        QualifiedObservableConnector.getSingleton().registerObserver(obs, ChatMessageReceivedReport.class);
        EasyMock.replay(obs);

        for (int i = 0; i < RandomFactsNPCBehavior.CHAT_DELAY_SECONDS * 100; i++)
        {
            behavior.doTimedEvent();
        }

        EasyMock.verify(obs);
        ArrayList<String> texts = new ArrayList<>();
        for (Capture<ChatMessageReceivedReport> report : messages)
        {
            texts.add(report.getValue().getChatText());
        }
        for (RandomFactDTO x : behavior.getFacts())
        {
            assertTrue(texts.contains(x.getFactText()));
        }
    }

}
