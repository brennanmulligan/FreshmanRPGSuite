package datasource;

/**
 * Tests the RDS Implementation
 *
 * @author merlin
 *
 */
public class ObjectiveTableDataGatewayRDSTest extends ObjectiveTableDataGatewayTest
{

	/**
	 * @see ObjectiveTableDataGatewayTest#getGateway()
	 */
	@Override
	public ObjectiveTableDataGateway getGateway()
	{
		return ObjectiveTableDataGatewayRDS.getSingleton();
	}

}
