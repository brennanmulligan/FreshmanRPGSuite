package edu.ship.engr.shipsim.view.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.List;

/**
 * A UI control for selecting an item by cycling through a list of options using previous and next buttons.
 */
public class SelectorControl<T> extends Table
{
    private final List<T> selectorOptions;
    private Integer selected;
    private final Label selectedLabel;
    private final TextButton prevButton;
    private final TextButton nextButton;

    public SelectorControl(Integer selected, List<T> options)
    {
        Skin skin = SkinPicker.getSkinPicker().getDefaultSkin();
        this.selectorOptions = options;

        // Create label for holding the name of the selected vanity
        selectedLabel = new Label("", skin);
        selectedLabel.setAlignment(Align.center);

        // Setup previous/next buttons
        ClickListener clickListener = createButtonListener();
        prevButton = new TextButton("<", skin);
        prevButton.addListener(clickListener);
        nextButton = new TextButton(">", skin);
        nextButton.addListener(clickListener);
        float BUTTON_WIDTH = 30f;

        // Build the selector table
        Table selectorTable = new Table(skin);
        selectorTable.setColor(Color.GRAY);
        selectorTable.add(prevButton).width(BUTTON_WIDTH).align(Align.left);
        selectorTable.add(selectedLabel).expandX();
        selectorTable.add(nextButton).width(BUTTON_WIDTH).align(Align.right).padRight(15f);
        row();
        add(selectorTable).expand().fill();

        // Set the selected item and update the view
        setSelectedIndex(selected);
    }

    /**
     * @return this selector's currently selected item
     */
    public T getSelectedItem()
    {
        return selectorOptions.get(selected);
    }

    public int getSelectedIndex()
    {
        return selected;
    }

    public void setSelectedIndex(int index)
    {
        selected = index;
        updateView();
    }

    /**
     * Updates the view of the selector control
     */
    private void updateView()
    {
        // Set the selected vanity
        T item = selectorOptions.get(selected);

        // Update the name label
        selectedLabel.setText(item.toString());

        // Update the state of the buttons
        boolean hasPrevious = !(selected == 0);
        boolean hasNext = !(selected == (selectorOptions.size() - 1));
        prevButton.setDisabled(!hasPrevious);
        prevButton.setColor(hasPrevious ? Color.CYAN : Color.GRAY);
        nextButton.setDisabled(!hasNext);
        nextButton.setColor(hasNext ? Color.CYAN : Color.GRAY);
    }

    /**
     * @return a button listener for the previous and next buttons.
     */
    private ClickListener createButtonListener()
    {
        return new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                TextButton buttonClicked = (TextButton) event.getListenerActor();

                if (buttonClicked.equals(prevButton) && !prevButton.isDisabled())
                {
                    setSelectedIndex(selected - 1);
                }
                else if (buttonClicked.equals(nextButton) && !nextButton.isDisabled())
                {
                    setSelectedIndex(selected + 1);
                }
            }
        };
    }
}
