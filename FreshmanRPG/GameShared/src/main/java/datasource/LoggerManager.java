package datasource;

import model.OptionsManager;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerManager
{
    private static LoggerManager loggerManager;
    private Logger logger;

    private LoggerManager()
    {

    }

    public static LoggerManager createLogger(String title)
    {
        if (loggerManager != null)
        {
            throw new IllegalStateException(
                    "Logger has already been created - you " + "can't do that twice");
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
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        logger = Logger.getLogger(title);
        FileHandler fileHandler = null;
        try
        {
            fileHandler = new FileHandler(title + ".log");
            fileHandler.setLevel(Level.ALL);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //Assigning handlers to LOGGER object
        logger.addHandler(fileHandler);
        logger.setLevel(Level.FINEST);
        logger.fine("Started logging");
    }
}
