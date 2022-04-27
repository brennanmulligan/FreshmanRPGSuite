package datasource;

/**
 * Returns a new mock gateway for testing
 * @author Aaron, Jake
 */
public class VanityShopTableDataGatewayMockTest extends VanityShopTableDataGatewayTest
{
    @Override
    VanityShopTableDataGateway findGateway() throws DatabaseException
    {
        return VanityShopTableDataGatewayMock.getSingleton();
    }
}
