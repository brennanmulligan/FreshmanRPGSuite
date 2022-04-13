package datasource;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;

/**
 *
 *
 */
public abstract class VisitedMapsGatewayTest extends DatabaseTest
{
	protected VisitedMapsGateway gateway;

	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
		gateway = getGateway();
	}

	/**
	 * @see datasource.DatabaseTest#tearDown()
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
	}

	/**
	 * @return the gateway
	 */
	public abstract VisitedMapsGateway getGateway();


}
