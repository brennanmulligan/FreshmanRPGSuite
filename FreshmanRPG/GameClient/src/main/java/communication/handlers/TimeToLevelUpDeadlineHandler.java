package communication.handlers;

import communication.messages.Message;
import communication.messages.TimeToLevelUpDeadlineMessage;
import model.QualifiedObservableConnector;
import model.reports.ClientTimeToLevelUpDeadlineReport;

/**
 * @author Evan, Marty, Chris
 *
 */
public class TimeToLevelUpDeadlineHandler extends MessageHandler
{

	/**
	 * A player has joined our area server, so notify the PlayerManager of his
	 * presence
	 *
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{

		if (msg.getClass().equals(TimeToLevelUpDeadlineMessage.class))
		{
			TimeToLevelUpDeadlineMessage realMsg = (TimeToLevelUpDeadlineMessage) msg;
			ClientTimeToLevelUpDeadlineReport report = new ClientTimeToLevelUpDeadlineReport(realMsg.getPlayerID(), realMsg.getTimeToDeadline(), realMsg.getNextLevel());
			QualifiedObservableConnector.getSingleton().sendReport(report);
		}

	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return TimeToLevelUpDeadlineMessage.class;
	}

}
