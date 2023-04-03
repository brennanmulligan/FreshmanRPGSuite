package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.MajorRowDataGateway;
import edu.ship.engr.shipsim.datasource.MajorTableDataGateway;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.model.OptionsManager;

public class BuildTestMajors
{
    
    public static void main(String[] args) throws DatabaseException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createMajorsTable();
    }

    /**
     * Populating the table with Majors
     */
    private static void createMajorsTable() throws DatabaseException
    {
        System.out.println("Building the Majors Table");
        MajorRowDataGateway.createTable();

        ProgressBar bar = new ProgressBar(Major.values().length);
        for(Major major : Major.values())
        {
            new MajorRowDataGateway(major.getID(), major.getMajorName());
            bar.update();
        }
    }
}
