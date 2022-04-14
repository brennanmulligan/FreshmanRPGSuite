package communication.handlers;

import communication.messages.InitializeThisClientsPlayerMessage;
import communication.messages.Message;
import model.ClientModelFacade;
import model.CommandDoubloonsChanged;
import model.CommandOverwriteExperience;
import model.CommandOverwriteQuestState;
import model.CommandUpdateFriendsList;

/**
 * Handles the CurrentQuestState message
 *
 * @author Merlin
 *
 */
public class InitializeThisClientsPlayerMessageHandler extends MessageHandler
{

	/**
	 * Queues the command in Model Facade
	 * @see communication.handlers.MessageHandler#process(communication.messages.Message)
	 */
	@Override
	public void process(Message msg)
	{
		InitializeThisClientsPlayerMessage ourMsg = (InitializeThisClientsPlayerMessage) msg;
		CommandOverwriteQuestState cmd = new CommandOverwriteQuestState(ourMsg);
		CommandOverwriteExperience cmdExperiencePts = new CommandOverwriteExperience(ourMsg);
		CommandDoubloonsChanged cmdDoubloons = new CommandDoubloonsChanged(ourMsg);
		CommandUpdateFriendsList cmdFriendList = new CommandUpdateFriendsList(ourMsg);
		ClientModelFacade.getSingleton().queueCommand(cmd);
		ClientModelFacade.getSingleton().queueCommand(cmdExperiencePts);
		ClientModelFacade.getSingleton().queueCommand(cmdDoubloons);
		ClientModelFacade.getSingleton().queueCommand(cmdFriendList);
	}

	/**
	 * @return message type that we expect
	 * @see communication.handlers.MessageHandler#getMessageTypeWeHandle()
	 */
	@Override
	public Class<?> getMessageTypeWeHandle()
	{
		return InitializeThisClientsPlayerMessage.class;
	}

}
