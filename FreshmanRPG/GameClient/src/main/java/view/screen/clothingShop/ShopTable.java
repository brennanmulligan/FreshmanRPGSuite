package view.screen.clothingShop;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dataDTO.VanityDTO;
import datatypes.VanityType;
import model.*;
import model.reports.ShopInventoryRequestReport;
import view.screen.OverlayingScreenTable;
import view.screen.SkinPicker;
import view.screen.SelectorControl;

import java.util.ArrayList;
import java.util.List;

public class ShopTable extends OverlayingScreenTable
{
    private List<VanityDTO> inventory;
    private VanityDTO selectedVanity;
    private int selectedVanityIndex;
    private int selectedFilterIndex = 0;
    private Table selectorTable;
    private ShopPreviewTable previewTable;
    private Label priceBox;
    private TextButton purchaseButton;
    private SelectorControl<VanityDTO> vanitySelector = null;
    private SelectorControl<String> filterSelector = null;
    ArrayList<String> types = new ArrayList<>();


    /**
     * @param scrollable Whether or not the overlaying screen table is scrollable
     */
    public ShopTable(boolean scrollable)
    {
        super(scrollable);

        Skin skin = SkinPicker.getSkinPicker().getDefaultSkin();

        // Add the vanity selector list pane
        selectorTable = new Table();
        ScrollPane listPane = new ScrollPane(selectorTable, skin);
        listPane.setScrollingDisabled(true, false);
        listPane.setFadeScrollBars(false);
        container.row();
        container.add(listPane).fill().expand();

        // Add the preview table
        previewTable = new ShopPreviewTable();
        container.add(previewTable).width(250f).fill();

        //Add the price text box
        container.row();
        priceBox = new Label("", skin);
        priceBox.setAlignment(Align.center);
        container.add(priceBox).expandX();

        // Add an update button
        purchaseButton = new TextButton("Purchase", skin);
        purchaseButton.setColor(Color.CYAN);
        purchaseButton.addListener(getPurchaseButtonListener());
        container.row();
        container.add(purchaseButton).expandX().fillX().padTop(10).padBottom(10);

        // Ignoring nameplate, skintones, and eyes since the player already has them all
        for(VanityType v : VanityType.values())
        {
            if (v == VanityType.NAMEPLATE || v == VanityType.BODY || v == VanityType.EYES)
            {
                continue;
            }
            types.add(v.name());
        }
    }

    private ClickListener getPurchaseButtonListener()
    {
        return new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                if(!purchaseButton.isDisabled())
                {
                    updatePlayerInventory();
                }
            }
        };
    }

    private void updatePlayerInventory()
    {
        VanityDTO selectedItem = getSelectedVanity();
        int playerID = ClientPlayerManager.getSingleton().getThisClientsPlayer().getID();
        CommandPlayerPurchasedClothing cmd = new CommandPlayerPurchasedClothing(playerID, selectedItem);
        ClientModelFacade.getSingleton().queueCommand(cmd);
        purchaseButton.setDisabled(true);
        purchaseButton.setColor(Color.GRAY);
    }

    private VanityDTO getSelectedVanity()
    {
        return vanitySelector.getSelectedItem();
    }

    protected void updateView()
    {
        selectorTable.clear();

        filterSelector = new SelectorControl<>(selectedFilterIndex, types);
        selectorTable.add(filterSelector).expand().fill();
        selectorTable.row();

        filterSelector.addListener(getFilterSelectorClickListener());

        vanitySelector = new SelectorControl<>(selectedVanityIndex, inventory);
        selectorTable.add(vanitySelector).expand().fill();

        vanitySelector.addListener(getVanitySelectorClickListener());

        priceBox.setText("Price: " + String.valueOf(inventory.get(selectedVanityIndex).getPrice()) + " doubloons");

        // Update preview
        setSelectedVanities();
    }

    private ClickListener getFilterSelectorClickListener()
    {
        return new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                setSelectedFilter();
            }
        };
    }

    private ClickListener getVanitySelectorClickListener()
    {
        return new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                setSelectedVanities();
            }
        };
    }

    private void setSelectedVanities()
    {

        ThisClientsPlayer player =  ClientPlayerManager.getSingleton().getThisClientsPlayer();
        selectedVanity = vanitySelector.getSelectedItem();
        selectedVanityIndex = vanitySelector.getSelectedIndex();
        priceBox.setText("Price: " + selectedVanity.getPrice() + " doubloons");

        System.out.println(ClientPlayerManager.getSingleton().getThisClientsPlayer().getDoubloons());

        if(player.getDoubloons() >= selectedVanity.getPrice() && !player.getOwnedItems().contains(selectedVanity))
        {
            purchaseButton.setDisabled(false);
            purchaseButton.setColor(Color.CYAN);
        }
        else
        {
            purchaseButton.setDisabled(true);
            purchaseButton.setColor(Color.GRAY);
        }


        previewTable.drawVanities(selectedVanity);
    }

    public void setInventory(ArrayList<VanityDTO> inventory)
    {
        this.inventory = inventory;
    }

    private void setSelectedFilter()
    {
        selectedFilterIndex = filterSelector.getSelectedIndex();
        selectedVanityIndex = 0;

        CommandShopInventoryRequest cmd = new CommandShopInventoryRequest();
        ClientModelFacade.getSingleton().queueCommand(cmd);

    }

    public int getSelectedFilterIndex()
    {
        return selectedFilterIndex;
    }
}
