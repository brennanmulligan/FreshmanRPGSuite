package datasource;

/**
 * Tests the functionality of QuestTableDataGatewayRDS.
 */
public class QuestTableDataGatewayRDSTest extends QuestTableDataGatewayTest
{

	/**
	 * @see datasource.QuestTableDataGatewayTest#getGatewaySingleton()
	 */
	@Override
	public QuestTableDataGateway getGatewaySingleton()
	{
		return QuestTableDataGatewayRDS.getInstance();
	}

}
