package datasource;

/**
 * Tests the RDS Implementation
 *
 * @author merlin
 *
 */
public class ObjectiveStateTableDataGatewayRDSTest extends ObjectiveStateTableDataGatewayTest
{

	/**
	 * @see ObjectiveStateTableDataGatewayTest#getGateway()
	 */
	@Override
	public ObjectiveStateTableDataGateway getGateway()
	{
		return ObjectiveStateTableDataGatewayRDS.getSingleton();
	}

}
