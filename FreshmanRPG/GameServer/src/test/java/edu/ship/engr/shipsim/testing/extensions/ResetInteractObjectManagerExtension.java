package edu.ship.engr.shipsim.testing.extensions;

import edu.ship.engr.shipsim.model.InteractObjectManager;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ResetInteractObjectManagerExtension implements BeforeEachCallback
{
    @Override
    public void beforeEach(ExtensionContext context) throws Exception
    {
        InteractObjectManager.resetSingleton();
    }
}
