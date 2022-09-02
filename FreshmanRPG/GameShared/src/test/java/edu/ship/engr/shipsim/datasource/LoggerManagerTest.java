package edu.ship.engr.shipsim.datasource;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LoggerManagerTest
{

    @Before
    public void reinit()
    {
        LoggerManager.resetSingleton();
    }

    @Test(expected = IllegalStateException.class)
    public void cantCreateTwo()
    {
        LoggerManager x = LoggerManager.createLogger("first");
        x = LoggerManager.createLogger("second");
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

    @Test(expected = IllegalStateException.class)
    public void haveToCreateIt()
    {
        LoggerManager x = LoggerManager.getSingleton();
    }
}
