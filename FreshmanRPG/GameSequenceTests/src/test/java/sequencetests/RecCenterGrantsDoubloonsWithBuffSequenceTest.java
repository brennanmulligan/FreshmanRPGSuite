package sequencetests;

import java.util.ArrayList;

import communication.messages.ChatMessage;
import communication.messages.DoubloonsChangedMessage;
import datasource.DatabaseException;
import datatypes.ChatType;
import datatypes.ChatTextDetails;
import model.ClientModelTestUtilities;
import model.Command;
import model.CommandChatMessageSent;
import model.InteractObjectManager;
import model.MapManager;
import model.MessageFlow;
import model.NPC;
import model.NPCQuestion;
import model.OptionsManager;
import model.PlayerManager;
import model.QuizBotBehavior;
import model.SequenceTest;
import model.ServerType;
import datatypes.NPCQuestionsForTest;
import datatypes.PlayersForTest;

/**
 * If a player has buff points, they should get a bonus in their doubloons.
 *
 * @author Adam Pine, Truc Chau, Andy Kim
 */
public class RecCenterGrantsDoubloonsWithBuffSequenceTest extends SequenceTest
{

	/**
	 * the flow of messages to occur
	 */
	private MessageFlow[] sequence = new MessageFlow[]{
			new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
					new ChatMessage(PlayersForTest.JEFF.getPlayerID(), 0,
							NPCQuestionsForTest.ONE.getA(), PlayersForTest.JEFF.getPosition(), ChatType.Zone),
					false),
			new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
					new DoubloonsChangedMessage(PlayersForTest.JEFF.getPlayerID(),
							PlayersForTest.JEFF.getDoubloons() + 2, PlayersForTest.JEFF.getBuffPool() - 1),
					false)

	};

	/**
	 * runs through the message flow
	 */
	public RecCenterGrantsDoubloonsWithBuffSequenceTest()
	{
		for (MessageFlow mf : sequence)
		{
			messageSequence.add(mf);
		}
		serverList.add(ServerType.THIS_PLAYER_CLIENT);
		serverList.add(ServerType.AREA_SERVER);
	}

	/**
	 * what starts the sequence of messages
	 */
	@Override
	public Command getInitiatingCommand()
	{
		return new CommandChatMessageSent(new ChatTextDetails(NPCQuestionsForTest.ONE.getQ(), ChatType.System));
	}

	/**
	 * the server the command starts on
	 */
	@Override
	public ServerType getInitiatingServerType()
	{
		return ServerType.THIS_PLAYER_CLIENT;
	}

	/**
	 * the player for these tests
	 */
	@Override
	public int getInitiatingPlayerID()
	{
		return PlayersForTest.JEFF.getPlayerID();
	}

	/**
	 * any setup required
	 * @throws DatabaseException  shouldn't
	 */
	@Override
	public void setUpMachines() throws DatabaseException
	{
		MapManager.getSingleton().changeToNewFile(PlayersForTest.JEFF.getMapName());
		ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.JEFF);
		PlayerManager.getSingleton().addPlayer(PlayersForTest.JEFF.getPlayerID());

		OptionsManager.getSingleton().setMapName("recCenter.tmx");
		PlayerManager.getSingleton().loadNpcs(true);
		ArrayList<NPC> npcs = PlayerManager.getSingleton().getNpcs();
		for (NPC npc : npcs)
		{
			if (npc.getBehaviorClass().equals(QuizBotBehavior.class))
			{
				((QuizBotBehavior) npc.getBehavior()).setExpectedQuestion(
						NPCQuestion.getSpecificQuestion(NPCQuestionsForTest.ONE.getQuestionID()));
			}
		}
	}

	/**
	 * reset data
	 */
	@Override
	public void resetDataGateways()
	{
		PlayerManager.resetSingleton();
		InteractObjectManager.resetSingleton();
	}
}
