package model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Timer;

import java.io.File;

/**
 * @author Merlin
 *
 */
public class CommandNewMap extends Command
{

	private String fileTitle;

	/**
	 * @param title
	 *            the title of the tmx file containing the new map
	 */
	public CommandNewMap(String title)
	{
		this.fileTitle = title;
	}

	/**
	 * @see Command#execute()
	 */
	@Override
	boolean execute()
	{
		if(fileTitle.contains("Ducktopia"))
		{
			SoundManager.setMapSound(Gdx.audio.newSound(new FileHandle(new File("../GameClient/assets/quack.mp3"))));
			SoundManager.startMapSound();
		}

		MapManager.getSingleton().changeToNewFile(fileTitle);
		ClientPlayerManager.getSingleton().removeOtherPlayers();
		return true;
	}

	/**
	 *
	 * @return the file title of the new map file
	 */
	public String getFileTitle()
	{
		return fileTitle;
	}
}
