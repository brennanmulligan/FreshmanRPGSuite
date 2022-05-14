package model;

import datasource.DatabaseException;
import datatypes.ChatType;
import datatypes.PlayersForTest;
import model.reports.ChatMessageReceivedReport;
import model.reports.NPCChatReport;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MowreyInfoNPCBehaviorTest
{

    private InfoNPCBehavior behavior;


    @Before
    public void setUp() throws DatabaseException
    {
        OptionsManager.getSingleton().setUsingMocKDataSource(true);
        behavior = new InfoNPCBehavior(PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID(), " ");
        QualifiedObservableConnector.resetSingleton();
        ChatManager.resetSingleton();
    }

    @Test
    public void testConstructor()
    {
        assertEquals(behavior.playerID, PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID());
    }

    @Test
    public void testRecieveReport()
    {
        Player p = PlayerManager.getSingleton().addPlayer(PlayersForTest.NICK.getPlayerID());
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID());

        PlayerManager.getSingleton().addPlayer(1);
        QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
        QualifiedObservableConnector.getSingleton().registerObserver(obs, ChatMessageReceivedReport.class);
        obs.receiveReport(EasyMock.anyObject(NPCChatReport.class));
        EasyMock.replay(obs);

        NPCChatReport report = new NPCChatReport(p.getPlayerID(), 0 , "Hello, Mowrey",
                PlayersForTest.NICK.getPosition(), ChatType.Local);
        behavior.receiveReport(report);

        EasyMock.verify(obs);
    }



}
