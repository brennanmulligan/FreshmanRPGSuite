package edu.ship.engr.shipsim.util;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;

/**
 * A drawable class that wraps an animation's functionality,
 * allowing for an Image actor to display an animation without having
 * to create a large amount of
 *
 * @author nhydock
 */
public class AnimationDrawable extends BaseDrawable
{

    Animation<TextureRegion> animation;
    TextureRegion currentFrame;
    float timer;

    /**
     * Creates a new animation drawable
     *
     * @param animation - the animation to wrap
     */
    public AnimationDrawable(Animation<TextureRegion> animation)
    {
        super();
        this.animation = animation;
        timer = 0;
        currentFrame = animation.getKeyFrame(0);
    }

    /**
     * Set's the current position in the animation's timeline
     *
     * @param time - time in seconds
     */
    public void setTime(float time)
    {
        timer = time;

        if (animation.getAnimationDuration() < timer)
        {
            timer = 0;
        }

        currentFrame = animation.getKeyFrame(timer);
    }

    /**
     * Advances an animation's current position in time
     *
     * @param delta - time in seconds since last update
     */
    public void update(float delta)
    {
        timer += delta;
        currentFrame = animation.getKeyFrame(timer);
    }

    /**
     * Draws the current frame of animation to the batch
     */
    @Override
    public void draw(Batch batch, float x, float y, float width, float height)
    {
        batch.draw(currentFrame, x, y, width, height);
    }

    /**
     * The minimum width of the animation drawable is determined by the region width
     * of the current frame of animation.
     *
     * @return region width of the current frame of animation
     */
    @Override
    public float getMinWidth()
    {
        return currentFrame.getRegionWidth();
    }

    /**
     * The minimum height of the animation drawable is determined by the region width
     * of the current frame of animation.
     *
     * @return region height of the current frame of animation
     */
    @Override
    public float getMinHeight()
    {
        return currentFrame.getRegionHeight();
    }
}
