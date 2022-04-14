package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.DoubloonPrizesTableDataGatewayRDS;
import model.OptionsManager;
import datatypes.DoubloonPrizesForTest;;

/**
 *
 * @author Mina, Christian
 *
 */
public class BuildTestDoubloonPrizes
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
		createDoubloonPrizesTable();
	}

	/**
	 * Create a table of levels
	 *
	 * @throws SQLException
	 * @throws DatabaseException
	 */
	private static void createDoubloonPrizesTable() throws SQLException, DatabaseException
	{
		DoubloonPrizesTableDataGatewayRDS.createTable();
		for (DoubloonPrizesForTest prize : DoubloonPrizesForTest.values())
		{
			DoubloonPrizesTableDataGatewayRDS.getInstance().createRow(prize.getName(), prize.getCost(), prize.getDescription());
		}
	}

}
