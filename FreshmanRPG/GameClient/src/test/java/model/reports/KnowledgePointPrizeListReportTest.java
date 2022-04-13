package model.reports;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import dataDTO.KnowledgePointPrizeDTO;
import nl.jqno.equalsverifier.EqualsVerifier;

public class KnowledgePointPrizeListReportTest 
{
	@Test
	public void testInitialization()
	{
		ArrayList<KnowledgePointPrizeDTO> knowledgePointPrizes = new ArrayList<>();
		
		knowledgePointPrizes.add(new KnowledgePointPrizeDTO("ItemName", 100, "ItemDesc"));
		
		KnowledgePointPrizeListReport rep = new KnowledgePointPrizeListReport(knowledgePointPrizes);
		
		assertEquals(knowledgePointPrizes.get(0).getName(), rep.getPrizes().get(0).getName());
		assertEquals(knowledgePointPrizes.get(0).getCost(), rep.getPrizes().get(0).getCost());
		assertEquals(knowledgePointPrizes.get(0).getDescription(), rep.getPrizes().get(0).getDescription());		
	}
	
	/**
	 * Testing the equality of two instances of this class
	 */
	@Test
	public void testEqualsContract()
	{
		EqualsVerifier.forClass(KnowledgePointPrizeListReport.class).verify();
	}
	
	
}
