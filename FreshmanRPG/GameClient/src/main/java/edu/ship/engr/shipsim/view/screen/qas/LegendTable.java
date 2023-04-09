package edu.ship.engr.shipsim.view.screen.qas;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

/**
 * @author TJ Renninger, Ian Keefer
 */
public class LegendTable extends ScrollPane
{
    private Table table;
    private String blue_hex = "00FFFF";

    /**
     * Table that displays all of the objectives of a selected quest for the player
     */
    public LegendTable()
    {
        super(null, SkinPicker.getSkinPicker().getCrewSkin());
        setFadeScrollBars(true);
        buildTable();
        setScrollingDisabled(true, true);
        setWidget(table);
    }

    /**
     * Creates a new table that will hold all of the quests and sets it to the top left position of the scroll pane
     */
    private void buildTable()
    {
        table = new Table();
        table.left().top();

        Label label = new Label("Legend:", SkinPicker.getSkinPicker().getCrewSkin());
        table.add(label).right().padRight(10f);
        label = new Label("Completed", SkinPicker.getSkinPicker().getCrewSkin());
        label.setColor(Color.GREEN);
        table.add(label).right().padRight(10f);
        label = new Label("Fulfilled", SkinPicker.getSkinPicker().getCrewSkin());
        label.setColor(Color.YELLOW);
        table.add(label).right().padRight(10f);
        label = new Label("In Progress", SkinPicker.getSkinPicker().getCrewSkin());
        label.setColor(Color.valueOf(blue_hex));
        table.add(label).right().padRight(10f);

        label = new Label("Late", SkinPicker.getSkinPicker().getCrewSkin());
        label.setColor(Color.RED);
        table.add(label).right().padRight(10f);

        label = new Label("Expired", SkinPicker.getSkinPicker().getCrewSkin());
        label.setColor(Color.BLACK);
        table.add(label).right().padRight(10f);
        label = new Label("[P] Printable", SkinPicker.getSkinPicker().getCrewSkin());
        label.setColor(Color.WHITE);
        table.add(label).right().padRight(10f);

    }

}
