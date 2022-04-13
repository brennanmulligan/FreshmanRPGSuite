package model;

import communication.messages.InitializeThisClientsPlayerMessage;

/**
 * @author Matthew Croft
 * @author Evan Stevenson
 *
 */
public class CommandKnowledgePointsChanged extends Command
{


	private int knowledge;
	private int playerID;
	private int buffPool;

	/**
	 * constructor for the command
	 * @param msg InitializeClientsPlayerMessage which contains all the data to
	 * initialize this client player
	 */
	public CommandKnowledgePointsChanged(InitializeThisClientsPlayerMessage msg)
	{
		this.knowledge = msg.getKnowledgePoints();
	}


	/**
	 * @param playerID of the player
	 * @param knowledgePoints of the player
	 * @param buffPool of the player
	 */
	public CommandKnowledgePointsChanged(int playerID, int knowledgePoints, int buffPool)
	{
		this.knowledge = knowledgePoints;
		this.playerID = playerID;
		this.buffPool = buffPool;
	}

	/**
	 * @return the knowledgePoints for this player
	 */
	public int getKnowledge()
	{
		return knowledge;
	}

	/**
	 * @return the playerID for this player
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	@Override
	boolean execute()
	{
		ThisClientsPlayer thisClientsPlayer = ClientPlayerManager.getSingleton().getThisClientsPlayer();
		thisClientsPlayer.knowledgePointsAndBuffPoolChanged(knowledge, buffPool);
		return true;
	}

}
