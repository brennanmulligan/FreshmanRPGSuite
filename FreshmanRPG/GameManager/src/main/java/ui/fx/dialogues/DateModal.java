package ui.fx.dialogues;

import java.time.LocalDate;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A date chooser
 *
 */
public class DateModal extends Modal
{

	private static DateModal instance;

	private DatePicker startDate;

	private DateModal(String id, String label, int height, int width)
	{
		super(id, label, height, width);
		Label dateLabel = new Label("Choose Start Date: ");
		startDate = new DatePicker();
		startDate.setId("StartDatePicker");
		HBox center = new HBox();
		center.getChildren().addAll(dateLabel, startDate);

		modal.setCenter(center);
	}

	/**
	 * @return true if this button has been created
	 */
	public static boolean isInstantiated()
	{
		return instance == null;
	}

	/**
	 * @return the one instance of this
	 */
	public static DateModal getInstance()
	{
		if (instance == null)
		{
			instance = new DateModal("DateModal", "Choose Date", 400, 150);
		}
		return instance;
	}

	/**
	 * @return the date that has been set by the user
	 */
	public LocalDate getDate()
	{
		return startDate.getValue();
	}

	/**
	 * @see ui.fx.dialogues.Modal#save()
	 */
	@Override
	public void save()
	{
	}

	/**
	 * @see ui.fx.dialogues.Modal#cancel()
	 */
	@Override
	public void cancel()
	{
	}

	/**
	 * @see ui.fx.dialogues.Modal#reset()
	 */
	@Override
	public void reset()
	{

	}

}
