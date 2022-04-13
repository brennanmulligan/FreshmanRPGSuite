package model;

import java.util.ArrayList;

/**
 * Just for testing how behaviors work
 */
public class NPCMockBehavior extends NPCBehavior
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int count = 0;

	/**
	 * @return the number of times we have done our timed event
	 */
	public int getCount()
	{
		return count;
	}

	/**
	 * @param playerID this player's unique ID
	 *
	 */
	public NPCMockBehavior(int playerID)
	{
		super(playerID);
		pollingInterval = 50;
	}

	/**
	 * @see model.NPCBehavior#doTimedEvent()
	 */
	protected void doTimedEvent()
	{
		this.count++;
	}

	/**
	 * @see model.NPCBehavior#getReportTypes()
	 */
	@Override
	protected ArrayList<Class<? extends QualifiedObservableReport>> getReportTypes()
	{
		return null;
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport arg)
	{
	}

}
