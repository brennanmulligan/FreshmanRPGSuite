package edu.ship.engr.shipsim.testing.extensions;

import edu.ship.engr.shipsim.model.ChatManager;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ResetChatManagerExtension implements BeforeEachCallback
{
    @Override
    public void beforeEach(ExtensionContext context) throws Exception
    {
        ChatManager.resetSingleton();
    }
}
