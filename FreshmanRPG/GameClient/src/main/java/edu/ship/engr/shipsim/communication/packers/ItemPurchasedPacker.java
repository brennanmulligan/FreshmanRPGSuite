package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.ItemPurchasedMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.reports.ItemPurchasedReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * Packer that transforms ItemPurchasedReport into an ItemPurchasedMessage
 *
 * @author Kevin Marek, Zachary Semanco
 */
public class ItemPurchasedPacker extends MessagePacker
{

    /**
     * Transform the ItemPurchasedReport into an ItemPurchasedMessage
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        if (object.getClass() != ItemPurchasedReport.class)
        {
            throw new IllegalArgumentException(
                    "ItemPurchasedPacker cannot pack messages of type "
                            + object.getClass());
        }
        ItemPurchasedReport report = (ItemPurchasedReport) object;
        Message msg = new ItemPurchasedMessage(report.getPlayerID(), report.isQuiet(), report.getPrice());
        return msg;
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(ItemPurchasedReport.class);
        return result;
    }

}
