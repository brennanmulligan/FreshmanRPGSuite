package model;

import communication.handlers.MessageHandlerSet;
import model.terminal.TerminalManager;
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