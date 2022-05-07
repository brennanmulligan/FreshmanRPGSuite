package datasource;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerManager
{
    private Logger logger;
    private static LoggerManager loggerManager;

    public static LoggerManager createLogger(String title)
    {
        if (loggerManager != null)
        {
            throw new IllegalStateException("Logger has already been created - you " +
                    "can't do that twice");
        }
        loggerManager = new LoggerManager();
        loggerManager.setFile(title);
        return loggerManager;
    }

    public static LoggerManager getSingleton()
    {
        if (loggerManager == null)
        {
            throw new IllegalStateException("Logger hasn't been set up");
        }
        return loggerManager;
    }
    private LoggerManager()
    {

    }

    public static void resetSingleton()
    {
        loggerManager = null;
    }

    public Logger getLogger()
    {
        return logger;
}
    private void setFile(String title)
    {
        logger = Logger.getLogger(title);
        FileHandler fileHandler = null;
        try
        {
            fileHandler = new FileHandler(title + ".log");
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        //Assigning handlers to LOGGER object
        logger.addHandler(fileHandler);

        fileHandler.setLevel(Level.ALL);
        logger.setLevel(Level.ALL);
    }
}
