package edu.ship.engr.shipsim.view.screen;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

/**
 * @author tr3897
 */
public abstract class OverlayingScreenTable extends ScrollPane
{
    private float containerPadding = 10f;
    protected Table container;

    /**
     * @param scrollable Whether or not the the overlaying screen table is scrollable
     */
    public OverlayingScreenTable(boolean scrollable)
    {
        super(null, SkinPicker.getSkinPicker().getCrewSkin());
        setFadeScrollBars(false);
        setScrollingDisabled(!scrollable, !scrollable);
        buildContainer();
        setWidget(container);
    }

    private void buildContainer()
    {
        container = new Table();
        container.left().top();
        container.pad(containerPadding);
    }

    /**
     * @return the padding for the container
     */
    public float getPadding()
    {
        return containerPadding;
    }

    /**
     * Set the padding for the container
     */
    public void setPadding(float padding)
    {
        containerPadding = padding;
        container.pad(padding);
    }
}
