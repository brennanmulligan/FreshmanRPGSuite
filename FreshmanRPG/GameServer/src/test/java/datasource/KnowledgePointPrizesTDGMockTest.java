package datasource;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.OptionsManager;

/**
 *
 * @author Andrew M, Christian C
 *
 * test for mock implementation
 *
 */
public class KnowledgePointPrizesTDGMockTest extends KnowledgePointPrizesTableDataGatewayTest
{

	// gateway instance
	private KnowledgePointPrizesTableDataGateway gateway;

	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}


	@Override
	public KnowledgePointPrizesTableDataGateway getGatewaySingleton() throws DatabaseException
	{
		return KnowledgePointPrizesTDGMock.getInstance();
	}
}
