package model;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;

/**
 * Test the NPCQuestion
 *
 * @author Steve
 */
public class NPCQuestionTest
{

	/**
	 *
	 */
	@Before
	public void setUp()
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
	}

	/**
	 * Want to make sure that we get different questions back from the DB. Don't
	 * care what they are
	 *
	 * @throws DatabaseException
	 *             shouldn't
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
