package manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import datasource.DatabaseException;
import model.OptionsManager;
import model.QualifiedObservableConnector;
import model.reports.AllPlayersReport;
import model.reports.AllQuestsAndAdventuresReport;
import model.reports.ObjectListReport;
import model.reports.QuestionListReport;
import ui.fx.contentviews.AdventureContentView;
import ui.fx.contentviews.InteractableObjectContentView;
import ui.fx.contentviews.PlayerContentView;
import ui.fx.contentviews.QuizbotContentView;

/**
 * Tests for GameManger
 *
 * @author Chris Boyer and Abe Loscher
 *
 */
public class GameManagerTestFX
{

	/**
	 * Tests that we are setting the GameManager to use the test database correctly
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testInitializationWithTestDBParameters() throws DatabaseException
	{
		String[] args =
				{"--localhost", "--db=04"};
		new GameManagerFX(args);
		assertTrue(OptionsManager.getSingleton().isUsingTestDB());
		assertEquals(OptionsManager.getSingleton().getDbIdentifier(), "04");
	}

	/**
	 * Tests that when we do not have a DB identifier, we will throw a illegal
	 * argument exception
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalArgumentException
	 *             should, as we are using an illegal set of arguments
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInitializationWithoutDBIdentifier() throws DatabaseException
	{
		String[] args =
				{"--localhost"};
		new GameManagerFX(args);
	}

	/**
	 * Testing that if we are initializing the application with no arguments, then
	 * we will just use the production database
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 */
	@Test
	public void testInitializationWithNoArguments() throws DatabaseException
	{
		String[] args = {};
		new GameManagerFX(args);
		assertFalse(OptionsManager.getSingleton().isUsingTestDB());
	}

	/**
	 * Testing that giving unrecognized arguments throws an
	 * IllegalArgumentExpection.
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalArgumentException
	 *             should, as we are using an illegal set of arguments
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInitializationWithIllegalArguments() throws DatabaseException
	{
		String[] args =
				{"illegal=true"};
		new GameManagerFX(args);
	}

	/**
	 * If we have an identifier but no DB host argument, then arguments are illegal
	 *
	 * @throws DatabaseException
	 *             shouldn't
	 * @throws IllegalArgumentException
	 *             should, as we are using an illegal set of arguments
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testInitializationWithoutDBHostArgument() throws DatabaseException
	{
		String[] args =
				{"--db=04"};
		new GameManagerFX(args);
	}

	/**
	 * Make sure that after the game manager starts, all content views are
	 * registered as qualified observers of their respective reports
	 */
	@Test
	public void testAllContentViewsObserve()
	{
		assertEquals(true, QualifiedObservableConnector.getSingleton().doIObserve(QuizbotContentView.getInstance(),
				QuestionListReport.class));
		assertEquals(true, QualifiedObservableConnector.getSingleton().doIObserve(PlayerContentView.getInstance(),
				AllPlayersReport.class));
		assertEquals(true, QualifiedObservableConnector.getSingleton().doIObserve(AdventureContentView.getInstance(),
				AllQuestsAndAdventuresReport.class));
		assertEquals(true, QualifiedObservableConnector.getSingleton()
				.doIObserve(InteractableObjectContentView.getInstance(), ObjectListReport.class));
	}

}
