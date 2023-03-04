package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.StubMessage1;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;
import edu.ship.engr.shipsim.model.reports.StubReport1;
import edu.ship.engr.shipsim.model.reports.StubReport2;

import java.util.ArrayList;

/**
 * A relatively empty packer that the tests for MessagePackerSet will detect.
 * This will let us test that it detects the MessagePackers that are in its
 * package. This is also used by the StateAccumulator tests.
 *
 * @author Merlin
 */
public class StubMessagePacker2a extends MessagePacker
{

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        return new StubMessage1(0);
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(StubReport1.class);
        result.add(StubReport2.class);
        return result;
    }

}
