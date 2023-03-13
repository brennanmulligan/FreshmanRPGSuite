package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerChangeAppearanceMessage;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.model.reports.ChangePlayerAppearanceReport;
import edu.ship.engr.shipsim.model.reports.SendMessageReport;

import java.util.ArrayList;

/**
 * Packer for the PlayerChangeAppearanceMessage
 */
public class PlayerChangeAppearanceMessagePacker extends MessagePacker
{
    /**
     * Build a message describing an event
     *
     * @param object the object pushed by the observable in its notification
     * @return the appropriate message
     */
    @Override
    public Message pack(SendMessageReport object)
    {
        ChangePlayerAppearanceReport cp = (ChangePlayerAppearanceReport) object;
        return new PlayerChangeAppearanceMessage(cp.getPlayerID(), cp.isQuiet(), (ArrayList<VanityDTO>) cp.getVanities());
    }

    /**
     * Tell which type of report we pack
     *
     * @return the type of report we pack
     */
    @Override
    public ArrayList<Class<? extends SendMessageReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends SendMessageReport>> result =
                new ArrayList<>();
        result.add(ChangePlayerAppearanceReport.class);
        return result;
    }
}
