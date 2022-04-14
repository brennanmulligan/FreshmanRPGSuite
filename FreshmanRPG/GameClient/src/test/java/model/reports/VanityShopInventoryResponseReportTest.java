package model.reports;

import dataDTO.VanityDTO;
import datatypes.VanityType;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for VanityShopItemResponseReport
 * @author Aaron, Jake
 */
public class VanityShopInventoryResponseReportTest
{
    /**
     * Test making a new report
     */
    @Test
    public void testInit()
    {
        ArrayList<VanityDTO> inventory = new ArrayList<>();
        inventory.add(new VanityDTO(0, "test0", "test desc", "myTexture", VanityType.BODY, 0));
        inventory.add(new VanityDTO(1, "test1", "test desc", "myTexture", VanityType.BODY, 1));

        VanityShopInventoryResponseReport report = new VanityShopInventoryResponseReport(inventory);
        assertEquals(2, report.getInventory().size());
    }

    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(VanityShopInventoryResponseReport.class).verify();
    }
}
