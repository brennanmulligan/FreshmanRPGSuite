package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.reports.VanityShopInventoryResponseReport;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the VanityShopResponse command
 *
 * @author Aaron, Jake
 */
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
        ArrayList<VanityDTO> inventory = new ArrayList<>();
        inventory.add(new VanityDTO(0, "test0", "test desc", "myTexture", VanityType.BODY, 0));
        inventory.add(new VanityDTO(1, "test1", "test desc", "myTexture", VanityType.BODY, 1));

        CommandVanityShopInventoryResponse cmd = new CommandVanityShopInventoryResponse(inventory);

        QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
        QualifiedObservableConnector.getSingleton().registerObserver(obs, VanityShopInventoryResponseReport.class);
        VanityShopInventoryResponseReport report = new VanityShopInventoryResponseReport(inventory);
        obs.receiveReport(EasyMock.eq(report));
        EasyMock.replay(obs);

        cmd.execute();

        EasyMock.verify(obs);
    }
}
