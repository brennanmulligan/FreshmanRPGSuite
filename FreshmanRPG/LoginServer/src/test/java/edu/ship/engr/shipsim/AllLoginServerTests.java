package edu.ship.engr.shipsim;

import edu.ship.engr.shipsim.communication.handlers.LoginMessageHandlerTest;
import edu.ship.engr.shipsim.model.PlayerLoginTest;
import edu.ship.engr.shipsim.model.PlayerManagerTest;
import edu.ship.engr.shipsim.model.reports.LoginSuccessfulReportTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/**
 * @author Merlin
 */
@Suite
@SelectClasses(
        {
                // edu.ship.engr.shipsim.communication.handlers
                LoginMessageHandlerTest.class,

                // edu.ship.engr.shipsim.communication.packers

                // edu.ship.engr.shipsim.model
                PlayerLoginTest.class, PlayerManagerTest.class,

                // edu.ship.engr.shipsim.model.reports
                LoginSuccessfulReportTest.class,})
public class AllLoginServerTests
{

}
