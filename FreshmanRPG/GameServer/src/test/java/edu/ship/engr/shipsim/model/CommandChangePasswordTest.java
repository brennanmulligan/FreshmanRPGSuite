package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.PlayerLoginRowDataGateway;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandChangePasswordTest
{
    @Test
    public void testChangePassword() throws DatabaseException
    {
        PlayerLoginRowDataGateway beforeGateway =
                new PlayerLoginRowDataGateway(42, "name", "pw");
        CommandChangePassword cmd = new CommandChangePassword("name", "boop");
        cmd.execute();
        PlayerLoginRowDataGateway afterGateway =
                new PlayerLoginRowDataGateway("name");
        assertTrue(afterGateway.checkPassword("boop"));
    }
}