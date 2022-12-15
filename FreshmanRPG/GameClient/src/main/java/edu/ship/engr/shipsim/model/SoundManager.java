package edu.ship.engr.shipsim.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Timer;
import edu.ship.engr.shipsim.datasource.ContentLoader;

/**
 * Singleton sound manager to manage all sounds in game.
 */
public class SoundManager
{
    // Add looping sounds such as walking as global variables so they can be accessed later.
//    static Sound walking = Gdx.audio.newSound(new FileHandle(new File("../GameClient/assets/steps.mp3")));
    static Sound walking = Gdx.audio.newSound(new FileHandle(ContentLoader.getAssetFile("steps.mp3")));
    static Sound mapSound = null;

    /**
     * Add a sound to be played.
     *
     * @param s      Sound to be played.
     * @param length int duration of sound.
     */
    public static void addSound(Sound s, int length)
    {
        s.play(); // Play sound
        Timer.schedule(new Timer.Task()
        {
            @Override
            public void run()
            {
                s.dispose();
            }
        }, length); // Dispose of sound after length seconds
    }

    /**
     * Start playing the walking sound.
     */
    public static void startWalkingSound()
    {
        walking.loop();
    }

    /**
     * Stop playing the walking sound.
     */
    public static void stopWalkingSound()
    {
        walking.stop();
    }

    public static void setMapSound(Sound s)
    {
        mapSound = s;
    }

    public static void startMapSound()
    {
        mapSound.loop();
    }

    /**
     * Stop all looping sounds.
     * Called upon teleport, exit, etc.
     */
    public static void stopLoopingSounds()
    {
        walking.stop();
        if (mapSound != null)
        {
            mapSound.stop();
        }
    }


}