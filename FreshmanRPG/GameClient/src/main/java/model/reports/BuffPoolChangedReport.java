package model.reports;

import model.QualifiedObservableReport;

/**
 * Class for when buffPool of player has changed.
 * @author Adam Pine & Emmanuel Douge
 *
 */
public class BuffPoolChangedReport implements QualifiedObservableReport
{
	private int buffPool;

	/**
	 * @param buffPool the remaining buffpool value
	 */
	public BuffPoolChangedReport(int buffPool)
	{
		this.setBuffPool(buffPool);
	}

	/**
	 * @return the remaining buffpool value.
	 */
	public int getBuffPool()
	{
		return buffPool;
	}

	private void setBuffPool(int buffPool)
	{
		this.buffPool = buffPool;
	}


}
