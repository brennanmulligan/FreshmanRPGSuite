package communication.handlers;

import communication.messages.DoubloonPrizeMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.PopulateDoubloonManagerCommand;

/**
 *
 * @author Andrew M, Christian C.
 *
 * This is the doubloon prize handler that the command uses
 *
 */
public class DoubloonPrizeHandler extends MessageHandler
{

	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(DoubloonPrizeMessage.class))
		{
			DoubloonPrizeMessage message = (DoubloonPrizeMessage) msg;

			PopulateDoubloonManagerCommand cmd = new PopulateDoubloonManagerCommand(message.getDtos());
			ClientModelFacade.getSingleton().queueCommand(cmd);
		}

	}

	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return DoubloonPrizeMessage.class;
	}

}
