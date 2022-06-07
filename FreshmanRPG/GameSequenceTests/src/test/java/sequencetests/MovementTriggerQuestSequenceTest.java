package sequencetests;

import communication.messages.OtherPlayerMovedMessage;
import communication.messages.PlayerMovedMessage;
import communication.messages.QuestStateChangeMessage;
import datatypes.PlayersForTest;
import datatypes.QuestStateEnum;
import datatypes.QuestsForTest;
import model.*;

/**
 * Defines the protocol for a successful login sequence
 *
 * @author Merlin
 */
public class MovementTriggerQuestSequenceTest extends SequenceTest
{

    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new PlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
                            QuestsForTest.THE_LITTLE_QUEST.getPosition()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
                            new OtherPlayerMovedMessage(PlayersForTest.MATT.getPlayerID(),
                                    QuestsForTest.THE_LITTLE_QUEST.getPosition()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new QuestStateChangeMessage(PlayersForTest.MATT.getPlayerID(),
                                    QuestsForTest.THE_LITTLE_QUEST.getQuestID(),
                                    QuestsForTest.THE_LITTLE_QUEST.getQuestTitle(),
                                    QuestsForTest.THE_LITTLE_QUEST.getQuestDescription(),
                                    QuestStateEnum.TRIGGERED), true)};

    public MovementTriggerQuestSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.OTHER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);

        interactions.add(new Interaction(
                new CommandClientMovePlayer(PlayersForTest.MATT.getPlayerID(),
                        QuestsForTest.THE_LITTLE_QUEST.getPosition()),
                PlayersForTest.MATT.getPlayerID(), ServerType.THIS_PLAYER_CLIENT,
                sequence));
    }

    /**
     * @see model.SequenceTest#resetNecessarySingletons()
     */
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
        QuestManager.resetSingleton();
    }

    /**
     * @see model.SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines()
    {
        MapManager.getSingleton().changeToNewFile(PlayersForTest.MATT.getMapName(),"ALL" );
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MATT);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MATT.getPlayerID());
    }
}
