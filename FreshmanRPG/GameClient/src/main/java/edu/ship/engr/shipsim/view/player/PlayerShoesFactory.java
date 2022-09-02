package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.ship.engr.shipsim.datatypes.VanityType;

public class PlayerShoesFactory
{
    private TextureAtlas atlas;

    //Constructor
    public PlayerShoesFactory(FileHandle atlas)
    {
        this.atlas = new TextureAtlas(atlas);
    }

    /**
     * Creates a vanity object from a texture region and vanity type. If the uniqueName does not exist,
     * then it uses the default
     *
     * @param shoesUniqueName name of the vanity object to create
     * @return the vanity object
     */
    public Vanity create(String shoesUniqueName)
    {
        TextureRegion region = this.atlas.findRegion("shoes_none");
        if (shoesUniqueName != null)
        {
            region = this.atlas.findRegion(shoesUniqueName);
        }

        Vanity shoes = new Vanity(region, VanityType.SHOES);
        return shoes;
    }
}
