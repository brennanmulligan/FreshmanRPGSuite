package DatabaseBuilders;

import datasource.DatabaseException;
import datasource.LevelTableDataGateway;
import datatypes.LevelsForTest;
import model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Level portion of the database
 *
 * @author Merlin
 */
public class BuildTestLevels
{

    /**
     * Create a table of levels
     */
    private static void createLevelTable() throws DatabaseException
    {
        LevelTableDataGateway.createTable();
        LevelTableDataGateway gateway =
                LevelTableDataGateway.getSingleton();
        for (LevelsForTest level : LevelsForTest.values())
        {
            gateway.createRow(level.getDescription(), level.getLevelUpPoints(),
                    level.getLevelUpMonth(), level.getLevelUpDayOfMonth());
        }
    }

    /**
     * @param args unused
     * @throws DatabaseException shouldn't
     * @throws SQLException      shouldn't
     */
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createLevelTable();
    }
}
