package datasource;


/**
 * Test our RDS data source
 *
 * @author Merlin
 *
 */
public class PlayerConnectionRowDataGatewayRDSTest extends PlayerConnectionRowDataGatewayTest
{

	/**
	 * @see datasource.PlayerConnectionRowDataGatewayTest#findRowDataGateway(int)
	 */
	@Override
	PlayerConnectionRowDataGateway findRowDataGateway(int playerID) throws DatabaseException
	{
		return new PlayerConnectionRowDataGatewayRDS(playerID);
	}

}
