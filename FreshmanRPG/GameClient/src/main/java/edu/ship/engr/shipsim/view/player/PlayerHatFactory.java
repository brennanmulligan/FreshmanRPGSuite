package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.ship.engr.shipsim.datatypes.VanityType;

/**
 * Creates PlayerHat objects which will be used to give the player a hat
 */
public class PlayerHatFactory
{
    private TextureAtlas atlas;

    //Constructor
    public PlayerHatFactory(FileHandle atlas)
    {
        this.atlas = new TextureAtlas(atlas);
    }

    /**
     * Creates a vanity object from a texture region and vanity type. If the uniqueName does not exist,
     * then it uses the default
     *
     * @param hatUniqueName name of the vanity object to create
     * @return the vanity object
     */
    public Vanity create(String hatUniqueName)
    {
        TextureRegion region = this.atlas.findRegion("hat_none");
        if (hatUniqueName != null)
        {
            region = this.atlas.findRegion(hatUniqueName);
        }

        Vanity hat = new Vanity(region, VanityType.HAT);
        return hat;
    }
}
