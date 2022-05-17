package communication.handlers;

import communication.messages.LoginFailedMessage;
import model.ClientModelFacade;
import model.Command;
import model.CommandLoginFailed;
import model.OptionsManager;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Matt and Andy
 */
public class LoginFailedMessageHandlerTest
{
    @BeforeClass
    public static void hardReset()
    {
        OptionsManager.getSingleton().setTestMode(true);
    }

    /**
     * Reset the ModelFacade
     */
    @Before
    public void setUp()
    {
        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, false);
    }

    /**
     * We should add a command to the ModelFacade command queue
     *
     * @throws InterruptedException shouldn't
     */
    @Test
    public void test() throws InterruptedException
    {
        LoginFailedMessageHandler handler = new LoginFailedMessageHandler();
        LoginFailedMessage msg = new LoginFailedMessage();
        handler.process(msg);
        assertEquals(1, ClientModelFacade.getSingleton().getCommandQueueLength());
        Command cmd = ClientModelFacade.getSingleton().getNextCommand();
        assertEquals(cmd.getClass(), CommandLoginFailed.class);
        ClientModelFacade.getSingleton().emptyQueue();
    }

    /**
     * Test the type of Message that we expect
     */
    @Test
    public void typeWeHandle()
    {
        LoginFailedMessageHandler h = new LoginFailedMessageHandler();
        assertEquals(LoginFailedMessage.class, h.getMessageTypeWeHandle());
    }

}
