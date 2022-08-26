package datasource;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

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

    private static FileHandler createFileHandler(String title) throws IOException
    {
        FileHandler fileHandler;
        fileHandler = new FileHandler(title + ".log");
        fileHandler.setLevel(Level.INFO);

        fileHandler.setFormatter(new SimpleFormatter()
        {
            private static final String format = "[%1$tF %1$tT] [%2$-7s] %3$s %n";

            @Override
            public synchronized String format(LogRecord lr)
            {
                return String.format(format,
                        new Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(),
                        lr.getMessage()
                );
            }
        });
        return fileHandler;
    }
}
