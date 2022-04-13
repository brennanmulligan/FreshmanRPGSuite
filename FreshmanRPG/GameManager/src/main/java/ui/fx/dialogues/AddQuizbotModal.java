package ui.fx.dialogues;

import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.CommandAddQuestion;
import model.ModelFacade;

/**
 * @author Ben Uleau, Chris Boyer
 *
 */
public class AddQuizbotModal extends Modal
{
	private static AddQuizbotModal instance;

	private TextField questionField;
	private TextField answerField;
	private DatePicker startDate;
	private DatePicker endDate;

	/**
	 * @param id
	 *            - modal id
	 * @param label
	 *            - modal title
	 * @param height
	 *            - modal height
	 * @param width
	 *            - modal width
	 */
	private AddQuizbotModal(String id, String label, int height, int width)
	{
		super(id, label, height, width);
		Label questionLabel = new Label("Question:");
		questionLabel.setPrefWidth(LABEL_WIDTH);
		questionField = new TextField();
		questionField.setId("QuestionField");
		questionField.setPromptText("Question");
		HBox question = new HBox();
		question.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		question.getChildren().addAll(questionLabel, questionField);

		Label answerLabel = new Label("Answer:");
		answerLabel.setPrefWidth(LABEL_WIDTH);
		answerField = new TextField();
		answerField.setId("AnswerField");
		answerField.setPromptText("Answer");
		HBox answer = new HBox();
		answer.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		answer.getChildren().addAll(answerLabel, answerField);

		Label questionTypeLabel = new Label("Question Type:");
		questionTypeLabel.setPrefWidth(LABEL_WIDTH);
		ObservableList<String> questionTypeSelection = FXCollections
				.observableArrayList("Free answer"/* , "Multiple choice" */);
		ComboBox<String> dropdown = new ComboBox<>(questionTypeSelection);
		dropdown.setId("QuestionType");
		dropdown.setValue(questionTypeSelection.get(0));
		HBox questionType = new HBox();
		questionType.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		questionType.getChildren().addAll(questionTypeLabel, dropdown);

		startDate = new DatePicker();
		startDate.setId("StartDate");
		Label startLabel = new Label("Start date:");
		startLabel.setPrefWidth(LABEL_WIDTH);
		startDate.setOnAction(new DatePickerEventHandler(startDate));
		HBox start = new HBox();
		start.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		start.getChildren().addAll(startLabel, startDate);

		endDate = new DatePicker();
		endDate.setId("EndDate");
		Label endLabel = new Label("End date:");
		endLabel.setPrefWidth(LABEL_WIDTH);
		endDate.setOnAction(new DatePickerEventHandler(endDate));
		HBox end = new HBox();
		end.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		end.getChildren().addAll(endLabel, endDate);

		VBox datePicker = new VBox();
		datePicker.getChildren().addAll(start, end);

		VBox qa = new VBox();
		qa.getChildren().addAll(question, answer);

		VBox center = new VBox();
		center.getChildren().addAll(qa, questionType, datePicker);
		modal.setCenter(center);
	}

	/**
	 * Check if the singleton is instantiated
	 *
	 * @return false if it is, true otherwise (Chris wrote this)
	 */
	public static boolean isInstantiated()
	{
		return instance == null;
	}

	/**
	 * @see ui.fx.dialogues.Modal#save()
	 */
	@Override
	public void save()
	{
		CommandAddQuestion command = new CommandAddQuestion(AddQuizbotModal.getInstance().getQuestion(),
				AddQuizbotModal.getInstance().getAnswer(), AddQuizbotModal.getInstance().getStartDate(),
				AddQuizbotModal.getInstance().getEndDate());
		ModelFacade.getSingleton().queueCommand(command);
	}

	/**
	 * @see ui.fx.dialogues.Modal#cancel()
	 */
	@Override
	public void cancel()
	{
		System.out.println("AddQuizbotModal-Cancel");
	}

	/**
	 * @return the question in the question field
	 */
	public String getQuestion()
	{
		return questionField.getText();
	}

	/**
	 * @return the answer in the answer field
	 */
	public String getAnswer()
	{
		return answerField.getText();
	}

	/**
	 * Get the singleton instance of the quizbot modal
	 *
	 * @return the singleton instance of teh quizbot modal
	 */
	public static AddQuizbotModal getInstance()
	{
		if (instance == null)
		{
			instance = new AddQuizbotModal("AddQuizBot", "Add Question", 400, 400);
		}
		return instance;
	}

	/**
	 * Check if this modal is the same instance as another modal
	 *
	 * @param modal
	 *            - the modal to check against
	 * @return true if they're the same
	 */
	public static boolean sameInstance(AddQuizbotModal modal)
	{
		return instance == modal;
	}

	/**
	 * Get the start date
	 *
	 * @return start date
	 */
	public LocalDate getStartDate()
	{
		return startDate.getValue();
	}

	/**
	 * Get the end date
	 *
	 * @return end date
	 */
	public LocalDate getEndDate()
	{
		return endDate.getValue();
	}

	/**
	 * Define what happens when a date is chosen.
	 */
	private class DatePickerEventHandler implements EventHandler<ActionEvent>
	{

		private DatePicker datePicker;

		DatePickerEventHandler(DatePicker datePicker)
		{
			this.datePicker = datePicker;
		}

		@Override
		public void handle(ActionEvent event)
		{
			LocalDate date = this.datePicker.getValue();
			System.out.println("Date: " + date);
		}
	}

	/**
	 * Reset the add quizbot modal
	 */
	@Override
	public void reset()
	{
		instance = null;
	}
}
