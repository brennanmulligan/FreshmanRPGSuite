package communication.packers;

import communication.messages.Message;
import communication.messages.ServerPlayerOwnedItemsRequestMessage;
import model.ClientPlayerManager;
import model.QualifiedObservableReport;
import model.reports.ServerPlayerOwnedItemsRequestReport;

import java.util.ArrayList;

public class ServerPlayerOwnedItemsRequestPacker extends MessagePacker
{

    @Override
    public Message pack(QualifiedObservableReport object) {
        System.out.println("step 2");
        return new ServerPlayerOwnedItemsRequestMessage(ClientPlayerManager.getSingleton().getThisClientsPlayer().getID());
    }

    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack() {
        ArrayList<Class<? extends QualifiedObservableReport>> result =
                new ArrayList<>();
        result.add(ServerPlayerOwnedItemsRequestReport.class);
        return result;
    }
}
