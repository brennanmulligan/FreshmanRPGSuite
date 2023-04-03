package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for crews row data gateway
 */
@GameTest("GameServer")
public class CrewRowDataGatewayTest
{
    /**
     * Creates the table in the database
     * @throws DatabaseException
     */
    @Test
    public void createTable() throws DatabaseException
    {
        CrewRowDataGateway.createTable();
    }

    /**
     * Tests in we can create a new crew in the databse
     * @throws DatabaseException
     */
    @Test
    public void createCrewTest() throws DatabaseException
    {
        CrewRowDataGateway gateway = new CrewRowDataGateway(4, "OffByOne");
        String name = new CrewRowDataGateway(4).getName();
        assertEquals(name, gateway.getName());
    }

    /**
     * Tests if we can find a crew from the databse
     * @throws DatabaseException
     */
    @Test
    public void findCrewTest() throws DatabaseException
    {
        CrewRowDataGateway gateway = new CrewRowDataGateway(2, "Forty percent");
        String name = new CrewRowDataGateway(2).getName();
        assertEquals(name, gateway.getName());
    }

    /**
     * Test if we can update a crew in the database
     * @throws DatabaseException
     */
    @Test
    public void persistTest() throws DatabaseException
    {
        CrewRowDataGateway gateway = new CrewRowDataGateway(3, "NPC");
        gateway.setName("OutOfBounds");
        gateway.persist();
        assertEquals("OutOfBounds", gateway.getName());
    }

    /**
     * Tests if we can delete a crew from the database
     * @throws DatabaseException
     */
    @Test
    public void deleteTest() throws DatabaseException
    {
        CrewRowDataGateway gateway = new CrewRowDataGateway(4, "OffByOne");
        gateway.delete();
        assertThrows(DatabaseException.class, () ->
        {
            CrewRowDataGateway gateway2 = new CrewRowDataGateway(4);
        });
    }
}
