package edu.ship.engr.shipsim.sequencetests;

import edu.ship.engr.shipsim.communication.messages.ObjectiveNotificationCompleteMessage;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.*;

/**
 * Tests that a notification is sent when an objective is completed
 *
 * @author Ronald Sease & Evan Stevenson
 */
public class ObjectiveNotificationCompleteSequenceTest extends SequenceTest
{
    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new ObjectiveNotificationCompleteMessage(
                            PlayersForTest.MERLIN.getPlayerID(), false, 1, 1), false)};

    public ObjectiveNotificationCompleteSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);
        interactions.add(new Interaction(new CommandObjectiveNotificationComplete(
                PlayersForTest.MERLIN.getPlayerID(), 1, 1),
                PlayersForTest.MERLIN.getPlayerID(), ServerType.THIS_PLAYER_CLIENT,
                0, sequence));
    }

    /**
     * @see edu.ship.engr.shipsim.model.SequenceTest#resetNecessarySingletons()
     */
    @Override
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
    }

    /**
     * @see edu.ship.engr.shipsim.model.SequenceTest#setUpMachines()
     */
    @Override
    public void setUpMachines()
    {
        MapManager.getSingleton().changeToNewFile(PlayersForTest.MERLIN.getMapName(),
                "MERLINE");
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(PlayersForTest.MERLIN);
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
    }
}
