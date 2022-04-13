package model;

import java.util.ArrayList;

import dataDTO.RandomFactDTO;
import datasource.DatabaseException;
import datasource.RandomFactsTableDataGateway;
import datasource.RandomFactsTableDataGatewayMock;
import datasource.RandomFactsTableDataGatewayRDS;
import datatypes.ChatType;

/**
 * The behavior for an NPC that spouts random facts
 * @author merlin
 *
 */
public class RandomFactsNPCBehavior extends NPCBehavior
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	static final int CHAT_DELAY_SECONDS = 5;
	private int chatDelayCounter = 0;
	private RandomFactsTableDataGateway gateway;
	private ArrayList<RandomFactDTO> facts;

	/**
	 * @param playerID the player ID for this NPC
	 */
	public RandomFactsNPCBehavior(int playerID)
	{
		super(playerID);
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			gateway = RandomFactsTableDataGatewayMock.getSingleton();
		}
		else
		{
			gateway = RandomFactsTableDataGatewayRDS.getSingleton();
		}
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
									.getPlayerPosition(),
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
	protected ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		return null;
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
	}

}
