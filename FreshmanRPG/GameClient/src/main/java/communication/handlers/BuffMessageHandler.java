package communication.handlers;

import communication.messages.BuffMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandDisplayText;

/**
 * Handles message from server for buff messages
 *
 * @author Andy, Emmanuel
 */
public class BuffMessageHandler extends MessageHandler
{
	/**
	 * queues command for buff
	 */
	@Override
	public void process(Message msg)
	{
		BuffMessage buffMsg = (BuffMessage) msg;
		CommandDisplayText cmd = new CommandDisplayText("You have received " + buffMsg.getExperiencePointPool() + " Rec Center Bonus Points.");
		ClientModelFacade.getSingleton().queueCommand(cmd);
	}

	/**
	 * returns the message type that we handle
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return BuffMessage.class;
	}

}
