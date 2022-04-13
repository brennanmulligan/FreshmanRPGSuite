package datasource;

import static org.junit.Assert.*;

import org.junit.Test;

public class FriendTableDataGatewayRDSTest extends FriendTableDataGatewayTest
{

	@Override
	public FriendTableDataGateway getGatewaySingleton() throws DatabaseException
	{
		return FriendTableDataGatewayRDS.getInstance();
	}
}