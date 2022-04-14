package communication.handlers;

import communication.messages.DoubloonsChangedMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandDoubloonsChanged;

/**
 * @author Matthew Croft
 * @author Evan Stevenson
 */
public class DoubloonsChangedMessageHandler extends MessageHandler
{

	/**
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		if (msg.getClass().equals(DoubloonsChangedMessage.class))
		{
			DoubloonsChangedMessage doubloonsChangedMessage = (DoubloonsChangedMessage) msg;
			CommandDoubloonsChanged cmd = new CommandDoubloonsChanged(doubloonsChangedMessage.getPlayerID(), doubloonsChangedMessage.getDoubloons(), doubloonsChangedMessage.getBuffPool());
			ClientModelFacade.getSingleton().queueCommand(cmd);
		}

	}

	/**
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		// TODO Auto-generated method stub
		return DoubloonsChangedMessage.class;
	}

}
