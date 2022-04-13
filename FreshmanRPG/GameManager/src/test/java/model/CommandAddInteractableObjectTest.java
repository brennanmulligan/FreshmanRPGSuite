package model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import dataDTO.InteractableItemDTO;
import datasource.DatabaseException;
import datasource.InteractableItemTableDataGatewayMock;
import model.reports.ObjectListReport;
import datatypes.InteractableItemsForTest;

/**
 * Tests for Adding Interactable Objects to the Database
 *
 * @author Jordan Long
 *
 */
public class CommandAddInteractableObjectTest
{
	/**
	 * Make sure we are in test mode
	 *
	 * @throws DatabaseException
	 *             Exception
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		InteractableItemTableDataGatewayMock.getInstance().resetData();
	}

	/**
	 * Adding Object to Database
	 *
	 * @throws DatabaseException
	 *             exception
	 */
	@Test
	public void testAddObject() throws DatabaseException
	{
		InteractableItemsForTest object1 = InteractableItemsForTest.BOOK;
		CommandAddInteractableObject cmd = new CommandAddInteractableObject(object1.getName(), object1.getPosition(),
				object1.getActionType(), object1.getActionParam(), object1.getMapName());

		cmd.execute();

		// check to see that the testDTO exists in the questions table
		GameManagerInteractableObjectManager manager = GameManagerInteractableObjectManager.getInstance();
		ObjectListReport report = new ObjectListReport(manager.getObjects());
		ArrayList<InteractableItemDTO> objects = report.getObjects();

		for (InteractableItemDTO o : objects)
		{
			if (o.getName().equals(object1.getName()) && o.getPosition().equals(object1.getPosition())
					&& o.getActionType().equals(object1.getActionType())
					&& o.getActionParam().equals(object1.getActionParam())
					&& o.getMapName().equals(object1.getMapName()))
			{
				assertTrue(true);
				return;
			}
		}
		fail();
	}

	/**
	 * Test that a QuestionAddedReport is created and sent when the command is
	 * executed
	 *
	 * @throws DatabaseException
	 *             DatabaseException
	 */
	@Test
	public void testReportIsSent() throws DatabaseException
	{
		InteractableItemsForTest object1 = InteractableItemsForTest.BOOK;
		MockQualifiedObserver obs = new MockQualifiedObserver(ObjectListReport.class);
		CommandAddInteractableObject cmd = new CommandAddInteractableObject(object1.getName(), object1.getPosition(),
				object1.getActionType(), object1.getActionParam(), object1.getMapName());

		cmd.execute();

		GameManagerInteractableObjectManager manager = GameManagerInteractableObjectManager.getInstance();
		ObjectListReport report = new ObjectListReport(manager.getObjects());
		assertTrue(report.getObjects().get(0).equals(((ObjectListReport) obs.getReport()).getObjects().get(0)));
	}

}
