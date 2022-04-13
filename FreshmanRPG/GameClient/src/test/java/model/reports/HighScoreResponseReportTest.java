/**
 * 
 */
package model.reports;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import datatypes.PlayerScoreRecord;
import nl.jqno.equalsverifier.EqualsVerifier;

/**
 * @author nk3668
 *
 */
public class HighScoreResponseReportTest 
{
	/**
	 * Test the basic construction of a High Score Response Report 
	 */
	@Test
	public void testInitialization() 
	{
		ArrayList<PlayerScoreRecord> list = new ArrayList<>();
		list.add(new PlayerScoreRecord("Ethan", 0));
		list.add(new PlayerScoreRecord("Weaver", 3));
		list.add(new PlayerScoreRecord("John", 25));
		list.add(new PlayerScoreRecord("Merlin", 9001));
		list.add(new PlayerScoreRecord("Nate", 984257));
		
		HighScoreResponseReport rep = new HighScoreResponseReport(list);
		
		assertEquals(5, rep.getScoreList().size());
	}
	
	/**
	 * Make sure the equals contract is obeyed
	 */
	@Test
	public void equalsContract()
	{
		EqualsVerifier.forClass(HighScoreResponseReport.class).verify();
	}
}
