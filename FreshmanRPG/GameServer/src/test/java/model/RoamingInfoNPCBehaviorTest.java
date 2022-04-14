package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dataDTO.RandomFactDTO;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datatypes.ChatType;
import datatypes.Position;
import model.reports.ChatMessageReceivedReport;
import datatypes.PlayersForTest;

import java.util.ArrayList;

/**
 * @author Joshua Ktyal
 *
 *         Make sure that the RoamingInfoNPCBehavior acts as expected
 */
public class RoamingInfoNPCBehaviorTest
{

    private RoamingInfoNPCBehavior behavior;
    private Player player;

    /**
     * @throws DatabaseException shouldn't Set up the behavior and a question
     *                           for each test
     */
    @Before
    public void setUp() throws DatabaseException
    {
        OptionsManager.getSingleton().setUsingMocKDataSource(true);
        behavior = new RoamingInfoNPCBehavior(PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID());

        player = PlayerManager.getSingleton().addPlayer(PlayersForTest.JOHN.getPlayerID());

        PlayerManager.getSingleton().addPlayer(PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID());

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
        obs.receiveReport(EasyMock.and(EasyMock.capture(messages.get(0)),EasyMock.isA(ChatMessageReceivedReport.class)));
        QualifiedObservableConnector.getSingleton().registerObserver(obs, ChatMessageReceivedReport.class);
        EasyMock.replay(obs);


        ChatMessageReceivedReport report = new ChatMessageReceivedReport(player.getPlayerID(), 0, "Heiddy ho, neighborino", player.getPlayerPosition(), ChatType.Local);

        behavior.receiveReport(report);

        EasyMock.verify(obs);
        ArrayList<String> texts = new ArrayList<>();

        texts.add(messages.get(0).getValue().getChatText());
        assertEquals(texts.get(0), "Hi welcome to Mowrey! What would you like to know more about?");
    }
}
