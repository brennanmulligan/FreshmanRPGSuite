package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.ship.engr.shipsim.datatypes.VanityType;

public class PlayerHairFactory
{
    private TextureAtlas atlas;

    //Constructor
    public PlayerHairFactory(FileHandle atlas)
    {
        this.atlas = new TextureAtlas(atlas);
    }

    /**
     * Creates a vanity object from a texture region and vanity type. If the uniqueName does not exist,
     * then it uses the default
     *
     * @param hairUniqueName name of the vanity object to create
     * @return the vanity object
     */
    public Vanity create(String hairUniqueName)
    {
        TextureRegion region = this.atlas.findRegion("hair_black");
        if (hairUniqueName != null)
        {
            region = this.atlas.findRegion(hairUniqueName);
        }

        Vanity hair = new Vanity(region, VanityType.HAIR);
        return hair;
    }
}
