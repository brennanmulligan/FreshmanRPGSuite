package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.KnowledgePointPrizesTableDataGatewayRDS;
import model.OptionsManager;
import datatypes.KnowledgePointPrizesForTest;;

/**
 *
 * @author Mina, Christian
 *
 */
public class BuildTestKnowledgePointPrizes
{

	/**
	 * @param args
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createKnowledgePointPrizesTable();
	}

	/**
	 * Create a table of levels
	 *
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createKnowledgePointPrizesTable() throws SQLException, DatabaseException
	{
		KnowledgePointPrizesTableDataGatewayRDS.createTable();
		for (KnowledgePointPrizesForTest prize : KnowledgePointPrizesForTest.values())
		{
			KnowledgePointPrizesTableDataGatewayRDS.getInstance().createRow(prize.getName(), prize.getCost(), prize.getDescription());
		}
	}

}
