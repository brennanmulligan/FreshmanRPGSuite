package edu.ship.engr.shipsim.datasource;



import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


@GameTest("GameServer")
public class MajorRowDataGatewayTest
{
    /**
     * test if a table can be created
     * @throws DatabaseException
     */
    @Test
    public void CreateTable() throws DatabaseException
    {
        MajorRowDataGateway.createTable();
    }

    /**
     * test if a major can be created
     * @throws DatabaseException
     */
    @Test
    public void createMajorTest() throws DatabaseException
    {
        MajorRowDataGateway gateway = new MajorRowDataGateway(1,"Biology");
        String name = new MajorRowDataGateway(1).getName();
        assertEquals(name,gateway.getName());
    }

    @Test
    public void findMajorsTest() throws DatabaseException
    {
        MajorRowDataGateway gateway = new MajorRowDataGateway(30, "Computer Science");
        String name = new MajorRowDataGateway(30).getName();
        assertEquals(name,gateway.getName());
        assertEquals(30,gateway.getMajorID());
    }

    /**
     * test if the database can update
     * @throws DatabaseException
     */
    @Test
    public void persistTest() throws DatabaseException
    {
        MajorRowDataGateway gateway = new MajorRowDataGateway(3,"Criminal Justice");
        gateway.setName("Communications");
        gateway.persist();
        assertEquals("Communications",gateway.getName());
    }

    /**
     * test if a row can be deleted a major from the database
     * @throws DatabaseException
     */
    @Test
    public void deleteTest() throws DatabaseException
    {
        MajorRowDataGateway gateway = new MajorRowDataGateway(4, "Marketing");
        gateway.delete();
        assertThrows(DatabaseException.class, () ->
        {
            MajorRowDataGateway gateway2 = new MajorRowDataGateway(4);
        });

    }


}
