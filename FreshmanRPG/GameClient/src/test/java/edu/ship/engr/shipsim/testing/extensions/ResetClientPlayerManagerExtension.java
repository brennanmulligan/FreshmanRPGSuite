package edu.ship.engr.shipsim.testing.extensions;

import edu.ship.engr.shipsim.model.ClientPlayerManager;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ResetClientPlayerManagerExtension implements BeforeEachCallback
{
    @Override
    public void beforeEach(ExtensionContext context) throws Exception
    {
        ClientPlayerManager.resetSingleton();
    }
}
