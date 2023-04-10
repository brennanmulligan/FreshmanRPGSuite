package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.TimerTableDataGateway;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@GameTest("GameServer")
@ResetModelFacade
public class TimerManagerTest
{
    TestCommand testCommand;
    Date endsAt;
    private final int TIMER_DELAY_MS = 250;
    private final int TIMER_DELAY_S = 20000;
    private final int BUFFER_MS = 100;
    @BeforeEach
    public void setup() throws DatabaseException
    {
        TimerTableDataGateway.rollback();
        TimerManager.resetSingleton();
        ModelFacade.resetSingleton();
        testCommand = mock(TestCommand.class);
        endsAt = new Date(System.currentTimeMillis() + TIMER_DELAY_MS);
    }

    /**
     * Tests the scheduling of a command without a player ID
     * @throws InterruptedException if the thread is interrupted
     * @throws DatabaseException will not
     */
    @Test
    public void testScheduledCommandNoPlayerID()
            throws InterruptedException, DatabaseException
    {
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand);

        verify(testCommand, times(0)).execute();

        Thread.sleep(TIMER_DELAY_MS + BUFFER_MS);

        verify(testCommand, times(1)).execute();
    }

    /**
     * Tests scheduling a command and attaching it to a given player
     * @throws InterruptedException if the thread is interrupted
     * @throws DatabaseException if the player does not exist in the DB
     */
    @Test
    public void testScheduledCommandWithPlayerID()
            throws InterruptedException, DatabaseException
    {
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());

        verify(testCommand, times(0)).execute();
        assertEquals(TimerManager.getSingleton().getNumCurrentTimers(PlayersForTest.MERLIN.getPlayerID()), 1);

        Thread.sleep(TIMER_DELAY_MS + BUFFER_MS);

        verify(testCommand, times(1)).execute();
        assertEquals(TimerManager.getSingleton().getNumCurrentTimers(PlayersForTest.MERLIN.getPlayerID()), 0);
    }

    @Test
    public void testMultipleScheduledCommands()
            throws DatabaseException, InterruptedException
    {
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.JOHN.getPlayerID());
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.MARTY.getPlayerID());

        verify(testCommand, times(0)).execute();
        assertEquals(TimerManager.getSingleton().getNumPlayers(), 3);

        Thread.sleep(TIMER_DELAY_MS * 3 + BUFFER_MS);

        verify(testCommand, times(3)).execute();
        assertEquals(TimerManager.getSingleton().getNumPlayers(), 0);
    }

    @Test
    public void testMultipleCommandsOnOnePlayer()
            throws DatabaseException, InterruptedException
    {
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());

        verify(testCommand, times(0)).execute();
        assertEquals(TimerManager.getSingleton().getNumCurrentTimers(PlayersForTest.MERLIN.getPlayerID()), 3);

        Thread.sleep(TIMER_DELAY_MS * 3 + BUFFER_MS);

        verify(testCommand, times(3)).execute();
        assertEquals(TimerManager.getSingleton().getNumCurrentTimers(PlayersForTest.MERLIN.getPlayerID()), 0);
    }

    /**
     * Tests loading a player's timer from the DB
     * @throws DatabaseException if the player does not exist
     * @throws InterruptedException if the thread is interrupted
     */
    @Test
    public void testloadUserTimers()
            throws DatabaseException
    {
        //Have to do this because the database does not store to millisecond precision
        Date testDate = new Date(
                (long) (Math.floor(
                        (System.currentTimeMillis() + TIMER_DELAY_S) / 1000) *
                        1000));

        TestCommand testCommand = new TestCommand();

        TimerTableDataGateway.createRow(testDate, testCommand,
                PlayersForTest.MERLIN.getPlayerID());

        TimerManager testTimerManager = spy(TimerManager.getSingleton());

        testTimerManager.loadUserTimers(PlayersForTest.MERLIN.getPlayerID());

        verify(testTimerManager, times(1)).scheduleCommand(testDate,
                testCommand,
                PlayersForTest.MERLIN.getPlayerID(), false);

        assertEquals(testTimerManager.getNumCurrentTimers(PlayersForTest.MERLIN.getPlayerID()), 1);

        assertEquals(TimerTableDataGateway.getAllPlayerTimers(
                PlayersForTest.MERLIN.getPlayerID()).size(), 1);
    }
    @Test
    public void testShouldPersist() throws DatabaseException
    {
        Date testDate = new Date(System.currentTimeMillis() + TIMER_DELAY_S);

        //This is where rows are added to the DB if they do not already exist
        TimerManager.getSingleton().scheduleCommand(testDate, testCommand, PlayersForTest.MERLIN.getPlayerID());

        //There should only be one entry
        assertEquals(1, TimerTableDataGateway.getAllPlayerTimers(PlayersForTest.MERLIN.getPlayerID()).size());
    }


    @Test
    public void testShouldNotPersist() throws DatabaseException
    {
        Date testDate = new Date(System.currentTimeMillis() + TIMER_DELAY_S);
        TimerTableDataGateway.createRow(testDate, testCommand, PlayersForTest.MERLIN.getPlayerID());

        //This is where we will 'persist' a new row if it does not already exist in the DB
        //if it does exist we do not add the row.
        TimerManager.getSingleton().scheduleCommand(testDate, testCommand, PlayersForTest.MERLIN.getPlayerID());

        //There should only be one entry
        assertEquals(1, TimerTableDataGateway.getAllPlayerTimers(PlayersForTest.MERLIN.getPlayerID()).size());
    }
}



class TestCommand extends Command
{
    @Override
    void execute()
    {
        System.out.println("Executed");
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof TestCommand)
        {
            return true;
        }
        return false;
    }
}