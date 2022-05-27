package model;

import static org.junit.Assert.assertEquals;

import datasource.ServerSideTest;
import model.reports.NPCChatReport;
import org.junit.Before;
import org.junit.Test;

import datasource.DatabaseException;
import datatypes.ChatType;
import datatypes.PlayersForTest;

/**
 * @author Frank & Dave & Nick
 *
 *         Make sure that the QuizBotBehavior acts as expected
 */
public class QuizBotBehaviorTest extends ServerSideTest
{
	private QuizBotBehavior behavior;
	private NPCQuestion question;
	private Player player;

	/**
	 * @throws DatabaseException shouldn't Set up the behavior and a question
	 *             for each test
	 */
	@Before
	public void localSetUp() throws DatabaseException
	{
		behavior = new QuizBotBehavior(PlayersForTest.QUIZBOT.getPlayerID());
		question = behavior.getQuestion();
		player = PlayerManager.getSingleton().addPlayer(PlayersForTest.ANDY.getPlayerID());

		// Loads Quizbot into PlayerManager
		PlayerManager.getSingleton().addPlayer(PlayersForTest.QUIZBOT.getPlayerID());

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
		String answer = question.getAnswer();

		// check that spaces don't matter
		NPCChatReport report = new NPCChatReport(player.getPlayerID(), 0,
				"    " + answer + " ", player.getPlayerPosition(), ChatType.Local);
		int score = player.getQuizScore();

		behavior.receiveReport(report);
		assertEquals(score + 1, player.getQuizScore());
		player.setDoubloons(score);
	}

	/**
	 * If a player answers incorrectly, their score should not change.
	 */
	@Test
	public void testIncorrectAnswer()
	{
		NPCChatReport report = new NPCChatReport(player.getPlayerID(), 0, "incorrect",
				player.getPlayerPosition(), ChatType.Local);
		int score = player.getQuizScore();
		behavior.receiveReport(report);
		assertEquals(score, player.getQuizScore());
	}
}
