package datasource;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Level object class that contains a description and levelUpPoints
 *
 * @author Merlin
 *
 */
public class LevelRecord implements Comparable<LevelRecord>, Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String description;
	private int levelUpPoints;
	private int levelUpMonth;
	private int levelUpDayOfMonth;
	private Date deadlineDate;

	/**
	 * Constructor for level object
	 *
	 * @param description the level's description
	 * @param levelUpPoints the levelUpPoints for the level
	 * @param levelUpMonth TODO
	 * @param levelUpDayOfMonth TODO
	 */
	public LevelRecord(String description, int levelUpPoints, int levelUpMonth, int levelUpDayOfMonth)
	{
		super();
		this.description = description;
		this.levelUpPoints = levelUpPoints;
		this.levelUpMonth = levelUpMonth;
		this.levelUpDayOfMonth = levelUpDayOfMonth;
		deadlineDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), levelUpMonth, levelUpDayOfMonth)
				.getTime();
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(LevelRecord o)
	{
		return levelUpPoints - o.levelUpPoints;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		LevelRecord other = (LevelRecord) obj;
		if (description == null)
		{
			if (other.description != null)
			{
				return false;
			}
		}
		else if (!description.equals(other.description))
		{
			return false;
		}
		if (levelUpPoints != other.levelUpPoints)
		{
			return false;
		}
		return true;
	}

	/**
	 * @return description of the level
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return the day of the month by which they are required to level out of
	 *         this level
	 */
	public int getLevelUpDayOfMonth()
	{
		return levelUpDayOfMonth;
	}

	/**
	 * @return the month by which they are required to level out of this level
	 */
	public int getLevelUpMonth()
	{
		return levelUpMonth;
	}

	/**
	 * @return levelUpPoints of the level
	 */
	public int getLevelUpPoints()
	{
		return levelUpPoints;
	}

	/**
	 * @return the deadline date
	 */
	public Date getDeadlineDate()
	{
		return deadlineDate;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + levelUpPoints;
		return result;
	}

}
