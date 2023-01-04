package edu.ship.engr.shipsim.model;

/**
 * Generic ReportObserver class that registers an observer
 */
public class MockReportObserver implements ReportObserver
{

    private Report report;

    /**
     * @param reportType the type of report type this observer would like to receive reports of
     */
    public MockReportObserver(Class<? extends Report> reportType)
    {
        ReportObserverConnector.getSingleton().registerObserver(this, reportType);
    }

    /**
     * Set the report when received
     */
    @Override
    public void receiveReport(Report report)
    {
        this.report = report;
    }

    /**
     * @return the report that was received
     */
    public Report getReport()
    {
        return this.report;
    }

    /**
     * @return if this observer has received a report yet
     */
    public boolean didReceiveReport()
    {
        return this.report != null;
    }

}