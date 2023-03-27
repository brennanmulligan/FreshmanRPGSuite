package edu.ship.engr.shipsim.datatypes;

import edu.ship.engr.shipsim.dataDTO.RandomFactDTO;

import java.time.LocalDate;

/**
 * @author merlin
 */

/**
 * Creates objectives for the DB
 *
 * @author merlin
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

    FACT_18(18, PlayersForTest.PRESIDENT_NPC.getPlayerID(), "I'm the president and very cool.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),

    /**
     * History Fact Set 1
     */
    FACT_19(19, PlayersForTest.HISTORY_NPC.getPlayerID(), "SU was originally founded as the Cumberland Valley State Normal School.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    FACT_20(20, PlayersForTest.HISTORY_NPC.getPlayerID(), "Until the 1960s, students and faculty still lived in Old Main.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    FACT_21(21, PlayersForTest.HISTORY_NPC.getPlayerID(), "The school only had 6 faculty and 217 students when it opened in 1873.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    FACT_22(22, PlayersForTest.HISTORY_NPC.getPlayerID(), "McCune Hall became the first co-ed dorm in 1974.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    /**
     * History Fact Set 2
     */
    FACT_23(23, PlayersForTest.HISTORY_NPC_2.getPlayerID(), "Due to the wartime draft, in 1944-45 the campus only had women's athletic teams.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    FACT_24(24, PlayersForTest.HISTORY_NPC_2.getPlayerID(), "The African American Organization (Afro Am) began in 1970.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    FACT_25(25, PlayersForTest.HISTORY_NPC_2.getPlayerID(), "The campus newspaper, The Slate, was founded in 1957.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    FACT_26(26, PlayersForTest.HISTORY_NPC_2.getPlayerID(), "Until the 1970s, new students wore a 'dink' hat during their first semester.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    FACT_27(27, PlayersForTest.HISTORY_NPC_2.getPlayerID(), "Anthony 'Tony' Ceddia was SU's longest president (24 years).", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    /**
     * History Fact Set 3
     */
    FACT_28(28, PlayersForTest.HISTORY_NPC_3.getPlayerID(), "SU XC Coach Steve Spence competed in the 1991 Olympics in Tokyo, Japan.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    FACT_29(29, PlayersForTest.HISTORY_NPC_3.getPlayerID(), "Stewart Hall was the campus's first gym and student union.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    FACT_30(30, PlayersForTest.HISTORY_NPC_3.getPlayerID(), "In 1968, students helped move 75,000 books to the new Ezra Lehman Library.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    FACT_31(31, PlayersForTest.HISTORY_NPC_3.getPlayerID(), "Before 1960, the school only granted teaching degrees.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2)),
    FACT_32(32, PlayersForTest.HISTORY_NPC_3.getPlayerID(), "In 1873-74, the school required all students to take a bath once a week.", LocalDate.of(2020, 5, 2), LocalDate.of(9999, 5, 2));


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
