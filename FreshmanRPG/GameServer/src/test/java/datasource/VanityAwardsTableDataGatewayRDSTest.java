package datasource;

public class VanityAwardsTableDataGatewayRDSTest extends VanityAwardsTableDataGatewayTest
{
    /**
     * Get a gateway for an existing row of the table
     * @return the gateway
     * @throws DatabaseException shouldnt
     */
    @Override
    VanityAwardsTableDataGateway findGateway() throws DatabaseException
    {
        return VanityAwardsTableDataGatewayRDS.getSingleton();
    }
}
