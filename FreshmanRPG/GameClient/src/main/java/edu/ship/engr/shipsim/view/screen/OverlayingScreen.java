package edu.ship.engr.shipsim.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * A general framework for screens that are displayed on top of the tiled map
 *
 * @author Merlin
 */
public abstract class OverlayingScreen extends Group
{
    protected Table container;
    boolean isResponsive = false;

    /**
     * @return the width of the overlaying screen
     */
    public abstract float getMyWidth();


    /**
     * @return the length of the overlaying screen
     */
    public abstract float getMyHeight();

    /**
     *
     */
    public OverlayingScreen()
    {
        setSize(getMyWidth(), getMyHeight());
        setPosition(getXPosition(), getYPosition());
        // Make the container
        container = new Table();
        container.setFillParent(true);
        container.left().top();

        addActor(container);
        setVisible(false);
    }

    /**
     * By default, overlaying screens are centered in the x dimension
     *
     * @return the x position this should be displayed at
     */
    public float getXPosition()
    {
        return (Gdx.graphics.getWidth() - getMyWidth()) / 2;
    }

    /**
     * By default, overlaying screens are displayed 10% down in the y direction
     *
     * @return the x position this should be displayed at
     */
    public float getYPosition()
    {
        return (Gdx.graphics.getHeight() - getMyHeight()) / 1.1f;
    }

    /**
     * Handles making the overlay screen toggling is visibility.
     */
    public abstract void toggleVisibility();

    /**
     * @return true if this overlaying screen should re-position/size itself with the screen.
     */
    public boolean getIsResponsive()
    {
        return isResponsive;
    }

    /**
     * Set whether this overlaying screen should re-position/size itself with the screen.
     *
     * @param isResponsive true to re-position/size with screen
     */
    public void setResponsive(boolean isResponsive)
    {
        this.isResponsive = isResponsive;
    }
}
