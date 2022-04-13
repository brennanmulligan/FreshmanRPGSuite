package datasource;

/**
 * @author merlin
 *
 */
public class RandomFactsTableDataGatewayRDSTest extends RandomFactsTableDataGatewayTest
{

	/**
	 * @see datasource.RandomFactsTableDataGatewayTest#getGateway()
	 */
	@Override
	public RandomFactsTableDataGateway getGateway()
	{
		return RandomFactsTableDataGatewayRDS.getSingleton();
	}

}
