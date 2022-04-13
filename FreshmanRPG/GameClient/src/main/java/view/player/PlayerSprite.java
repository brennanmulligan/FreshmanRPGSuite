package view.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ObjectMap;

import util.AnimationDrawable;
import util.SpriteSheet;

/**
 * The renderable instance of a player on a map
 */
public class PlayerSprite extends Image implements Comparable<PlayerSprite>
{
	/**
	 * Global constant defining the speed in seconds at which a 
	 *  sprite will move across the screen and animate.
	 */
	public float moveDuration = 0.3f;

	protected ObjectMap<Direction, AnimationDrawable> animation;
	protected AnimationDrawable currentAnimation;

	// destination location of the movement
	protected Vector2 dest;

	protected Direction facing;

	/**
	 * Creates an instance where the sprite uses this texture region
	 *
	 * @param region
	 */
	protected PlayerSprite(TextureRegion region)
	{
		SpriteSheet sourceImg = new SpriteSheet(region, 4, 4);
		animation = new ObjectMap<>();
		for (Direction dir : Direction.values())
		{
			TextureRegion[] row = sourceImg.getRow(dir.ordinal());
			Animation a = new Animation(moveDuration / row.length, row);
			a.setPlayMode(Animation.PlayMode.LOOP);
			AnimationDrawable ad = new AnimationDrawable(a);
			animation.put(dir, ad);
		}
		currentAnimation = animation.get(Direction.South);
		setDrawable(currentAnimation);
		setSize(currentAnimation.getMinWidth(), currentAnimation.getMinHeight());
		dest = new Vector2();
		facing = Direction.South;
	}

	/**
	 * Constructs a headless player sprite
	 */
	protected PlayerSprite()
	{
		dest = new Vector2();
		facing = Direction.South;

		animation = new ObjectMap<>();
		for (Direction dir : Direction.values())
		{
			animation.put(dir, null);
		}
		currentAnimation = animation.get(Direction.South);
		setDrawable(currentAnimation);
	}

	/**
	 * Forcably sets the location on screen of the sprite without animating it
	 *
	 */
	@Override
	public void setPosition(float x, float y)
	{
		super.setPosition(x, y);
	}

	/**
	 * Sets the location on screen that the sprite is to move to with animation
	 *
	 * @param x
	 *            horizontal screen location of the sprite
	 * @param y
	 *            vertical screen location
	 */
	public void move(final float x, final float y)
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
	 * @param facing
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
	 * @param delta
	 *            differences in time between update cycles of the application
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
	}

	/**
	 * @return the current direction the sprite is facing
	 */
	public Direction getFacing()
	{
		return facing;
	}

	/**
	 *
	 * @return true if the player's movement is done
	 */
	public boolean doneWalking()
	{
		return getActions().size == 0;
	}

	/**
	 * Compares and sorts player sprites by their Y position
	 */
	@Override
	public int compareTo(PlayerSprite p)
	{
		if (this.dest.epsilonEquals(p.dest, .05f))
		{
			return 0;
		}
		else if (this.dest.y < p.dest.y)
		{
			return 1;
		}
		else
		{
			return -1;
		}
	}

	/**
	 * get the length of time one movement takes
	 * @return move duration 
	 */
	public float getMoveDuration()
	{
		return moveDuration;
	}

	/**
	 * set the length of time one movement takes
	 * @param newDuration the duration to be set to
	 */
	public void setMoveDuration(float newDuration)
	{
		this.moveDuration = newDuration;
	}
}
