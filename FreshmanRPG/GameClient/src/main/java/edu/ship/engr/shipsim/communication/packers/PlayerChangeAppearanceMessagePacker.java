package edu.ship.engr.shipsim.communication.packers;

import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.communication.messages.PlayerChangeAppearanceMessage;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.model.QualifiedObservableReport;
import edu.ship.engr.shipsim.model.reports.ChangePlayerAppearanceReport;

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
    public Message pack(QualifiedObservableReport object)
    {
        ChangePlayerAppearanceReport cp = (ChangePlayerAppearanceReport) object;
        return new PlayerChangeAppearanceMessage(cp.getPlayerID(), (ArrayList<VanityDTO>) cp.getVanities());
    }

    /**
     * Tell which type of report we pack
     *
     * @return the type of report we pack
     */
    @Override
    public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypesWePack()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> result =
                new ArrayList<>();
        result.add(ChangePlayerAppearanceReport.class);
        return result;
    }
}
