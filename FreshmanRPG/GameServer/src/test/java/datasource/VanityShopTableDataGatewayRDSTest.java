package datasource;

/**
 * Gets a new instance of the RDS database
 */
public class VanityShopTableDataGatewayRDSTest extends VanityShopTableDataGatewayTest
{
    /**
     * @return instance of RDS gateway
     * @throws DatabaseException shouldnt
     */
    @Override
    VanityShopTableDataGateway findGateway() throws DatabaseException
    {
        return VanityShopTableDataGatewayRDS.getSingleton();
    }
}
