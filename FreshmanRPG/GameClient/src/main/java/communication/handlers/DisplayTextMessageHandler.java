package communication.handlers;

import communication.messages.DisplayTextMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandDisplayText;

/**
 * Handles message from server for text messages
 * @author Andy, Emmanuel, Adam, Truc
 *
 */
public class DisplayTextMessageHandler extends MessageHandler
{
	/**
	 * queues command to display text
	 */
	@Override
	public void process(Message msg)
	{
		DisplayTextMessage textMsg = (DisplayTextMessage) msg;
		CommandDisplayText cmd = new CommandDisplayText(textMsg.getText());
		ClientModelFacade.getSingleton().queueCommand(cmd);
	}

	/**
	 * returns the message type that we handle
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return DisplayTextMessage.class;
	}

}
