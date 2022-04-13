package model.reports;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import dataDTO.KnowledgePointPrizeDTO;

public class KnowledgePointPrizeReportTest
{

	@Test
	public void testConstructor()
	{

		//This is the set up for making the report
		//We are just adding random test data in the DTO
		KnowledgePointPrizeDTO kppDTO1 = new KnowledgePointPrizeDTO("Prize1", 20, "Prize1");
		KnowledgePointPrizeDTO kppDTO2 = new KnowledgePointPrizeDTO("Prize2", 25, "Prize2");
		ArrayList<KnowledgePointPrizeDTO> dtoList = new ArrayList<>();
		dtoList.add(kppDTO1);
		dtoList.add(kppDTO2);

		KnowledgePointPrizeReport kppr = new KnowledgePointPrizeReport(1, dtoList);

		ArrayList<KnowledgePointPrizeDTO> dtoList2 = kppr.getPrizes();
		assertEquals(kppDTO1.getName(), dtoList2.get(0).getName());

	}

}
