package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.VanityShopInventoryRequestMessage;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;
import edu.ship.engr.shipsim.model.reports.ShopInventoryRequestReport;

import java.util.ArrayList;

/**
 * Packer for the VanityShopInventoryRequestMessage
 *
 * @author Aaron, Jake
 */
public class VanityShopInventoryRequestPacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        return new VanityShopInventoryRequestMessage(false);
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result =
                new ArrayList<>();
        result.add(ShopInventoryRequestReport.class);
        return result;
    }
}
