package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.CommunicationException;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.sequencetests.TeleportationMovementSequenceTest;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Runs all client tests
 *
 * @author Merlin
 */
public class RunOneSequenceTest
{

    /**
     * @throws InstantiationException    shouldn't
     * @throws IllegalAccessException    shouldn't
     * @throws IllegalArgumentException  shouldn't
     * @throws InvocationTargetException shouldn't
     * @throws NoSuchMethodException     shouldn't
     * @throws SecurityException         shouldn't
     * @throws IOException               shouldn't
     * @throws CommunicationException    shouldn't
     * @throws DatabaseException         shouldn't
     */

    public static void main(String[] args)
            throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, IOException, CommunicationException,
            DatabaseException, ModelFacadeException
    {
        OptionsManager.getSingleton().setDbFilePath("GameShared/config.txt");
        OptionsManager.getSingleton().setTestMode(true);
        Class<TeleportationMovementSequenceTest> testClass = TeleportationMovementSequenceTest.class;
        RunAllSequenceTests testToRun;
        SequenceTest sequence = testClass.getConstructor().newInstance();
        testToRun = new RunAllSequenceTests("My Single Sequence Test", testClass);

        testToRun.setUpTheTest(sequence);
        testToRun.singleSequenceTest();
        System.out.println("Single Sequence Tests Complete");
    }

}
