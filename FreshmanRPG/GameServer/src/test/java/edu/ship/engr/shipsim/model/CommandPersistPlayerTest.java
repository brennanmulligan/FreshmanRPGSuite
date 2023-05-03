package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test that a player is persisted
 *
 * @author Steve
 */
@GameTest("GameServer")
@ResetPlayerManager
public class CommandPersistPlayerTest
{
    /**
     * Test that persistence happens
     *
     * @throws DatabaseException           shouldn't
     * @throws IllegalQuestChangeException the state changed illegally
     */
    @Test
    public void testPersists() throws DatabaseException, IllegalQuestChangeException
    {
        Player player = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setPositionWithoutNotifying(new Position(101, 101));
        player.setAppearanceType("appearance");
        PlayerManager.getSingleton().persistPlayer(player.getPlayerID());

        PlayerManager.resetSingleton();

        Player fetched = PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
        assertEquals(player.getPosition(), fetched.getPosition());
        assertEquals(player.getAppearanceType(), fetched.getAppearanceType());
    }

}
