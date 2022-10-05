package edu.ship.engr.shipsim.datasource;

import edu.ship.engr.shipsim.dataDTO.QuestionDTO;
import edu.ship.engr.shipsim.datatypes.NPCQuestionsForTest;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests required of all player gateways
 *
 * @author Merlin
 */
@GameTest("GameServer")
public class NPCQuestionRowDataGatewayTest
{

    protected NPCQuestionRowDataGateway gateway;

    /**
     * Make sure that the creation method assigns an id.  Also check other parameters.
     *
     * @throws DatabaseException Will throw an exception if we cannot add the
     *                           information to the NPCQuestion table
     */
    @Test
    public void creation() throws DatabaseException
    {
        NPCQuestionRowDataGateway gateway =
                new NPCQuestionRowDataGateway("hello?", "world.",
                        LocalDate.of(1, 2, 3), LocalDate.of(4, 5, 6));

        assertTrue(gateway.getQuestionID() > 0);
        assertEquals("hello?", gateway.getQuestionStatement());
        assertEquals("world.", gateway.getAnswer());
        assertEquals(LocalDate.of(1, 2, 3), gateway.getStartDate());
        assertEquals(LocalDate.of(4, 5, 6), gateway.getEndDate());
    }

    /**
     * Tests to see if we can delete a question from the database after creating one
     */
    @Test
    public void deleteCreatedQuestion()
    {
        assertThrows(DatabaseException.class, () ->
        {
            NPCQuestionRowDataGateway gateway = null;

            try
            {
                gateway = new NPCQuestionRowDataGateway("hello?", "world.",
                        LocalDate.of(2012, 2, 14), LocalDate.of(2018, 12, 15));
            }
            catch (DatabaseException e)
            {
                fail("Creating a question failed when it shouldn't");
            }

            gateway.delete();
            findGateway(gateway.getQuestionID());
        });
    }

    /**
     * Tests to see if we can delete a question from the database
     */
    @Test
    public void deleteQuestion()
    {
        assertThrows(DatabaseException.class, () ->
        {
            try
            {
                gateway = findGateway(NPCQuestionsForTest.ONE.getQuestionID());
            }
            catch (DatabaseException e)
            {
                fail("An exception was thrown before the delete operation could be tested.");
            }

            gateway.delete();
            findGateway(NPCQuestionsForTest.ONE.getQuestionID());
        });
    }

    /**
     * Tests to see if we can delete a question from the database
     *
     * @throws DatabaseException Will be thrown when looking for a deleted question
     */
    @Test
    public void deleteQuestionNoException() throws DatabaseException
    {
        gateway = findGateway(NPCQuestionsForTest.ONE.getQuestionID());

        try
        {
            gateway.delete();
        }
        catch (DatabaseException e)
        {
            fail("The delete operation should not have thrown an exception.");
        }
    }

    /**
     * Tests to see if we can create a question and find the information
     * about the question
     *
     * @throws DatabaseException Will be thrown if we cannot find the question
     *                           based off the ID
     */
    @Test
    public void findCreatedQuestion() throws DatabaseException
    {
        NPCQuestionRowDataGateway gateway =
                new NPCQuestionRowDataGateway("hello?", "world.",
                        LocalDate.of(2012, 2, 14), LocalDate.of(2018, 12, 15));

        NPCQuestionRowDataGateway gateway2 =
                new NPCQuestionRowDataGateway(gateway.getQuestionID());
        assertNotNull(gateway2);
        assertEquals(gateway.getQuestionStatement(), gateway2.getQuestionStatement());
        assertEquals(gateway.getAnswer(), gateway2.getAnswer());

        assertEquals(LocalDate.of(2012, 2, 14), gateway2.getStartDate());
        assertEquals(LocalDate.of(2018, 12, 15), gateway2.getEndDate());
    }

    /**
     * make sure we get the right exception if we try to find someone who doesn't
     * exist
     */
    @Test
    public void findNotExisting()
    {
        assertThrows(DatabaseException.class, () ->
        {
            gateway = findGateway(11111);
        });
    }

    /**
     * @return a random gateway
     * @throws DatabaseException shouldn't
     */
    public NPCQuestionRowDataGateway findRandomGateway() throws DatabaseException
    {
        return NPCQuestionRowDataGateway.findRandomGateway();
    }

    /**
     * Make sure we can retrieve a specific question
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void finder() throws DatabaseException
    {
        NPCQuestionsForTest question = NPCQuestionsForTest.ONE;
        gateway = findGateway(NPCQuestionsForTest.ONE.getQuestionID());
        assertEquals(question.getQuestionID(), gateway.getQuestionID());
        assertEquals(question.getQ(), gateway.getQuestionStatement());
        assertEquals(question.getA(), gateway.getAnswer());

        assertEquals(question.getStartDate(), gateway.getStartDate());
        assertEquals(question.getEndDate(), gateway.getEndDate());
    }

    /**
     * make sure we can retrieve the data transfer object for a given question
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testGetQuestionDTO() throws DatabaseException
    {
        NPCQuestionsForTest question = NPCQuestionsForTest.FOUR;
        NPCQuestionRowDataGateway gateway =
                new NPCQuestionRowDataGateway(question.getQ(), question.getA(),
                        question.getStartDate(), question.getEndDate());

        QuestionDTO questionInfo = gateway.getQuestionInfo();

        assertEquals(question.getA(), questionInfo.getAnswer());
        assertEquals(question.getQ(), questionInfo.getQuestion());
        assertEquals(question.getStartDate(), questionInfo.getStartDate());
        assertEquals(question.getEndDate(), questionInfo.getEndDate());
    }

    /**
     * Tests to see if we can update the NPCQuestion database table
     *
     * @throws DatabaseException Shouldn't
     */
    @Test
    public void updateQuestionRowDataGateway() throws DatabaseException
    {
        NPCQuestionsForTest question = NPCQuestionsForTest.ONE;
        gateway = findGateway(NPCQuestionsForTest.ONE.getQuestionID());
        assertEquals(question.getQ(), gateway.getQuestionStatement());
        gateway.setQuestion("This is a test?");
        gateway.setAnswer("Another Test");
        gateway.setStartDate(LocalDate.of(2014, 2, 11));
        gateway.setEndDate(LocalDate.of(2015, 3, 21));
        gateway.updateGateway();
        gateway = findGateway(NPCQuestionsForTest.ONE.getQuestionID());
        assertEquals("This is a test?", gateway.getQuestionStatement());
        assertEquals("Another Test", gateway.getAnswer());
        assertEquals(LocalDate.of(2014, 2, 11), gateway.getStartDate());
        assertEquals(LocalDate.of(2015, 3, 21), gateway.getEndDate());

    }

    /**
     * get a gateway for a given question
     *
     * @param questionID the unique ID of the question we are interested in
     * @return the gateway
     * @throws DatabaseException if we couldn't find the ID in the data source
     */
    NPCQuestionRowDataGateway findGateway(int questionID) throws DatabaseException
    {
        return new NPCQuestionRowDataGateway(questionID);
    }

}
