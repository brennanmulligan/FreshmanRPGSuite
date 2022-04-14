package model.reports;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import dataDTO.PlayerDTO;
import datasource.DatabaseException;
import model.OptionsManager;
import datatypes.PlayersForTest;

/**
 * Tests AllPlayersReport - pretty simple since it is just a data transfer
 * object
 *
 * @author Merlin
 *
 */
public class AllPlayersReportTest
{

	/**
	 * Make sure we are in test mode
	 */
	@BeforeClass
	public static void setup()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Even an empty list of players should not be null
	 */
	@Test
	public void testEmpty()
	{
		AllPlayersReport r = new AllPlayersReport(new ArrayList<>());
		ArrayList<PlayerDTO> a = r.getPlayerInfo();
		assertEquals(0, a.size());
	}

	/**
	 * We should just get back the list we give it
	 * @throws DatabaseException shouldn't
	 */
	@Test
	public void testNotEmpty() throws DatabaseException
	{
		ArrayList<PlayerDTO> list = new ArrayList<>();
		PlayersForTest t = PlayersForTest.ANDY;
		PlayerDTO p1 = new PlayerDTO(t.getPlayerID(), t.getPlayerName(), t.getPlayerPassword(), t.getAppearanceType(),
				t.getDoubloons(), t.getPosition(), t.getMapName(), t.getExperiencePoints(), t.getCrew(), t.getMajor(),
				t.getSection(), t.getMapsVisited(), new ArrayList<>());
		t = PlayersForTest.DAVE;
		PlayerDTO p2 = new PlayerDTO(t.getPlayerID(), t.getPlayerName(), t.getPlayerPassword(), t.getAppearanceType(),
				t.getDoubloons(), t.getPosition(), t.getMapName(), t.getExperiencePoints(), t.getCrew(), t.getMajor(),
				t.getSection(), t.getMapsVisited(), new ArrayList<>());
		AllPlayersReport r = new AllPlayersReport(list);
		list.add(p1);
		list.add(p2);
		ArrayList<PlayerDTO> a = r.getPlayerInfo();
		assertEquals(2, a.size());
		assertTrue(a.contains(p1));
		assertTrue(a.contains(p2));
	}

}
