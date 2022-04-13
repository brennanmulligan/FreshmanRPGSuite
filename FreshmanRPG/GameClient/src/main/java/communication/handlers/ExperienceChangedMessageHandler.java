package communication.handlers;

import communication.messages.ExperienceChangedMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandOverwriteExperience;

/**
 * @author Ryan
 *
 */
public class ExperienceChangedMessageHandler extends MessageHandler
{

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(ExperienceChangedMessage.class))
		{
			ExperienceChangedMessage experienceChangedMessage = (ExperienceChangedMessage) msg;
			CommandOverwriteExperience cmd = new CommandOverwriteExperience(experienceChangedMessage.getPlayerID(), experienceChangedMessage.getExperiencePoints(),
					experienceChangedMessage.getLevel());
			ClientModelFacade.getSingleton().queueCommand(cmd);
		}

	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return ExperienceChangedMessage.class;
	}

}
