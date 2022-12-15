package edu.ship.engr.shipsim.testing.extensions;

import edu.ship.engr.shipsim.model.ModelFacade;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ResetModelFacadeExtension implements BeforeEachCallback
{
    @Override
    public void beforeEach(ExtensionContext context) throws Exception
    {
        ModelFacade.resetSingleton();
        ModelFacade.getSingleton();
    }
}
