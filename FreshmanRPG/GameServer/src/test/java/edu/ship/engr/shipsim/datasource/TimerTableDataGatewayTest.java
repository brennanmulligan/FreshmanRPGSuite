package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.TimerDTO;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.Command;
import edu.ship.engr.shipsim.model.CommandAddPlayer;
import edu.ship.engr.shipsim.model.TimerManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

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
     * Test that checks the creation of a row in the DB and ensures that the info has stayed the same.
     * @throws DatabaseException if the player we are looking for does not exist.
     */
    @Test
    public void testCreateTimer() throws DatabaseException
    {
        Command before = new CommandAddPlayer(0, 0);
        Date testDate = new Date(System.currentTimeMillis());

        TimerTableDataGateway.createRow(testDate,before,PlayersForTest.NEWBIE.getPlayerID());

        ArrayList<TimerDTO> timers = TimerTableDataGateway.getAllPlayerTimers(PlayersForTest.NEWBIE.getPlayerID());

        assertSame(timers.get(0).getCommand().getClass(), before.getClass());

        assertEquals(timers.get(0).getEndsAt().getTime(),
                TimerTableDataGateway.normalizeDate(testDate));

        assertEquals(timers.get(0).getPlayerID(), PlayersForTest.NEWBIE.getPlayerID());
    }

    /**
     * Test that creates a row in the table with a given Command, and makes sure we can retrieve the command from the DB
     */
    @Test
    public void testGetPlayerTimer()
    {
        //Create a random command
        Command before = new CommandAddPlayer(0, 0);
        try
        {
            Date testDate = new Date(System.currentTimeMillis());

            TimerTableDataGateway.createRow(testDate, before, PlayersForTest.MERLIN.getPlayerID());

            //Retrieve all info associated w/ a given playerID
            ArrayList<TimerDTO> results = TimerTableDataGateway.getAllPlayerTimers(PlayersForTest.MERLIN.getPlayerID());

            //We are just testing the Command here
            Command after = results.get(0).getCommand();

            //Make sure that the command we got from the DB is of the same type
            assertSame(before.getClass(), after.getClass());

            //Check the times, but we need to normalize the date because MySQL does not store timestamps to the same
            //precision that Java does.
            assertEquals(TimerTableDataGateway.normalizeDate(testDate),
                    results.get(0).getEndsAt().getTime());
        }
        catch (DatabaseException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testDeleteExpiredTimers() throws DatabaseException
    {
        Command before = new CommandAddPlayer(0, 0);
        Date testDate = new Date(System.currentTimeMillis());

        TimerTableDataGateway.createRow(testDate,
                before,
                PlayersForTest.JOHN.getPlayerID());

        assertEquals(1, TimerTableDataGateway.getAllPlayerTimers(PlayersForTest.JOHN.getPlayerID()).size());

        TimerTableDataGateway.deleteExpiredTimers(PlayersForTest.JOHN.getPlayerID());

        assertEquals(0, TimerTableDataGateway.getAllPlayerTimers(PlayersForTest.JOHN.getPlayerID()).size());
    }

    @Test
    public void testGrabAndDelete() throws DatabaseException
    {
        TimerTableDataGateway.createRow(new Date(System.currentTimeMillis()),
                new CommandAddPlayer(0, 0),
                PlayersForTest.MERLIN.getPlayerID());
        TimerTableDataGateway.createRow(new Date(System.currentTimeMillis()),
                new CommandAddPlayer(0, 0),
                PlayersForTest.MERLIN.getPlayerID());
        TimerTableDataGateway.createRow(new Date(System.currentTimeMillis()),
                new CommandAddPlayer(0, 0),
                PlayersForTest.MERLIN.getPlayerID());

        assertEquals(TimerTableDataGateway.getAllPlayerTimers(PlayersForTest.MERLIN.getPlayerID()).size(), 3);
        assertEquals(TimerTableDataGateway.getAllPlayerTimers(PlayersForTest.MERLIN.getPlayerID()).size(), 0);
    }
}