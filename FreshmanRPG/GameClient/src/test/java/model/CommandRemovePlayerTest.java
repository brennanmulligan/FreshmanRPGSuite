package model;

import static org.junit.Assert.fail;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import model.reports.PlayerDisconnectedFromAreaServerReport;

/**
 * @author nhydock
 *
 */
public class CommandRemovePlayerTest
{


	/**
	 * Setup the model facade for mock testing
	 */
	@Before
	public void setup()
	{
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, true);
	}

	/**
	 * Test executing a remove player command
	 */
	@Test
	public void testExecution()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		obs.receiveReport(EasyMock.isA(PlayerDisconnectedFromAreaServerReport.class));
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				PlayerDisconnectedFromAreaServerReport.class);
		EasyMock.replay(obs);

		// setup the player
		ClientPlayerManager pm = ClientPlayerManager.getSingleton();
		pm.initiateLogin("john", "pw");
		try
		{
			pm.finishLogin(1);
		}
		catch (AlreadyBoundException | NotBoundException e)
		{
			e.printStackTrace();
			fail("Could not create this client's player from login");
		}

		CommandRemovePlayer cmd = new CommandRemovePlayer(1);
		cmd.execute();

		EasyMock.verify(obs);
	}
}
