package ui.fx.dialogues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import manager.GameManagerFX;

/**
 * @author Benjamin Uleau, Chris Boyer
 * Abstract class all content view modals extend
 */
public abstract class Modal extends BorderPane
{
	/**
	 *
	 */
	protected Stage stage;
	/**
	 *
	 */
	protected Scene scene;
	/**
	 *
	 */
	protected static final int LABEL_WIDTH = 240;
	/**
	 *
	 */
	protected static final int HBOX_PADDING = 8;
	/**
	 *
	 */
	protected BorderPane modal;
	private Label modalTitle;

	/**
	 * @param id - id of the modal
	 * @param label - title of the modal 
	 * @param height - height of the modal
	 * @param width - width of the modal
	 */
	public Modal(String id, String label, int height, int width)
	{
		this.setPrefHeight(height);
		this.setPrefWidth(width);

		this.modal = new BorderPane();
		modal.setId(id);

		modalTitle = new Label(label);
		modalTitle.getStyleClass().add("title");
		modalTitle.setPrefWidth(Double.MAX_VALUE);
		modalTitle.setPadding(new Insets(4, 4, 4, 4));
		modalTitle.setAlignment(Pos.CENTER);
		modal.setTop(modalTitle);

		SaveButton saveButton = new SaveButton(this);
		CancelButton cancelButton = new CancelButton(this);

		BorderPane close = new BorderPane();
		close.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		close.setLeft(cancelButton);
		close.setRight(saveButton);
		modal.setBottom(close);

		scene = new Scene(modal, height, width);
		scene.getStylesheets().add(getClass().getResource("/res/game_manager.css").toExternalForm());

		stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(GameManagerFX.getStage());


	}

	/**
	 * Save the changes made in the modal
	 */
	public abstract void save();

	/**
	 * Cancel the changes made in the modal
	 */
	public abstract void cancel();

	/**
	 * Reset the singleton to close the window.
	 */
	public abstract void reset();

	/**
	 * Show the modal
	 */
	public void show()
	{
		stage.setScene(scene);
		stage.showAndWait();
	}

	/**
	 * Generates a complete modal field component
	 * @param labelText text of the label to be generated for the component
	 * @param item javafx component that is passed in to be styled and etc.
	 * @return HBox containing all of the components required for a field
	 */
	protected HBox generateElement(String labelText, Node item)
	{
		Label label = new Label(labelText);
		label.setPrefWidth(LABEL_WIDTH);
		HBox hbox = new HBox();
		hbox.getChildren().addAll(label, item);
		hbox.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		return hbox;
	}


	/**
	 * Returns the title of the modal
	 * @return modal title
	 */
	Label getModalTitle()
	{
		return modalTitle;
	}


}
