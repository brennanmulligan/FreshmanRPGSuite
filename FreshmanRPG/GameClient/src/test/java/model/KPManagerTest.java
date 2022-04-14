package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.DoubloonPrizeDTO;

/**
 * @author am3243
 *
 */
public class KPManagerTest
{
	
	/**
	 * Always start with a new singleton
	 */
	@Before
	public void reset()
	{
		DoubloonManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}
	
	/**
	 * There should be only one manager
	 */
	@Test
	public void testSingleton()
	{
		DoubloonManager kp = DoubloonManager.getSingleton();
		assertSame(kp, DoubloonManager.getSingleton());
		DoubloonManager.resetSingleton();
		assertNotSame(kp, DoubloonManager.getSingleton());
	}
	
	/**
	 * Added Doubloons Prize DTO's to the manager successfully.
	 */
	@Test
	public void testAdding()
	{
		DoubloonManager manager = DoubloonManager.getSingleton();
		ArrayList<DoubloonPrizeDTO> doubloonsList = new ArrayList<>();
		DoubloonPrizeDTO doubloonDTO = new DoubloonPrizeDTO("pizza", 0, "pizza man");
		doubloonsList.add(doubloonDTO);
		assertTrue(doubloonsList.contains(doubloonDTO));
		
		manager.add(doubloonsList);
		assertTrue(manager.getDoubloons().containsAll(doubloonsList));
	}
	
	/**
	 * Test that the command works properly to populate the manager
	 */
	@Test
	public void testPopulateCommand()
	{
		DoubloonManager manager = DoubloonManager.getSingleton();
		ArrayList<DoubloonPrizeDTO> doubloonList = new ArrayList<>();
		DoubloonPrizeDTO doubloonPrizeDTO = new DoubloonPrizeDTO("pizza", 0, "pizza man");
		doubloonList.add(doubloonPrizeDTO);
		
		PopulateDoubloonManagerCommand command = new PopulateDoubloonManagerCommand(doubloonList);
		command.execute();
		
		assertTrue(command.getDtos().containsAll(doubloonList));
		
		assertTrue(manager.getDoubloons().containsAll(doubloonList));
	}
}
