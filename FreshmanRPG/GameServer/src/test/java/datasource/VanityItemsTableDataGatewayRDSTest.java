package datasource;

/**
 * Test the vanityItemsTableDataGateway RDS
 */
public class VanityItemsTableDataGatewayRDSTest extends VanityItemsTableDataGatewayTest
{

    /**
     * Get a gateway for an existing row of the table
     * @return the gateway
     * @throws DatabaseException shouldnt
     */
    @Override
    VanityItemsTableDataGatewayInterface findGateway() throws DatabaseException
    {
        return VanityItemsTableDataGatewayRDS.getSingleton();
    }
}
