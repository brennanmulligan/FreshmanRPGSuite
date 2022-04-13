package ui.fx.dialogues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Saves changes in a modal.
 * @author Benjamin Uleau, Chris Boyer Saves
 */
public class SaveButton extends Button
{
	/**
	 * @param modal The modal associated with this save button.
	 */
	public SaveButton(Modal modal)
	{
		this.setText("Save");
		this.setId("ModalSaveButton");

		ButtonClick action = new ButtonClick(modal);
		this.setOnAction(action);

	}

	/**
	 * Manages what happens when the button is click.
	 */
	class ButtonClick implements EventHandler<ActionEvent>
	{
		private Modal modal;

		/**
		 * @param modal the modal the button is one
		 */
		ButtonClick(Modal modal)
		{
			this.modal = modal;
		}

		/**
		 * @see javafx.event.EventHandler#handle(javafx.event.Event)
		 */
		@Override
		public void handle(ActionEvent event)
		{
			this.modal.save();
			this.modal.stage.close();
			this.modal.reset();
		}
	}
}
