package edu.ship.engr.shipsim.view.screen.closet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.VisibleAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.*;
import edu.ship.engr.shipsim.model.reports.ClientKeyInputSentReport;
import edu.ship.engr.shipsim.model.reports.ServerPlayerOwnedItemsResponseReport;
import edu.ship.engr.shipsim.view.screen.OverlayingScreen;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A screen where the player can modify their appearance.
 *
 * @author Stefan Milanovic & Nick Starkey
 */
public class ClosetUI extends OverlayingScreen implements ReportObserver
{
    private final float WIDTH = 600f;
    private final float HEIGHT = 380f;
    private final ClosetTable closetTable;
    List<VanityDTO> currentVanities;

    /**
     * Basic constructor.
     */
    public ClosetUI()
    {
        super();

        ReportObserverConnector cm = ReportObserverConnector.getSingleton();
        cm.registerObserver(this, ClientKeyInputSentReport.class);
        cm.registerObserver(this, ServerPlayerOwnedItemsResponseReport.class);

        Table mainTable = new Table(SkinPicker.getSkinPicker().getCrewSkin());
        mainTable.setFillParent(true);
        mainTable.row();

        closetTable = new ClosetTable(false);
        mainTable.add(closetTable).expand().fill();
        container.addActor(mainTable);
    }

    /**
     * Retrieves the players current and owned vanities then updates the view.
     */
    private synchronized void loadAllVanities()
    {
        ClientPlayerManager playerManager = ClientPlayerManager.getSingleton();
        currentVanities = playerManager.getThisClientsPlayer().getVanities();

        CommandServerPlayerOwnedItemsRequest cmd = new CommandServerPlayerOwnedItemsRequest();
        ClientModelFacade.getSingleton().queueCommand(cmd);
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

    @Override
    public void toggleVisibility()
    {
        VisibleAction action;
        if (isVisible())
        {
            action = Actions.hide();
        }
        else
        {
            action = Actions.show();

            loadAllVanities();
        }
        addAction(action);

    }

    /**
     * TEMP: for testing purposes pulls all vanities directly from the TextureAtlas files.
     *
     * @return a list of VanityDTOs for testing purposes.
     */
    private List<VanityDTO> getAllPossibleVanitiesForTest()
    {
        // Add the current vanities
        ClientPlayerManager manager = ClientPlayerManager.getSingleton();
        List<VanityDTO> currentVanities = manager.getThisClientsPlayer().getVanities();
        List<VanityDTO> allVanities = new ArrayList<>(currentVanities);

        // For testing purposes, generate all possible vanities.
        String uiPath = "ui-data/";
        HashMap<VanityType, String> atlasPathByVanityType = new HashMap<>(Map.of(
                VanityType.HAT, uiPath.concat("hats.atlas"),
                VanityType.HAIR, uiPath.concat("hair.atlas"),
                VanityType.SHIRT, uiPath.concat("shirts.atlas"),
                VanityType.PANTS, uiPath.concat("pants.atlas"),
                VanityType.SHOES, uiPath.concat("shoes.atlas"),
                VanityType.BODY, uiPath.concat("body.atlas")
        ));

        List<String> currentTextures = currentVanities.stream()
                .map(VanityDTO::getTextureName).collect(Collectors.toList());

        int vanityIndex = currentVanities.stream().max(Comparator.comparing(VanityDTO::getID)).get().getID() + 1;
        for (VanityType vanityType : VanityType.values())
        {
            TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlasPathByVanityType.get(vanityType)));
            for (TextureAtlas.AtlasRegion region : atlas.getRegions())
            {
                if (!currentTextures.contains(region.name))
                {
                    allVanities.add(new VanityDTO(vanityIndex++, region.name, "", region.name,
                            vanityType, 0));
                }
            }
        }

        return allVanities;
    }

    @Override
    public void receiveReport(Report report)
    {
        if (report.getClass().equals(ClientKeyInputSentReport.class))
        {
            ClientKeyInputSentReport r = (ClientKeyInputSentReport) report;
            // Check for I
            if (r.getInput().equals(Input.Keys.toString(Input.Keys.I)))
            {
                this.toggleVisibility();
            }
        }
        else if (report.getClass().equals(ServerPlayerOwnedItemsResponseReport.class))
        {
//            System.out.println("\nReport received");
            ServerPlayerOwnedItemsResponseReport r = (ServerPlayerOwnedItemsResponseReport) report;

            closetTable.setOwnedVanities(r.getServerOwnedVanities());
//            r.getServerOwnedVanities().forEach(System.out::println);
            closetTable.setSelectedVanities(currentVanities);
            closetTable.updateView();
        }
    }
}
