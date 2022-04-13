package datatypes;

/**
 * @author Emily Maust
 * @author Matthew Croft
 *
 */
public enum Major
{

	/**
	 *
	 */
	SOFTWARE_ENGINEERING("SWE"),

	/**
	 *
	 */
	COMPUTER_SCIENCE("Computer Science"),

	/**
	 *
	 */
	COMPUTER_ENGINEERING("Comp Eng"),

	/**
	 *
	 */
	ELECTRICAL_ENGINEERING("EE"),
	/**
	 *
	 */
	CS_AND_E_GENERAL("CS&E Gen"),
	/**
	 *
	 */
	OTHER("Other");

	private String majorName;

	Major(String majorName)
	{
		this.majorName = majorName;
	}

	/**
	 * @return a unique ID for this Major
	 */
	public int getID()
	{
		return ordinal();
	}

	/**
	 * @param id
	 *            the MajorID we are interested in
	 * @return the Major with the given ID
	 */
	public static Major getMajorForID(int id)
	{
		return Major.values()[id];
	}

	/**
	 * @return the user friendly name of this major
	 */
	public String getMajorName()
	{
		return majorName;
	}

}
