package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertThrows;

@GameTest(value = "GameShared", createLogger = false)
public class LoggerManagerTest
{

    @BeforeEach
    public void reinit()
    {
        LoggerManager.resetSingleton();
    }

    @Test
    public void cantCreateTwo()
    {
        assertThrows(IllegalStateException.class, () ->
        {
            LoggerManager x = LoggerManager.createLogger("first");
            x = LoggerManager.createLogger("second");
        });
    }

    @Test
    public void createsFile() throws FileNotFoundException
    {
        LoggerManager x = LoggerManager.createLogger("Test");
        x.getLogger().fine("Test");
        Scanner scan = new Scanner(new File("Test.log"));
        String in = scan.nextLine();
        System.out.println(in);
    }

    @Test
    public void haveToCreateIt()
    {
        assertThrows(IllegalStateException.class, () ->
        {
            LoggerManager x = LoggerManager.getSingleton();
        });
    }
}
