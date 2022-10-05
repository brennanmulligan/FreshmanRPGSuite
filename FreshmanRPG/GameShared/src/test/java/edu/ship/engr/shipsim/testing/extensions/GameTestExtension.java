package edu.ship.engr.shipsim.testing.extensions;

import edu.ship.engr.shipsim.datasource.DatabaseManager;
import edu.ship.engr.shipsim.datasource.LoggerManager;
import edu.ship.engr.shipsim.model.OptionsManager;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class GameTestExtension implements BeforeEachCallback, AfterEachCallback
{
    @Override
    public void beforeEach(ExtensionContext context) throws Exception
    {
        createLogger(context);

        OptionsManager.resetSingleton();
        OptionsManager.getSingleton().setTestMode(true);
        OptionsManager.getSingleton().setUsingTestDB(true);

        DatabaseManager.getSingleton().setTesting();
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception
    {
        LoggerManager.resetSingleton();

        DatabaseManager.resetSingleton();
    }

    private void createLogger(ExtensionContext context)
    {
        if (context.getTestClass().isPresent())
        {
            Class<?> clazz = context.getTestClass().get();
            GameTest annotation = clazz.getAnnotation(GameTest.class);

            if (annotation != null)
            {
                String loggerName = annotation.value();

                if (annotation.createLogger())
                {
                    LoggerManager.createLogger(loggerName + "Tests");
                }
            }
        }
    }
}
