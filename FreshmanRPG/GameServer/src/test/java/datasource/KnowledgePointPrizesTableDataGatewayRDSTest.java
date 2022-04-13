package datasource;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Mina Kindo, Christian Chroutamel
 *
 */
public class KnowledgePointPrizesTableDataGatewayRDSTest extends KnowledgePointPrizesTableDataGatewayTest
{

	@Override
	/**
	 * @throws DatabaseException
	 */
	public KnowledgePointPrizesTableDataGateway getGatewaySingleton() throws DatabaseException
	{
		return KnowledgePointPrizesTableDataGatewayRDS.getInstance();
	}

}
