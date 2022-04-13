package datasource;

import org.junit.Before;

/**
 * @author Robert Windsich
 *
 * a class to test the RDS NPCQuestion table data gateway
 *
 */
public class NPCQuestionTableDataGatewayRDSTest extends NPCQuestionTableDataGatewayTest
{

	/**
	 * @throws DatabaseException shouldn't
	 * @see datasource.NPCQuestionTableDataGatewayTest#setUp()
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		super.setUp();
	}

	/**
	 * @see datasource.NPCQuestionTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public NPCQuestionTableDataGateway getGatewaySingleton()
	{

		return NPCQuestionTableDataGatewayRDS.getSingleton();
	}
}
