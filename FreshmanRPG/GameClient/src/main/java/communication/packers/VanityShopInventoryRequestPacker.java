package communication.packers;

import communication.messages.Message;
import communication.messages.VanityShopInventoryRequestMessage;
import model.QualifiedObservableReport;
import model.reports.ShopInventoryRequestReport;
import java.util.ArrayList;

/**
 * Packer for the VanityShopInventoryRequestMessage
 * @author Aaron, Jake
 */
public class VanityShopInventoryRequestPacker extends MessagePacker
{

    /**
     * @see communication.packers.MessagePacker#pack(model.QualifiedObservableReport)
     */
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        return new VanityShopInventoryRequestMessage();
    }

    /**
     * @see communication.packers.MessagePacker#getReportTypesWePack()
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
