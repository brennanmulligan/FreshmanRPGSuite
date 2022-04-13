package ui.fx.contentviews;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;

import dataDTO.QuestionDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import model.CommandDeleteAllQuestions;
import model.CommandDeleteQuestion;
import model.CommandGetAllQuestions;
import model.CommandImportQuestion;
import model.ModelFacade;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.QuestionListReport;
import ui.fx.dialogues.AddQuizbotModal;
import ui.fx.dialogues.DateModal;
import ui.fx.dialogues.EditQuizbotModal;
import ui.fx.framework.AlertBar;
import ui.fx.framework.ContentView;
import ui.fx.framework.FileChooserContainer;

/**
 * @author jwmcmillen And Mohammed Almaslamani 
 * Workspace content for the quizbot content (quizbot menu)
 */
public class QuizbotContentView extends ContentView implements QualifiedObserver
{
	private static QuizbotContentView instance;
	private FileChooserContainer container;
	private ArrayList<QuestionDTO> questions;
	private TableView<QuestionDTO> questionsTable;
	private boolean wipeTable = false;


	/**
	 * Singleton constructor
	 */
	@SuppressWarnings("unchecked")
	private QuizbotContentView()
	{
		super("QuizbotContentView", "Quizbot");
		questions = new ArrayList<>();
		this.questionsTable = new TableView<>();
		this.questionsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.questionsTable.setId("QuestionsTable");

		TableColumn<QuestionDTO, Integer> questionId = new TableColumn<>("ID");
		questionId.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<QuestionDTO, String> question = new TableColumn<>("QUESTION");
		question.setCellValueFactory(new PropertyValueFactory<>("question"));

		TableColumn<QuestionDTO, String> questionAnswer = new TableColumn<>("ANSWER");
		questionAnswer.setCellValueFactory(new PropertyValueFactory<>("answer"));

		TableColumn<QuestionDTO, Date> questionStart = new TableColumn<>("START");
		questionStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));

		TableColumn<QuestionDTO, Date> questionEnd = new TableColumn<>("END");
		questionEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));

		this.questionsTable.getColumns().addAll(questionId, question, questionAnswer, questionStart, questionEnd);
		this.setCenter(this.questionsTable);
		this.refresh();
		questions = new ArrayList<>();
	}


	/**
	 * @return the singleton instance of the quizbot content
	 */
	public static QuizbotContentView getInstance()
	{
		if (instance == null)
		{
			instance = new QuizbotContentView();
		}
		return instance;
	}

	/**
	 * Gets the container for the File Chooser
	 * @return
	 *        FileChooser
	 */
	public FileChooserContainer getContainer()
	{
		return container;
	}


	/**
	 * @return Questions DTO Table Object
	 */
	public TableView<QuestionDTO> getQuestionsTable()
	{
		return this.questionsTable;
	}

	/**
	 * Add a question
	 */
	@Override
	public void add()
	{
		AlertBar.getInstance().receiveMessage("ADD QUIZ");
		AddQuizbotModal modal = AddQuizbotModal.getInstance();
		modal.show();
	}

	/**
	 * Delete a question
	 */
	@Override
	public void delete()
	{

		QuestionDTO targetedQuestion = this.questionsTable.getSelectionModel().getSelectedItem();

		if (targetedQuestion == null)
		{
			AlertBar.getInstance().receiveMessage("MUST SELECT QUESTION TO DELETE");
		}
		else
		{

			// showing a confirmation message when delete question is clicked. 
			AlertBar.getInstance().receiveMessage("DELETE QUIZ");
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Question");
			alert.setContentText("Are you sure you want to delete this question?");
			ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonData.NO);

			alert.getButtonTypes().setAll(yesButton, noButton);

			alert.getDialogPane().lookupButton(yesButton).setId("YesButton");

			alert.getDialogPane().lookupButton(noButton).setId("NoButton");

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get().getText().equals(ButtonType.YES.getText()))
			{
				CommandDeleteQuestion command = new CommandDeleteQuestion(targetedQuestion.getId());
				command.execute();
				AlertBar.getInstance().receiveMessage("QUESTION DELETED");
			}
			else if (result.get().getText().equals(ButtonType.NO.getText()))
			{
				AlertBar.getInstance().receiveMessage("QUESTION DELETION CANCELLED");
			}
		}
	}

	/**
	 * Edit a question
	 */
	@Override
	public void edit()
	{
		AlertBar.getInstance().receiveMessage("EDIT QUIZ");
		QuestionDTO question = this.questionsTable.getSelectionModel().getSelectedItem();
		EditQuizbotModal.getInstance().setQuestion(question.getQuestion());
		EditQuizbotModal.getInstance().setAnswer(question.getAnswer());
		EditQuizbotModal.getInstance().setId(question.getId());
		EditQuizbotModal.getInstance().show();


	}

	/**
	 * Filter questions
	 */
	@Override
	public void filter(String filter)
	{
		AlertBar.getInstance().receiveMessage("FILTER QUIZZES BY \"" + filter.toUpperCase() + "\"");
	}

	/**
	 * Refresh questions
	 */
	@Override
	public void refresh()
	{
		AlertBar.getInstance().receiveMessage("REFRESH QUIZBOT VIEW");
		questions = new ArrayList<>();
		CommandGetAllQuestions command = new CommandGetAllQuestions();
		ModelFacade.getSingleton().queueCommand(command);
	}


	/**
	 * Import CSV File for questions
	 */
	@Override
	public void importFile()
	{
		AlertBar.getInstance().receiveMessage("IMPORT QUIZBOT QUESTIONS");
		container = new FileChooserContainer(new FileChooser(), "Open Quizbot Import File");
		container.load();
		if (container.getSelectedFile() != null)
		{


			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setDialogPane(new DialogPane()
			{
				@Override
				protected Node createDetailsButton()
				{
					CheckBox optOut = new CheckBox();
					optOut.setText("Wipe all existing questions?");
					optOut.selectedProperty().addListener((ov, old_val, new_val) -> wipeTable = optOut.isSelected());
					return optOut;
				}
			});

			alert.setTitle("Confirm Import");
			alert.setContentText("Are you sure you want to import this file?");
			alert.getDialogPane().setExpandableContent(new Group());
			alert.getDialogPane().setExpanded(true);
			alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
			Optional<ButtonType> result = alert.showAndWait();


			if (result.get() == ButtonType.YES)
			{
				/**
				 * If check box to wipe table is called, clear the table before importing.
				 */
				if (wipeTable == true)
				{
					CommandDeleteAllQuestions command = new CommandDeleteAllQuestions();
					ModelFacade.getSingleton().queueCommand(command);
				}

				// Continue with import
				DateModal.getInstance().show();
				if (DateModal.getInstance().getDate() != null)
				{
					CommandImportQuestion command = new CommandImportQuestion(container.getSelectedFile(), DateModal.getInstance().getDate());
					ModelFacade.getSingleton().queueCommand(command);
					AlertBar.getInstance().receiveMessage("QUESTIONS IMPORTED");
				}

			}
			else
			{
				// Cancel import
			}
		}
	}

	/**
	 * Receive Report
	 */
	@Override
	/*
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	public void receiveReport(QualifiedObservableReport report)
	{
		this.questions = ((QuestionListReport) report).getQuestions();
		this.questionsTable.setItems(FXCollections.observableArrayList(questions));

	}


}
