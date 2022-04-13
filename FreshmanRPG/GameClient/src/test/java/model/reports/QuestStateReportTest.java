package model.reports;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import dataDTO.ClientPlayerQuestStateDTO;
import datatypes.QuestStateEnum;

/**
 * Test the QuestStateReport
 * @author Merlin
 *
 */
public class QuestStateReportTest
{

	/**
	 * Test that a QuestStateReport is initialized correctly
	 */
	@Test
	public void test()
	{
		ArrayList<ClientPlayerQuestStateDTO> data = new ArrayList<>();
		ClientPlayerQuestStateDTO q = new ClientPlayerQuestStateDTO(4, "title", "silly", QuestStateEnum.TRIGGERED, 42, 13, true, null);
		data.add(q);
		QuestStateReport report = new QuestStateReport(data);
		assertEquals(data, report.getClientPlayerQuestList());
		
	}

}
