package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.reports.PingWatchdogReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@GameTest("GameServer")
public class CommandPingWatchdogTest
{
    @Test
    public void testCommandPingWatchdog()
    {
        ReportObserver mock = mock(ReportObserver.class);
        ReportObserverConnector.getSingleton().registerObserver(mock, PingWatchdogReport.class);

        OptionsManager.getSingleton().setHostName("server1");

        CommandPingWatchdog command = new CommandPingWatchdog();

        command.execute();

        PingWatchdogReport expectedReport = new PingWatchdogReport("server1");
        verify(mock, times(1)).receiveReport(eq(expectedReport));
    }
}
