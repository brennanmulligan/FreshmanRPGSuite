package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.restfulcommunication.controllers.PlayerController;

public class CommandGetMajorsCrews extends Command
{
    private Major[] majors;
    private Crew[] crews;

    /**
     *
     * @param majors the majors
     * @param crews the crews
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
            PlayerController.getMajorsCrews(majors, crews);
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }
}
