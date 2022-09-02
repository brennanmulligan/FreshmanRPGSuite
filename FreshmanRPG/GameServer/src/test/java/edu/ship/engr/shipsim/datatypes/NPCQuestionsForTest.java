package edu.ship.engr.shipsim.datatypes;

import java.time.LocalDate;

/**
 * Create test questions for the DB.
 *
 * @author gl9859
 */
public enum NPCQuestionsForTest
{
    /**
     *
     */

    ONE(1, "First question", "First answer", LocalDate.of(2014, 2, 11), LocalDate.of(9999, 3, 21)),

    /**
     *
     */
    TWO(2, "Second question", "Second answer", LocalDate.of(2014, 2, 11), LocalDate.of(9999, 2, 11)),

    /**
     *
     */
    MULTIPLE_CHOICE(3, "Would you like to pick A for correct answer?\nA. First Choice\nB. Second Choice\nC. Third Choice\nD. Fourth Choice\n", "A", LocalDate.of(2015, 2, 11), LocalDate.of(9999, 3, 28)),

    /**
     *
     */
    FOUR(4, "Third question", "Third answer", LocalDate.of(2014, 2, 11), LocalDate.of(9999, 2, 11)),

    /**
     * This question should never be seen in game
     */
    NOT_POSSIBLE(5, "YOU SHOULDN'T SEE ME, answer: 'tell someone'", "tell someone", LocalDate.of(9999, 2, 11), LocalDate.of(0000, 2, 11));


    private String q;

    private String a;

    private int questionID;

    private LocalDate startDate;

    private LocalDate endDate;

    /**
     * Constructor
     *
     * @param questionID this question's unique ID
     * @param q          question
     * @param a          answer
     * @param startDate  first day the question is available
     * @param endDate    last day the question is available
     */
    NPCQuestionsForTest(int questionID, String q, String a, LocalDate startDate, LocalDate endDate)
    {
        this.questionID = questionID;
        this.q = q;
        this.a = a;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @return the answer
     */
    public String getA()
    {
        return a;
    }

    /**
     * @return the question
     */
    public String getQ()
    {
        return q;
    }

    /**
     * @return the question's unique ID
     */
    public int getQuestionID()
    {
        return questionID;
    }

    /**
     * @return the first day the question is available
     */
    public LocalDate getStartDate()
    {
        return startDate;
    }

    /**
     * @return the last day the question is available
     */
    public LocalDate getEndDate()
    {
        return endDate;
    }

}
