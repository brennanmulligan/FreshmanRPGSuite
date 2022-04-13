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
import model.CommandUpdateQuestion;
import model.ModelFacade;

/**
 * @author Benjamin Uleau, Jordan Long Modal for editing quizbot questions
 */
public class EditQuizbotModal extends Modal
{
	private static EditQuizbotModal instance;

	private TextField questionField;
	private TextField answerField;
	private DatePicker startDate;
	private DatePicker endDate;
	private int id;

	private EditQuizbotModal(String id, String label, int height, int width)
	{
		super(id, label, height, width);

		Label questionLabel = new Label("Question:");
		questionLabel.setPrefWidth(LABEL_WIDTH);
		questionField = new TextField();
		questionField.setId("QuestionField");
		HBox question = new HBox();
		question.setPadding(new Insets(HBOX_PADDING, HBOX_PADDING, HBOX_PADDING, HBOX_PADDING));
		question.getChildren().addAll(questionLabel, questionField);

		Label answerLabel = new Label("Answer:");
		answerLabel.setPrefWidth(LABEL_WIDTH);
		answerField = new TextField();
		answerField.setId("AnswerField");
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
	 * @return singleton get instance
	 */
	public static EditQuizbotModal getInstance()
	{
		if (instance == null)
		{
			instance = new EditQuizbotModal("EditQuizbot", "Edit Question", 400, 400);
		}
		return instance;
	}

	/**
	 * Save changes
	 */
	@Override
	public void save()
	{
		CommandUpdateQuestion command = new CommandUpdateQuestion(id, getQuestion(), getAnswer(), getStartDate(),
				getEndDate());
		ModelFacade.getSingleton().queueCommand(command);
	}

	/**
	 * Cancel changes
	 */
	@Override
	public void cancel()
	{
	}

	/**
	 * @return the question in the question field
	 */
	public String getQuestion()
	{
		return questionField.getText();
	}

	/**
	 * @param question
	 *            - the question that the quizbot should ask.
	 */
	public void setQuestion(String question)
	{
		questionField.setText(question);
	}

	/**
	 * @return the answer in the answer field
	 */
	public String getAnswer()
	{
		return answerField.getText();
	}

	/**
	 * @param answer
	 *            the preload text for question
	 */
	public void setAnswer(String answer)
	{
		answerField.setText(answer);
	}

	/**
	 * @param id
	 *            od of the question
	 */
	public void setId(int id)
	{
		this.id = id;
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
		}
	}

	/**
	 * @see ui.fx.dialogues.Modal#reset()
	 */
	@Override
	public void reset()
	{
		instance = null;
	}
}
