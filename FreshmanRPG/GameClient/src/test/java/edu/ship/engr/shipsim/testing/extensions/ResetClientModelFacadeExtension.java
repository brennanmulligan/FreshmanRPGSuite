package edu.ship.engr.shipsim.testing.extensions;

import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetClientModelFacade;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

public class ResetClientModelFacadeExtension implements BeforeEachCallback, AfterEachCallback
{
    @Override
    public void beforeEach(ExtensionContext context) throws Exception
    {
        ClientModelFacade.resetSingleton();
        ClientModelFacade.getSingleton(true, true);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception
    {
        Optional<ResetClientModelFacade> annotation = getAnnotation(context);

        if (annotation.isPresent())
        {
            if (annotation.get().shouldClearQueue())
            {
                ClientModelFacade.getSingleton().emptyQueue();
            }
        }
    }

    private Optional<ResetClientModelFacade> getAnnotation(ExtensionContext context)
    {
        if (context.getTestClass().isPresent())
        {
            Class<?> clazz = context.getTestClass().get();
            ResetClientModelFacade annotation = clazz.getAnnotation(ResetClientModelFacade.class);

            return Optional.ofNullable(annotation);
        }

        return Optional.empty();
    }
}
