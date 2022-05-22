package DatabaseBuilders;

import java.sql.SQLException;

import datasource.DatabaseException;
import datasource.LevelTableDataGatewayRDS;
import datasource.TableDataGatewayManager;
import model.OptionsManager;
import datatypes.LevelsForTest;

/**
 * Builds the Level portion of the database
 *
 * @author Merlin
 *
 */
public class BuildTestLevels
{

	/**
	 *
	 * @param args unused
	 * @throws DatabaseException shouldn't
	 * @throws SQLException shouldn't
	 */
	public static void main(String[] args) throws DatabaseException, SQLException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(false);
		OptionsManager.getSingleton().setUsingTestDB(true);
		createLevelTable();
	}

	/**
	 * Create a table of levels
	 *
	 */
	private static void createLevelTable() throws  DatabaseException
	{
		LevelTableDataGatewayRDS.createTable();
		LevelTableDataGatewayRDS gateway =
				(LevelTableDataGatewayRDS) TableDataGatewayManager.getSingleton().getTableGateway("Level");
		for (LevelsForTest level : LevelsForTest.values())
		{
			gateway.createRow(level.getDescription(), level.getLevelUpPoints(),
					level.getLevelUpMonth(), level.getLevelUpDayOfMonth());
		}
	}
}
