package datasource;

/**
 * Test our mock behavior
 */
public class VanityInventoryTableDataGatewayMockTest extends VanityInventoryTableDataGatewayTest
{
    /**
     * Gets a new mock gateway for the vanityInventory table
     * @return the gateway
     * @throws DatabaseException shouldnt
     */
    @Override
    VanityInventoryTableDataGatewayInterface findGateway() throws DatabaseException
    {
        return VanityInventoryTableDataGatewayMock.getSingleton();
    }
}
