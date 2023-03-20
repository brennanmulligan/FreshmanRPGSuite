package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.PlayerLoginRowDataGateway;

import edu.ship.engr.shipsim.datasource.PlayerRowDataGateway;
import edu.ship.engr.shipsim.datasource.PlayerTableDataGateway;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.model.reports.ChangePlayerReport;
import edu.ship.engr.shipsim.model.reports.GetAllPlayersReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CommandChangePasswordTest
{
    @Test
    public void testChangePassword() throws DatabaseException
    {
        CommandChangePlayer changePassword = new CommandChangePlayer("John", "boop");
        changePassword.execute();

        PlayerLoginRowDataGateway afterGateway =
                new PlayerLoginRowDataGateway("John");
        assertTrue(afterGateway.checkPassword("boop"));

        CommandChangePlayer revertPassword = new CommandChangePlayer("John", "pw");
        revertPassword.execute();
    }
}