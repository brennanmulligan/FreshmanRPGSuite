package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.DoubloonPrizesTableDataGatewayRDS;
import datasource.TableDataGatewayManager;
import model.OptionsManager;
import datatypes.DoubloonPrizesForTest;

/**
 *
 * @author Mina, Christian
 *
 */
public class BuildTestDoubloonPrizes
{

	public static void main(String[] args) throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createDoubloonPrizesTable();
	}

	/**
	 * Create a table of levels
	 */
	private static void createDoubloonPrizesTable() throws DatabaseException
	{
		DoubloonPrizesTableDataGatewayRDS.createTable();
		for (DoubloonPrizesForTest prize : DoubloonPrizesForTest.values())
		{
			DoubloonPrizesTableDataGatewayRDS gateway =
					(DoubloonPrizesTableDataGatewayRDS) TableDataGatewayManager.getSingleton().getTableGateway(
							"DoubloonPrizes");
			gateway.createRow(prize.getName(), prize.getCost(), prize.getDescription());
		}
	}

}
