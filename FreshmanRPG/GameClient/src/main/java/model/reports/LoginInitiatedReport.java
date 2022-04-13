package model.reports;

import model.QualifiedObservableReport;

/**
 * This report is sent when the player initiates his login to the system
 *
 * @author Merlin
 *
 */
public final class LoginInitiatedReport implements QualifiedObservableReport
{

	private final String name;
	private final String password;

	/**
	 * @param name
	 *            the player's name
	 * @param password
	 *            the player's password
	 */
	public LoginInitiatedReport(String name, String password)
	{
		this.name = name;
		this.password = password;
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
		LoginInitiatedReport other = (LoginInitiatedReport) obj;
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!name.equals(other.name))
		{
			return false;
		}
		if (password == null)
		{
			if (other.password != null)
			{
				return false;
			}
		}
		else if (!password.equals(other.password))
		{
			return false;
		}
		return true;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @return the name
	 */
	public String getPlayerName()
	{
		return name;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

}
