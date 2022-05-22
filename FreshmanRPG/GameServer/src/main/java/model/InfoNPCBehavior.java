package model;

import dataDTO.InfoNPCDTO;
import datasource.*;
import datatypes.ChatType;
import datatypes.PlayersForTest;
import datatypes.Position;
import model.reports.NPCChatReport;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * The Behavior of the Mowrey Info NPC
 * Has a timed event to let players know of the responses they can give to trigger more information from our NPC
 * @author Marlee L and Josh K class of 2022
 */
public class InfoNPCBehavior extends NPCBehavior
{
    private static final long serialVersionUID = 1L;
    private ArrayList<InfoNPCDTO> info;
    private HashMap<String, String> responseAndInfoHashMap;
    static final int CHAT_DELAY_SECONDS = 18;
    private int chatDelayCounter = 0;


    /**
     * Constructor to initalize the MowreyInfoNPC Behavior
     * @param playerID the MowreyInfoNPC unqiue id
     */
    public InfoNPCBehavior(int playerID, String filePath)
    {

        super(playerID);
        setUpListening();

        InfoNPCTableDataGateway gateway =
                (InfoNPCTableDataGateway) TableDataGatewayManager.getSingleton()
                        .getTableGateway("InfoNPC");

        try
        {
            info = gateway.getAllInfoForNPC(playerID);
            responseAndInfoHashMap = new HashMap<>();
            HashMap<Integer, Integer> playersGreeted = new HashMap<>();
            transferInfoDTOContentsToHashMap();
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * for quicker look-ups, just transfer it into a hashmap. Not sure if this is ok or I'm violating layer separation.
     */
    private void transferInfoDTOContentsToHashMap()
    {
        info.forEach((infodto) -> responseAndInfoHashMap.put(infodto.getResponse(), infodto.getInformation()));
    }

    /**
     * Let players know at 15 second intervals what they can enter to trigger more inforation.
     */
    @Override
    protected void doTimedEvent()
    {
        if (chatDelayCounter == 0)
        {
            ChatManager.getSingleton().sendChatToClients(playerID, 0, "Hello and welcome to Mowry Tutoring Center!\n" +
                            "If you would like more information on scheduling an appointment with the tutoring center, please type \"scheduling\" in the chat. " +
                            "If you would like more information on the services we provide, please type \"services\" in the chat. ",
                    new Position(48, 56), ChatType.Local);
        }
        chatDelayCounter = (chatDelayCounter + 1) % CHAT_DELAY_SECONDS;
    }

    /**
     * Get report types that this class watches for. We only need to listen for Chat messages from local chat for now.
     */
    @Override
    protected ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
    {
        ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<>();
        reportTypes.add(NPCChatReport.class);
        return reportTypes;
    }


    /**
     *
     * @param incomingReport
     * We check to see if the incoming local chat message was one of the user reponses that will trigger more
     * information from our NPC.
     */

    @Override
    public void receiveReport(QualifiedObservableReport incomingReport)
    {
        if (incomingReport instanceof NPCChatReport)
        {

            NPCChatReport chatReport = (NPCChatReport) incomingReport;
            String userReply = chatReport.getChatText().toLowerCase().replaceAll(" ", "");


            if (chatReport.getType() == ChatType.Local && chatReport.getSenderID() != PlayersForTest.MOWREY_FRONTDESK_NPC.getPlayerID()
            && responseAndInfoHashMap.containsKey(userReply))
            {
                ChatManager.getSingleton().sendChatToClients(playerID, chatReport.getSenderID(), responseAndInfoHashMap.get(userReply), new Position(48, 56), ChatType.Local);


            }
        }
    }
}
