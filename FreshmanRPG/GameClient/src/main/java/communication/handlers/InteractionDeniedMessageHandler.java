package communication.handlers;

import communication.messages.InteractionDeniedMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandInteractionDenied;

/**
 * Message handler for when Interaction is Denied
 */
public class InteractionDeniedMessageHandler extends MessageHandler
{

	/** (non-Javadoc)
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		ClientModelFacade.getSingleton().queueCommand(new CommandInteractionDenied());
	}

	/** (non-Javadoc)
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return InteractionDeniedMessage.class;
	}

}
