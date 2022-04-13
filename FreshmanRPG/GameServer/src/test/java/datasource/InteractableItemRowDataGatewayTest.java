package datasource;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Test;

import criteria.CriteriaIntegerDTO;
import criteria.InteractableItemActionParameter;
import dataENUM.InteractableItemActionType;
import datatypes.Position;
import datatypes.InteractableItemsForTest;

/**
 * Tests for InteractableItemGateway
 *
 * @author Jake Moore, Elisabeth Ostrow
 */
public abstract class InteractableItemRowDataGatewayTest extends DatabaseTest
{
	// gateway instance
	private InteractableItemRowDataGateway gateway;

	// finder
	abstract InteractableItemRowDataGateway findGateway(int itemID) throws DatabaseException;

	// creation
	abstract InteractableItemRowDataGateway createGateway(String name, Position pos, InteractableItemActionType action, InteractableItemActionParameter param, String mapName)
			throws DatabaseException;

	/**
	 * Rollsback DB
	 *
	 * @throws SQLException if rollback is unsuccessful
	 * @throws DatabaseException if rollback is unsuccessful
	 */
	@After
	public void tearDown() throws DatabaseException, SQLException
	{
		super.tearDown();
		if (gateway != null)
		{
			gateway.resetData();
		}
	}

	/**
	 * Tests finder See that we can get a gateway given an id for an item
	 *
	 * @throws DatabaseException if an item for the given id does not exist
	 */
	@Test
	public void finder() throws DatabaseException
	{
		InteractableItemsForTest book = InteractableItemsForTest.BOOK;
		this.gateway = this.findGateway(book.getItemID());
		assertEquals(book.getItemID(), gateway.getItemID());
		assertEquals(book.getPosition().getRow(), gateway.getPosition().getRow());
		assertEquals(book.getPosition().getColumn(), gateway.getPosition().getColumn());
		assertEquals(book.getActionType(), gateway.getActionType());
		assertEquals(book.getActionParam(), gateway.getActionParam());
	}

	/**
	 * Tests creation constructor See that we should be able to construct a new row
	 * in the db given the attributes provided
	 *
	 * @throws DatabaseException if creation of the row is unsuccessful
	 */
	@Test
	public void creation() throws DatabaseException
	{
		this.gateway = this.createGateway("Buff Book", new Position(1, 1), InteractableItemActionType.BUFF, new CriteriaIntegerDTO(10), "mapName");

		InteractableItemRowDataGateway after = this.findGateway(this.gateway.getItemID());

		assertEquals("Buff Book", after.getName());
		assertEquals(1, after.getPosition().getRow());
		assertEquals(1, after.getPosition().getColumn());
		assertEquals(InteractableItemActionType.BUFF, after.getActionType());
		assertEquals("mapName", after.getMapName());
	}

	/**
	 * Tests exception thrown if id given does not correspond with an existing row
	 *
	 * @throws DatabaseException - if error
	 */
	@Test(expected = DatabaseException.class)
	public void findNotExisting() throws DatabaseException
	{
		this.gateway = this.findGateway(11111);
	}

	/**
	 * Tests remove correctly deletes a gateway and then tries to retrieve it
	 * unsuccessfully
	 *
	 * @throws DatabaseException should throw when it tries to get gateway for row
	 *         that is deleted
	 */
	@Test(expected = DatabaseException.class)
	public void removeTest() throws DatabaseException
	{
		InteractableItemsForTest item = InteractableItemsForTest.BOOK;
		InteractableItemRowDataGateway gateway = this.findGateway(item.getItemID());

		gateway.delete();

		this.findGateway(item.getItemID());
	}

	/**
	 * Tests position changes correctly
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void changePosition() throws DatabaseException
	{
		this.gateway = this.findGateway(InteractableItemsForTest.BOOK.getItemID());
		this.gateway.setPosition(new Position(5, 5));
		this.gateway.persist();

		InteractableItemRowDataGateway after = this.findGateway(InteractableItemsForTest.BOOK.getItemID());
		assertEquals(5, after.getPosition().getRow());
		assertEquals(5, after.getPosition().getColumn());

		this.gateway.resetData();
	}

	/**
	 * Tests name gets changed correctly
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void changeName() throws DatabaseException
	{
		this.gateway = this.findGateway(InteractableItemsForTest.BOOK.getItemID());
		this.gateway.setName("testy bois");
		this.gateway.persist();

		InteractableItemRowDataGateway after = this.findGateway(InteractableItemsForTest.BOOK.getItemID());
		assertEquals("testy bois", after.getName());

		gateway.resetData();
	}

	/**
	 * Tests action changes correctly
	 *
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void changeAction() throws DatabaseException
	{
		this.gateway = this.findGateway(InteractableItemsForTest.BOOK.getItemID());
		this.gateway.setActionType(InteractableItemActionType.BUFF);
		this.gateway.persist();

		InteractableItemRowDataGateway after = this.findGateway(InteractableItemsForTest.BOOK.getItemID());
		assertEquals(InteractableItemActionType.BUFF, after.getActionType());

		this.gateway.resetData();
	}

	/**
	 * Test that we can change which map the item is on
	 *
	 * @throws DatabaseException if error
	 */
	@Test
	public void changeMapName() throws DatabaseException
	{
		this.gateway = this.findGateway(InteractableItemsForTest.BOOK.getItemID());
		this.gateway.setMapName("test");
		this.gateway.persist();

		InteractableItemRowDataGateway after = this.findGateway(InteractableItemsForTest.BOOK.getItemID());
		assertEquals("test", after.getMapName());

		this.gateway.resetData();
	}
}
