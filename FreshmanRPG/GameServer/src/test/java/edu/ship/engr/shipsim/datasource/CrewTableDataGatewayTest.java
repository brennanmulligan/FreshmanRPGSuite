package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.CrewDTO;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * Test for crews table data gateway
 */
@GameTest("GameServer")
public class CrewTableDataGatewayTest
{

    /**
     * Test to retrieve all crews from the database
     * @throws DatabaseException
     */
    @Test
    public void getAllCrewsTest() throws DatabaseException
    {
        CrewRowDataGateway crew1 = new CrewRowDataGateway(1, "OffByOne");
        CrewRowDataGateway crew2 = new CrewRowDataGateway(2, "FortyPercent");
        CrewRowDataGateway crew3 = new CrewRowDataGateway(3, "NPC");
        CrewRowDataGateway crew4 = new CrewRowDataGateway(4, "OutOfBounds");

        CrewTableDataGateway gateway = CrewTableDataGateway.getSingleton();
        ArrayList<CrewDTO> crews = gateway.getAllCrews();

        assertEquals(4, crews.size());
    }
}
