package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.model.reports.NPCChatReport;
import edu.ship.engr.shipsim.model.reports.PlayerFinishedInitializingReport;

import java.util.ArrayList;

/**
 * Defines the behavior of the red hat
 *
 * @author merlin
 */
public class RedHatBehavior extends NPCBehavior
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * Initialize the RedHatBehavior
     *
     * @param playerID this NPC's id
     */
    public RedHatBehavior(int playerID)
    {
        super(playerID);
        setUpListening();
    }

    /**
     * Receives SendChatMessageReports and PlayerConnectionReports When a player
     * connects and has the default_appearance sends a welcome message to them
     * asking if they are ready to be sorted. When a player connects and does not
     * have the default_appearance tells them to complete the introduction quest.
     *
     * @see ReportObserver#receiveReport(Report)
     */
    @Override
    public void receiveReport(Report report)
    {
        if (report instanceof PlayerFinishedInitializingReport)
        {
            PlayerFinishedInitializingReport playerCon = (PlayerFinishedInitializingReport) report;
            if (playerCon.getAppearanceType().equals("default_player"))
            {
                ChatManager.getSingleton().sendChatToClients(playerID, 0,
                        playerCon.getPlayerName() + " welcome to the game. Are you ready to be sorted?",
                        new Position(9, 7), ChatType.Zone);
            }
            else
            {
                ChatManager.getSingleton().sendChatToClients(playerID, 0,
                        "Welcome back " + playerCon.getPlayerName()
                                + ". Complete the introduction quest to leave this area.",
                        new Position(9, 7), ChatType.Zone);
            }
        }
        if (report instanceof NPCChatReport)
        {
            NPCChatReport chatReport = (NPCChatReport) report;

            Player player = PlayerManager.getSingleton().getPlayerFromID(chatReport.getSenderID());
            if (player.getAppearanceType().equals("default_player"))
            {
                if (chatReport.getChatText().toLowerCase().equals("yes"))
                {
                    ChatManager.getSingleton().sendChatToClients(playerID, 0,
                            player.getPlayerName() + " your crew is " + player.getCrew().getCrewName() + ".",
                            new Position(9, 7), ChatType.Zone);

//					CommandChangePlayerAppearance cmd = new CommandChangePlayerAppearance(player.getPlayerID(),
//							player.getCrew().getCrewAppearanceType());
//					ModelFacade.getSingleton().queueCommand(cmd);
                }
                else
                {
                    ChatManager.getSingleton().sendChatToClients(playerID, 0,
                            player.getPlayerName() + " respond 'Yes' to get your crew.", new Position(9, 7),
                            ChatType.Zone);
                }
            }

        }
    }

    /**
     * @see NPCBehavior#doTimedEvent()
     */
    @Override
    protected void doTimedEvent()
    {
        // we don't do anything at regular intervals
    }

    /**
     * @see NPCBehavior#getReportTypes()
     */
    @Override
    protected ArrayList<Class<? extends Report>> getReportTypes()
    {
        ArrayList<Class<? extends Report>> reportTypes = new ArrayList<>();
        reportTypes.add(NPCChatReport.class);
        reportTypes.add(PlayerFinishedInitializingReport.class);

        return reportTypes;
    }
}