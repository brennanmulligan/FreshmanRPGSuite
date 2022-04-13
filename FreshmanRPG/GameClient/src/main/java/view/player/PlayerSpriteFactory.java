package view.player;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * A Factory for generating renderable instances of players on a map
 */
public class PlayerSpriteFactory
{

	private TextureAtlas atlas;

	/**
	 * Creates a new PlayerSpriteFactory that generates renderable players using
	 * a specified texture atlas
	 *
	 * @param atlas
	 *            the file of the texture dictionary of appearances
	 */
	public PlayerSpriteFactory(FileHandle atlas)
	{
		this.atlas = new TextureAtlas(atlas);
	}

	/**
	 * Generates a player sprite with a PlayerSprite representation
	 *
	 * @param type
	 *            The sprite type identifying the look of the player
	 * @return new player sprite instance
	 */
	public PlayerSprite create(PlayerType type)
	{
		TextureRegion region = this.atlas.findRegion(type.regionName);

		PlayerSprite player = new PlayerSprite(region);
		return player;
	}
}
