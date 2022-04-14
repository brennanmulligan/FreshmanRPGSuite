package communication.packers;

import communication.messages.Message;
import communication.messages.PlayerChangeAppearanceMessage;
import dataDTO.VanityDTO;
import model.QualifiedObservableReport;
import model.reports.ChangePlayerAppearanceReport;

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
