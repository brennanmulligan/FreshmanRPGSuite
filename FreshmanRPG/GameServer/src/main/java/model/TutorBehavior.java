package model;

import java.util.ArrayList;

import datatypes.ChatType;
import datatypes.PlayersForTest;
import datatypes.Position;
import model.reports.ChatMessageReceivedReport;

/**
 * @author cr5603
 *
 */
public class TutorBehavior extends NPCBehavior
{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates the behavior for tutors to respond to local messages
	 * @param playerID the tutor's unique id
	 */
	public TutorBehavior(int playerID)
	{
		super(playerID);
		setUpListening();
	}

	/**
	 *
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report instanceof ChatMessageReceivedReport)
		{
			ChatMessageReceivedReport chatReport = (ChatMessageReceivedReport) report;

			if (chatReport.getType() == ChatType.Local && chatReport.getSenderID() != PlayersForTest.TUTOR.getPlayerID())
			{
				ChatManager.getSingleton().sendChatToClients(playerID, 0, "Hello, student", new Position(10, 3), ChatType.Local);
			}

		}
	}

	/**
	 * @see model.NPCBehavior#doTimedEvent()
	 */
	@Override
	protected void doTimedEvent()
	{
		//Does nothing for tutors	
	}

	/**
	 * @see model.NPCBehavior#getReportTypes()
	 */
	@Override
	protected ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		ArrayList<Class<? extends QualifiedObservableReport>> reportTypes = new ArrayList<>();
		reportTypes.add(ChatMessageReceivedReport.class);
		return reportTypes;
	}


}
