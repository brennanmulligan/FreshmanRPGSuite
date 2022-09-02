package edu.ship.engr.shipsim.view.screen.coordinates;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.view.screen.OverlayingScreenTable;
import edu.ship.engr.shipsim.view.screen.SkinPicker;


/**
 * Class to build the Table functionality for the CoordinatesUI class
 *
 * @author Michael Umbelina
 */
public class CoordinatesTable extends OverlayingScreenTable
{
    private Skin skin;
    private Table grid;
    private Label header;
    private Label row;
    private Label column;

    /**
     * @param scrollable Whether or not the the overlaying screen table is scrollable
     */
    public CoordinatesTable(boolean scrollable)
    {
        super(scrollable);
        setupUI();
    }

    /**
     * Builds the window for seeing the coordinates
     */
    private void setupUI()
    {
        skin = SkinPicker.getSkinPicker().getDefaultSkin();
        grid = new Table();
        grid.setWidth(100f); //placeholder values
        grid.setHeight(100f); //placeholder values

        row = new Label("ROW: 0", skin);
        column = new Label("COLUMN: 0", skin);

        grid.add(row);
        grid.row();
        grid.add(column);

        grid.top();
        container.add(grid);
    }

    /**
     * Updates the TextAreas for new positions
     *
     * @param position from PlayerMovedReport
     */
    public void updateCoordinates(Position position)
    {
        row.setText("ROW: " + position.getRow());
        column.setText("COLUMN: " + position.getColumn());
    }
}
