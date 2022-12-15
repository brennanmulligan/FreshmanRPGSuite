package edu.ship.engr.shipsim.communication.handlers;

import edu.ship.engr.shipsim.communication.messages.DoubloonPrizeMessage;
import edu.ship.engr.shipsim.communication.messages.Message;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.PopulateDoubloonManagerCommand;

/**
 * @author Andrew M, Christian C.
 * <p>
 * This is the doubloon prize handler that the command uses
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
