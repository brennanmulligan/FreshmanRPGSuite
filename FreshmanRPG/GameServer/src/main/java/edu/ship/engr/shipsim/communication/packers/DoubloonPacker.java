package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.DoubloonPrizeMessage;
import edu.ship.engr.shipsim.dataDTO.DoubloonPrizeDTO;
import edu.ship.engr.shipsim.model.reports.DoubloonPrizeReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/*
 * @Author: Andrew M, Christian C
 */
public class DoubloonPacker extends MessagePacker
{


    ArrayList<DoubloonPrizeDTO> dtos = new ArrayList<>();
    DoubloonPrizeMessage msg = null;

    /*
     * pack the message
     */
    public DoubloonPrizeMessage pack(SendMessageReport object)
    {

        if (object.getClass() != DoubloonPrizeReport.class)
        {
            throw new IllegalArgumentException("Doubloon Prize Report cannot pack messages of type " + object.getClass());
        }

        DoubloonPrizeReport report = (DoubloonPrizeReport) object;
        if (this.getAccumulator().getPlayerID() == report.getPlayerID())
        {
            msg = new DoubloonPrizeMessage(report.getPlayerID(), report.isQuiet(), report.getPrizes());
        }
        return msg;
    }

    /*
     * listen for report
     */
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(DoubloonPrizeReport.class);
        return result;
    }
}
