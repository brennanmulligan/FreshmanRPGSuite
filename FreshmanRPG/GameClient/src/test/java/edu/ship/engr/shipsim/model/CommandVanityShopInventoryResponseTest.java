package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.reports.VanityShopInventoryResponseReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Tests for the VanityShopResponse command
 *
 * @author Aaron, Jake
 */
@GameTest("GameClient")
@ResetReportObserverConnector
public class CommandVanityShopInventoryResponseTest
{

    /**
     * Test to make sure the constructor can set values
     */
    @Test
    public void testInit()
    {
        ArrayList<VanityDTO> inventory = new ArrayList<>();
        inventory.add(new VanityDTO(0, "test0", "test desc", "myTexture", VanityType.BODY, 0));
        inventory.add(new VanityDTO(1, "test1", "test desc", "myTexture", VanityType.BODY, 1));

        CommandVanityShopInventoryResponse cmd = new CommandVanityShopInventoryResponse(inventory);
        assertEquals(inventory, cmd.getInventory());
    }

    /**
     * Test to make sure it executes correctly
     */
    @Test
    public void testExecute()
    {
        // mock the connector and observer
        ReportObserverConnector connector = spy(ReportObserverConnector.getSingleton());
        ReportObserver observer = mock(ReportObserver.class);

        // register the observer to be notified if a VanityShopInventoryResponseReport is sent
        connector.registerObserver(observer, VanityShopInventoryResponseReport.class);

        ArrayList<VanityDTO> inventory = new ArrayList<>();
        inventory.add(new VanityDTO(0, "test0", "test desc", "myTexture", VanityType.BODY, 0));
        inventory.add(new VanityDTO(1, "test1", "test desc", "myTexture", VanityType.BODY, 1));

        // set up the command and execute it
        CommandVanityShopInventoryResponse cmd = new CommandVanityShopInventoryResponse(inventory);
        cmd.execute();

        // set up the report for verification
        VanityShopInventoryResponseReport expectedReport = new VanityShopInventoryResponseReport(inventory);

        // since the command sends a VanityShopInventoryResponseReport, verify that the observer was notified of it
        verify(observer, times(1)).receiveReport(eq(expectedReport));
    }
}
