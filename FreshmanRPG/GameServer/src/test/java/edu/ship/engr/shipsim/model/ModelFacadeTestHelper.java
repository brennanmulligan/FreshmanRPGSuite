package edu.ship.engr.shipsim.model;


import edu.ship.engr.shipsim.datasource.LoggerManager;

public class ModelFacadeTestHelper
{
    public static void waitForFacadeToProcess() throws ModelFacadeException
    {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[0];

        waitForFacadeToProcess(10, stackTraceElement.getClassName() + "#" + stackTraceElement.getMethodName());
    }

    public static void waitForFacadeToProcess(int hundredMSecs, String testName)
            throws ModelFacadeException
    {
        int count = 0;
        while (count < hundredMSecs && ModelFacade.getSingleton().hasCommandsPending())
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                throw new ModelFacadeException(e.getMessage());
            }
            count++;
        }

        if (ModelFacade.getSingleton().hasCommandsPending())
        {
            LoggerManager.getSingleton().getLogger().warning("ModelFacade queue wasn't empty after: " + testName);

            throw new ModelFacadeException("ModelFacade queue wasn't empty after: " + testName);
        }
    }
}