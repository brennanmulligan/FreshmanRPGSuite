package datasource;

import org.junit.Before;

import model.OptionsManager;

/**
 * @author Robert Windisch
 *
 *         a Test Class to test the Mock NPCQuestion table data gateway
 *
 */
public class NPCQuestionTableDataGatewayMockTest extends NPCQuestionTableDataGatewayTest
{

	/**
	 * @see datasource.NPCQuestionTableDataGatewayTest#setUp()
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingTestDB(false);
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * @see datasource.NPCQuestionTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public NPCQuestionTableDataGatewayMock getGatewaySingleton()
	{

		return NPCQuestionTableDataGatewayMock.getSingleton();
	}

}
