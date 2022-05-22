package datasource;

/**
 * @author Mina Kindo, Christian Chroutamel
 *
 */
public class DoubloonPrizesTableDataGatewayRDSTest extends DoubloonPrizesTableDataGatewayTest
{

	@Override
	public DoubloonPrizesTableDataGateway getGatewaySingleton() throws DatabaseException
	{
		return (DoubloonPrizesTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
				"DoubloonPrizes");
	}

}
