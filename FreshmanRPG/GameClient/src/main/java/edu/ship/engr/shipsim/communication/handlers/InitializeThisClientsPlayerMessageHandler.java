package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.InitializeThisClientsPlayerMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.*;

/**
 * Handles the CurrentQuestState message
 *
 * @author Merlin
 */
public class InitializeThisClientsPlayerMessageHandler extends MessageHandler
{

    /**
     * Queues the command in Model Facade
     *
     * @see MessageHandler#process(Message)
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
     * @see MessageHandler#getMessageTypeWeHandle()
     */
    @Override
    public Class<?> getMessageTypeWeHandle()
    {
        return InitializeThisClientsPlayerMessage.class;
    }

}
