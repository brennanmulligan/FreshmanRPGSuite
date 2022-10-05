package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.LoggerManager;

public class ClientModelFacadeTestHelper
{
    public static void waitForFacadeToProcess() throws ClientModelFacadeException
    {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[0];

        waitForFacadeToProcess(10, stackTraceElement.getClassName() + "#" + stackTraceElement.getMethodName());
    }

    public static void waitForFacadeToProcess(int hundredMSecs, String testName)
            throws ClientModelFacadeException
    {
        int count = 0;
        while (count < hundredMSecs && ClientModelFacade.getSingleton().hasCommandsPending())
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                throw new ClientModelFacadeException(e.getMessage());
            }
            count++;
        }

        if (ClientModelFacade.getSingleton().hasCommandsPending())
        {
            LoggerManager.getSingleton().getLogger().warning("ClientModelFacade queue wasn't empty after: " + testName);

            throw new ClientModelFacadeException("ClientModelFacade queue wasn't empty after: " + testName);
        }
    }
}
