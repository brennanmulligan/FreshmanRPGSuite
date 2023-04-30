package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Portions of the system that are outside of the model need to be notified when
 * the game state changes. In other words, they need to be observers of a
 * variety of places within the Model. However, we don't want things that are
 * outside of the model to have knowledge of where in the model those place are.
 * Therefore, we have created the ReportObserverConnector (which is a
 * singleton) on the edge of the Model. Entities outside of (or inside) the
 * model can use this to become an observer of all of the places within the
 * model that report messages of a given type on state change.
 * <p>
 * Changes it game state are called game state reports and we want to make sure
 * that each observer only gets the reports in which it is interested.
 * Therefore, ReportObserverConnector allows the observer to specify
 * exactly which type of report they want to receive. For example, registering
 * to receive a report that the current player moved is done like this:
 *
 * <p>
 *
 * <pre>
 * ReportObserverConnector.getSingleton().registerObserver(this, ThisPlayerMovedReport.class);
 * </pre>
 *
 * @author Merlin
 */
public class ReportObserverConnector
{

    private static ReportObserverConnector singleton;

    private final HashMap<Class<? extends Report>,
            ArrayList<ReportObserver>> observers;

    private ReportObserverConnector()
    {
        observers = new HashMap<>();
    }

    /**
     * @return the only one of these in the system
     */
    public synchronized static ReportObserverConnector getSingleton()
    {
        if (singleton == null)
        {
            singleton = new ReportObserverConnector();
        }
        return singleton;
    }

    /**
     *
     */
    public static void resetSingleton()
    {
        OptionsManager.getSingleton().assertTestMode();
        singleton = null;
    }

    /**
     * Distributes a given report to everyone who is interested in reports of
     * that type
     *
     * @param report the report
     */
    public void sendReport(Report report)
    {

        ArrayList<ReportObserver> relevantObservers =
                observers.get(report.getClass());
        if (relevantObservers != null)
        {
            // Clone the relevant observer list because there is a chance that
            // someone who gets this report will want to register another observer.
            // That would cause concurrent modification exception
            @SuppressWarnings("unchecked")
            ArrayList<ReportObserver> x =
                    (ArrayList<ReportObserver>) relevantObservers.clone();
            for (ReportObserver a : x)
            {
                a.receiveReport(report);
            }
        }

    }

    /**
     * Used when an observer wants to receive reports of a given type
     *
     * @param observer   the observer who is interested
     * @param reportType the report type the observer wants to receive
     */
    public void registerObserver(ReportObserver observer,
                                 Class<? extends Report> reportType)
    {
        synchronized (this)
        {
            rememberObserver(observer, reportType);
        }
    }

    /**
     * @param observer   the observer we should remember
     * @param reportType the report type this observer is interested in
     */
    private void rememberObserver(ReportObserver observer, Class<? extends Report> reportType)
    {
        ArrayList<ReportObserver> relevantObservers =
                observers.computeIfAbsent(reportType, k -> new ArrayList<>());
        if (!relevantObservers.contains(observer))
        {
            relevantObservers.add(observer);
        }

    }

    /**
     * This is called when an observer no longer wants to receive reports of a
     * given type
     *
     * @param observer   the observer who is no longer interested
     * @param reportType the report types they no longer want to receive
     */
    public void unregisterObserver(ReportObserver observer, Class<? extends Report> reportType)
    {
        synchronized (this)
        {
            ArrayList<ReportObserver> observerList = observers.get(reportType);
            if (observerList != null)
            {
                observerList.remove(observer);
            }
        }
    }

    /**
     * Allows an observer to see if it is currently listening to a given report
     * type
     *
     * @param obs        the observer
     * @param reportType the report type
     * @return true if the observer is hooked up for that report type
     */
    public boolean doIObserve(ReportObserver obs, Class<? extends Report> reportType)
    {
        synchronized (this)
        {
            ArrayList<ReportObserver> relavantObservers = observers.get(reportType);
            if (relavantObservers == null)
            {
                return false;
            }
            return relavantObservers.contains(obs);
        }
    }

    /**
     * A self-contained wait/receive method for QualifiedObservableReports
     *
     * - This method registers a custom observer to receive the requested reports.
     *
     * - Inside an asynchronous task, a provided initiationAction is executed that eventually sends a report.
     *      This task will run for a specified amount of time
     *
     * - If a report has not been received in the specified amount of time, null is returned
     *      Otherwise, the first received report is returned.

     * - Before the end of the method, the custom observer is unregistered.
     *
     * @param initiationAction The initiationAction that contains logic for sending the expected report
     * @param maxWaitTime The amount of time to wait before failing
     * @param reportClasses The report classes that could be expected
     *
     * @return The report if received in time, and null otherwise
     */
    @Nullable
    @SuppressWarnings("unchecked")
    public static <T extends Report> T processAction(Runnable initiationAction, long maxWaitTime, Class<? extends Report>... reportClasses)
    {
        try (AsyncReportObserver observer = new AsyncReportObserver(Thread.currentThread(), reportClasses))
        {
            // Run the initiationAction then wait for a specified amount of time
            initiationAction.run();

            try
            {
                Thread.sleep(maxWaitTime);
            }
            catch (InterruptedException e)
            {
                // The report has been received. Nothing bad actually happened. Stop worrying so much...
            }

            // Return the received report, null otherwise
            return (T) observer.getReport();
        }
    }

    /**
     * Get the count of all current reports
     *
     * @return size
     */
    public int getCount()
    {
        return observers.size();
    }

    /**
     * A custom ReportObserver for use in {@link ReportObserverConnector#processAction}
     *
     * <br></br>
     *
     * The difference between this observer and any other is the method: {@link AsyncReportObserver#getReport()}
     */
    private static class AsyncReportObserver implements ReportObserver, AutoCloseable
    {
        private Report report;
        private final Thread waitingThread;
        private final Class<? extends Report>[] reportClasses;

        @SafeVarargs
        public AsyncReportObserver(Thread waitingThread, Class<? extends Report>... reportClasses)
        {
            this.waitingThread = waitingThread;
            this.reportClasses = reportClasses;

            register();
        }

        @Nullable
        public Report getReport()
        {
            return report;
        }

        @Override
        public void receiveReport(Report report)
        {
            for (Class<? extends Report> reportClass : reportClasses)
            {
                if (report.getClass().equals(reportClass))
                {
                    this.report = report;

                    this.waitingThread.interrupt();

                    return;
                }
            }
        }

        public void register()
        {
            for (Class<? extends Report> reportClass : reportClasses)
            {
                ReportObserverConnector.getSingleton().registerObserver(this, reportClass);
            }
        }

        public void unregister()
        {
            for (Class<? extends Report> reportClass : reportClasses)
            {
                ReportObserverConnector.getSingleton().unregisterObserver(this, reportClass);
            }
        }

        @Override
        public void close()
        {
            unregister();
        }
    }
}
