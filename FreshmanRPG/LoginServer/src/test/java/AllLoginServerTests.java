import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import communication.handlers.LoginMessageHandlerTest;
import model.PlayerLoginTest;
import model.PlayerManagerTest;
import model.reports.LoginSuccessfulReportTest;

/**
 * @author Merlin
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
		{
				// communication.handlers
				LoginMessageHandlerTest.class,

				// communication.packers

				// model
				PlayerLoginTest.class, PlayerManagerTest.class,

				// model.reports
				LoginSuccessfulReportTest.class,})
public class AllLoginServerTests
{

}
