package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.ServerSideTest;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.ChatMessageReceivedReport;
import edu.ship.engr.shipsim.model.reports.NPCChatReport;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * @author Joshua Ktyal
 * <p>
 * Make sure that the RoamingInfoNPCBehavior acts as expected
 */
public class RoamingInfoNPCBehaviorTest extends ServerSideTest
{

    private RoamingInfoNPCBehavior behavior;
    private Player player;

    @Before
    public void localSetUp()
    {
        behavior =
                new RoamingInfoNPCBehavior(
                        PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID());

        player =
                PlayerManager.getSingleton().addPlayer(PlayersForTest.JOHN.getPlayerID());
        PlayerManager.getSingleton()
                .addPlayer(PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID());
        QualifiedObservableConnector.resetSingleton();
        ChatManager.resetSingleton();
    }

    /**
     * When a question is correctly answered, the player who got it right should
     * have their score incremented.
     */
    @Test
    public void testGetAResponse()
    {
        QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
        ArrayList<Capture<ChatMessageReceivedReport>> messages = new ArrayList<>();
        messages.add(new Capture<>());
        obs.receiveReport(EasyMock.and(EasyMock.capture(messages.get(0)),
                EasyMock.isA(ChatMessageReceivedReport.class)));
        QualifiedObservableConnector.getSingleton()
                .registerObserver(obs, ChatMessageReceivedReport.class);
        EasyMock.replay(obs);


        NPCChatReport report = new NPCChatReport(player.getPlayerID(), 0, "Heiddy ho, " +
                "neighborino", player.getPlayerPosition(), ChatType.Local);

        behavior.receiveReport(report);

        EasyMock.verify(obs);
        ArrayList<String> texts = new ArrayList<>();

        texts.add(messages.get(0).getValue().getChatText());
        assertEquals(texts.get(0),
                "Hi welcome to Mowrey! What would you like to know more about?");
    }
}
