package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.LevelTableDataGateway;
import edu.ship.engr.shipsim.datatypes.LevelsForTest;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

/**
 * Builds the Level portion of the database
 *
 * @author Merlin
 */
public class BuildTestLevels
{
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

    /**
     * Create a table of levels
     */
    private static void createLevelTable() throws DatabaseException
    {
        System.out.println("Building the Level Table");
        LevelTableDataGateway.createTable();
        LevelTableDataGateway gateway = LevelTableDataGateway.getSingleton();

        ProgressBar bar = new ProgressBar(LevelsForTest.values().length);
        for (LevelsForTest level : LevelsForTest.values())
        {
            gateway.createRow(level.getDescription(), level.getLevelUpPoints(), level.getLevelUpMonth(), level.getLevelUpDayOfMonth());

            bar.update();
        }
    }
}
