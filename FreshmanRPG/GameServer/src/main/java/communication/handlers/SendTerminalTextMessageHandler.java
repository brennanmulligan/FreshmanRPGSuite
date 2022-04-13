package communication.handlers;

import communication.messages.Message;
import communication.messages.SendTerminalTextMessage;
import model.CommandReceiveTerminalText;
import model.ModelFacade;

/**
 * Message Handler for OnlinePlayersMessage
 * @author Denny Fleagle
 * @author Ben Lehman
 * @author Austin Smale
 *
 */
public class SendTerminalTextMessageHandler extends MessageHandler
{

	/**
	 * Process the message
	 * @param msg message being sent
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(SendTerminalTextMessage.class))
		{
			SendTerminalTextMessage oMsg = (SendTerminalTextMessage) msg;

			CommandReceiveTerminalText cmd = new CommandReceiveTerminalText(oMsg.getPlayerID(), oMsg.getTerminalText());
			ModelFacade.getSingleton().queueCommand(cmd);
		}
	}

	/**
	 * We handle OnlinePlayersMessage coming from client
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return SendTerminalTextMessage.class;
	}

}
