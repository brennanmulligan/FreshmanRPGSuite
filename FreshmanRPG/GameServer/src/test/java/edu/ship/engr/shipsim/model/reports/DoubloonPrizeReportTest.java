package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@GameTest("GameServer")
public class DoubloonPrizeReportTest
{

    @Test
    public void testConstructor()
    {

        //This is the set up for making the report
        //We are just adding random test data in the DTO
        DoubloonPrizeDTO kppDTO1 = new DoubloonPrizeDTO("Prize1", 20, "Prize1");
        DoubloonPrizeDTO kppDTO2 = new DoubloonPrizeDTO("Prize2", 25, "Prize2");
        ArrayList<DoubloonPrizeDTO> dtoList = new ArrayList<>();
        dtoList.add(kppDTO1);
        dtoList.add(kppDTO2);

        DoubloonPrizeReport kppr = new DoubloonPrizeReport(1, dtoList);

        ArrayList<DoubloonPrizeDTO> dtoList2 = kppr.getPrizes();
        assertEquals(kppDTO1.getName(), dtoList2.get(0).getName());

    }

}
