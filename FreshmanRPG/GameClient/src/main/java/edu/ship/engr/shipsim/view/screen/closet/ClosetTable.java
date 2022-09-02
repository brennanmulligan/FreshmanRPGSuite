package edu.ship.engr.shipsim.view.screen.closet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.ClientModelFacade;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.model.CommandChangePlayerAppearance;
import edu.ship.engr.shipsim.view.screen.OverlayingScreenTable;
import edu.ship.engr.shipsim.view.screen.SelectorControl;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ClosetUI table which holds the player's closet.
 *
 * @author Stefan Milanovic & Nick Starkey
 */
public class ClosetTable extends OverlayingScreenTable
{
    private List<VanityDTO> selectedVanities;
    private List<VanityDTO> ownedVanities;
    private final Table selectorTable;
    private final List<SelectorControl<VanityDTO>> selectors;
    private final PreviewTable previewTable;

    /**
     * Creates the closet table
     *
     * @param scrollable Whether the overlaying screen table is scrollable
     */
    public ClosetTable(boolean scrollable)
    {
        super(scrollable);

        Skin skin = SkinPicker.getSkinPicker().getDefaultSkin();

        // Add the vanity selector list pane
        selectorTable = new Table();
        selectors = new ArrayList<>();
        ScrollPane listPane = new ScrollPane(selectorTable, skin);
        listPane.setScrollingDisabled(true, false);
        listPane.setFadeScrollBars(false);
        container.row();
        container.add(listPane).fill().expand();

        // Add the preview table
        previewTable = new PreviewTable();
        container.add(previewTable).width(250f).fill();

        // Add an update button
        TextButton updateButton = new TextButton("Update Appearance", skin);
        updateButton.setColor(Color.CYAN);
        updateButton.addListener(getUpdateButtonListener());
        container.row();
        container.add(updateButton).expandX().fillX().padTop(10).padBottom(10);
    }

    /**
     * Sets the currently selected player vanities.
     *
     * @param selectedVanities the vanities to select
     */
    protected void setSelectedVanities(List<VanityDTO> selectedVanities)
    {
        this.selectedVanities = selectedVanities;
    }

    /**
     * Sets the player's currently owned vanities.
     *
     * @param ownedVanities the player's owned vanities
     */
    protected void setOwnedVanities(List<VanityDTO> ownedVanities)
    {
        this.ownedVanities = ownedVanities;
    }

    /**
     * Updates the underlying view tables.
     */
    protected void updateView()
    {
        // Remove any existing vanity selectors
        selectorTable.clear();
        selectors.clear();

        // Map the list of owned vanities by their types
        Map<VanityType, List<VanityDTO>> ownedVanitiesByType = ownedVanities.stream()
                .sorted(Comparator.comparing(VanityDTO::getID))
                .collect(Collectors.groupingBy(VanityDTO::getVanityType));

        // Create and add a vanity selector for each vanity type
        for (VanityType vanityType : VanityType.values())
        {
            if (vanityType == VanityType.NAMEPLATE)
            {
                continue;
            }
            Integer selectedId = selectedVanities.get(vanityType.ordinal()).getID();
            List<VanityDTO> owned = ownedVanitiesByType.get(vanityType);

            int index = IntStream.range(0, owned.size())
                    .filter(i -> owned.get(i).getID() == selectedId)
                    .findFirst()
                    .orElse(-1);

            SelectorControl<VanityDTO> selector = new SelectorControl<VanityDTO>(index, owned);

            selector.addListener(getVanitySelectorClickListener());
            selectors.add(selector);

            // Add to table
            selectorTable.row();
            selectorTable.add(selector).expand().fill().padTop(5);
        }

        // Update preview
        drawVanities();
    }

    /**
     * Collects the selected vanities from the vanity selectors and
     * tells the preview table to draw them.
     */
    private void drawVanities()
    {
        selectedVanities = selectors.stream()
                .map(SelectorControl::getSelectedItem)
                .collect(Collectors.toList());

        previewTable.drawVanities(selectedVanities);
    }

    /**
     * Sets the client player's vanities to selectedVanities.
     */
    private void updatePlayerAppearance()
    {
        int clientID = ClientPlayerManager.getSingleton().getThisClientsPlayer().getID();
        CommandChangePlayerAppearance command = new CommandChangePlayerAppearance(clientID, selectedVanities);
        ClientModelFacade.getSingleton().queueCommand(command);
    }

    /**
     * @return a click listener for the update button
     */
    private ClickListener getUpdateButtonListener()
    {
        return new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                updatePlayerAppearance();
            }
        };
    }

    /**
     * @return a click listener for the vanity selectors
     */
    private ClickListener getVanitySelectorClickListener()
    {
        return new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                drawVanities();
            }
        };
    }
}
