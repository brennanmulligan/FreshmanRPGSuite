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
/**
 * @author Travis Ritter
 */
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
     */
    @Test
    public void testScheduledCommandNoPlayerID()
            throws InterruptedException
    {
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand);

        verify(testCommand, times(0)).execute();

        Thread.sleep(TIMER_DELAY_MS + BUFFER_MS);

        verify(testCommand, times(1)).execute();
    }

    /**
     * Tests scheduling a command and attaching it to a given player
     * @throws InterruptedException if the thread is interrupted
     */
    @Test
    public void testScheduledCommandWithPlayerID()
            throws InterruptedException
    {
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());

        verify(testCommand, times(0)).execute();
        assertEquals(1, TimerManager.getSingleton().getNumCurrentTimers(PlayersForTest.MERLIN.getPlayerID()));

        Thread.sleep(TIMER_DELAY_MS + BUFFER_MS);

        verify(testCommand, times(1)).execute();
        assertEquals(0, TimerManager.getSingleton().getNumCurrentTimers(PlayersForTest.MERLIN.getPlayerID()));
    }

    /**
     * Tests to make sure we can schedule multiple commands on different players.
     * @throws InterruptedException should not
     */
    @Test
    public void testMultipleScheduledCommands()
            throws InterruptedException
    {
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.JOHN.getPlayerID());
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.MARTY.getPlayerID());

        verify(testCommand, times(0)).execute();
        assertEquals(3, TimerManager.getSingleton().getNumPlayers());

        Thread.sleep(TIMER_DELAY_MS + 3 * BUFFER_MS);

        verify(testCommand, times(3)).execute();
        assertEquals(0, TimerManager.getSingleton().getNumPlayers());
    }

    /**
     * Test scheduling multiple commands on the same player
     * @throws InterruptedException should not
     */
    @Test
    public void testMultipleCommandsOnOnePlayer()
            throws InterruptedException
    {
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());
        TimerManager.getSingleton().scheduleCommand(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());

        verify(testCommand, times(0)).execute();
        assertEquals(TimerManager.getSingleton().getNumCurrentTimers(PlayersForTest.MERLIN.getPlayerID()), 3);

        Thread.sleep(TIMER_DELAY_MS + 3 * BUFFER_MS);

        verify(testCommand, times(3)).execute();
        assertEquals(0, TimerManager.getSingleton().getNumCurrentTimers(PlayersForTest.MERLIN.getPlayerID()));
    }

    /**
     * Tests that we can persist timer info into the DB from this manager
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testPersistTimers() throws DatabaseException
    {
        TimerManager.getSingleton().addTimer(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());
        TimerManager.getSingleton().addTimer(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());
        TimerManager.getSingleton().addTimer(endsAt, testCommand, PlayersForTest.MERLIN.getPlayerID());

        assertEquals(0, TimerTableDataGateway.getAllPlayerTimers(PlayersForTest.MERLIN.getPlayerID()).size());

        TimerManager.getSingleton().persistPlayerTimers(PlayersForTest.MERLIN.getPlayerID());

        assertEquals(3, TimerTableDataGateway.getAllPlayerTimers(PlayersForTest.MERLIN.getPlayerID()).size());
    }

    /**
     * Tests loading a player's timer from the DB
     * @throws DatabaseException if the player does not exist
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
                PlayersForTest.MERLIN.getPlayerID());

        assertEquals(testTimerManager.getNumCurrentTimers(PlayersForTest.MERLIN.getPlayerID()), 1);

        //Rows are deleted after they are grabbed
        assertEquals(TimerTableDataGateway.getAllPlayerTimers(
                PlayersForTest.MERLIN.getPlayerID()).size(), 0);
    }
}

/**
 * Mock Command used for this test class only to simulate using a different subclass of command.
 */
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