package model;

/**
 * Generic QualifiedObserver class that registers an observer
 */
public class MockQualifiedObserver implements QualifiedObserver
{

	private QualifiedObservableReport report;

	/**
	 * @param reportType the type of report type this observer would like to receive reports of
	 */
	public MockQualifiedObserver(Class<? extends QualifiedObservableReport> reportType)
	{
		QualifiedObservableConnector.getSingleton().registerObserver(this, reportType);
	}

	/**
	 * Set the report when received
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		this.report = report;
	}

	/**
	 * @return the report that was received
	 */
	public QualifiedObservableReport getReport()
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