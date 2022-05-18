package datasource;

public class DefaultItemsTableDataGatewayMockTest extends DefaultItemsTableDataGatewayTest
{
    @Override
    DefaultItemsTableDataGateway findGateway() throws DatabaseException
    {
        return (DefaultItemsTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
                "DefaultItems");
    }
}
