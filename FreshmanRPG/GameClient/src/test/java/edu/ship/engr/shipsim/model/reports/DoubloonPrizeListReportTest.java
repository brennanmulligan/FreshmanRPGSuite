package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DoubloonPrizeListReportTest
{
    @Test
    public void testInitialization()
    {
        ArrayList<DoubloonPrizeDTO> doubloonPrizes = new ArrayList<>();

        doubloonPrizes.add(new DoubloonPrizeDTO("ItemName", 100, "ItemDesc"));

        DoubloonPrizeListReport rep = new DoubloonPrizeListReport(doubloonPrizes);

        assertEquals(doubloonPrizes.get(0).getName(), rep.getPrizes().get(0).getName());
        assertEquals(doubloonPrizes.get(0).getCost(), rep.getPrizes().get(0).getCost());
        assertEquals(doubloonPrizes.get(0).getDescription(), rep.getPrizes().get(0).getDescription());
    }

    /**
     * Testing the equality of two instances of this class
     */
    @Test
    public void testEqualsContract()
    {
        EqualsVerifier.forClass(DoubloonPrizeListReport.class).verify();
    }


}
