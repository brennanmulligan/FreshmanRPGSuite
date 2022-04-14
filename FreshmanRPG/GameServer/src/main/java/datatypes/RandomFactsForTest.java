package datatypes;

import java.time.LocalDate;

import dataDTO.RandomFactDTO;

/**
 * @author merlin
 *
 */

/**
 * Creates objectives for the DB
 *
 * @author merlin
 *
 */
public enum RandomFactsForTest
{

	/**
	 *
	 */
	FACT_1(1, PlayersForTest.BIG_RED.getPlayerID(), "Glad to have you on campus!", LocalDate.of(2014, 2, 11),
			LocalDate.of(9999, 2, 11)),
	/**
	 *
	 */
	FACT_2(2, PlayersForTest.RANDOM_FACTS_NPC_2.getPlayerID(), "This game is REALLY fun!", LocalDate.of(2014, 2, 11),
			LocalDate.of(9999, 2, 11)),
	/**
	 *
	 */
	FACT_3(3, PlayersForTest.BIG_RED.getPlayerID(), "Go Red Raiders!",
			LocalDate.of(2014, 2, 11), LocalDate.of(9999, 2, 11)),
	/**
	 *
	 */
	FACT_4(4, PlayersForTest.RANDOM_FACTS_NPC_2.getPlayerID(), "We definitely need more facts!", LocalDate.of(2014, 2, 11),
			LocalDate.of(9999, 2, 11)),
	/**
	 *
	 */
	FACT_5(5, PlayersForTest.RANDOM_FACTS_NPC_2.getPlayerID(), "Merlin is a great wizard - you should search for her", LocalDate.of(2014, 2, 11),
			LocalDate.of(9999, 2, 11)),
	/**
	 *
	 */
	FACT_6(6, PlayersForTest.PROFESSOR_H.getPlayerID(), "Have you ever heard of the rubber duck debugging method?", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

	/**
	 *
	 */
	FACT_7(7, PlayersForTest.PROFESSOR_H.getPlayerID(), "Did you know a rubber duck is only 20 doubloons in the shop", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

	/**
	 *
	 */
	FACT_8(8, PlayersForTest.PROFESSOR_H.getPlayerID(), "Stuck on a problem? Talk it out with a trusty rubber ducky!!", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

	/**
	 *
	 */
	FACT_9(9, PlayersForTest.QUAD_GUY.getPlayerID(), "Welcome to the Shippensburg University campus.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

	/**
	 *
	 */
	FACT_10(10, PlayersForTest.IT_GUY_NPC.getPlayerID(), "Make sure to logout when you are done working.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

	/**
	 *
	 */
	FACT_11(11, PlayersForTest.QUAD_GUY.getPlayerID(), "You should go check out MCT!", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

	/**
	 *
	 */
	FACT_12(12, PlayersForTest.QUAD_GUY.getPlayerID(), "If you walk down Shippen hall is where you'll be.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

	/**
	 *
	 */
	FACT_13(13, PlayersForTest.QUAD_GUY.getPlayerID(), "If you walk down the right path you'll find Franklin Science center.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

	/**
	 *
	 */
	FACT_14(14, PlayersForTest.QUAD_GUY.getPlayerID(), "The library and the cub are towards the top right of this area, go explore!!", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

	/**
	 *
	 */
	FACT_15(15, PlayersForTest.QUAD_GUY.getPlayerID(), "There's another telporter somewhere around here, go explore!!", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

	/**
	 *
	 */
	FACT_16(16, PlayersForTest.IT_GUY_NPC.getPlayerID(), "Have any questions?", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

	FACT_17(17, PlayersForTest.PRESIDENT_NPC.getPlayerID(), "Hello there! Hope you're having a good semester!", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

	FACT_18(18, PlayersForTest.PRESIDENT_NPC.getPlayerID(), "I'm the president and very cool.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2));



	private int randomFactID;
	private int npcID;
	private String factText;
	private LocalDate startDate;
	private LocalDate endDate;

	/**
	 * @return this fact's unique id
	 */
	public int getRandomFactID()
	{
		return randomFactID;
	}

	/**
	 * @return the id of the NPC that should spout this fact
	 */
	public int getNpcID()
	{
		return npcID;
	}

	/**
	 * @return this fact's text
	 */
	public String getFactText()
	{
		return factText;
	}

	/**
	 * @return the first date this fact should be spouted
	 */
	public LocalDate getStartDate()
	{
		return startDate;
	}

	/**
	 * @return the last date this fact should be spouted
	 */
	public LocalDate getEndDate()
	{
		return endDate;
	}

	private RandomFactsForTest(int factID, int npcID, String factText, LocalDate startDate, LocalDate endDate)
	{
		this.randomFactID = factID;
		this.npcID = npcID;
		this.factText = factText;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * @return a DTO for this fact
	 */
	public RandomFactDTO getDTO()
	{
		return new RandomFactDTO(randomFactID, npcID, factText, startDate, endDate);
	}

}
