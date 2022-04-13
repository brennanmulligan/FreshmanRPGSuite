package communication.handlers;

import communication.messages.LoginFailedMessage;
import communication.messages.LoginMessage;
import communication.messages.LoginSuccessfulMessage;
import communication.messages.Message;
import model.LoginFailedException;
import model.LoginPlayerManager;
import model.reports.LoginSuccessfulReport;

/**
 * Handles a report of a player moving
 *
 * @author merlin
 *
 */
public class LoginMessageHandler extends MessageHandler
{

	/**
	 * When a player tries to login, we should tell the model so that it can
	 * check the credentials
	 *
	 * @see MessageHandler#process(Message)
	 */
	@Override
	public void process(Message msg)
	{
		LoginMessage loginMsg = (LoginMessage) msg;
		try
		{
			LoginSuccessfulReport report = LoginPlayerManager.getSingleton().login(loginMsg.getPlayerName(),
					loginMsg.getPassword());
			LoginSuccessfulMessage response = new LoginSuccessfulMessage(report.getPlayerID(), report.getHostname(),
					report.getPort(), report.getPin());
			this.getStateAccumulator().queueMessage(response);
		}
		catch (LoginFailedException e)
		{
			LoginFailedMessage response = new LoginFailedMessage(e.getMessage());
			this.getStateAccumulator().queueMessage(response);
		}
	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return LoginMessage.class;
	}

}
