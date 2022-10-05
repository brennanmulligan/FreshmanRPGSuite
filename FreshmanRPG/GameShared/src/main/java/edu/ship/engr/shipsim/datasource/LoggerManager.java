package edu.ship.engr.shipsim.datasource;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

public class LoggerManager
{
    private static volatile LoggerManager loggerManager;
    private Logger logger;

    private LoggerManager()
    {

    }

    private static FileHandler createFileHandler(String title) throws IOException
    {
        FileHandler fileHandler;
        fileHandler = new FileHandler(title + ".log", true);
        fileHandler.setLevel(Level.INFO);

        fileHandler.setFormatter(new SimpleFormatter()
        {
            private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

            @Override
            public synchronized String format(LogRecord lr)
            {
                return String.format(format, new Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(), lr.getMessage());
            }
        });
        return fileHandler;
    }

    public static LoggerManager createLogger(String title)
    {
        if (loggerManager != null)
        {
            throw new IllegalStateException(
                    "Logger has already been created - you can't do that twice");
        }
        loggerManager = new LoggerManager();
        loggerManager.setFile(title);
        return loggerManager;
    }

    public synchronized static LoggerManager getSingleton()
    {
        if (loggerManager == null)
        {
            throw new IllegalStateException("Logger hasn't been set up");
        }
        return loggerManager;
    }

    public static void resetSingleton()
    {
        for (Handler handler : loggerManager.getLogger().getHandlers())
        {
            handler.close();
        }

        loggerManager = null;
    }

    public Logger getLogger()
    {
        return logger;
    }

    /**
     * Log the current stack trace for a given number of lines (skipping the call on
     * this method
     * @param numberOfLines the number of lines of that stack trace to long
     */
    public void logStackTrace(int numberOfLines)
    {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 1; i < numberOfLines; i++)
        {
            LoggerManager.getSingleton().getLogger().info(stackTrace[i].toString());
        }
    }

    private void setFile(String title)
    {
        //        System.setProperty("java.util.logging.SimpleFormatter.format",
        //                "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        logger = Logger.getLogger(title);
        try
        {
            FileHandler fileHandler = createFileHandler(title);
            //Assigning handlers to LOGGER object
            logger.addHandler(fileHandler);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        logger.info("Started logging");
    }
}
