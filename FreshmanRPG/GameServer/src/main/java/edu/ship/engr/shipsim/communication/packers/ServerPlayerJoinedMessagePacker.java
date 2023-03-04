package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerJoinedMessage;
import edu.ship.engr.shipsim.model.reports.AddExistingPlayerReport;
import edu.ship.engr.shipsim.model.reports.PlayerConnectionReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * Packs a message telling clients that a new player has joined this area server
 *
 * @author Merlin
 */
public class ServerPlayerJoinedMessagePacker extends MessagePacker
{

    /**
     * @see MessagePacker#pack(SendMessageReport)
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        if (object.getClass().equals(PlayerConnectionReport.class))
        {
            PlayerConnectionReport report = (PlayerConnectionReport) object;

            PlayerJoinedMessage msg = new PlayerJoinedMessage(report.getPlayerID(), report.isQuiet(), report.getPlayerName(),
                    report.getVanity(), report.getPosition(), report.getCrew(), report.getMajor(), report.getSection(),
                    report.getOwnedItems());
            return msg;
        }
        else if (object.getClass().equals(AddExistingPlayerReport.class))
        {
            AddExistingPlayerReport report = (AddExistingPlayerReport) object;
            if (report.getRecipientPlayerID() == getAccumulator().getPlayerID())
            {
                PlayerJoinedMessage msg = new PlayerJoinedMessage(report.getPlayerID(), report.isQuiet(), report.getPlayerName(),
                        report.getVanity(), report.getPosition(), report.getCrew(), report.getMajor(), report.getSection());
                return msg;
            }
        }
        return null;
    }

    /**
     * @see MessagePacker#getReportTypesWePack()
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result = new ArrayList<>();
        result.add(PlayerConnectionReport.class);
        result.add(AddExistingPlayerReport.class);
        return result;
    }

}
