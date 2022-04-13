package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import criteria.InteractableNullAction;
import dataDTO.InteractableItemDTO;
import dataENUM.InteractableItemActionType;
import datasource.DatabaseException;
import datasource.InteractableItemRowDataGateway;
import datasource.InteractableItemRowDataGatewayMock;
import datatypes.Position;
import model.reports.ObjectListReport;

/**
 * Test command for editing interactable object.
 *
 * @author Christopher Boyer and Ben Uleau
 */
public class CommandEditInteractableObjectTest
{

	/**
	 *
	 */
	@Before
	public void setup()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * Test that the command executes correctly when everything is there
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testExecute() throws DatabaseException
	{

		InteractableItemRowDataGateway itemGateway = new InteractableItemRowDataGatewayMock("Name", new Position(1, 1),
				InteractableItemActionType.NO_ACTION, new InteractableNullAction(), "sorthingRoom.tmx");

		InteractableItemDTO item = new InteractableItemDTO(itemGateway.getItemID(), "Name2", new Position(1, 1),
				InteractableItemActionType.NO_ACTION, new InteractableNullAction(), "sortingRoom.tmx");
		CommandEditInteractableObject command = new CommandEditInteractableObject(item);
		assertTrue(command.execute());

		InteractableItemRowDataGateway newItemGateway = new InteractableItemRowDataGatewayMock(itemGateway.getItemID());
		assertEquals("Name2", newItemGateway.getName());

	}

	/**
	 * Test to make sure that a report is being generated and sent to listeners
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testSendsReport() throws DatabaseException
	{
		MockQualifiedObserver observer = new MockQualifiedObserver(ObjectListReport.class);
		GameManagerInteractableObjectManager.resetSingleton();
		GameManagerInteractableObjectManager manager = GameManagerInteractableObjectManager.getInstance();
		InteractableItemDTO item = manager.getObjects().get(3);

		CommandEditInteractableObject command = new CommandEditInteractableObject(item);
		command.execute();

		GameManagerInteractableObjectManager.resetSingleton();
		ArrayList<InteractableItemDTO> items = GameManagerInteractableObjectManager.getInstance().getObjects();

		ObjectListReport report = new ObjectListReport(items);

		assertEquals(report.getObjects(), ((ObjectListReport) observer.getReport()).getObjects());
		assertEquals(report.getObjects().get(3).getId(),
				((ObjectListReport) observer.getReport()).getObjects().get(3).getId());
	}
}
