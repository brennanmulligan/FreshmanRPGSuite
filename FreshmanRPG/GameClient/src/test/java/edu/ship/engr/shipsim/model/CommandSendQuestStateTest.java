package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.QuestStateReport;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Test the Command to send quest state to the view
 *
 * @author Merlin
 */
public class CommandSendQuestStateTest
{

    /**
     * Test that the command executes successfully
     * and does what it is supposed to do
     */
    @Test
    public void executeTest()
    {
        ThisClientsPlayer cp = ThisClientsPlayerTest.setUpThisClientsPlayer(PlayersForTest.JOHN);
        ClientPlayerQuestStateDTO q = ClientPlayerQuestTest.createOneQuestWithTwoObjectives();
        cp.addQuest(q);
        ArrayList<ClientPlayerQuestStateDTO> expected = new ArrayList<>();
        expected.add(q);

        QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
        QualifiedObservableConnector.getSingleton().registerObserver(obs, QuestStateReport.class);
        QuestStateReport report = new QuestStateReport(expected);
        obs.receiveReport(EasyMock.eq(report));
        EasyMock.replay(obs);

        CommandSendQuestState cmd = new CommandSendQuestState();
        cmd.execute();

        EasyMock.verify(obs);
    }
}
