package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 *
 */
@GameTest("GameClient")
public class CommandTerminalTextDateTest
{

    SimpleDateFormat sdf = new SimpleDateFormat("EEE MM dd HH:mm:ss yyyy");

    /**
     *
     */
    @Test
    public void testExecute()
    {
        //make sure the seconds value of sdf's date and the commandTerminalTextDate are within 1 second apart from each other
        CommandTerminalTextDate cttd = new CommandTerminalTextDate();
        String sdfSeconds = sdf.format(new Date()).split(":")[2].split(" ")[0];
        String cttdSeconds = cttd.execute(PlayersForTest.ANDY.getPlayerID(), new String[]{""}).split(":")[2].split(" ")[0];
        assertTrue(Math.abs(Integer.parseInt(sdfSeconds) - Integer.parseInt(cttdSeconds)) < 2);
    }

    /**
     *
     */
    @Test
    public void testGetIdentifier()
    {
        CommandTerminalTextDate cttd = new CommandTerminalTextDate();
        assertEquals("date", cttd.getTerminalIdentifier());

    }

}
