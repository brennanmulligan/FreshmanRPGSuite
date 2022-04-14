package model;

import java.util.ArrayList;

import datasource.DatabaseException;
import datatypes.ChatType;
import datatypes.Position;
import model.reports.ChatMessageReceivedReport;

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

	private NPCQuestion question;
	private boolean isRandom = true;
	private final ChatType chatType = ChatType.Local;


	/**
	 * Initialize the QuizBotBehavior
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
	 *
	 * @param playerID
	 * @param noFilePath does not need a filepath as of right now, needed due to filepath column in database
	 */
	public QuizBotBehavior(int playerID, String noFilePath)
	{
		this(playerID);
	}

	/**
	 * @param playerID the quizbot's unique id
	 * @param random
	 *            whether or not the quizbot will get a random question, vs a
	 *            specific question, used for testing.
	 */
	public QuizBotBehavior(int playerID, boolean random)
	{
		this(playerID);
		isRandom = random;
	}

	/**
	 * Asks the question at 30 second intervals.
	 */
	@Override
	protected void doTimedEvent()
	{
		askQuestion();
	}

	/**
	 * Used only for testing! Tells the quizbot to behave as if he has asked a particular question
	 * @param q the question the quizbot should be expecting
	 */
	public void setExpectedQuestion(NPCQuestion q)
	{
		question = q;
	}

	/**
	 * Asks the question.
	 */
	private void askQuestion()
	{
		String questionString = question.getQuestionStatement();

		ChatManager.getSingleton().sendChatToClients(playerID, 0, questionString, PlayerManager.getSingleton().getPlayerFromID(playerID).getPlayerPosition(), chatType);
		//ChatManager.getSingleton().sendChatToClients(playerID, 0, questionString, new Position(0, 0), chatType);
	}

	/**
	 * Gets a new random question from the database.
	 *
	 * @throws DatabaseException
	 */
	private void pullNewQuestion()
	{
		try
		{
			if (isRandom)
			{
				question = NPCQuestion.getRandomQuestion();
			}
			else
			{
				question = NPCQuestion.getSpecificQuestion(1);
			}
		}
		catch (DatabaseException e)
		{
			System.err.println("Unable to retrieve a random question for the quiz bot");
			e.printStackTrace();
		}
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
	public ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<>();
		reportTypes.add(ChatMessageReceivedReport.class);
		return reportTypes;
	}




	/**
	 * Watches SendChatMessageReports for a correct answer. On correct answer,
	 * announce the correct answer, pull a new random question, and ask that
	 * question. *
	 *
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport incomingReport)
	{
		if (incomingReport instanceof ChatMessageReceivedReport)
		{
			String answer = question.getAnswer().toLowerCase().replaceAll(" ", "");
			ChatMessageReceivedReport report = (ChatMessageReceivedReport) incomingReport;
			String userAnswer = report.getChatText().toLowerCase().replaceAll(" ", "");
			if (answer.equals(userAnswer))
			{
				Player player = PlayerManager.getSingleton().getPlayerFromID(report.getSenderID());

				ChatManager.getSingleton().sendChatToClients(playerID, 0,player.getPlayerName() + " answered correctly.  The answer was " + question.getAnswer(), PlayerManager.getSingleton().getPlayerFromID(playerID).getPlayerPosition(), chatType);
				player.incrementDoubloons();
				ChatManager.getSingleton().sendChatToClients(playerID, 0,
						player.getPlayerName() + " score is now " + player.getQuizScore(), PlayerManager.getSingleton().getPlayerFromID(playerID).getPlayerPosition(),
						chatType);

				pullNewQuestion();
				askQuestion();
			}
		}

	}
}
