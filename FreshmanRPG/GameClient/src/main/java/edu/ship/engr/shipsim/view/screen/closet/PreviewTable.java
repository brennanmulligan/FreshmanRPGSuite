package edu.ship.engr.shipsim.view.screen.closet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.view.player.*;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.List;

/**
 * ClosetUI right-side table which holds the preview of the player's appearance.
 *
 * @author Stefan Milanovic & Nick Starkey
 */
public class PreviewTable extends Table
{
    private final Table vanityTable;
    private final float vanityWidth = 150f;
    private final float vanityHeight = 200f;

    /**
     * Creates a preview table
     */
    public PreviewTable()
    {
        vanityTable = new Table();

        Skin skin = SkinPicker.getSkinPicker().getDefaultSkin();

        Label previewLabel = new Label("Preview", skin);
        previewLabel.setAlignment(Align.center);
        previewLabel.setFontScale(1.5F);

        row();
        add(previewLabel).padTop(20f).expandX().fillX();
        row();

        add(vanityTable).size(vanityWidth, vanityHeight).expand().fill();
    }

    /**
     * Draws a list of vanities in the preview table.
     *
     * @param vanityDTOs the DTOs of the vanities we wish to draw
     */
    public void drawVanities(List<VanityDTO> vanityDTOs)
    {
        vanityTable.clearChildren();

        PlayerBodyFactory bodyFactory = new PlayerBodyFactory(Gdx.files.internal("ui-data/body.atlas"));
        PlayerHairFactory hairFactory = new PlayerHairFactory(Gdx.files.internal("ui-data/hair.atlas"));
        PlayerHatFactory hatFactory = new PlayerHatFactory(Gdx.files.internal("ui-data/hats.atlas"));
        PlayerShirtFactory shirtFactory = new PlayerShirtFactory(Gdx.files.internal("ui-data/shirts.atlas"));
        PlayerPantsFactory pantsFactory = new PlayerPantsFactory(Gdx.files.internal("ui-data/pants.atlas"));
        PlayerShoesFactory shoesFactory = new PlayerShoesFactory(Gdx.files.internal("ui-data/shoes.atlas"));
        PlayerEyesFactory eyesFactory = new PlayerEyesFactory(Gdx.files.internal("ui-data/eyes.atlas"));
        PlayerBikeFactory bikeFactory = new PlayerBikeFactory(Gdx.files.internal("ui-data/bikes.atlas"));
        PlayerSpriteFactory playerFactory = new PlayerSpriteFactory(hatFactory, hairFactory, bodyFactory,
                shirtFactory, pantsFactory, shoesFactory, eyesFactory, bikeFactory);

        PlayerSprite playerPreview = playerFactory.create(vanityDTOs);
        List<VanityType> vanityOrder = playerPreview.getFacing().getVanityOrder();

        for (VanityType vanityType : vanityOrder)
        {
            if (vanityType == VanityType.NAMEPLATE)
            {
                continue;
            }
            Vanity previewVanity = playerPreview.getVanity(vanityType);
            previewVanity.setSize(vanityWidth, vanityHeight);
            previewVanity.setAlign(Align.center);
            vanityTable.addActor(previewVanity);
        }

    }
}
