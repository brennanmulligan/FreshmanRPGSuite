package datasource;

import org.junit.After;
import org.junit.Before;

import model.OptionsManager;

/**
 * Mock test
 *
 * @author Jake Moore, Elisabeth Ostrow
 */
public class InteractableItemTableDataGatewayMockTest extends InteractableItemTableDataGatewayTest
{

	/**
	 * @see datasource.DatabaseTest#setUp()
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * @see datasource.DatabaseTest#tearDown()
	 */
	@After
	public void tearDown()
	{

	}

	/**
	 * Gets the correct singleton
	 */
	@Override
	public InteractableItemTableDataGateway getGatewaySingleton()
	{
		return InteractableItemTableDataGatewayMock.getInstance();
	}

}
