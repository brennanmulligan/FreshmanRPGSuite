package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import edu.ship.engr.shipsim.datatypes.VanityType;

public class PlayerPantsFactory
{
    private TextureAtlas atlas;

    //Constructor
    public PlayerPantsFactory(FileHandle atlas)
    {
        this.atlas = new TextureAtlas(atlas);
    }

    /**
     * Creates a vanity object from a texture region and vanity type. If the uniqueName does not exist,
     * then it uses the default
     *
     * @param pantsUniqueName name of the vanity object to create
     * @return the vanity object
     */
    public Vanity create(String pantsUniqueName)
    {
        TextureRegion region = this.atlas.findRegion("base_pants"); // change to empty hat
        if (pantsUniqueName != null)
        {
            region = this.atlas.findRegion(pantsUniqueName); // change to empty hat
        }

        Vanity pants = new Vanity(region, VanityType.PANTS);
        return pants;
    }
}
