package communication.packers;

import communication.messages.Message;
import communication.messages.PlayerPurchasedClothingMessage;
import model.QualifiedObservableReport;
import model.reports.PlayerPurchasedClothingReport;

import java.util.ArrayList;

public class PlayerPurchasedClothingPacker extends MessagePacker
{
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        if(object.getClass() != PlayerPurchasedClothingReport.class)
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
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
        result.add(PlayerPurchasedClothingReport.class);
        return result;
    }
}
