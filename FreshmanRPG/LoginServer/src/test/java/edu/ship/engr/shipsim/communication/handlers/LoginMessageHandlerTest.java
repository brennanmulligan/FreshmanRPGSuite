package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.StateAccumulator;
import edu.ship.engr.shipsim.communication.messages.LoginMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.LoginPlayerManager;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.model.ReportObserver;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.reports.LoginFailedReport;
import edu.ship.engr.shipsim.model.reports.LoginSuccessfulReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Merlin
 */
@GameTest("LoginServer")
@ResetReportObserverConnector
public class LoginMessageHandlerTest
{

    /**
     * reset the singleton before each test
     */
    @BeforeEach
    public void setup()
    {
        OptionsManager.resetSingleton();
        LoginPlayerManager.resetSingleton();
    }

    /**
     *
     */
    @Test
    public void tellsTheModel()
    {
        LoginMessageHandler handler = new LoginMessageHandler();
        StateAccumulator accum = new StateAccumulator(null);
        handler.setAccumulator(accum);
        handler.process(
                new LoginMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerName(), PlayersForTest.MERLIN_OFFLINE.getPlayerPassword(), false));
        assertEquals(1, LoginPlayerManager.getSingleton().getNumberOfPlayers());
    }

    @Test
    public void testSendsSuccessReport()
    {
        LoginMessageHandler handler = new LoginMessageHandler();

        ReportObserver observer = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(observer, LoginSuccessfulReport.class);

        LoginMessage loginMessage = new LoginMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerName(), PlayersForTest.MERLIN_OFFLINE.getPlayerPassword(), false);
        handler.process(loginMessage);

        verify(observer, times(1)).receiveReport(any(LoginSuccessfulReport.class));
    }

    @Test
    public void testSendsFailureReport()
    {
        LoginMessageHandler handler = new LoginMessageHandler();

        ReportObserver observer = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(observer, LoginFailedReport.class);

        LoginMessage loginMessage = new LoginMessage(PlayersForTest.MERLIN_OFFLINE.getPlayerName(), PlayersForTest.MERLIN_OFFLINE.getPlayerPassword() + "Z", false);
        handler.process(loginMessage);

        verify(observer, times(1)).receiveReport(any(LoginFailedReport.class));
    }
}
