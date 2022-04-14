package sequencetests;

import communication.messages.ObjectiveStateChangeMessage;
import communication.messages.KeyInputMessage;
import datasource.DatabaseException;
import datatypes.ObjectiveStateEnum;
import model.ClientModelTestUtilities;
import model.Command;
import model.CommandKeyInputSent;
import model.IllegalObjectiveChangeException;
import model.IllegalQuestChangeException;
import model.InteractObjectManager;
import model.MapManager;
import model.MessageFlow;
import model.Player;
import model.PlayerManager;
import model.QuestManager;
import model.SequenceTest;
import model.ServerType;
import datatypes.ObjectivesForTest;
import datatypes.InteractableItemsForTest;
import datatypes.PlayersForTest;

/**
 * An objective is completed by interacting with an item
 * @author Truc Chau & Elisabeth Ostrow
 */
public class ObjectiveCompletionItemInteractSequenceTest extends SequenceTest
{
	private MessageFlow[] sequence =
			{
					new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
							new KeyInputMessage("e"), true),
					new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
							//last two arguments should be false and null (bc this isn't a real life objective)
							new ObjectiveStateChangeMessage(PlayersForTest.MERLIN.getPlayerID(), ObjectivesForTest.QUEST13_OBJECTIVE_1.getQuestID(),
									ObjectivesForTest.QUEST13_OBJECTIVE_1.getObjectiveID(), ObjectivesForTest.QUEST13_OBJECTIVE_1.getObjectiveDescription(),
									ObjectiveStateEnum.COMPLETED, false, null), true),
			};

	/**
	 * Test that Objectives can be completed by interaction
	 */
	public ObjectiveCompletionItemInteractSequenceTest()
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

		//get an objective of completion type interact with item, and give its quest to the player
		int questId = ObjectivesForTest.QUEST13_OBJECTIVE_1.getQuestID();

		try
		{
			QuestManager.getSingleton().triggerQuest(p.getPlayerID(), questId);

		}
		catch (IllegalObjectiveChangeException | IllegalQuestChangeException | DatabaseException e)
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
