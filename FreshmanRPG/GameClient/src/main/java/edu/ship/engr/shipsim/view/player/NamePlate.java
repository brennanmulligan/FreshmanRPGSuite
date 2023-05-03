package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import edu.ship.engr.shipsim.datatypes.VanityType;


/***
 * Hold the information for printing the name and crew above the head of a player.
 *
 * @author Chase Banyai, Adam Weissinger
 */
public class NamePlate extends Label
{
    protected Vector2 dest;
    protected Direction facing;

    protected VanityType type;
    public float moveDuration = 0.3f;
    private float offsetX, offSetY = 42;

    /**
     * Creates a NamePlate
     *
     * @param playerName The name of the player
     * @param crew The crew of the player
     * @param skin the Skin to use for the nameplate
     */
    public NamePlate(String playerName, String crew, Skin skin)
    {
        super(playerName + "\n" + crew, skin);
        dest = new Vector2();
        facing = Direction.South;
        type = VanityType.NAMEPLATE;
    }

    /**
     * When moving calls act
     *
     * @param delta the amount of time to act for.
     */
    @Override
    public void act(float delta)
    {
        super.act(delta);
    }

    /**
     * Sets the offset of X
     *
     * @param offsetX The new Offset
     */
    public void setOffsetX(float offsetX)
    {
        this.offsetX = offsetX;
    }

    /**
     * The offset of the x position
     *
     * @return the x offset
     */
    public float getOffsetX()
    {
        return offsetX;
    }

    /**
     * the offest of the y position
     *
     * @return the y offset
     */
    public float getOffSetY()
    {
        return offSetY;
    }

    /**
     * Updates the position of the nameplate
     *
     * @param x the new x
     * @param y the new y
     */
    public void update(float x, float y)
    {
        Vector2 current = new Vector2();
        current.set(dest.x, dest.y);

        clearActions();

        // Interpolation.linear is how to transition between two
        //points and in this case it is a linear transition
        addAction(
                Actions.moveTo(x + offsetX, y + offSetY, moveDuration, Interpolation.linear)
        );

        dest.set(x + offsetX, y + offSetY);
    }
}
