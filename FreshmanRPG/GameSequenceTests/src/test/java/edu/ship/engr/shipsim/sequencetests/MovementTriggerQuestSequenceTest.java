package edu.ship.engr.shipsim.sequencetests;

import edu.ship.engr.shipsim.communication.messages.OtherPlayerMovedMessage;
import edu.ship.engr.shipsim.communication.messages.PlayerMovedMessage;
import edu.ship.engr.shipsim.communication.messages.QuestStateChangeMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import edu.ship.engr.shipsim.model.*;

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
                    new PlayerMovedMessage(PlayersForTest.MATT.getPlayerID(), false,
                            QuestsForTest.THE_LITTLE_QUEST.getPosition()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.OTHER_CLIENT,
                            new OtherPlayerMovedMessage(PlayersForTest.MATT.getPlayerID(), false,
                                    QuestsForTest.THE_LITTLE_QUEST.getPosition()), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new QuestStateChangeMessage(PlayersForTest.MATT.getPlayerID(), false,
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
                0, sequence));
    }

    /**
     * @see edu.ship.engr.shipsim.model.SequenceTest#resetNecessarySingletons()
     */
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
        QuestManager.resetSingleton();
    }

    /**
     * @see edu.ship.engr.shipsim.model.SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines()
    {
        MapManager.getSingleton().changeToNewFile(PlayersForTest.MATT.getMapName(), "ALL");
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MATT);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MATT.getPlayerID());
    }
}
