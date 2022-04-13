package view.screen.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * A basic screen that displays any needed Pop-up information.
 * @author sl6469 ck4124
 *
 */
public class OneChoiceScreenPopUp extends Group implements ScreenPopUp
{
	private final Skin skin = new Skin(Gdx.files.internal("ui-data/uiskin.json"));
	private ExitDialog pop_close;
	private Stage stage;

	/**
	 * Basic constructor. will call showPopUp() to initialize all the data in the tables.
	 * @param header name for the dialog box
	 * @param description for text in popup
	 * @param stage the stage
	 * @param behavior the PopupBehavior of the popup
	 * @param display the display to notify when closed
	 */
	public OneChoiceScreenPopUp(String header, String description, Stage stage, PopupBehavior behavior, DisplayPopupPrompt display)
	{
		pop_close = new ExitDialog(header, description, skin, behavior, display, this);
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
	 * @author sl6469
	 *
	 */
	public static class ExitDialog extends Dialog
	{

		private PopupBehavior behavior;
		private DisplayPopupPrompt display;
		private OneChoiceScreenPopUp parent;

		/**
		 * @param header The name of the pop up window
		 * @param description information pop up is displaying
		 * @param skin The skin the window uses
		 * @param behavior the PopupBehavior of the popup
		 * @param display the display to notify when closed
		 * @param parent the parent ScreenPopUp to this dialog
		 */
		public ExitDialog(String header, String description, Skin skin, PopupBehavior behavior, DisplayPopupPrompt display, OneChoiceScreenPopUp parent)
		{
			super(header, skin);
			Label label = new Label(description, skin);
			label.setWrap(true);
			label.setFontScale(1.0f);
			label.setAlignment(Align.center);
			this.getContentTable().add(label).width(400).row();
			button("OK");
			key(Input.Keys.ENTER, null);
			this.behavior = behavior;
			this.parent = parent;
			this.display = display;
		}

		@Override
		protected void result(Object object)
		{
			this.behavior.clicked();
			display.dialogClosed(parent);
		}
	}


}
