package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.model.reports.CreatePlayerResponseReport;

public class CommandGetMajorsCrews extends Command
{
    private Major[] majors;
    private Crew[] crews;

    /**
     *
     * @param majors the player's major
     * @param crews the player's crew
     */
    public CommandGetMajorsCrews(Major[] majors, Crew[] crews) {
        this.majors = majors;
        this.crews = crews;
    }
    @Override
    void execute()
    {
        try
        {
            MajorsCrewsManager.getSingleton().addMajorsCrews(majors, crews);
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }
}
