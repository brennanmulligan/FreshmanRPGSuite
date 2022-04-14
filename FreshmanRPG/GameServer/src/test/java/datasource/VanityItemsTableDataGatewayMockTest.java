package datasource;

/**
 * Test our mock behavior
 */
public class VanityItemsTableDataGatewayMockTest extends VanityItemsTableDataGatewayTest
{
    /**
     * Gets a new mock gateway for the VanityItems table
     * @return the gateway
     * @throws DatabaseException shouldnt
     */
    @Override
    VanityItemsTableDataGatewayInterface findGateway() throws DatabaseException
    {
        return VanityItemsTableDataGatewayMock.getSingleton();
    }
}
