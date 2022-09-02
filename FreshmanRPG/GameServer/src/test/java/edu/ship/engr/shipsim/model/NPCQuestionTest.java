package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.ServerSideTest;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test the NPCQuestion
 *
 * @author Steve
 */
public class NPCQuestionTest extends ServerSideTest
{
    /**
     * Want to make sure that we get different questions back from the DB. Don't
     * care what they are
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void testGetRandomQuestion() throws DatabaseException
    {
        ArrayList<NPCQuestion> list = new ArrayList<>();
        for (int i = 0; i < 5; i++)
        {
            NPCQuestion q = NPCQuestion.getRandomQuestion();
            assertNotNull(q);
            if (!list.contains(q))
            {
                list.add(q);
            }
        }

        assertTrue(list.size() > 1);
    }

}
