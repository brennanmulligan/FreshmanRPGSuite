package sequencetests;

import communication.messages.AdventureStateChangeMessage;
import communication.messages.KeyInputMessage;
import datasource.DatabaseException;
import datatypes.AdventureStateEnum;
import model.ClientModelTestUtilities;
import model.Command;
import model.CommandKeyInputSent;
import model.IllegalAdventureChangeException;
import model.IllegalQuestChangeException;
import model.InteractObjectManager;
import model.MapManager;
import model.MessageFlow;
import model.Player;
import model.PlayerManager;
import model.QuestManager;
import model.SequenceTest;
import model.ServerType;
import datatypes.AdventuresForTest;
import datatypes.InteractableItemsForTest;
import datatypes.PlayersForTest;

/**
 * An adventure is completed by interacting with an item
 * @author Truc Chau & Elisabeth Ostrow
 */
public class AdventureCompletionItemInteractSequenceTest extends SequenceTest
{
	private MessageFlow[] sequence =
			{
					new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
							new KeyInputMessage("e"), true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							//last two arguments should be false and null (bc this isn't a real life adventure)
							new AdventureStateChangeMessage(PlayersForTest.MERLIN.getPlayerID(), AdventuresForTest.QUEST13_ADVENTURE_1.getQuestID(),
									AdventuresForTest.QUEST13_ADVENTURE_1.getAdventureID(), AdventuresForTest.QUEST13_ADVENTURE_1.getAdventureDescription(),
									AdventureStateEnum.COMPLETED, false, null), true),
			};

	/**
	 * Test that Adventures can be completed by interaction
	 */
	public AdventureCompletionItemInteractSequenceTest()
	{
		for (MessageFlow mf : sequence)
		{
			messageSequence.add(mf);
		}

		serverList.add(ServerType.THIS_PLAYER_CLIENT);
	}

	/**
	 * Player needs to press "e" first
	 */
	@Override
	public Command getInitiatingCommand()
	{
		return new CommandKeyInputSent("e");
	}

	/**
	 * Message is sent from client side first
	 */
	@Override
	public ServerType getInitiatingServerType()
	{
		return ServerType.THIS_PLAYER_CLIENT;
	}

	/**
	 * @see model.SequenceTest#getInitiatingPlayerID()
	 */
	@Override
	public int getInitiatingPlayerID()
	{
		return PlayersForTest.MERLIN.getPlayerID();
	}

	/**
	 * puts the player close to the interactable item
	 * so that the quest can be completed
	 */
	@Override
	public void setUpMachines()
	{
		MapManager.getSingleton().changeToNewFile(PlayersForTest.MERLIN.getMapName());
		ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);

		PlayerManager playerManager = PlayerManager.getSingleton();
		playerManager.addPlayer(PlayersForTest.MERLIN.getPlayerID());

		//set up player position that is near an interactable item
		Player p = playerManager.getPlayerFromID(PlayersForTest.MERLIN.getPlayerID());
		p.setPlayerPosition(InteractableItemsForTest.CHEST.getPosition());

		//get an adventure of completion type interact with item, and give its quest to the player
		int questId = AdventuresForTest.QUEST13_ADVENTURE_1.getQuestID();

		try
		{
			QuestManager.getSingleton().triggerQuest(p.getPlayerID(), questId);

		}
		catch (IllegalAdventureChangeException | IllegalQuestChangeException | DatabaseException e)
		{

			e.printStackTrace();
		}

		InteractObjectManager.getSingleton();
	}

	/**
	 * @see model.SequenceTest#resetDataGateways()
	 */
	@Override
	public void resetDataGateways()
	{
		PlayerManager.resetSingleton();
		QuestManager.resetSingleton();
		InteractObjectManager.resetSingleton();

	}
}
