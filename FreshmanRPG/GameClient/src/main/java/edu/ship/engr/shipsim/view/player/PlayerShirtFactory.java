package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.ship.engr.shipsim.datatypes.VanityType;

public class PlayerShirtFactory
{
    private TextureAtlas atlas;

    //Constructor
    public PlayerShirtFactory(FileHandle atlas)
    {
        this.atlas = new TextureAtlas(atlas);
    }

    /**
     * Creates a vanity object from a texture region and vanity type. If the uniqueName does not exist,
     * then it uses the default
     *
     * @param shirtUniqueName name of the vanity object to create
     * @return the vanity object
     */
    public Vanity create(String shirtUniqueName)
    {
        TextureRegion region = this.atlas.findRegion("base_shirt");
        if (shirtUniqueName != null)
        {
            region = this.atlas.findRegion(shirtUniqueName);
        }

        Vanity shirt = new Vanity(region, VanityType.SHIRT);
        return shirt;
    }
}
