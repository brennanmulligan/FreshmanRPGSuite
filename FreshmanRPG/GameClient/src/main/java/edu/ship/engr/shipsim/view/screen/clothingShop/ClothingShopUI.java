package edu.ship.engr.shipsim.view.screen.clothingShop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.model.reports.ClientKeyInputSentReport;
import edu.ship.engr.shipsim.model.reports.VanityShopInventoryResponseReport;
import edu.ship.engr.shipsim.view.screen.OverlayingScreen;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ClothingShopUI extends OverlayingScreen implements ReportObserver
{
    private final float WIDTH = 600f;
    private final float HEIGHT = 380f;
    private final ShopTable shopTable;
    private ArrayList<VanityDTO> inventory;

    public ClothingShopUI()
    {
        super();
        setUpListening();

        Table mainTable = new Table(SkinPicker.getSkinPicker().getCrewSkin());
        mainTable.setFillParent(true);
        mainTable.row();

        shopTable = new ShopTable(false);
        mainTable.add(shopTable).expand().fill();
        container.addActor(mainTable);
    }

    @Override
    public float getMyWidth()
    {
        return WIDTH;
    }

    @Override
    public float getMyHeight()
    {
        return HEIGHT;
    }

    public void setUpListening()
    {
        ReportObserverConnector cm = ReportObserverConnector.getSingleton();
        cm.registerObserver(this, VanityShopInventoryResponseReport.class);
        cm.registerObserver(this, ClientKeyInputSentReport.class);
    }

    @Override
    public void toggleVisibility()
    {
        if (isVisible())
        {
            this.addAction(Actions.hide());
        }
        else
        {
            CommandShopInventoryRequest cmd = new CommandShopInventoryRequest();
            ClientModelFacade.getSingleton().queueCommand(cmd);
            this.addAction(Actions.show());
        }
    }

    @Override
    public void receiveReport(Report report)
    {
        if (report.getClass().equals(VanityShopInventoryResponseReport.class))
        {
            VanityShopInventoryResponseReport r = (VanityShopInventoryResponseReport) report;
            inventory = r.getInventory();
            int type = shopTable.getSelectedFilterIndex();

            inventory = inventory.stream().filter(vanityDTO -> vanityDTO.getVanityType().ordinal() == type)
                    .collect(Collectors.toCollection(ArrayList::new));

            if (inventory.size() > 0)
            {
                shopTable.setInventory(inventory);
                shopTable.updateView();
            }

        }
        else if (report.getClass().equals(ClientKeyInputSentReport.class))
        {
            ClientKeyInputSentReport r2 = (ClientKeyInputSentReport) report;
            // Check for P
            if (r2.getInput().equals(Input.Keys.toString(Input.Keys.U)))
            {
                this.toggleVisibility();
            }
        }


    }
}


