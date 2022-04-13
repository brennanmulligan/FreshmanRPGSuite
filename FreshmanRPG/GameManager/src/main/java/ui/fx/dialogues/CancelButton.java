package ui.fx.dialogues;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * Button for calling the cancel action.
 * @author Benjamin Uleau and Chris Boyer
 */
public class CancelButton extends Button
{

	/**
	 * Constructor
	 * @param modal - the modal to add the cancel button to
	 */
	public CancelButton(Modal modal)
	{
		this.setText("Cancel");

		ButtonClick action = new ButtonClick(modal);
		this.setOnAction(action);
		this.setId("ModalCancelButton");

	}

	/**
	 * Manage what happens the cancel button is cliecked.
	 */
	class ButtonClick implements EventHandler<ActionEvent>
	{
		private Modal modal;

		/**
		 * @param modal the modal holding the button we are listening to
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
			this.modal.cancel();
			this.modal.stage.close();
			this.modal.reset();
		}
	}
}
