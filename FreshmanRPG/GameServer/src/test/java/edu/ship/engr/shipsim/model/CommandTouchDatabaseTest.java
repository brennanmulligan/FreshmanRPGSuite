package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DatabaseManager;
import edu.ship.engr.shipsim.datasource.DuplicateNameException;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@GameTest("GameServer")
public class CommandTouchDatabaseTest
{
    @Test
    public void testCommandTouchDatabase()
            throws DatabaseException, DuplicateNameException,
            InterruptedException
    {
        DatabaseManager databaseMock = mock(DatabaseManager.class);
        CommandTouchDatabase command = new CommandTouchDatabase(databaseMock, 1);

        // execute command
        // verify touchConnection was called once within the 1st second
        command.execute();
        verify(databaseMock, times(1)).touchConnection();

        // wait 1 second
        // verify it was automatically called again
        Thread.sleep(1000);
        verify(databaseMock, times(2)).touchConnection();
    }
}
