package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.datatypes.PlayerScoreRecord;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * An abstract class that tests the table data gateways into the Objective table
 *
 * @author merlin
 */
@GameTest("GameServer")
public class PlayerTableDataGatewayTest
{

    protected PlayerTableDataGateway gateway;

    /**
     * tests the first and last index have correct data
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void confirmCorrectDataInList() throws DatabaseException
    {
        PlayerTableDataGateway gateway = getGatewaySingleton();
        ArrayList<PlayerDTO> playerList = gateway.getAllPlayers();
        PlayersForTest[] playerExpected = PlayersForTest.values();

        assertEquals(playerList.size(), playerExpected.length);

        for (int i = 0; i < playerList.size(); i++)
        {
            PlayerDTO dbPlayer = playerList.get(i);
            PlayersForTest testPlayer = playerExpected[i];

            assertEquals(dbPlayer.getPlayerID(), testPlayer.getPlayerID());
            assertEquals(dbPlayer.getCrew(), testPlayer.getCrew());
            assertEquals(dbPlayer.getMajor(), testPlayer.getMajor());
            assertEquals(dbPlayer.getSection(), testPlayer.getSection());
            assertEquals(dbPlayer.getPlayerName(), testPlayer.getPlayerName());

            assertEquals(dbPlayer.getAppearanceType(), testPlayer.getAppearanceType());
            assertEquals(dbPlayer.getMapName(), testPlayer.getMapName());
            assertEquals(dbPlayer.getPosition(), testPlayer.getPosition());
            assertEquals(dbPlayer.getDoubloons(), testPlayer.getDoubloons());
            assertEquals(dbPlayer.getExperiencePoints(),
                    testPlayer.getExperiencePoints());
            // don't need to test buff pool here, that is retrieved through the Row Data
            // Gateway
        }

    }

    /**
     * Test that we get the online players.
     *
     * @throws DatabaseException if we cannot get the data
     */
    @Test
    public void getAllOnlinePlayerTest() throws DatabaseException
    {
        PlayerTableDataGateway gateway = getGatewaySingleton();

        ArrayList<PlayerDTO> playerList = gateway.getAllOnlinePlayers();

        int numberOfOnline = 0;
        for (PlayersForTest player : PlayersForTest.values())
        {
            if (player.getOnline())
            {
                System.out.println(player.getPlayerName());
                numberOfOnline++;
            }
        }
        assertEquals(numberOfOnline, playerList.size());
    }

    /**
     * @return the gateway we should test
     */
    public PlayerTableDataGateway getGatewaySingleton()
    {
        return PlayerTableDataGateway.getSingleton();
    }

    /**
     *
     */
    @Test
    public void isASingleton()
    {
        PlayerTableDataGateway x = getGatewaySingleton();
        PlayerTableDataGateway y = getGatewaySingleton();
        assertSame(x, y);
        assertNotNull(x);
    }

    /**
     * tests that the total size is correct for the players list
     *
     * @throws DatabaseException shouldn't occur
     */
    @Test
    public void getAllPlayersSize() throws DatabaseException
    {
        PlayerTableDataGateway gateway = getGatewaySingleton();

        ArrayList<PlayerDTO> playerList = gateway.getAllPlayers();
        assertEquals(PlayersForTest.values().length, playerList.size());
    }

    /**
     * @throws DatabaseException shouldn't
     */
    @Test
    public void getHighScoreList() throws DatabaseException
    {

        gateway = getGatewaySingleton();
        ArrayList<PlayerScoreRecord> results = gateway.getHighScoreList();
        PlayersForTest[] expected = PlayersForTest.values();
        assertEquals(expected.length, results.size());

        for (int i = 0; i < results.size() - 1; i++)
        {
            assertTrue(results.get(i).getExperiencePoints() >=
                    results.get(i + 1).getExperiencePoints());
        }

    }

    /**
     * @throws DatabaseException shouldn't
     */
    @Test
    public void getTopTenHighScores() throws DatabaseException
    {
        gateway = getGatewaySingleton();
        ArrayList<PlayerScoreRecord> results = gateway.getTopTenList();
        assertEquals(10, results.size());
        assertEquals(new PlayerScoreRecord(PlayersForTest.MERLIN.getPlayerName(),
                PlayersForTest.MERLIN.getExperiencePoints()), results.get(0));
        assertEquals(1, results.get(0).getSection());
        assertFalse(results.contains(
                new PlayerScoreRecord(PlayersForTest.LOSER.getPlayerName(),
                        PlayersForTest.LOSER.getExperiencePoints())));

    }

}
