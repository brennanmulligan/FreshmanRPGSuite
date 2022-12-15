package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.communication.messages.InitializeThisClientsPlayerMessage;
import edu.ship.engr.shipsim.dataDTO.ClientPlayerQuestStateDTO;
import edu.ship.engr.shipsim.datasource.LevelRecord;

import java.util.List;

/**
 * Command to overwrite ThisClientsPlayers quest list
 *
 * @author Merlin
 */
public class CommandOverwriteQuestState extends Command
{

    private List<ClientPlayerQuestStateDTO> clientPlayerQuestList;
    private LevelRecord record;
    private int expPoints;

    /**
     * Initializes clientPlayerQuestList
     *
     * @param msg message that contains new clientPlayerQuestList
     */
    public CommandOverwriteQuestState(InitializeThisClientsPlayerMessage msg)
    {
        this.clientPlayerQuestList = msg.getClientPlayerQuestList();

        this.record = msg.getLevel();
        this.expPoints = msg.getExperiencePts();
    }

    @Override
    boolean execute()
    {
        ClientPlayerManager.getSingleton().getThisClientsPlayer().overwriteQuestList(clientPlayerQuestList);
        ClientPlayerManager.getSingleton().getThisClientsPlayer().setLevelInfo(record, expPoints);
        return true;
    }

    /**
     * @return the clientPlayerQuestList
     */
    public List<ClientPlayerQuestStateDTO> getClientPlayerQuestList()
    {
        return clientPlayerQuestList;
    }

}
