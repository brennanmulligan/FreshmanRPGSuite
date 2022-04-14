package datasource;

public class DefaultItemsTableDataGatewayRDSTest extends DefaultItemsTableDataGatewayTest
{
    /**
     * Get a gateway for an existing row of the table
     * @return the gateway
     * @throws DatabaseException shouldnt
     */
    @Override
    DefaultItemsTableDataGateway findGateway() throws DatabaseException
    {
        return DefaultItemsTableDataGatewayRDS.getSingleton();
    }
}
