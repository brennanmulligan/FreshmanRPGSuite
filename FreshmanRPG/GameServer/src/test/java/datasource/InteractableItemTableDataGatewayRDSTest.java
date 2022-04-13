package datasource;

/**
 * Test for RDS
 * @author Jake Moore, Lis Ostrow
 */
public class InteractableItemTableDataGatewayRDSTest extends InteractableItemTableDataGatewayTest
{
	/**
	 * Gets singleton for test
	 */
	@Override
	public InteractableItemTableDataGateway getGatewaySingleton() throws DatabaseException
	{
		return InteractableItemTableDataGatewayRDS.getInstance();
	}

}
