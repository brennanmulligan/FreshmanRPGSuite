package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.StubMessage1;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.StubQualifiedObservableReport1;
import edu.ship.engr.shipsim.model.reports.StubQualifiedObservableReport2;

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
     * @see MessagePacker#pack(QualifiedObservableReport)
     */
    @Override
    public Message pack(QualifiedObservableReport object)
    {
        return new StubMessage1();
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> result = new ArrayList<>();
        result.add(StubQualifiedObservableReport1.class);
        result.add(StubQualifiedObservableReport2.class);
        return result;
    }

}
