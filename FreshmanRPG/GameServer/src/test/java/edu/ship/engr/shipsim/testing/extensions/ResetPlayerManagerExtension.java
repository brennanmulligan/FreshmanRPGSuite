package edu.ship.engr.shipsim.testing.extensions;

import edu.ship.engr.shipsim.model.PlayerManager;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ResetPlayerManagerExtension implements BeforeEachCallback
{
    @Override
    public void beforeEach(ExtensionContext context) throws Exception
    {
        PlayerManager.resetSingleton();
    }
}
