package model.cheatCodeBehaviors;

import static org.junit.Assert.*;

import datasource.ServerSideTest;
import datatypes.PlayersForTest;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import model.PlayerManager;
import model.QualifiedObservableConnector;
import model.QualifiedObserver;
import model.reports.InteractableObjectBuffReport;

/**
 * Tests the cheat code behavior that gives a player more buff
 *
 * @author merlin
 *
 */
public class BuffBehaviorTest extends ServerSideTest
{

	/**
	 * We need to be in test mode and reset some singletons
	 */
	@Before
	public void localSetUp()
	{
		QualifiedObservableConnector.resetSingleton();
		PlayerManager.resetSingleton();
	}

	/**
	 * The player should get the buff if they have typed in the correct chat message
	 */
	@Test
	public void testGivesOnCorrectMessage()
	{
		PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.MERLIN.getPlayerID()).setBuffPool(0);

		// set up an observer to check for the report
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, InteractableObjectBuffReport.class);
		obs.receiveReport(
				new InteractableObjectBuffReport(PlayersForTest.MERLIN.getPlayerID(), BuffBehavior.BUFF_VALUE));
		EasyMock.replay(obs);

		BuffBehavior behavior = new BuffBehavior();
		behavior.giveCheat(PlayersForTest.MERLIN.getPlayerID(), BuffBehavior.text);
		assertEquals(BuffBehavior.BUFF_VALUE,
				PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.MERLIN.getPlayerID()).getBuffPool());

		EasyMock.verify(obs);
	}

	/**
	 * If they have existing buff, their buff should max at the cheat code's value
	 */
	@Test
	public void testAddBuffOnCorrectMessage()
	{
		PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.MERLIN.getPlayerID()).setBuffPool(3);
		// set up an observer to check for the report
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, InteractableObjectBuffReport.class);
		obs.receiveReport(
				new InteractableObjectBuffReport(PlayersForTest.MERLIN.getPlayerID(), BuffBehavior.BUFF_VALUE - 3));
		EasyMock.replay(obs);

		BuffBehavior behavior = new BuffBehavior();
		behavior.giveCheat(PlayersForTest.MERLIN.getPlayerID(), BuffBehavior.text);
		assertEquals(BuffBehavior.BUFF_VALUE,
				PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.MERLIN.getPlayerID()).getBuffPool());

		EasyMock.verify(obs);
	}

	/**
	 * If they have existing buff that is more than the cheat code's value , their
	 * buff should be unchanged
	 */
	@Test
	public void testDontOverMaxBuffOnCorrectMessage()
	{
		PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.MERLIN.getPlayerID())
				.setBuffPool(BuffBehavior.BUFF_VALUE + 3);
		// set up an observer to check for the report
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, InteractableObjectBuffReport.class);
		EasyMock.replay(obs);

		BuffBehavior behavior = new BuffBehavior();
		behavior.giveCheat(PlayersForTest.MERLIN.getPlayerID(), BuffBehavior.text);
		assertEquals(BuffBehavior.BUFF_VALUE + 3,
				PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.MERLIN.getPlayerID()).getBuffPool());
		EasyMock.verify(obs);
	}

	/**
	 * If they typed in the wrong value, nothing should happen
	 */
	@Test
	public void testDoesntGiveBuffOnIncorrectMessage()
	{
		PlayerManager.getSingleton().addPlayer(PlayersForTest.MERLIN.getPlayerID());
		PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.MERLIN.getPlayerID()).setBuffPool(3);

		// set up an observer to check for the report
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, InteractableObjectBuffReport.class);
		EasyMock.replay(obs);

		BuffBehavior behavior = new BuffBehavior();
		behavior.giveCheat(PlayersForTest.MERLIN.getPlayerID(), BuffBehavior.text + "z");
		assertEquals(3,
				PlayerManager.getSingleton().getPlayerFromID(PlayersForTest.MERLIN.getPlayerID()).getBuffPool());

		EasyMock.verify(obs);
	}
}
