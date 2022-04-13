package datasource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * These are the tests for the MapTableDataGatewayRDS class
 *
 * @author abraham
 *
 */
public class MapTableDataGatewayRDSTest
{

	/**
	 * make sure it is a singleton
	 */
	@Test
	public void testGetInstance()
	{
		assertNotNull(MapTableDataGatewayRDS.getInstance());
		assertSame(MapTableDataGatewayRDS.getInstance(), MapTableDataGatewayRDS.getInstance());
	}

	/**
	 * We should be able to get the correct list of names
	 */
	@Test
	public void testGetListOfMapNames()
	{
		assertEquals(MapTableDataGatewayRDS.getInstance().getMapNames().size(), 6);
		assertEquals(MapTableDataGatewayRDS.getInstance().getMapNames().get(0), "map1.tmx");
	}

}
