package datasource;

/**
 * A class for testing the mock vanity awards table gateway
 */
public class VanityAwardsTableDataGatewayMockTest extends VanityAwardsTableDataGatewayTest
{
    /**
     * @return an instance of the mock vanity awards table
     * @throws DatabaseException
     */
    @Override
    VanityAwardsTableDataGateway findGateway() throws DatabaseException
    {
        return VanityAwardsTableDataGatewayMock.getSingleton();
    }
}
