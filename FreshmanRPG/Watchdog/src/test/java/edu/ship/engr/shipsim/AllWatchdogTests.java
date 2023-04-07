package edu.ship.engr.shipsim;

import edu.ship.engr.shipsim.communication.handlers.PingWatchdogMessageHandlerTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        // communication.handlers
        PingWatchdogMessageHandlerTest.class,

        // base package
        TestWatchdog.class
})
public class AllWatchdogTests
{
}
