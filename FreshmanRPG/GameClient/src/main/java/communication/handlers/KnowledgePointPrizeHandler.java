package communication.handlers;

import communication.messages.ChatMessage;
import communication.messages.KnowledgePointPrizeMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandChatMessageReceived;
import model.PopulateKnowledgePointManagerCommand;

/**
 *
 * @author Andrew M, Christian C.
 *
 * This is the knowledge point prize handler that the command uses
 *
 */
public class KnowledgePointPrizeHandler extends MessageHandler
{

	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(KnowledgePointPrizeMessage.class))
		{
			KnowledgePointPrizeMessage kpMessage = (KnowledgePointPrizeMessage) msg;

			PopulateKnowledgePointManagerCommand cmd = new PopulateKnowledgePointManagerCommand(kpMessage.getDtos());
			ClientModelFacade.getSingleton().queueCommand(cmd);
		}

	}

	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return KnowledgePointPrizeMessage.class;
	}

}
