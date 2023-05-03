package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.ChatType;
import edu.ship.engr.shipsim.model.reports.NPCChatReport;

import java.util.ArrayList;

/**
 * The Bot Behavior of the Rec Center NPC.
 *
 * @author Frank and Dave
 */
public class QuizBotBehavior extends NPCBehavior
{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final ChatType chatType = ChatType.Zone;
    private NPCQuestion question;
    private boolean isRandom = true;


    /**
     * Initialize the QuizBotBehavior
     *
     * @param playerID The quizbot's unique ID
     */
    public QuizBotBehavior(int playerID)
    {
        super(playerID);
        pollingInterval = 30000;
        pullNewQuestion();
        setUpListening();
    }

    /**
     * @return the question currently being asked by the quiz bot
     */
    public NPCQuestion getQuestion()
    {
        return this.question;
    }

    /**
     * Get report types that this class watches for.
     */
    @Override
    public ArrayList<Class<? extends Report>> getReportTypes()
    {
        ArrayList<Class<? extends Report>> reportTypes =
                new ArrayList<>();
        reportTypes.add(NPCChatReport.class);
        return reportTypes;
    }

    /**
     * Watches SendChatMessageReports for a correct answer. On correct answer,
     * announce the correct answer, pull a new random question, and ask that
     * question. *
     *
     * @see ReportObserver#receiveReport(Report)
     */
    @Override
    public void receiveReport(Report incomingReport)
    {
        if (incomingReport instanceof NPCChatReport)
        {
            String answer = question.getAnswer().toLowerCase().replaceAll(" ", "");
            NPCChatReport report = (NPCChatReport) incomingReport;
            String userAnswer = report.getChatText().toLowerCase().replaceAll(" ", "");
            if (answer.equals(userAnswer))
            {
                Player player = PlayerManager.getSingleton()
                        .getPlayerFromID(report.getSenderID());

                ChatManager.getSingleton().sendChatToClients(playerID, 0,
                        player.getPlayerName() + " answered correctly.  The answer was " +
                                question.getAnswer(),
                        PlayerManager.getSingleton().getPlayerFromID(playerID)
                                .getPosition(), chatType);
                player.incrementDoubloons();
                ChatManager.getSingleton().sendChatToClients(playerID, 0,
                        player.getPlayerName() + " score is now " + player.getQuizScore(),
                        PlayerManager.getSingleton().getPlayerFromID(playerID)
                                .getPosition(), chatType);

                player.receiveBike();
                //                pullNewQuestion();
                //                askQuestion();
            }
        }

    }

    /**
     * Used only for testing! Tells the quizbot to behave as if he has asked a particular question
     *
     * @param q the question the quizbot should be expecting
     */
    public void setExpectedQuestion(NPCQuestion q)
    {
        question = q;
        isRandom = false;
    }

    /**
     * Asks the question.
     */
    private void askQuestion()
    {
        String questionString = question.getQuestionStatement();

        ChatManager.getSingleton().sendChatToClients(playerID, 0, questionString,
                PlayerManager.getSingleton().getPlayerFromID(playerID)
                        .getPosition(), chatType);
        //ChatManager.getSingleton().sendChatToClients(playerID, 0, questionString, new Position(0, 0), chatType);
    }

    /**
     * Gets a new random question from the database.
     */
    private void pullNewQuestion()
    {
        try
        {
            if (isRandom)
            {
                question = NPCQuestion.getRandomQuestion();
            }
        }
        catch (DatabaseException e)
        {
            System.err.println("Unable to retrieve a random question for the quiz bot");
            e.printStackTrace();
        }
    }

    /**
     * Asks the question at 30 second intervals.
     */
    @Override
    protected void doTimedEvent()
    {
        askQuestion();
    }
}
