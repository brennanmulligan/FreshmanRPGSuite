package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.cheatCodeBehaviors.MockCheatCodeBehavior;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the Server's CheatCodeManager
 *
 * @author merlin
 */
@GameTest("GameServer")
public class CheatCodeManagerTest
{

    private CheatCodeManager mgr;

    /**
     * Start fresh for each test
     */
    @BeforeEach
    public void reset()
    {
        mgr = new CheatCodeManager();
    }

    /**
     * It needs to find all of the cheat behaviors (including our mock one for
     * testing)
     */
    @Test
    public void registersBehaviors()
    {
        assertTrue(mgr.hasCheatCodeBehavior(MockCheatCodeBehavior.class));
    }

    /**
     * Makes sure that behaviors are fired for every chat type
     */
    @Test
    public void tellsBehaviorToFireOnAllChatTypes()
    {
        PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        for (ChatType type : ChatType.values())
        {
            tellsBehaviorToFireIfChatTypeIs(type);
        }
    }

    private void tellsBehaviorToFireIfChatTypeIs(ChatType type)
    {
        MockCheatCodeBehavior.gaveCheat = false;
        assertTrue(mgr.handleChatTextForCheatBehaviors(PlayersForTest.MERLIN.getPlayerID(), MockCheatCodeBehavior.CHAT_TEXT));
        assertTrue(MockCheatCodeBehavior.gaveCheat);
    }

    /**
     * If the string isn't a valid cheat code, we should get a false return value
     */
    @Test
    public void returnsFalseIfNotACheatCode()
    {
        MockCheatCodeBehavior.gaveCheat = false;
        assertFalse(mgr.handleChatTextForCheatBehaviors(PlayersForTest.MERLIN.getPlayerID(), MockCheatCodeBehavior.CHAT_TEXT + 'Z'));
    }
}
