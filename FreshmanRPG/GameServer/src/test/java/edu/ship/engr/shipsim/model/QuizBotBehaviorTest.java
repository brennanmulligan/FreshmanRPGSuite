package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.datatypes.PlayersForTest;
import edu.ship.engr.shipsim.model.reports.NPCChatReport;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import edu.ship.engr.shipsim.testing.annotations.ResetChatManager;
import edu.ship.engr.shipsim.testing.annotations.ResetPlayerManager;
import edu.ship.engr.shipsim.testing.annotations.ResetReportObserverConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Frank & Dave & Nick
 * <p>
 * Make sure that the QuizBotBehavior acts as expected
 */
@GameTest("GameServer")
@ResetPlayerManager
@ResetChatManager
@ResetReportObserverConnector
public class QuizBotBehaviorTest
{
    private QuizBotBehavior behavior;
    private NPCQuestion question;
    private Player player;

    /**
     * @throws DatabaseException shouldn't Set up the behavior and a question
     *                           for each test
     */
    @BeforeEach
    public void localSetUp() throws DatabaseException
    {
        behavior = new QuizBotBehavior(PlayersForTest.QUIZBOT.getPlayerID());
        question = behavior.getQuestion();
        player = PlayerManager.getSingleton().addPlayer(PlayersForTest.ANDY.getPlayerID());

        // Loads Quizbot into PlayerManager
        PlayerManager.getSingleton().addPlayer(PlayersForTest.QUIZBOT.getPlayerID());
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
