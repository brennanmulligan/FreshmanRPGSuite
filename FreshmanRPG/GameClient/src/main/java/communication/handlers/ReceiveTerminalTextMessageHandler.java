package communication.handlers;

import communication.messages.Message;
import communication.messages.ReceiveTerminalTextMessage;
import model.ClientModelFacade;
import model.CommandRecieveTerminalResponse;

/**
 * @author Denny Fleagle
 * @author Ben Lehman
 */
public class ReceiveTerminalTextMessageHandler extends MessageHandler
{
	/**
	 * Process the message 
	 *
	 * Currently the process only prints to the console 
	 * we should build a command that will do something more 
	 * meaningful in the future. ie, display to the client in the GUI
	 */
	@Override
	public void process(Message msg)
	{
		ReceiveTerminalTextMessage message = (ReceiveTerminalTextMessage) msg;
		CommandRecieveTerminalResponse cmd = new CommandRecieveTerminalResponse(
				message.getRequestingPlayerID(), message.getResultText());

		ClientModelFacade.getSingleton().queueCommand(cmd);
	}

	/**
	 * identify the type of the message we are handling
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return ReceiveTerminalTextMessage.class;
	}

}
