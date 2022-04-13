package datasource;

/**
 * @author merlin
 *
 */
public class RandomFactsTableDataGatewayMockTest extends RandomFactsTableDataGatewayTest
{

	/**
	 * @see datasource.RandomFactsTableDataGatewayTest#getGateway()
	 */
	@Override
	public RandomFactsTableDataGateway getGateway()
	{
		return RandomFactsTableDataGatewayMock.getSingleton();
	}

}
