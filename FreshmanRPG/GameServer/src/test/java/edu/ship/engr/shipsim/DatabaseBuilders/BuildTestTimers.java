package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.TimerTableDataGateway;
import edu.ship.engr.shipsim.model.OptionsManager;

import java.sql.SQLException;

public class BuildTestTimers
{
    public static void main(String[] args) throws DatabaseException, SQLException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        buildTimerTable();
    }

    private static void buildTimerTable() throws DatabaseException
    {
        TimerTableDataGateway.createTable();
    }
}
