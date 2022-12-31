package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.util.AnimationDrawable;
import edu.ship.engr.shipsim.util.SpriteSheet;
import org.jetbrains.annotations.NotNull;

/**
 * Class to represent each individual piece of clothing or sprite attributed to the character
 */
public class Vanity extends Image implements Comparable<Object>
{
    protected ObjectMap<Direction, AnimationDrawable> animation;
    protected AnimationDrawable currentAnimation;
    protected Vector2 dest;
    protected Direction facing;

    protected VanityType type;
    public float moveDuration = 0.3f;

    /**
     * Default constructor for a test Vanity object.
     */
    public Vanity()
    {
        facing = Direction.South;

        animation = new ObjectMap<>();
        for (Direction dir : Direction.values())
        {
            animation.put(dir, null);
        }
        currentAnimation = animation.get(Direction.South);
        setDrawable(currentAnimation);
        type = VanityType.BODY;

        dest = new Vector2();
    }


    /**
     * Vanity constructor that takes in a region and builds an animation.
     *
     * @param region the texture region from the atlas of sprites.
     */
    public Vanity(TextureRegion region, VanityType type)
    {
        this.type = type;
        SpriteSheet sourceImg = new SpriteSheet(region, 4, 4);
        animation = new ObjectMap<>();
        for (Direction dir : Direction.values())
        {
            Array<TextureRegion> row = new Array<>(sourceImg.getRow(dir.ordinal()));
            Animation<TextureRegion> a = new Animation<>(moveDuration / row.size, row);
            a.setPlayMode(Animation.PlayMode.LOOP);
            AnimationDrawable ad = new AnimationDrawable(a);
            animation.put(dir, ad);
        }
        currentAnimation = animation.get(Direction.South);
        setDrawable(currentAnimation);
        setSize(currentAnimation.getMinWidth(), currentAnimation.getMinHeight());
        facing = Direction.South;
        dest = new Vector2();
    }

    /**
     * Updates the current position of the animation based off the current position and the destination
     *
     * @param x new x position
     * @param y new y position
     */
    public void update(float x, float y)
    {
        Vector2 current = new Vector2();
        current.set(dest.x, dest.y);

        clearActions();

        // Interpolation.linear is how to transition between two
        //points and in this case it is a linear transition
        addAction(
                Actions.moveTo(x, y, moveDuration, Interpolation.linear)
        );

        dest.set(x, y);
        // change the facing direction so it shows the proper sprite strip to
        // animate
        if (current.x != dest.x || current.y != dest.y)
        {
            setFacing(Direction.getFacing(current, dest));
        }
    }

    /**
     * Changes the facing direction of the player sprite
     *
     * @param facing the direction the sprite is facing based on the direction enum
     */
    private void setFacing(Direction facing)
    {
        this.facing = facing;
        currentAnimation = animation.get(facing);
        if (currentAnimation != null)
        {
            currentAnimation.setTime(0);
        }
        setDrawable(currentAnimation);
    }

    /**
     * Updates the properties of the sprite dependent on the game cycle rate.
     *
     * @param delta differences in time between update cycles of the application
     */
    @Override
    public void act(float delta)
    {
        super.act(delta);
        // update animation if moving
        if (!doneWalking() && currentAnimation != null)
        {
            currentAnimation.update(delta);
        }
        if (doneWalking() && currentAnimation != null)
        {
            currentAnimation.setTime(0);
        }
    }

    /**
     * @return the current direction the sprite is facing
     */
    public Direction getFacing()
    {
        return facing;
    }

    /**
     * Forcably sets the location on screen of the sprite without animating it
     */
    @Override
    public void setPosition(float x, float y)
    {
        super.setPosition(x, y);
    }

    /**
     * @return true if the player's movement is done
     */
    public boolean doneWalking()
    {
        return getActions().size == 0;
    }

    public float getMoveDuration()
    {
        return moveDuration;
    }

    @Override
    public int compareTo(@NotNull Object vanity)
    {
        if (vanity instanceof Vanity)
        {
            return this.facing.getVanityOrder().indexOf(type) - ((Vanity) vanity).facing.getVanityOrder().indexOf(((Vanity) vanity).type);
        }
        else if (vanity instanceof NamePlate)
        {
            return this.facing.getVanityOrder().indexOf(type) - ((NamePlate) vanity).facing.getVanityOrder().indexOf(((NamePlate) vanity).type);
        }
        return 0;
    }
}
