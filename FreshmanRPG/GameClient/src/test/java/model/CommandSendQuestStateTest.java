package model;

import java.util.ArrayList;

import datatypes.PlayersForTest;
import org.easymock.EasyMock;
import org.junit.Test;

import dataDTO.ClientPlayerQuestStateDTO;
import dataDTO.ClientPlayerQuestTest;
import model.reports.QuestStateReport;

/**
 * Test the Command to send quest state to the view
 * @author Merlin
 *
 */
public class CommandSendQuestStateTest
{

	/**
	 * Test that the command executes successfully 
	 * and does what it is supposed to do
	 */
	@Test
	public void executeTest()
	{
		ThisClientsPlayer cp = ThisClientsPlayerTest.setUpThisClientsPlayer(PlayersForTest.JOHN);
		ClientPlayerQuestStateDTO q = ClientPlayerQuestTest.createOneQuestWithTwoAdventures();
		cp.addQuest(q);
		ArrayList<ClientPlayerQuestStateDTO> expected = new ArrayList<>() ;
		expected.add(q);
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, QuestStateReport.class);
		QuestStateReport report = new QuestStateReport(expected);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);
	
		CommandSendQuestState cmd = new CommandSendQuestState();
		cmd.execute();
	
		EasyMock.verify(obs);
	}
}
