package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import criteria.InteractableNullAction;
import dataENUM.InteractableItemActionType;
import datasource.DatabaseException;
import datasource.InteractableItemRowDataGateway;
import datasource.InteractableItemRowDataGatewayMock;
import datasource.InteractableItemTableDataGatewayMock;
import datatypes.Position;
import model.reports.ObjectListReport;

/**
 * tests the command to delete an interactable object
 *
 * @author Mohammed Almaslamani
 *
 */
public class CommandDeleteInteractableObjectTest
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
	 * test deleting an interactable item
	 *
	 * @throws DatabaseException
	 *             - shouldn't
	 */
	@Test
	public void deleteInteractableItemTest() throws DatabaseException
	{
		MockQualifiedObserver obs = new MockQualifiedObserver(ObjectListReport.class);

		InteractableItemRowDataGateway itemGateway = new InteractableItemRowDataGatewayMock("Name", new Position(1, 1),
				InteractableItemActionType.NO_ACTION, new InteractableNullAction(), "sorthingRoom.tmx");

		CommandDeleteInteractableObject cmd = new CommandDeleteInteractableObject(itemGateway.getItemID());
		cmd.execute();

		GameManagerInteractableObjectManager manager = GameManagerInteractableObjectManager.getInstance();
		ObjectListReport report = new ObjectListReport(manager.getObjects());
		assertEquals(report.getObjects().size(), ((ObjectListReport) obs.getReport()).getObjects().size());
	}

}
