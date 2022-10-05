package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for VanityShopItemResponseReport
 *
 * @author Aaron, Jake
 */
@GameTest("GameClient")
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
