package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sk5587
 */
@GameTest("GameClient")
public class CommandDisplayTextTest
{

    /**
     * make sure command holds right information
     */
    @Test
    public void test()
    {
        CommandDisplayText cmd = new CommandDisplayText("text");
        assertEquals("text", cmd.getText());
    }

}
