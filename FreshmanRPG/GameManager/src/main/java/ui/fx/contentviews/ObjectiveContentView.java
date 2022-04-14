package ui.fx.contentviews;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.GameLocationDTO;
import dataENUM.ObjectiveCompletionType;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.ObjectiveRecord;
import model.Command;
import model.CommandDeleteObjective;
import model.CommandDeleteAllQuestsObjectives;
import model.CommandDeleteQuest;
import model.CommandGetAllQuestsAndObjectives;
import model.CommandImportQuestObjective;
import model.ModelFacade;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.QuestRecord;
import model.reports.AllQuestsAndObjectivesReport;
import ui.fx.dialogues.AddObjectiveModal;
import ui.fx.dialogues.AddQuestModal;
import ui.fx.dialogues.EditObjectiveModal;
import ui.fx.dialogues.EditQuestModal;
import ui.fx.framework.AlertBar;
import ui.fx.framework.ContentView;
import ui.fx.framework.FileChooserContainer;

/**
 * @author Josh McMillen, Ben Uleau
 * Workspace content for the objective content (objective menu)
 */
public class ObjectiveContentView extends ContentView implements QualifiedObserver
{
	private static ObjectiveContentView instance;
	private FileChooserContainer container;

	private ArrayList<QuestRecord> quests;
	private TableView<QuestRecord> questsTable;

	private TableView<ObjectiveRecord> objectivesTable;

	private int lastSelectedQuest;
	private final int NO_SELECTED_QUEST = -1;

	private boolean wipeTable = false;


	/**
	 * Gets the container for the File Chooser
	 * @return FileChooser
	 */
	public FileChooserContainer getContainer()
	{
		return container;
	}

	/**
	 * Singleton constructor
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private ObjectiveContentView()
	{
		super("ObjectiveContentView", "Objectives and Quests");

		this.questsTable = new TableView<>();
		this.questsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.questsTable.setId("QuestsTable");

		this.objectivesTable = new TableView<>();
		this.objectivesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.objectivesTable.setId("ObjectivesTable");

		TableColumn questHeader = new TableColumn("QUESTS");

		TableColumn<QuestRecord, Integer> questID = new TableColumn<>("ID");
		questID.setCellValueFactory(new PropertyValueFactory<>("questID"));

		TableColumn<QuestRecord, String> questTitle = new TableColumn<>("TITLE");
		questTitle.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn<QuestRecord, String> questDesc = new TableColumn<>("DESC");
		questDesc.setCellValueFactory(new PropertyValueFactory<>("description"));

		TableColumn<QuestRecord, String> questStart = new TableColumn<>("START");
		questStart.setCellValueFactory(new PropertyValueFactory<>("startDate"));

		TableColumn<QuestRecord, String> questEnd = new TableColumn<>("END");
		questEnd.setCellValueFactory(new PropertyValueFactory<>("endDate"));

		questHeader.getColumns().addAll(questID, questTitle, questDesc, questStart, questEnd);
		this.questsTable.getColumns().addAll(questHeader);


		TableColumn objectiveHeader = new TableColumn("OBJECTIVES");


		TableColumn<ObjectiveRecord, String> objectiveDesc = new TableColumn<>("DESC");
		objectiveDesc.setCellValueFactory(new PropertyValueFactory<>("objectiveDescription"));

		TableColumn<ObjectiveRecord, String> objectiveCompletionType = new TableColumn<>("TYPE");
		objectiveCompletionType.setCellValueFactory(new PropertyValueFactory<>("completionType"));

		TableColumn<ObjectiveRecord, String> objectiveCompletionCriteria = new TableColumn<>("CRITERIA");
		objectiveCompletionCriteria.setCellValueFactory(new PropertyValueFactory<>("completionCriteria"));

		TableColumn<ObjectiveRecord, String> objectiveXP = new TableColumn<>("XP");
		objectiveXP.setCellValueFactory(new PropertyValueFactory<>("experiencePointsGained"));

		objectiveHeader.getColumns().addAll(objectiveDesc, objectiveCompletionType, objectiveCompletionCriteria, objectiveXP);
		this.objectivesTable.getColumns().addAll(objectiveHeader);

		questsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
		{
			if (newSelection != null)
			{
				objectivesTable.getSelectionModel().clearSelection();
				objectivesTable.setItems(FXCollections.observableArrayList(newSelection.getObjectives()));

				lastSelectedQuest = questsTable.getSelectionModel().getSelectedIndex();
			}
		});

		objectivesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
		{
			if (newSelection != null)
			{
				questsTable.getSelectionModel().clearSelection();
			}
		});


		VBox tableContainer = new VBox();
		tableContainer.setFillWidth(true);
		tableContainer.getChildren().addAll(questsTable, objectivesTable);

		this.setCenter(tableContainer);
		this.refresh();
	}

	/**
	 * Add an objective
	 */
	@Override
	public void add()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Objective or Quest");
		alert.setContentText("Do you want to Add a new Objective or Quest?");
		ButtonType objectiveButton = new ButtonType("Objective", ButtonData.YES);
		ButtonType questButton = new ButtonType("Quest", ButtonData.NO);
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(objectiveButton, questButton, cancelButton);
		alert.getDialogPane().lookupButton(objectiveButton).setId("ObjectiveButton");
		alert.getDialogPane().lookupButton(questButton).setId("QuestButton");
		alert.getDialogPane().lookupButton(cancelButton).setId("CancelButton");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get().getText().equals("Objective"))
		{
			AddObjectiveModal.getInstance().show();
		}
		else if (result.get().getText().equals("Quest"))
		{
			AddQuestModal.getInstance().show();
		}

		//Log an action message to the alert bar
		AlertBar.getInstance().receiveMessage("ADD OBJECTIVE OR QUEST");
	}

	/**
	 * Delete a quest or objective
	 */
	@Override
	public void delete()
	{
		QuestRecord targetQuest = this.questsTable.getSelectionModel().getSelectedItem();
		ObjectiveRecord targetObjective = this.objectivesTable.getSelectionModel().getSelectedItem();

		if (targetQuest == null && targetObjective == null)
		{
			AlertBar.getInstance().receiveMessage("MUST SELECT A QUEST OR OBJECTIVE TO DELETE");
		}
		else if (targetQuest != null)
		{
			AlertBar.getInstance().receiveMessage("DELETE QUEST");
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Quest");
			alert.setContentText("Are you sure you want to delete " + targetQuest.getTitle() + "?");
			ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonData.NO);
			alert.getButtonTypes().setAll(yesButton, noButton);
			alert.getDialogPane().lookupButton(yesButton).setId("YesButton");
			alert.getDialogPane().lookupButton(noButton).setId("NoButton");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get().getText().equals(ButtonType.YES.getText()))
			{
				CommandDeleteQuest command = new CommandDeleteQuest(targetQuest.getQuestID());
				lastSelectedQuest = NO_SELECTED_QUEST;
				ModelFacade.getSingleton().queueCommand(command);
				refresh();
				AlertBar.getInstance().receiveMessage("QUEST DELETED");
			}
			else if (result.get().getText().equals(ButtonType.NO.getText()))
			{
				AlertBar.getInstance().receiveMessage("DELETE QUEST");
			}
			else
			{
				AlertBar.getInstance().receiveMessage("TEST");
			}
		}
		else if (targetObjective != null)    //If target quest is null, then objective must not be null
		{
			AlertBar.getInstance().receiveMessage("DELETE OBJECTIVE");
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Objetive");
			alert.setContentText("Are you sure you want to delete " + targetObjective.getObjectiveDescription() + "?");
			ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonData.NO);
			alert.getButtonTypes().setAll(yesButton, noButton);
			alert.getDialogPane().lookupButton(yesButton).setId("YesButton");
			alert.getDialogPane().lookupButton(noButton).setId("NoButton");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get().getText().equals(ButtonType.YES.getText()))
			{
				CommandDeleteObjective command = new CommandDeleteObjective(targetObjective.getQuestID(), targetObjective.getObjectiveID());
				ModelFacade.getSingleton().queueCommand(command);

				objectivesTable.getItems().remove(targetObjective);

				refresh();
				AlertBar.getInstance().receiveMessage("OBJECTIVE DELETED");
			}
			else if (result.get().getText().equals(ButtonType.NO.getText()))
			{
				AlertBar.getInstance().receiveMessage("DELETE OBJECTIVE");
			}
			else
			{
				AlertBar.getInstance().receiveMessage("TEST");
			}

		}
	}

	/**
	 * Edit an objective
	 */
	@Override
	public void edit()
	{
		//Log an action message to the alert bar
		if (this.questsTable.getSelectionModel().getSelectedItem() != null)
		{
			AlertBar.getInstance().receiveMessage("EDIT QUEST");
			QuestRecord questRecord = this.questsTable.getSelectionModel().getSelectedItem();
			EditQuestModal.getInstance().setQuestId(questRecord.getQuestID());
			EditQuestModal.getInstance().setTitle(questRecord.getTitle());
			EditQuestModal.getInstance().setDesc(questRecord.getDescription());
			EditQuestModal.getInstance().setMapOption(questRecord.getMapName());
			EditQuestModal.getInstance().setTrigPos(questRecord.getPos());
			EditQuestModal.getInstance().setXpGained(questRecord.getExperiencePointsGained());
			EditQuestModal.getInstance().setNumObjectives(questRecord.getObjectivesForFulfillment());
			EditQuestModal.getInstance().setQuestCompletionActionType(questRecord.getCompletionActionType());
			EditQuestModal.getInstance().setQuestCompletionActionParam(questRecord.getCompletionActionParameter());
			EditQuestModal.getInstance().setStartDate(new java.sql.Date(questRecord.getStartDate().getTime()).toLocalDate());
			EditQuestModal.getInstance().setEndDate(new java.sql.Date(questRecord.getEndDate().getTime()).toLocalDate());
			EditQuestModal.getInstance().show();
		}
		else if (this.objectivesTable.getSelectionModel().getSelectedItem() != null)
		{
			AlertBar.getInstance().receiveMessage("EDIT OBJECTIVE");
			ObjectiveRecord objectiveRecord = this.objectivesTable.getSelectionModel().getSelectedItem();
			EditObjectiveModal.getInstance().setObjectiveId(objectiveRecord.getObjectiveId());
			EditObjectiveModal.getInstance().getDesc().setText(objectiveRecord.getObjectiveDescription());
			EditObjectiveModal.getInstance().getObjectiveCompletionType().setValue(objectiveRecord.getCompletionType());
			EditObjectiveModal.getInstance().getQuestName().setValue(getQuestNameById(objectiveRecord.getQuestId()));
			EditObjectiveModal.getInstance().getXpGained().getValueFactory().setValue(objectiveRecord.getExperiencePointsGained());
			if (objectiveRecord.getCompletionType().equals(ObjectiveCompletionType.REAL_LIFE) || objectiveRecord.getCompletionType().equals(ObjectiveCompletionType.CHAT))
			{
				CriteriaStringDTO criteria = (CriteriaStringDTO) objectiveRecord.getCompletionCriteria();
				EditObjectiveModal.getInstance().getMapOptions().setValue(null);
				EditObjectiveModal.getInstance().getTrigPosX().getValueFactory().setValue(0);
				EditObjectiveModal.getInstance().getTrigPosY().getValueFactory().setValue(0);
				EditObjectiveModal.getInstance().getObjectiveParam().setText(criteria.getString());
				EditObjectiveModal.getInstance().getRequiredKey().setText(null);
				EditObjectiveModal.getInstance().getDoubloonsRequired().getValueFactory().setValue(0);
			}
			else if (objectiveRecord.getCompletionType().equals(ObjectiveCompletionType.KEYSTROKE))
			{
				CriteriaStringDTO criteria = (CriteriaStringDTO) (objectiveRecord.getCompletionCriteria());
				EditObjectiveModal.getInstance().getMapOptions().setValue(null);
				EditObjectiveModal.getInstance().getTrigPosX().getValueFactory().setValue(0);
				EditObjectiveModal.getInstance().getTrigPosY().getValueFactory().setValue(0);
				EditObjectiveModal.getInstance().getObjectiveParam().setText(null);
				EditObjectiveModal.getInstance().getRequiredKey().setText(criteria.getString());
				EditObjectiveModal.getInstance().getDoubloonsRequired().getValueFactory().setValue(0);
			}
			else if (objectiveRecord.getCompletionType().equals(ObjectiveCompletionType.KEYSTROKE))
			{
				@SuppressWarnings("unchecked")
				CriteriaStringDTO criteria = (CriteriaStringDTO) (objectiveRecord.getCompletionCriteria());
				EditObjectiveModal.getInstance().getMapOptions().setValue(null);
				EditObjectiveModal.getInstance().getTrigPosX().getValueFactory().setValue(0);
				EditObjectiveModal.getInstance().getTrigPosY().getValueFactory().setValue(0);
				EditObjectiveModal.getInstance().getObjectiveParam().setText(null);
				EditObjectiveModal.getInstance().getRequiredKey().setText(criteria.toString());
				EditObjectiveModal.getInstance().getRequiredKey2().setText(criteria.toString());
				EditObjectiveModal.getInstance().getDoubloonsRequired().getValueFactory().setValue(0);
			}
			else if (objectiveRecord.getCompletionType().equals(ObjectiveCompletionType.DOUBLOONS))
			{
				CriteriaIntegerDTO criteria = (CriteriaIntegerDTO) (objectiveRecord.getCompletionCriteria());
				EditObjectiveModal.getInstance().getMapOptions().setValue(null);
				EditObjectiveModal.getInstance().getTrigPosX().getValueFactory().setValue(0);
				EditObjectiveModal.getInstance().getTrigPosY().getValueFactory().setValue(0);
				EditObjectiveModal.getInstance().getObjectiveParam().setText(null);
				EditObjectiveModal.getInstance().getRequiredKey().setText(null);
				EditObjectiveModal.getInstance().getDoubloonsRequired().getValueFactory().setValue(criteria.getTarget());
			}
			else if (objectiveRecord.getCompletionType().equals(ObjectiveCompletionType.MOVEMENT))
			{
				GameLocationDTO criteria = (GameLocationDTO) (objectiveRecord.getCompletionCriteria());
				EditObjectiveModal.getInstance().getMapOptions().setValue(criteria.getMapName());
				EditObjectiveModal.getInstance().getTrigPosX().getValueFactory().setValue(criteria.getPosition().getColumn());
				EditObjectiveModal.getInstance().getTrigPosY().getValueFactory().setValue(criteria.getPosition().getRow());
				EditObjectiveModal.getInstance().getObjectiveParam().setText(null);
				EditObjectiveModal.getInstance().getRequiredKey().setText(null);
				EditObjectiveModal.getInstance().getDoubloonsRequired().getValueFactory().setValue(0);

			}
			EditObjectiveModal.getInstance().show();
		}
		else
		{
			AlertBar.getInstance().receiveMessage("SELECT OBJECTIVE OR QUEST TO EDIT");
		}
	}

	private String getQuestNameById(int questId)
	{
		for (QuestRecord record : questsTable.getItems())
		{
			if (record.getQuestID() == questId)
			{
				return record.getTitle();
			}
		}
		return null;
	}

	/**
	 * Filter objectives
	 */
	@Override
	public void filter(String filter)
	{
		//Log an action message to the alert bar
		AlertBar.getInstance().receiveMessage("FILTER OBJECTIVES AND QUESTS BY \"" + filter.toUpperCase() + "\"");
	}

	/**
	 * Refresh quests
	 */
	@Override
	public void refresh()
	{
		AlertBar.getInstance().receiveMessage("REFRESH OBJECTIVES AND QUESTS VIEW");
		this.quests = new ArrayList<>();
		new ArrayList<ObjectiveRecord>();
		CommandGetAllQuestsAndObjectives command = new CommandGetAllQuestsAndObjectives();
		ModelFacade.getSingleton().queueCommand(command);

	}

	/**
	 * @see ui.fx.framework.ContentView#importFile()
	 */
	/**
	 * Import CSV File for Objectives and Quests
	 */
	@Override
	public void importFile()
	{
		AlertBar.getInstance().receiveMessage("IMPORT OBJECTIVES AND/OR QUESTS");
		container = new FileChooserContainer(new FileChooser(), "Open Objective/Quest Import File");
		container.load();
		if (container.getSelectedFile() != null)
		{
			Dialog<ButtonType> dialog = new Dialog<>();

			dialog.setDialogPane(new DialogPane()
			{
				@Override
				protected Node createDetailsButton()
				{
					CheckBox optOut = new CheckBox();
					optOut.setText("Wipe all existing quests and objectives?");
					optOut.selectedProperty().addListener((ov, old_val, new_val) -> wipeTable = optOut.isSelected());
					return optOut;
				}
			});
			dialog.setTitle("Select Semester Start Date");
			dialog.setHeaderText("Select the start date of the semester and then click OK to import quests and objectives.");
			dialog.getDialogPane().setExpandableContent(new Group());
			dialog.getDialogPane().setExpanded(true);
			//Adds ship icon to top of dialog
			Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			stage.getIcons().add(new Image("res/ship.png"));

			//Create Date Picker and add it to dialog
			DatePicker startDate = new DatePicker();
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));
			grid.add(new Label("Semester Start Date:"), 0, 0);
			grid.add(startDate, 1, 0);

			dialog.getDialogPane().setContent(grid);
			dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
			Optional<ButtonType> result = dialog.showAndWait();

			if (result.get() == ButtonType.OK)
			{

				/**
				 * If check box to wipe table is called, clear the table before importing.
				 */
				if (wipeTable == true)
				{
					CommandDeleteAllQuestsObjectives command = new CommandDeleteAllQuestsObjectives();
					ModelFacade.getSingleton().queueCommand(command);
				}
				// Continue with import
				LocalDate date = startDate.getValue();
				Command command = new CommandImportQuestObjective(container.getSelectedFile(), date);
				ModelFacade.getSingleton().queueCommand(command);
				wipeTable = false;
			}
			else
			{
				// Cancel import
			}
		}
	}

	/**
	 * Recieve the all quests and objectives report
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		this.objectivesTable.setItems(null);
		this.quests = ((AllQuestsAndObjectivesReport) report).getQuestInfo();
		this.questsTable.setItems(FXCollections.observableArrayList(quests));
		if (lastSelectedQuest != NO_SELECTED_QUEST)
		{
			this.questsTable.getSelectionModel().select(lastSelectedQuest);
		}
	}


	/**
	 * @return the singleton instance of the objective content
	 */
	public static ObjectiveContentView getInstance()
	{
		if (instance == null)
		{
			instance = new ObjectiveContentView();
		}
		return instance;
	}

	/**
	 * @return QuestRecord Table Object
	 */
	public TableView<QuestRecord> getQuestTable()
	{
		return this.questsTable;
	}

	/**
	 * @return ObjectiveRecord Table Object
	 */
	public TableView<ObjectiveRecord> getObjectiveTable()
	{
		return this.objectivesTable;
	}

}
