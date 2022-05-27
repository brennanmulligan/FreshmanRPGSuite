package model.reports;

import static org.junit.Assert.*;

import java.util.ArrayList;

import datasource.ServerSideTest;
import org.junit.Test;

import dataDTO.DoubloonPrizeDTO;

public class DoubloonPrizeReportTest extends ServerSideTest
{

	@Test
	public void testConstructor()
	{

		//This is the set up for making the report
		//We are just adding random test data in the DTO
		DoubloonPrizeDTO kppDTO1 = new DoubloonPrizeDTO("Prize1", 20, "Prize1");
		DoubloonPrizeDTO kppDTO2 = new DoubloonPrizeDTO("Prize2", 25, "Prize2");
		ArrayList<DoubloonPrizeDTO> dtoList = new ArrayList<>();
		dtoList.add(kppDTO1);
		dtoList.add(kppDTO2);

		DoubloonPrizeReport kppr = new DoubloonPrizeReport(1, dtoList);

		ArrayList<DoubloonPrizeDTO> dtoList2 = kppr.getPrizes();
		assertEquals(kppDTO1.getName(), dtoList2.get(0).getName());

	}

}
