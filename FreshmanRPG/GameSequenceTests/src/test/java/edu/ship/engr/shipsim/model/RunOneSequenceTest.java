package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.CommunicationException;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.DuplicateNameException;
import edu.ship.engr.shipsim.sequencetests.LoginSuccessSequenceTest;
import edu.ship.engr.shipsim.sequencetests.RecCenterGrantsDoubloonsWithBuffSequenceTest;
import edu.ship.engr.shipsim.sequencetests.RestfulLoginBadPasswordSequenceTest;
import edu.ship.engr.shipsim.sequencetests.RestfulLoginBadUsernameSequenceTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Runs all client tests
 *
 * @author Merlin
 */
@GameTest("GameSequenceTests")
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
    @Test
    public void runSingleTest()
            throws InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException, IOException,
            CommunicationException,
            DatabaseException, ModelFacadeException, DuplicateNameException
    {
        Class<? extends SequenceTest> testClass = RecCenterGrantsDoubloonsWithBuffSequenceTest.class;

        RunAllSequenceTests runner = new RunAllSequenceTests();
        runner.runTest("My Single Sequence Test", testClass);
    }
}
