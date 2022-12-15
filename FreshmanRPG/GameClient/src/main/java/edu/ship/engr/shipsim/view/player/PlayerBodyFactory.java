package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.ship.engr.shipsim.datatypes.VanityType;

public class PlayerBodyFactory
{
    private TextureAtlas atlas;

    //Constructor
    public PlayerBodyFactory(FileHandle atlas)
    {
        this.atlas = new TextureAtlas(atlas);
    }

    /**
     * Creates a vanity object from a texture region and vanity type. If the uniqueName does not exist,
     * then it uses the default
     *
     * @param bodyUniqueName name of the vanity object to create
     * @return the vanity object
     */
    public Vanity create(String bodyUniqueName)
    {
        TextureRegion region = this.atlas.findRegion("skin_default");
        if (bodyUniqueName != null)
        {
            region = this.atlas.findRegion(bodyUniqueName);
        }

        Vanity body = new Vanity(region, VanityType.BODY);
        return body;
    }
}
