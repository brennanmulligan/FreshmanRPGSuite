package edu.ship.engr.shipsim.view.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Everything a basic screen needs
 *
 * @author Merlin
 */
public abstract class ScreenBasic implements Screen
{
    protected Stage stage;

    /**
     * Get the stage that this screen displays
     *
     * @return the stage
     */
    public Stage getStage()
    {
        return stage;
    }
}
