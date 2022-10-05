package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.criteria.CriteriaIntegerDTO;
import edu.ship.engr.shipsim.criteria.InteractableItemActionParameter;
import edu.ship.engr.shipsim.dataENUM.InteractableItemActionType;
import edu.ship.engr.shipsim.datatypes.InteractableItemsForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for InteractableItemGateway
 *
 * @author Jake Moore, Elisabeth Ostrow
 */
@GameTest("GameServer")
public class InteractableItemRowDataGatewayTest
{
    // gateway instance
    private InteractableItemRowDataGateway gateway;

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

        InteractableItemRowDataGateway after =
                this.findGateway(InteractableItemsForTest.BOOK.getItemID());
        assertEquals(InteractableItemActionType.BUFF, after.getActionType());
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

        InteractableItemRowDataGateway after =
                this.findGateway(InteractableItemsForTest.BOOK.getItemID());
        assertEquals("test", after.getMapName());
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

        InteractableItemRowDataGateway after =
                this.findGateway(InteractableItemsForTest.BOOK.getItemID());
        assertEquals("testy bois", after.getName());
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

        InteractableItemRowDataGateway after =
                this.findGateway(InteractableItemsForTest.BOOK.getItemID());
        assertEquals(5, after.getPosition().getRow());
        assertEquals(5, after.getPosition().getColumn());
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
        this.gateway = this.createGateway("Buff Book", new Position(1, 1),
                InteractableItemActionType.BUFF, new CriteriaIntegerDTO(10), "mapName");

        InteractableItemRowDataGateway after =
                this.findGateway(this.gateway.getItemID());

        assertEquals("Buff Book", after.getName());
        assertEquals(1, after.getPosition().getRow());
        assertEquals(1, after.getPosition().getColumn());
        assertEquals(InteractableItemActionType.BUFF, after.getActionType());
        assertEquals("mapName", after.getMapName());
    }

    /**
     * Tests exception thrown if id given does not correspond with an existing row
     */
    @Test
    public void findNotExisting()
    {
        assertThrows(DatabaseException.class, () ->
        {
            this.gateway = this.findGateway(11111);
        });
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
     * Tests remove correctly deletes a gateway and then tries to retrieve it
     * unsuccessfully
     */
    @Test
    public void removeTest()
    {
        assertThrows(DatabaseException.class, () ->
        {
            InteractableItemsForTest item = InteractableItemsForTest.BOOK;
            InteractableItemRowDataGateway gateway = this.findGateway(item.getItemID());

            gateway.delete();

            this.findGateway(item.getItemID());
        });
    }

    @Test
    public void testDeletesCorrectItem()
    {
        assertThrows(DatabaseException.class, () ->
        {
            InteractableItemRowDataGateway firstItem =
                    this.findGateway(InteractableItemsForTest.BOOK.getItemID());
            firstItem.delete();
            this.findGateway(InteractableItemsForTest.BOOK.getItemID());
        });
    }

    // finder
    InteractableItemRowDataGateway findGateway(int itemID) throws DatabaseException
    {
        return new InteractableItemRowDataGateway(itemID);
    }

    InteractableItemRowDataGateway createGateway(String name, Position pos,
                                                 InteractableItemActionType actionType,
                                                 InteractableItemActionParameter actionParam,
                                                 String mapName)
            throws DatabaseException
    {
        return new InteractableItemRowDataGateway(name, pos, actionType, actionParam,
                mapName);
    }
}
