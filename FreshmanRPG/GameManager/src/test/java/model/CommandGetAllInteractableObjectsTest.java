package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datasource.InteractableItemTableDataGatewayMock;
import model.reports.ObjectListReport;

/**
 * Tests for CommandGetAllInteractableObjects
 *
 * @author Ben Uleau and Abe Loscher
 */
public class CommandGetAllInteractableObjectsTest
{

	/**
	 * Resets the data
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
	 * Tests that upon executing the command, a report is sent back
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testCommandResultsInSentReportWithList() throws DatabaseException
	{
		MockQualifiedObserver obs = new MockQualifiedObserver(ObjectListReport.class);
		CommandGetAllInteractableObjects cmd = new CommandGetAllInteractableObjects();
		cmd.execute();

		GameManagerInteractableObjectManager manager = GameManagerInteractableObjectManager.getInstance();
		ObjectListReport report = new ObjectListReport(manager.getObjects());
		assertEquals(report.getObjects().size(), ((ObjectListReport) obs.getReport()).getObjects().size());
	}

}
