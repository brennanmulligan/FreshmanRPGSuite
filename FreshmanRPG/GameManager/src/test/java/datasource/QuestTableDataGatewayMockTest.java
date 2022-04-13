package datasource;

import org.junit.Before;

import model.OptionsManager;

/**
 * Tests the functionality of QuestTableDataGatewayMock.
 */
public class QuestTableDataGatewayMockTest extends QuestTableDataGatewayTest
{

	/**
	 * @see datasource.QuestTableDataGatewayTest#setUp()
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * @see datasource.DatabaseTest#tearDown()
	 */
	@Before
	public void tearDown()
	{

	}

	/**
	 * @see datasource.QuestTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public QuestTableDataGateway getGatewaySingleton()
	{
		return QuestTableDataGatewayMock.getInstance();
	}

}
