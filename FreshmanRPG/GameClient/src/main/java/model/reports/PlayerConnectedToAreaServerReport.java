package model.reports;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import model.QualifiedObservableReport;

/**
 * Is sent when the player on this client has completed the connection to an
 * area server (must be sent AFTER the map report has been sent)
 *
 * @author Merlin
 *
 */
public class PlayerConnectedToAreaServerReport implements QualifiedObservableReport
{
	private int playerID;
	private String playerName;
	private String appearanceType;
	private Position position;
	private boolean isThisClientsPlayer;
	private Crew crew;
	private Major major;

	/**
	 *
	 * @param playerID
	 *            the id of the player
	 * @param playerName
	 *            the name of the player
	 * @param appearanceType
	 *            the type of the player
	 * @param position
	 *            the position of the player
	 * @param crew
	 *            the crew to which this player belongs
	 * @param major
	 * 			  the major to which this player belongs
	 * @param isThisClientsPlayer
	 *            statement saying if the player connected was the one
	 *            controlled by the client
	 * @see view.player.PlayerType
	 */
	public PlayerConnectedToAreaServerReport(int playerID, String playerName,
											 String appearanceType, Position position, Crew crew,
											 Major major, boolean isThisClientsPlayer)
	{
		this.playerID = playerID;
		this.playerName = playerName;
		this.appearanceType = appearanceType;
		this.position = position;
		this.isThisClientsPlayer = isThisClientsPlayer;
		this.crew = crew;
		this.major = major;
	}

	/**
	 *
	 * @return the crew to which this player belongs
	 */
	public Crew getCrew()
	{
		return crew;
	}


	/**
	 * Report the player type which reflects how this player should be
	 * displayed. This must be a string that matches the name of one of the
	 * members of the PlayerType enum.
	 *
	 * @return the player appearance type
	 */
	public String getPlayerAppearanceType()
	{
		return appearanceType;
	}

	/**
	 * @return the player id
	 */
	public int getPlayerID()
	{
		return playerID;
	}

	/**
	 * Report the name this player used to login to the system
	 *
	 * @return the name
	 */
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 * @return the player's position
	 */
	public Position getPlayerPosition()
	{
		return position;
	}

	/**
	 *
	 * @return the major of the player
	 */
	public Major getMajor()
	{
		return major;
	}


	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appearanceType == null) ? 0 : appearanceType.hashCode());
		result = prime * result + ((crew == null) ? 0 : crew.hashCode());
		result = prime * result + (isThisClientsPlayer ? 1231 : 1237);
		result = prime * result + ((major == null) ? 0 : major.hashCode());
		result = prime * result + playerID;
		result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
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
		PlayerConnectedToAreaServerReport other = (PlayerConnectedToAreaServerReport) obj;
		if (appearanceType == null)
		{
			if (other.appearanceType != null)
			{
				return false;
			}
		}
		else if (!appearanceType.equals(other.appearanceType))
		{
			return false;
		}
		if (crew != other.crew)
		{
			return false;
		}
		if (isThisClientsPlayer != other.isThisClientsPlayer)
		{
			return false;
		}
		if (major != other.major)
		{
			return false;
		}
		if (playerID != other.playerID)
		{
			return false;
		}
		if (playerName == null)
		{
			if (other.playerName != null)
			{
				return false;
			}
		}
		else if (!playerName.equals(other.playerName))
		{
			return false;
		}
		if (position == null)
		{
			if (other.position != null)
			{
				return false;
			}
		}
		else if (!position.equals(other.position))
		{
			return false;
		}
		return true;
	}


	/**
	 * @return if the player connected was the client's player
	 */
	public boolean isThisClientsPlayer()
	{
		return isThisClientsPlayer;
	}
}
