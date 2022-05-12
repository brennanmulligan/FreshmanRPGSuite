package model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import datasource.DatabaseManager;
import model.OptionsManager;
import org.junit.Test;

import communication.CommunicationException;
import datasource.DatabaseException;
import model.SequenceTest;
import model.SequenceTestRunner;
import sequencetests.CdTeleportationSequenceTest;
import sequencetests.LoginSuccessSequenceTest;

/**
 * Runs all client tests
 *
 * @author Merlin
 *
 */
public class RunOneSequenceTest
{

	/**
	 *
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
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, IOException, CommunicationException, DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		OptionsManager.getSingleton().setDbFilePath("GameShared/config.txt");
		Class<CdTeleportationSequenceTest> testClass = CdTeleportationSequenceTest.class;
		SequenceTestRunner testToRun;
		SequenceTest sequence = testClass.getConstructor().newInstance();
		testToRun = new SequenceTestRunner("My Single Sequence Test", testClass);

		testToRun.setUpTheTest(sequence);
		testToRun.singleSequenceTest();
		System.out.println("Single Sequence Tests Complete");
	}

}
