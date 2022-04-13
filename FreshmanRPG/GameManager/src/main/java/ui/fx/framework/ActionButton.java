package ui.fx.framework;

import javafx.scene.control.Button;
import ui.fx.IconFont;

/**
 *
 * @author Josh McMillen, Ben Uleau
 *
 */
public abstract class ActionButton extends Button
{


	/**
	 * @param id = style sheet identifier
	 * @param icon = button label
	 */
	public ActionButton(String id, IconFont icon)
	{
		this.setId(id);
		this.setText(icon.toString());
		this.getStyleClass().add("ActionButton");
		this.setPrefWidth(48);
		this.setPrefHeight(Double.MAX_VALUE);
	}
}
