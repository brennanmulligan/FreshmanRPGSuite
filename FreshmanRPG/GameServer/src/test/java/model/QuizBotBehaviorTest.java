package model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datatypes.ChatType;
import datatypes.Position;
import model.reports.ChatMessageReceivedReport;
import datatypes.PlayersForTest;

/**
 * @author Frank & Dave & Nick
 *
 *         Make sure that the QuizBotBehavior acts as expected
 */
public class QuizBotBehaviorTest
{

	private QuizBotBehavior behavior;
	private NPCQuestion question;

	/**
	 * @throws DatabaseException shouldn't Set up the behavior and a question
	 *             for each test
	 */
	@Before
	public void setUp() throws DatabaseException
	{
		OptionsManager.getSingleton().setUsingMocKDataSource(true);
		behavior = new QuizBotBehavior(PlayersForTest.QUIZBOT.getPlayerID());
		question = behavior.getQuestion();
		QualifiedObservableConnector.resetSingleton();
		ChatManager.resetSingleton();
	}

	/**
	 * When a question is correctly answered, the player who got it right should
	 * have their score incremented.
	 */
	@Test
	public void testCorrectAnswer()
	{
		Player p = PlayerManager.getSingleton().addPlayer(PlayersForTest.ANDY.getPlayerID());

		String answer = question.getAnswer();

		// check that spaces don't matter
		ChatMessageReceivedReport report = new ChatMessageReceivedReport(p.getPlayerID(), 0, "    " + answer + " ", new Position(0, 0),
				ChatType.Zone);
		int score = p.getQuizScore();

		behavior.receiveReport(report);
		assertEquals(score + 1, p.getQuizScore());
		p.setQuizScore(score);
	}

	/**
	 * If a player answers incorrectly, their score should not change.
	 */
	@Test
	public void testIncorrectAnswer()
	{
		Player p = PlayerManager.getSingleton().addPlayer(PlayersForTest.ANDY.getPlayerID());
		ChatMessageReceivedReport report = new ChatMessageReceivedReport(p.getPlayerID(), 0, "incorrect", new Position(0, 0),
				ChatType.Zone);

		int score = p.getQuizScore();

		behavior.receiveReport(report);

		assertEquals(score, p.getQuizScore());
	}
}
