package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.TimerDTO;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.Command;
import edu.ship.engr.shipsim.model.CommandAddPlayer;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Seth Miller, Travis Ritter, Evan Paules
 */
@GameTest("GameServer")
public class TimerTableDataGatewayTest
{
    @BeforeEach
    public void setup() throws DatabaseException
    {
        TimerTableDataGateway.rollback();
    }

    /**
     * Test that creates a row in the table with a given Command, and makes sure we can retrieve the command from the DB
     */
    @Test
    public void testCommandCreationWithPlayerID()
    {
        //Create a random command
        Command before = new CommandAddPlayer(0, 0);
        try
        {
            Date timerDate = new Date(System.currentTimeMillis());

            //Push it to DB
            assertTrue(TimerTableDataGateway.createRow(timerDate,
                    before, PlayersForTest.MERLIN.getPlayerID()) > 0);

            //Retrieve all info associated w/ a given playerID
            ArrayList<TimerDTO> results = TimerTableDataGateway.getPlayerTimers(PlayersForTest.MERLIN.getPlayerID());

            //We are just testing the Command here
            Command after = results.get(0).getCommand();

            //Make sure that the command we got from the DB is of the same type
            assertSame(before.getClass(), after.getClass());

            //Check the times, but we need to use delta because MySQL does not store
            //Down to the millisecond precision as Java does
            assertEquals(timerDate.getTime(),
                    results.get(0).getEndsAt().getTime(), 1000);
        }
        catch (DatabaseException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testTimerCreationWithoutPlayerID()
    {
        Command before = new CommandAddPlayer(0, 0);

        try
        {
            assertTrue(TimerTableDataGateway.createRow(new Date(System.currentTimeMillis()), before) > 0);
            ArrayList<TimerDTO> results = TimerTableDataGateway.getNonPlayerTimers();
            assertEquals(results.size(), 1);

            Command after = results.get(0).getCommand();
            assertSame(before.getClass(), after.getClass());
        }
        catch (DatabaseException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetTimerByID() throws DatabaseException
    {
        Date before = new Date(System.currentTimeMillis());
            int timerID = TimerTableDataGateway.createRow(before,
                    new CommandAddPlayer(0, 0));
            Date after =
                    TimerTableDataGateway.getTimerByID(timerID).getEndsAt();
            assertEquals(before.getTime(),
                    after.getTime(), 1000);
    }
    @Test
    public void testDeleteTimer()
    {
        Command before = new CommandAddPlayer(0,0);
        try
        {
            ArrayList<TimerDTO> idresults = TimerTableDataGateway.getPlayerTimers(0);
            assertEquals(idresults.size(), 0);
            int id = TimerTableDataGateway.createRow(new Date(System.currentTimeMillis()), before, 0);
            idresults = TimerTableDataGateway.getPlayerTimers(0);
            assertEquals(idresults.size(), 1);
            TimerTableDataGateway.deleteTimer(id);
            idresults = TimerTableDataGateway.getPlayerTimers(0);
            assertEquals(idresults.size(), 0);
        }
        catch (DatabaseException e)
        {
            throw new RuntimeException(e);
        }
    }
}