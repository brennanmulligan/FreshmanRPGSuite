package edu.ship.engr.shipsim.DatabaseBuilders;

import edu.ship.engr.shipsim.datasource.CrewRowDataGateway;
import edu.ship.engr.shipsim.datasource.CrewTableDataGateway;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.model.OptionsManager;

public class BuildTestCrews
{

    public static void main(String[] args) throws DatabaseException
    {
        OptionsManager.getSingleton().setUsingTestDB(true);
        createCrewTable();
    }

    /**
     * Fill table with Crews
     */
    public static void createCrewTable() throws DatabaseException
    {
        System.out.println("Building the Crew Table");
        CrewRowDataGateway.createTable();

        ProgressBar bar = new ProgressBar(Crew.values().length);
        for(Crew crew : Crew.values())
        {
            new CrewRowDataGateway(crew.getID(), crew.getCrewName());

            bar.update();
        }
    }
}
