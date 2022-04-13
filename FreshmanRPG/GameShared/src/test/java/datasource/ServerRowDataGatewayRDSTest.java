package datasource;

/**
 * Test the AWS RDS data source
 *
 * @author Merlin
 *
 */
public class ServerRowDataGatewayRDSTest extends ServerRowDataGatewayTest
{
	/**
	 * @see datasource.ServerRowDataGatewayTest#createGateway(java.lang.String,
	 *      java.lang.String, int)
	 */
	@Override
	public ServerRowDataGateway createGateway(String mapName, String hostName, int port, String mapTitle, int teleportPositionX, int teleportPositionY) throws DatabaseException
	{
		return new ServerRowDataGatewayRDS(mapName, hostName, port, mapTitle, teleportPositionX, teleportPositionY);
	}

	/**
	 * @see datasource.ServerRowDataGatewayTest#findGateway(java.lang.String)
	 */
	@Override
	public ServerRowDataGateway findGateway(String mapName) throws DatabaseException
	{
		return ServerRowDataGatewayRDS.findServerInfoFromMapName(mapName);
	}
}
