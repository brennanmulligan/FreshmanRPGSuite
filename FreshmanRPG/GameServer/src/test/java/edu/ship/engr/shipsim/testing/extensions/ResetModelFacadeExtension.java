package edu.ship.engr.shipsim.testing.extensions;

import edu.ship.engr.shipsim.model.ModelFacade;
import edu.ship.engr.shipsim.testing.annotations.ResetModelFacade;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

public class ResetModelFacadeExtension implements BeforeEachCallback, AfterEachCallback
{
    @Override
    public void beforeEach(ExtensionContext context) throws Exception
    {
        ModelFacade.resetSingleton();
        ModelFacade.getSingleton();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception
    {
        Optional<ResetModelFacade> annotation = getAnnotation(context);

        if (annotation.isPresent())
        {
            if (annotation.get().waitUntilEmpty())
            {
                while (ModelFacade.getSingleton().hasCommandsPending())
                {
                    Thread.sleep(500);
                }
            }
        }
    }

    private Optional<ResetModelFacade> getAnnotation(ExtensionContext context)
    {
        if (context.getTestClass().isPresent())
        {
            Class<?> clazz = context.getTestClass().get();
            ResetModelFacade annotation = clazz.getAnnotation(ResetModelFacade.class);

            return Optional.ofNullable(annotation);
        }

        return Optional.empty();
    }
}
