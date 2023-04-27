package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.PlayerJoinedMessage;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.CommandInitializePlayer;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Make sure the message is handled properly
 *
 * @author Merlin
 */
@GameTest("GameClient")
@ResetClientModelFacade
public class PlayerJoinedMessageHandlerTest
{
    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        PlayerJoinedMessageHandler h = new PlayerJoinedMessageHandler();
        assertEquals(PlayerJoinedMessage.class, h.getMessageTypeWeHandle());
    }

    /**
     * We should add the player to the player manager
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void test() throws InterruptedException
    {
        VanityDTO body = new VanityDTO(2, "", "", "character_clothed", VanityType.BODY, 0
        );

        VanityDTO hat = new VanityDTO(-1, "", "", "hat_duck", VanityType.HAT, 0
        );

        PlayerJoinedMessage msg = new PlayerJoinedMessage(PlayersForTest.JOSH.getPlayerID(), false, PlayersForTest.JOSH.name(),
                PlayersForTest.JOSH.getVanityItems(), PlayersForTest.JOSH.getPosition(),
                PlayersForTest.JOSH.getCrew(), PlayersForTest.JOSH.getMajor(), PlayersForTest.JOSH.getSection());
        PlayerJoinedMessageHandler handler = new PlayerJoinedMessageHandler();
        handler.process(msg);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());

        CommandInitializePlayer cmd = (CommandInitializePlayer) ClientModelFacade.getSingleton().getNextCommand();

        assertEquals(PlayersForTest.JOSH.getPlayerID(), cmd.getPlayerID());
        assertEquals(PlayersForTest.JOSH.getPlayerName().toUpperCase(), cmd.getPlayerName());

        assertEquals(PlayersForTest.JOSH.getPosition(), cmd.getPosition());
        assertEquals(PlayersForTest.JOSH.getCrew(), cmd.getCrew());
        assertEquals(PlayersForTest.JOSH.getMajor(), cmd.getMajor());
        assertEquals(PlayersForTest.JOSH.getSection(), cmd.getSection());
    }

}
