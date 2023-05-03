package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.MajorDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class MajorTableDataGatewayTest
{
    @Test
    public void getAllMajorsTest() throws DatabaseException
    {
        MajorRowDataGateway major = new MajorRowDataGateway(1,"Biology");
        MajorRowDataGateway major2 = new MajorRowDataGateway(2,"Computer Science");
        MajorRowDataGateway major3 = new MajorRowDataGateway(3,"Accounting");

        MajorTableDataGateway gateway = MajorTableDataGateway.getSingleton();
        ArrayList<MajorDTO> majors = gateway.getAllMajors();
        assertEquals(3,majors.size());
    }

}
