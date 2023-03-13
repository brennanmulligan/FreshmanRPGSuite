package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.ServerPlayerOwnedItemsRequestMessage;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;
import edu.ship.engr.shipsim.model.reports.ServerPlayerOwnedItemsRequestReport;

import java.util.ArrayList;

public class ServerPlayerOwnedItemsRequestPacker extends MessagePacker
{

    @Override
    public Message pack(SendMessageReport object)
    {
        ServerPlayerOwnedItemsRequestReport report = (ServerPlayerOwnedItemsRequestReport) object;
        System.out.println("step 2");
        return new ServerPlayerOwnedItemsRequestMessage(ClientPlayerManager.getSingleton().getThisClientsPlayer().getID(), report.isQuiet());
    }

    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result =
                new ArrayList<>();
        result.add(ServerPlayerOwnedItemsRequestReport.class);
        return result;
    }
}
