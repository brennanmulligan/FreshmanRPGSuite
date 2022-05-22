package datasource;

/**
 * Tests the mock implementation
 *
 * @author merlin
 *
 */
public class ObjectiveTableDataGatewayMockTest extends ObjectiveTableDataGatewayTest
{

	/**
	 * @see ObjectiveTableDataGatewayTest#getGateway()
	 */
	@Override
	public ObjectiveTableDataGateway getGateway()
	{
		return (ObjectiveTableDataGateway) TableDataGatewayManager.getSingleton().getTableGateway(
				"Objective");
	}

}
