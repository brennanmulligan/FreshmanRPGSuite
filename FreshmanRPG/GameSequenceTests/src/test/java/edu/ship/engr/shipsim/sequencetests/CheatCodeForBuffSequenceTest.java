package edu.ship.engr.shipsim.sequencetests;

import edu.ship.engr.shipsim.communication.messages.BuffMessage;
import edu.ship.engr.shipsim.communication.messages.ChatMessageToServer;
import edu.ship.engr.shipsim.datatypes.ChatTextDetails;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.model.cheatCodeBehaviors.BuffBehavior;

/**
 * Tests that when a Client presses e the server adds exp points to the player
 *
 * @author ed9737, sk5587, tc9538
 */
public class CheatCodeForBuffSequenceTest extends SequenceTest
{

    /**
     * the sequence of messages to occur
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final MessageFlow[] sequence =
            {new MessageFlow(ServerType.THIS_PLAYER_CLIENT, ServerType.AREA_SERVER,
                    new ChatMessageToServer(PlayersForTest.JAWN.getPlayerID(), 0, false,
                            "Magic Buff", PlayersForTest.JAWN.getPosition(),
                            ChatType.Local), true),
                    new MessageFlow(ServerType.AREA_SERVER, ServerType.THIS_PLAYER_CLIENT,
                            new BuffMessage(PlayersForTest.JAWN.getPlayerID(), false,
                                    BuffBehavior.BUFF_VALUE), true)

            };

    /**
     * Runs through the message flow
     */
    public CheatCodeForBuffSequenceTest()
    {
        serverList.add(ServerType.THIS_PLAYER_CLIENT);
        serverList.add(ServerType.AREA_SERVER);
        interactions.add(new Interaction(new CommandChatMessageSent(
                new ChatTextDetails("Magic Buff", ChatType.Local)),
                PlayersForTest.JAWN.getPlayerID(), ServerType.THIS_PLAYER_CLIENT,
                0, sequence));
    }

    /**
     * Clear used data
     */
    @Override
    public void resetNecessarySingletons()
    {
        PlayerManager.resetSingleton();
        InteractObjectManager.resetSingleton();
    }

    /**
     * generic setup
     */
    @Override
    public void setUpMachines()
    {
        PlayersForTest jawn = PlayersForTest.JAWN;
        ClientPlayerManager.resetSingleton();
        MapManager.getSingleton().changeToNewFile(jawn.getMapName(), "STUFF");
        ClientModelTestUtilities.setUpThisClientsPlayerForTest(jawn);
        ClientPlayerManager.getSingleton().getPlayerFromID(jawn.getPlayerID())
                .setPosition(jawn.getPosition());

        // set up players through player manager
        PlayerManager pm = PlayerManager.getSingleton();
        pm.addPlayer(jawn.getPlayerID());
    }

}
