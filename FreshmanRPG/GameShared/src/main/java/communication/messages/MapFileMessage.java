package communication.messages;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Merlin
 */
public class MapFileMessage implements Message, Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String mapFileName;
    private String fileContents;

    /**
     * @param fileTitle the name of the file we want to send
     * @throws IOException if the file doesn't exist or is poorly formatted
     */
    public MapFileMessage(String fileTitle) throws IOException
    {
        this.mapFileName = fileTitle;
        StringBuffer x = new StringBuffer();
        Scanner file = new Scanner(new File("../GameServer/maps/" + fileTitle));
        while (file.hasNext())
        {
            x.append(file.nextLine());
        }
        fileContents = x.toString();
    }

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		MapFileMessage that = (MapFileMessage) o;

		if (!Objects.equals(mapFileName, that.mapFileName))
		{
			return false;
		}
		return Objects.equals(fileContents, that.fileContents);
	}

	@Override
	public int hashCode()
	{
		int result = mapFileName != null ? mapFileName.hashCode() : 0;
		result = 31 * result + (fileContents != null ? fileContents.hashCode() : 0);
		return result;
	}

	public String getFileContents()
    {
        return fileContents;
    }

    /**
     * @return the name of the map file we're referencing
     */
    public String getMapFileName()
    {
        return this.mapFileName;
    }



    /**
     * @return a string describing this message
     */
    public String toString()
    {
        return this.mapFileName;
    }

}
