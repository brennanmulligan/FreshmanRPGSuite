package model;

import datatypes.PlayersForTest;
import model.reports.TerminalTextExitReport;
import model.terminal.CommandTerminalTextExit;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * Test to ensure our command sends the ExitReport upon execution
 *
 * Authors: John G. , Ian L.
 */
public class CommandTerminalTextExitTest
{

    /**
     * Setup the model facade for mock testing
     */
    @Before
    public void setup()
    {
        ModelFacade.resetSingleton();
        ModelFacade.getSingleton();
    }


    /**
     * Test executing a remove player command
     */
    @Test
    public void testExecution()
    {
        QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
        obs.receiveReport(EasyMock.isA(TerminalTextExitReport.class));
        QualifiedObservableConnector.getSingleton().registerObserver(obs,
                TerminalTextExitReport.class);
        EasyMock.replay(obs);

        CommandTerminalTextExit cmd = new CommandTerminalTextExit();
        cmd.execute(PlayersForTest.MERLIN.getPlayerID(), null);

        EasyMock.verify(obs);
    }



}
