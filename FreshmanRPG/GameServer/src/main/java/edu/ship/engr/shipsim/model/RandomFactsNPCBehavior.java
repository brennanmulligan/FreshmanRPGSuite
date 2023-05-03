package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.RandomFactDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.RandomFactsTableDataGateway;
import edu.ship.engr.shipsim.datatypes.ChatType;

import java.util.ArrayList;

/**
 * The behavior for an NPC that spouts random facts
 *
 * @author merlin
 */
public class RandomFactsNPCBehavior extends NPCBehavior
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    static final int CHAT_DELAY_SECONDS = 5;
    private int chatDelayCounter = 0;
    private ArrayList<RandomFactDTO> facts;

    /**
     * @param playerID the player ID for this NPC
     *                 Overloaded constructor with String parameter to account for filepath column in behavior db.
     */
    public RandomFactsNPCBehavior(int playerID)
    {
        super(playerID);
        RandomFactsTableDataGateway gateway =
                RandomFactsTableDataGateway.getSingleton();
        try
        {
            facts = gateway.getAllFactsForNPC(playerID);
        }
        catch (DatabaseException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void doTimedEvent()
    {
        if (chatDelayCounter == 0 && facts.size() > 0)
        {
            int factNumber = (int) (Math.random() * facts.size());
            ChatManager.getSingleton()
                    .sendChatToClients(playerID, 0, facts.get(factNumber).getFactText(), PlayerManager.getSingleton()
                                    .getPlayerFromID(playerID)
                                    .getPosition(),
                            ChatType.Local);
        }
        chatDelayCounter = (chatDelayCounter + 1) % CHAT_DELAY_SECONDS;
    }


    /**
     * @return the facts this NPC could spout
     */
    public ArrayList<RandomFactDTO> getFacts()
    {
        return facts;
    }

    @Override
    protected ArrayList<Class<? extends Report>> getReportTypes()
    {
        return null;
    }


    /**
     * @see ReportObserver#receiveReport(Report)
     */
    @Override
    public void receiveReport(Report report)
    {
    }

}
