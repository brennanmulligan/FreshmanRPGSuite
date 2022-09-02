package edu.ship.engr.shipsim.view.screen.clothingShop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.view.player.*;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

public class ShopPreviewTable extends Table
{
    private Table vanityTable;
    private final float vanityWidth = 150f;
    private final float vanityHeight = 200f;


    public ShopPreviewTable()
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

    public void drawVanities(VanityDTO vanity)
    {
        vanityTable.clearChildren();

        Vanity vanityPreview = null;

        switch (vanity.getVanityType())
        {
            case HAT:
                PlayerHatFactory hatFactory = new PlayerHatFactory(Gdx.files.internal("ui-data/hats.atlas"));
                vanityPreview = hatFactory.create(vanity.getTextureName());
                break;
            case HAIR:
                PlayerHairFactory hairFactory = new PlayerHairFactory(Gdx.files.internal("ui-data/hair.atlas"));
                vanityPreview = hairFactory.create(vanity.getTextureName());
                break;
            case SHIRT:
                PlayerShirtFactory shirtFactory = new PlayerShirtFactory(Gdx.files.internal("ui-data/shirts.atlas"));
                vanityPreview = shirtFactory.create(vanity.getTextureName());
                break;
            case BODY:
                PlayerBodyFactory bodyFactory = new PlayerBodyFactory(Gdx.files.internal("ui-data/body.atlas"));
                vanityPreview = bodyFactory.create(vanity.getTextureName());
                break;
            case PANTS:
                PlayerPantsFactory pantsFactory = new PlayerPantsFactory(Gdx.files.internal("ui-data/pants.atlas"));
                vanityPreview = pantsFactory.create(vanity.getTextureName());
                break;
            case SHOES:
                PlayerShoesFactory shoesFactory = new PlayerShoesFactory(Gdx.files.internal("ui-data/shoes.atlas"));
                vanityPreview = shoesFactory.create(vanity.getTextureName());
                break;
            default:
                System.out.println("Unknown vanity type!");
                break;
        }

        vanityPreview.setSize(vanityWidth, vanityHeight);
        vanityPreview.setAlign(Align.center);
        vanityTable.addActor(vanityPreview);
    }
}
