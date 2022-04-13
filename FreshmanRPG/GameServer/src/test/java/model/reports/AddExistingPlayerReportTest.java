package model.reports;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import model.OptionsManager;
import datatypes.PlayersForTest;

/**
 * @author alan, regi
 *
 */
public class AddExistingPlayerReportTest
{

	/**
	 *
	 */
	@Before
	public void setup()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		OptionsManager.getSingleton().setUsingTestDB(true);
	}

	/**
	 * Test the report constructor and values.
	 */
	@Test
	public void testConstructor()
	{
		AddExistingPlayerReport report = new AddExistingPlayerReport(
				PlayersForTest.QUIZBOT.getPlayerID(),
				PlayersForTest.JOHN.getPlayerID(),
				PlayersForTest.JOHN.getPlayerName(),
				PlayersForTest.JOHN.getAppearanceType(),
				PlayersForTest.JOHN.getPosition(),
				PlayersForTest.JOHN.getCrew(),
				PlayersForTest.JOHN.getMajor(),
				PlayersForTest.JOHN.getSection()
		);

		assertEquals(PlayersForTest.QUIZBOT.getPlayerID(), report.getRecipientPlayerID());
		assertEquals(PlayersForTest.JOHN.getPlayerID(), report.getPlayerID());
		assertEquals(PlayersForTest.JOHN.getPlayerName(), report.getPlayerName());
		assertEquals(PlayersForTest.JOHN.getAppearanceType(), report.getAppearanceType());
		assertEquals(PlayersForTest.JOHN.getPosition(), report.getPosition());
		assertEquals(PlayersForTest.JOHN.getCrew(), report.getCrew());
		assertEquals(PlayersForTest.JOHN.getMajor(), report.getMajor());
		assertEquals(PlayersForTest.JOHN.getSection(), report.getSection());

	}

}
