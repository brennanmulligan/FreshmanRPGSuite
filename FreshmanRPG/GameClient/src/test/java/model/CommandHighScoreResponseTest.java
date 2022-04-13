package model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.Test;

import datatypes.PlayerScoreRecord;
import model.reports.HighScoreResponseReport;

/**
 * @author nk3668
 *
 */
public class CommandHighScoreResponseTest 
{

	/**
	 * Tests to make sure constructor sets values
	 */
	@Test
	public void testConstructor() 
	{
		ArrayList<PlayerScoreRecord> list = new ArrayList<>();
		list.add(new PlayerScoreRecord("Ethan", 0));
		list.add(new PlayerScoreRecord("Weaver", 3));
		list.add(new PlayerScoreRecord("Merlin", 9001));
		list.add(new PlayerScoreRecord("Nate", 984257));
		
		CommandHighScoreResponse res = new CommandHighScoreResponse(list);
		assertEquals(list, res.getScoreRecord());
	}
	
	/**
	 * Tests to make sure execute correctly
	 * sends high scores.
	 */
	@Test
	public void testExecute()
	{
		ArrayList<PlayerScoreRecord> list = new ArrayList<>();
		list.add(new PlayerScoreRecord("Ethan", 0));
		list.add(new PlayerScoreRecord("Weaver", 3));
		list.add(new PlayerScoreRecord("John", 25));
		list.add(new PlayerScoreRecord("Merlin", 9001));
		list.add(new PlayerScoreRecord("Nate", 984257));
		
		CommandHighScoreResponse res = new CommandHighScoreResponse(list);
		
		QualifiedObserver obs = EasyMock.createMock(QualifiedObserver.class);
		QualifiedObservableConnector.getSingleton().registerObserver(obs, HighScoreResponseReport.class);
		HighScoreResponseReport report = new HighScoreResponseReport(list);
		obs.receiveReport(EasyMock.eq(report));
		EasyMock.replay(obs);

		res.execute();
		
		EasyMock.verify(obs);	
	}

}
