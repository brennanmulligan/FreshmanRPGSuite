package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerPurchasedClothingMessage;
import edu.ship.engr.shipsim.model.reports.PlayerPurchasedClothingReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

public class PlayerPurchasedClothingPacker extends MessagePacker
{
    @Override
    public Message pack(SendMessageReport object)
    {
        if (object.getClass() != PlayerPurchasedClothingReport.class)
        {
            throw new IllegalArgumentException(
                    "PlayerPurchasedClothingPacker cannot pack messages of type: " +
                            object.getClass()
            );
        }

        PlayerPurchasedClothingReport purchaseReport = (PlayerPurchasedClothingReport) object;

        Message msg = new PlayerPurchasedClothingMessage(purchaseReport.getPlayerID(), purchaseReport.isQuiet(), purchaseReport.getVanityDTO());
        return msg;
    }

    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(PlayerPurchasedClothingReport.class);
        return result;
    }
}
