package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DatabaseManager;
import edu.ship.engr.shipsim.datasource.DuplicateNameException;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class CommandTouchDatabase extends Command
{
    private long frequencyInSeconds;
    private DatabaseManager databaseManager;

    public CommandTouchDatabase(DatabaseManager databaseManager, long frequencyInSeconds)
    {
        this.databaseManager = databaseManager;
        this.frequencyInSeconds = frequencyInSeconds;
    }

    @Override
    void execute() throws DuplicateNameException, DatabaseException
    {
        // touch connections
        databaseManager.touchConnection();

        // touch connections again later (repeat)
        Instant current = Instant.now();
        current = current.plus(frequencyInSeconds, ChronoUnit.SECONDS);
        TimerManager.getSingleton().scheduleCommand(Date.from(current), this);
    }

}
