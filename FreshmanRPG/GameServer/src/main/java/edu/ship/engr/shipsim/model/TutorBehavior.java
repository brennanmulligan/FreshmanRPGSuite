package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.NPCChatReport;

import java.util.ArrayList;

/**
 * @author cr5603
 */
public class TutorBehavior extends NPCBehavior
{
    private static final long serialVersionUID = 1L;

    /**
     * Creates the behavior for tutors to respond to local messages
     *
     * @param playerID the tutor's unique id
     */
    public TutorBehavior(int playerID)
    {
        super(playerID);
        setUpListening();
    }

    /**
     * @see ReportObserver#receiveReport(Report)
     */
    @Override
    public void receiveReport(Report report)
    {
        if (report instanceof NPCChatReport)
        {
            NPCChatReport chatReport = (NPCChatReport) report;

            if (chatReport.getType() == ChatType.Local && chatReport.getSenderID() != playerID)
            {
                ChatManager.getSingleton().sendChatToClients(playerID, 0, "Hello, student", new Position(10, 3), ChatType.Local);
            }

        }
    }

    /**
     * @see NPCBehavior#doTimedEvent()
     */
    @Override
    protected void doTimedEvent()
    {
        //Does nothing for tutors
    }

    /**
     * @see NPCBehavior#getReportTypes()
     */
    @Override
    protected ArrayList<Class<? extends Report>> getReportTypes()
    {
        ArrayList<Class<? extends Report>> reportTypes = new ArrayList<>();
        reportTypes.add(NPCChatReport.class);
        return reportTypes;
    }


}
