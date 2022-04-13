package model;

import java.util.ArrayList;

import communication.messages.InitializeThisClientsPlayerMessage;
import dataDTO.ClientPlayerQuestStateDTO;
import datasource.LevelRecord;

/**
 * Command to overwrite ThisClientsPlayers quest list
 *
 * @author Merlin
 *
 */
public class CommandOverwriteQuestState extends Command
{

	private ArrayList<ClientPlayerQuestStateDTO> clientPlayerQuestList;
	private LevelRecord record;
	private int expPoints;

	/**
	 * Initializes clientPlayerQuestList
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
	public ArrayList<ClientPlayerQuestStateDTO> getClientPlayerQuestList()
	{
		return clientPlayerQuestList;
	}

}
