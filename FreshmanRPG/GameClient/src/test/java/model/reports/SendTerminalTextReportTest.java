package model.reports;

import static org.junit.Assert.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import org.junit.Test;

import model.ClientPlayerManager;

/**
 * @author bl5922 Tests the creation of the GetOnlinePlayersReport
 */
public class SendTerminalTextReportTest
{

	private int playerID = ClientPlayerManager.getSingleton().getThisClientsPlayer().getID();
	private String terminalText = "who";

	/**
	 * @throws AlreadyBoundException
	 *             for finishLogin
	 * @throws NotBoundException
	 *             for finishLogin
	 */
	@Test
	public void create() throws AlreadyBoundException, NotBoundException
	{

		ClientPlayerManager.getSingleton().initiateLogin("1", "1");
		ClientPlayerManager.getSingleton().finishLogin(1);
		SendTerminalTextReport report = new SendTerminalTextReport(playerID, terminalText);
		assertEquals(playerID, report.getPlayerID());
		assertEquals(terminalText, report.getTerminalText());
	}

}
