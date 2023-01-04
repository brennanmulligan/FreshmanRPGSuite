package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerPurchasedClothingMessage;
import edu.ship.engr.shipsim.model.Report;
import edu.ship.engr.shipsim.model.reports.PlayerPurchasedClothingReport;

import java.util.ArrayList;

public class PlayerPurchasedClothingPacker extends MessagePacker
{
    @Override
    public Message pack(Report object)
    {
        if (object.getClass() != PlayerPurchasedClothingReport.class)
        {
            throw new IllegalArgumentException(
                    "PlayerPurchasedClothingPacker cannot pack messages of type: " +
                            object.getClass()
            );
        }

        PlayerPurchasedClothingReport purchaseReport = (PlayerPurchasedClothingReport) object;

        Message msg = new PlayerPurchasedClothingMessage(purchaseReport.getPlayerID(), purchaseReport.getVanityDTO());
        return msg;
    }

    @Override
    public ArrayList<Class<? extends Report>> getReportTypesWePack()
    {
        ArrayList<Class<? extends Report>> result = new ArrayList<>();
        result.add(PlayerPurchasedClothingReport.class);
        return result;
    }
}
