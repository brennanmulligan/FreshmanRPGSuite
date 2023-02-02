package edu.ship.engr.shipsim.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import edu.ship.engr.shipsim.datasource.ContentLoader;

/**
 * @author Merlin
 */
public class CommandNewMap extends Command
{

    private final String fileContents;
    private final String fileTitle;

    /**
     * @param title        the title of the tmx file containing the new map
     * @param fileContents The contents that should be written to the map file
     */
    public CommandNewMap(String title, String fileContents)
    {
        this.fileContents = fileContents;
        this.fileTitle = title;
    }

    /**
     * @see Command#execute()
     */
    @Override
    void execute()
    {
        if (fileTitle.contains("Ducktopia"))
        {
//			SoundManager.setMapSound(Gdx.audio.newSound(new FileHandle(new File("../GameClient/assets/quack.mp3"))));
            SoundManager.setMapSound(Gdx.audio.newSound(new FileHandle(ContentLoader.getAssetFile("quack.mp3"))));
            SoundManager.startMapSound();
        }

        MapManager.getSingleton().changeToNewFile(fileTitle, fileContents);
        ClientPlayerManager.getSingleton().removeOtherPlayers();
    }

    /**
     * @return the file title of the new map file
     */
    public String getFileTitle()
    {
        return fileTitle;
    }
}
