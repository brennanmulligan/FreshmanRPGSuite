package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.ship.engr.shipsim.datatypes.VanityType;

public class PlayerEyesFactory
{

    private TextureAtlas atlas;

    public PlayerEyesFactory(FileHandle atlas)
    {
        this.atlas = new TextureAtlas(atlas);
    }

    /**
     * Creates a vanity object from a texture region and vanity type. If the uniqueName does not exist,
     * then it uses the default
     *
     * @param eyeUniqueName name of the vanity object to create
     * @return the vanity object
     */
    public Vanity create(String eyeUniqueName)
    {
        TextureRegion region = this.atlas.findRegion("eyes_black");
        if (eyeUniqueName != null)
        {
            region = this.atlas.findRegion(eyeUniqueName);
        }

        Vanity eyes = new Vanity(region, VanityType.EYES);
        return eyes;
    }
}
