package ui.fx.framework;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Josh McMillen, Ben Uleau Alert bar that can receive messages from any
 *         source and will display it at bottom of UI
 */
public class AlertBar extends BorderPane
{

	private static AlertBar instance;
	private Label message;

	private AlertBar()
	{
		// Set style preferences
		this.setId("AlertBar");
		this.setPrefHeight(48);
		this.setPrefWidth(Double.MAX_VALUE);

		// Initialize Message Label
		this.message = new Label();
		this.message.setPrefWidth(Double.MAX_VALUE);
		this.message.setPrefHeight(Double.MAX_VALUE);

		// Add Message Label to Center ( Eats Left ) Region
		this.setCenter(message);
	}

	/**
	 * @param messageText
	 * set Label text to incoming message
	 */
	public void receiveMessage(String messageText)
	{
		message.setText(messageText);
	}

	/**
	 * @return messageText
	 */
	public String getMessage()
	{
		return message.getText();
	}

	/**
	 * @return Singleton reference to this Alert Bar
	 */
	public static AlertBar getInstance()
	{
		if (instance == null)
		{
			instance = new AlertBar();
		}
		return instance;
	}

}
