package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.VanityShopInventoryRequestMessage;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
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
     * @see MessagePacker#pack(QualifiedObservableReport)
     */
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        return new VanityShopInventoryRequestMessage();
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> result =
                new ArrayList<>();
        result.add(ShopInventoryRequestReport.class);
        return result;
    }
}
