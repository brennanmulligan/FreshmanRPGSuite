package datasource;

/**
 * @author Mina Kindo, Christian Chroutamel
 *
 */
public class DoubloonPrizesTableDataGatewayRDSTest extends DoubloonPrizesTableDataGatewayTest
{

	@Override
	/**
	 * @throws DatabaseException
	 */
	public DoubloonPrizesTableDataGateway getGatewaySingleton() throws DatabaseException
	{
		return DoubloonPrizesTableDataGatewayRDS.getInstance();
	}

}
