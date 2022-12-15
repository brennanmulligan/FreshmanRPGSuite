package edu.ship.engr.shipsim.view.screen.popup;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * TwoChoiceScreenPopup.java
 * Method for the creation of a popup with two options
 *
 * @author Zachary Thompson & Abdullah Alsuwailem
 */
public class TwoChoiceScreenPopup extends Group implements ScreenPopUp
{
    private final static Skin skin = new Skin(Gdx.files.internal("ui-data/uiskin.json"));
    private ExitDialog pop_close;
    private Stage stage;

    /**
     * Basic constructor. will call showPopUp() to initialize all the data in the tables
     *
     * @param description for text in popup
     * @param button1     The text for the first button
     * @param button2     text for the second button
     * @param stage       the stage
     * @param behavior1   the PopupBehavior for the first button
     * @param behavior2   the PopupBehavior for the second button
     */
    public TwoChoiceScreenPopup(String description, String button1, String button2, Stage stage, PopupBehavior behavior1, PopupBehavior behavior2)
    {
        pop_close = new ExitDialog(description, button1, button2, behavior1, behavior2);
        this.stage = stage;
    }

    /**
     * Show this dialog on screen
     */
    @Override
    public void showDialog()
    {
        pop_close.show(stage);
        stage.setKeyboardFocus(pop_close);
    }


    /**
     * @author Zachary Thompson & Abdul
     */
    public static class ExitDialog extends Dialog
    {

        private PopupBehavior behavior1;
        private PopupBehavior behavior2;

        /**
         * @param description information pop up is displaying
         * @param button2     the second button text
         * @param button1     the first button text
         * @param behavior1   the PopupBehavior for the first button
         * @param behavior2   the PopupBehavior for the second button
         */
        public ExitDialog(String description, String button1, String button2, PopupBehavior behavior1, PopupBehavior behavior2)
        {
            super("", skin);
            Label label = new Label(description, skin);
            label.setWrap(true);
            label.setFontScale(1.0f);
            label.setAlignment(Align.center);
            this.getContentTable().add(label).width(400).row();
            button(button1, behavior1);
            button(button2, behavior2);
            this.behavior1 = behavior1;
            this.behavior2 = behavior2;
        }

        /**
         * Activate the Behavior after the button is pressed
         */
        @Override
        protected void result(Object object)
        {
            if (object != null)
            {
                if (object == behavior1)
                {
                    behavior1.clicked();
                }
                if (object == behavior2)
                {
                    behavior2.clicked();
                }
            }
        }
    }


}
