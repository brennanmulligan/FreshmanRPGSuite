package communication.messages;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author Merlin
 *
 */
public class MapFileMessage implements Message, Serializable
{

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mapFileName == null) ? 0 : mapFileName.hashCode());
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
		MapFileMessage other = (MapFileMessage) obj;
		if (mapFileName == null)
		{
			if (other.mapFileName != null)
			{
				return false;
			}
		}
		else if (!mapFileName.equals(other.mapFileName))
		{
			return false;
		}
		return true;
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String mapFileName;

	/**
	 * @param fileTitle the name of the file we want to send
	 * @throws IOException if the file doesn't exist or is poorly formatted
	 */
	public MapFileMessage(String fileTitle) throws IOException
	{
		this.mapFileName = fileTitle;
	}

	/**
	 * @return a string describing this message
	 */
	public String toString()
	{
		return this.mapFileName;
	}

	/**
	 * @return the name of the map file we're referencing
	 */
	public String getMapFileName()
	{
		return this.mapFileName;
	}

}
