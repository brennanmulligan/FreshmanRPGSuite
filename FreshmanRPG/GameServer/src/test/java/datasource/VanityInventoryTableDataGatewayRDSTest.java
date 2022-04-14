package datasource;

/**
 * Test the vanityInventoryTableDataGateway RDS
 */
public class VanityInventoryTableDataGatewayRDSTest extends VanityInventoryTableDataGatewayTest
{
    /**
     * Get a gateway for an existing row of the table
     * @return the gateway
     * @throws DatabaseException shouldnt
     */
    @Override
    VanityInventoryTableDataGatewayInterface findGateway() throws DatabaseException
    {
        return VanityInventoryTableDataGatewayRDS.getSingleton();
    }
}
