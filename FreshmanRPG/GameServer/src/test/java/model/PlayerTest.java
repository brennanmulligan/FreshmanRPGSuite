package model;

import dataDTO.LevelManagerDTO;
import datasource.DatabaseException;
import datasource.ServerSideTest;
import datatypes.Crew;
import datatypes.Major;
import datatypes.PlayersForTest;
import datatypes.Position;
import model.reports.ExperienceChangedReport;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Test the Player class
 *
 * @author Merlin
 */
public class PlayerTest extends ServerSideTest
{

    private PlayerManager playerManager;

    /**
     * Make sure we can retrieve a player's appearanceType
     */
    @Test
    public void canGetAppearanceType()
    {
        Player p = playerManager.addPlayer(1);
        assertEquals(PlayersForTest.JOHN.getAppearanceType(), p.getAppearanceType());
    }

    /**
     * Make sure we can retrieve a player's major
     */
    @Test
    public void canGetPlayerMajor()
    {
        Player p = playerManager.addPlayer(1);
        assertEquals(Major.COMPUTER_ENGINEERING, p.getMajor());
    }

    /**
     * Make sure we can retrieve a player's unique name from the db
     */
    @Test
    public void canGetPlayerName()
    {
        Player p = playerManager.addPlayer(1);
        assertEquals("John", p.getPlayerName());
    }

    /**
     * Make sure we can get a player's section number
     */
    @Test
    public void canGetSectionNumber()
    {
        Player p = playerManager.addPlayer(20);
        assertEquals(PlayersForTest.JEFF.getSection(), p.getSection());
    }

    /**
     * Test to make sure we have a legitimate PIN
     *
     * @throws DatabaseException           shouldn't
     * @throws IllegalQuestChangeException the state changed illegally
     */
    @Test
    public void legitPin() throws DatabaseException, IllegalQuestChangeException
    {
        PlayerConnection playerPin = new PlayerConnection(1);
        playerPin.generateTestPin();
        playerManager.addPlayer(1, PlayerConnection.DEFAULT_PIN);
    }

    /**
     * make sure the necessary singletons are reset
     *
     * @throws DatabaseException shouldn't
     */
    @Before
    public void localSetUp() throws DatabaseException
    {
        QualifiedObservableConnector.resetSingleton();
        PlayerManager.resetSingleton();
        playerManager = PlayerManager.getSingleton();
    }

    /**
     * Make sure we can set a player's section number
     */
    @Test
    public void setSetSectionNumber()
    {
        Player p = playerManager.addPlayer(20);
        p.setSection(2);
        assertEquals(2, p.getSection());
    }

    /**
     * Tests that adding experience points to a player object generates
     * ExperienceChangedReport
     */
    @Test
    public void testAddExpPointsCreatesReport()
    {
        Player p = playerManager.addPlayer(PlayersForTest.JOHN.getPlayerID());

        QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
        QualifiedObservableConnector.getSingleton()
                .registerObserver(obs, ExperienceChangedReport.class);
        int updatedPoints = p.getExperiencePoints() + 15;
        ExperienceChangedReport expectedReport =
                new ExperienceChangedReport(p.getPlayerID(), updatedPoints,
                        LevelManagerDTO.getSingleton().getLevelForPoints(updatedPoints));
        obs.receiveReport(expectedReport);
        EasyMock.replay(obs);

        p.addExperiencePoints(15);
        EasyMock.verify(obs);
    }

    /**
     * Test the doubloons when buffPool is 0.
     */
    @Test
    public void testBuffPoolIs0()
    {
        Player player = PlayerManager.getSingleton()
                .addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setBuffPool(0);
        player.setDoubloons(10);
        player.incrementDoubloons();
        assertEquals(11, player.getQuizScore());
    }

    /**
     * Test the decrement of buffPool
     */
    @Test
    public void testDecrementBuffPool()
    {
        Player player = PlayerManager.getSingleton()
                .addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setBuffPool(50);
        player.setDoubloons(10);
        player.incrementDoubloons();
        assertEquals(49, player.getBuffPool());
    }

    /**
     * Test the decrement of buffPool TWICE
     */
    @Test
    public void testDecrementTwiceBuffPool()
    {
        Player player = PlayerManager.getSingleton()
                .addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setBuffPool(50);
        player.setDoubloons(10);
        player.incrementDoubloons();
        player.incrementDoubloons();
        assertEquals(48, player.getBuffPool());
    }

    /**
     * Test the edit player method for Player
     *
     * @throws DatabaseException           shouldn't
     * @throws IllegalQuestChangeException shouldn't
     */
    @Test
    public void testEditPlayer() throws DatabaseException, IllegalQuestChangeException
    {
        Player player = PlayerManager.getSingleton()
                .addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setPlayerPositionWithoutNotifying(new Position(101, 101));
        player.setAppearanceType("appearance");
        PlayerManager.getSingleton().persistPlayer(player.getPlayerID());
        player.editPlayer("test_appearance_type",
                9000,
                1234,
                Crew.OFF_BY_ONE,
                Major.ELECTRICAL_ENGINEERING,
                10,
                "Test Player",
                "pw");

        assertEquals("test_appearance_type", player.getAppearanceType());
        assertEquals(9000, player.getQuizScore());
        assertEquals(1234, player.getExperiencePoints());
        assertEquals(Crew.OFF_BY_ONE, player.getCrew());
        assertEquals(Major.ELECTRICAL_ENGINEERING, player.getMajor());
        assertEquals(10, player.getSection());

    }

    /**
     * Test the increment of quiz score
     */
    @Test
    public void testIncrementQuizScore()
    {
        Player player = PlayerManager.getSingleton()
                .addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setBuffPool(50);
        player.setDoubloons(10);
        player.incrementDoubloons();
        assertEquals(12, player.getQuizScore());
    }

    /**
     * test we can set a player online
     */
    @Test
    public void testOnlineStatus()
    {
        Player p = new Player(10);
        p.setPlayerOnline(true);
        assertTrue(p.isPlayerOnline());

        p.setPlayerOnline(false);
        assertFalse(p.isPlayerOnline());
    }

    /**
     * Test that we can set Player's experience points and add to it
     */
    @Test
    public void testPlayerExpPoints()
    {
        Player p = playerManager.addPlayer(1);

        p.setExperiencePoints(34);
        assertEquals(34, p.getExperiencePoints());

        p.addExperiencePoints(3);
        assertEquals(37, p.getExperiencePoints());
    }

    /**
     * Sets the players position and checks it
     */
    @Test
    public void testPlayerPosition()
    {
        Player p = playerManager.addPlayer(1);
        Position pos = new Position(3, 3);
        p.setPlayerPosition(pos);
        assertEquals(pos, p.getPlayerPosition());
    }

    /**
     * Test the removal of doubloons
     */
    @Test
    public void testRemoveDoubloons()
    {
        Player player = PlayerManager.getSingleton()
                .addPlayer(PlayersForTest.MERLIN.getPlayerID());
        player.setDoubloons(10);
        player.removeDoubloons(5);
        assertEquals(5, player.getQuizScore());
    }

    /**
     * Test the visited maps is stored
     */
    @Test
    public void testVisitedMaps()
    {
        Player player = new Player(20);
        ArrayList<String> maps = new ArrayList<>(Arrays.asList("Rec Center", "Quad"));
        player.setPlayerVisitedMaps(maps);
        assertEquals(maps, player.getPlayerVisitedMaps());
    }
}