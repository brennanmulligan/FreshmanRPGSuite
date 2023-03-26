package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.ship.engr.shipsim.datatypes.VanityType;

public class PlayerBikeFactory
{
    private TextureAtlas atlas;

    //Constructor
    public PlayerBikeFactory(FileHandle atlas)
    {
        this.atlas = new TextureAtlas(atlas);
    }

    /**
     * Creates a vanity object from a texture region and vanity type. If the uniqueName does not exist,
     * then it uses the default
     *
     * @param bikeUniqueName name of the vanity object to create
     * @return the vanity object
     */
    public Vanity create(String bikeUniqueName)
    {
        TextureRegion region = this.atlas.findRegion("bike_none");
        if (bikeUniqueName != null)
        {
            region = this.atlas.findRegion(bikeUniqueName);
        }

        Vanity bike = new Vanity(region, VanityType.BIKE);
        return bike;
    }
}
