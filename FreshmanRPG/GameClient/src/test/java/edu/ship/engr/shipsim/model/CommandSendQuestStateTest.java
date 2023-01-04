package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestTest;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.QuestStateReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

/**
 * Test the Command to send quest state to the view
 *
 * @author Merlin
 */
@GameTest("GameClient")
@ResetReportObserverConnector
public class CommandSendQuestStateTest
{

    /**
     * Test that the command executes successfully
     * and does what it is supposed to do
     */
    @Test
    public void executeTest()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a QuestStateReport is sent
        connector.registerObserver(observer, QuestStateReport.class);

        ThisClientsPlayer cp = ThisClientsPlayerTest.setUpThisClientsPlayer(PlayersForTest.JOHN);
        ClientPlayerQuestStateDTO q = ClientPlayerQuestTest.createOneQuestWithTwoObjectives();
        cp.addQuest(q);
        ArrayList<ClientPlayerQuestStateDTO> expected = new ArrayList<>();
        expected.add(q);

        // set up the report for verification
        QuestStateReport expectedReport = new QuestStateReport(expected);

        // set up the command and execute it
        CommandSendQuestState cmd = new CommandSendQuestState();
        cmd.execute();

        // since the command sends a QuestStateReport, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }
}
