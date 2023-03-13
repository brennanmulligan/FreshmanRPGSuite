package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.LoginMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.reports.LoginInitiatedReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * Takes the information given to us when MovementNotifier updates and
 * translates it to the appropriate MovementMessage. It turns out this is pretty
 * trivial since the MovementNotifier pushes a MovementMessage
 *
 * @author merlin
 */
public class LoginMessagePacker extends MessagePacker
{
    /**
     *
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        if (object.getClass() != LoginInitiatedReport.class)
        {
            throw new IllegalArgumentException(
                    "LoginMessagePacker cannot pack messages of type "
                            + object.getClass());
        }
        LoginInitiatedReport report = (LoginInitiatedReport) object;
        Message msg = new LoginMessage(report.getPlayerName(), report.getPassword(), report.isQuiet());
        return msg;
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result =
                new ArrayList<>();
        result.add(LoginInitiatedReport.class);
        return result;
    }

}
