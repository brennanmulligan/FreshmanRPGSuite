package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.handlers.MessageHandlerSet;
import edu.ship.engr.shipsim.model.terminal.TerminalManager;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StupidClientTest
{

    @Test
    public void areTheyThere()
    {
        MessageHandlerSet x = new MessageHandlerSet(null);
        TerminalManager tm = TerminalManager.getSingleton();
        assertTrue(true);
    }
}