package model;

import static org.junit.Assert.fail;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import model.reports.NewMapReport;

/**
 * @author Merlin
 * 
 */
public class CommandNewMapTest
{

	/**
	 * 
	 */
	@Before
	public void setup()
	{
		ClientModelFacade.resetSingleton();
		ClientModelFacade.getSingleton(true, true);
	}

	/**
	 * 
	 */
	@Test
	public void testExecution()
	{
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		obs.receiveReport(EasyMock.isA(NewMapReport.class));
		QualifiedObservableConnector.getSingleton().registerObserver(obs,
				NewMapReport.class);
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

		CommandNewMap cmd = new CommandNewMap("testMaps/simple.tmx");
		cmd.execute();

		EasyMock.verify(obs);
	}
}
