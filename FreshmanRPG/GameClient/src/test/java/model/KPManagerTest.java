package model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.KnowledgePointPrizeDTO;

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
		KnowledgePointsManager.resetSingleton();
		QualifiedObservableConnector.resetSingleton();
	}
	
	/**
	 * There should be only one manager
	 */
	@Test
	public void testSingleton()
	{
		KnowledgePointsManager kp = KnowledgePointsManager.getSingleton();
		assertSame(kp, KnowledgePointsManager.getSingleton());
		KnowledgePointsManager.resetSingleton();
		assertNotSame(kp, KnowledgePointsManager.getSingleton());
	}
	
	/**
	 * Added Knowledge Point Prize DTO's to the manager successfully.
	 */
	@Test
	public void testAdding()
	{
		KnowledgePointsManager manager = KnowledgePointsManager.getSingleton();
		ArrayList<KnowledgePointPrizeDTO> knowledgePointsList = new ArrayList<>();
		KnowledgePointPrizeDTO knowledgePoint = new KnowledgePointPrizeDTO("pizza", 0, "pizza man");
		knowledgePointsList.add(knowledgePoint);
		assertTrue(knowledgePointsList.contains(knowledgePoint));
		
		manager.add(knowledgePointsList);
		assertTrue(manager.getAllKnowledgePoints().containsAll(knowledgePointsList));
	}
	
	/**
	 * Test that the command works properly to populate the manager
	 */
	@Test
	public void testPopulateCommand()
	{
		KnowledgePointsManager manager = KnowledgePointsManager.getSingleton();
		ArrayList<KnowledgePointPrizeDTO> knowledgePointsList = new ArrayList<>();
		KnowledgePointPrizeDTO knowledgePoint = new KnowledgePointPrizeDTO("pizza", 0, "pizza man");
		knowledgePointsList.add(knowledgePoint);
		
		PopulateKnowledgePointManagerCommand command = new PopulateKnowledgePointManagerCommand(knowledgePointsList);
		command.execute();
		
		assertTrue(command.getDtos().containsAll(knowledgePointsList));
		
		assertTrue(manager.getAllKnowledgePoints().containsAll(knowledgePointsList));
	}
}
