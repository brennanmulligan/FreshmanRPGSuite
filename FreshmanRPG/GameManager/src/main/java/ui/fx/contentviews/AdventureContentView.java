package ui.fx.contentviews;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.GameLocationDTO;
import dataENUM.AdventureCompletionType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.AdventureRecord;
import model.Command;
import model.CommandDeleteAdventure;
import model.CommandDeleteAllQuestsAdventures;
import model.CommandDeleteQuest;
import model.CommandGetAllQuestsAndAdventures;
import model.CommandImportQuestAdventure;
import model.ModelFacade;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.QuestRecord;
import model.reports.AllQuestsAndAdventuresReport;
import ui.fx.dialogues.AddAdventureModal;
import ui.fx.dialogues.AddQuestModal;
import ui.fx.dialogues.EditAdventureModal;
import ui.fx.dialogues.EditQuestModal;
import ui.fx.framework.AlertBar;
import ui.fx.framework.ContentView;
import ui.fx.framework.FileChooserContainer;

/**
 * @author Josh McMillen, Ben Uleau
 * Workspace content for the adventure content (adventure menu)
 */
public class AdventureContentView extends ContentView implements QualifiedObserver
{
	private static AdventureContentView instance;
	private FileChooserContainer container;

	private ArrayList<QuestRecord> quests;
	private TableView<QuestRecord> questsTable;

	private TableView<AdventureRecord> adventuresTable;

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
	private AdventureContentView()
	{
		super("AdventureContentView", "Adventures and Quests");

		this.questsTable = new TableView<>();
		this.questsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.questsTable.setId("QuestsTable");

		this.adventuresTable = new TableView<>();
		this.adventuresTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.adventuresTable.setId("AdventuresTable");

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


		TableColumn adventureHeader = new TableColumn("ADVENTURES");


		TableColumn<AdventureRecord, String> adventureDesc = new TableColumn<>("DESC");
		adventureDesc.setCellValueFactory(new PropertyValueFactory<>("adventureDescription"));

		TableColumn<AdventureRecord, String> adventureCompletionType = new TableColumn<>("TYPE");
		adventureCompletionType.setCellValueFactory(new PropertyValueFactory<>("completionType"));

		TableColumn<AdventureRecord, String> adventureCompletionCriteria = new TableColumn<>("CRITERIA");
		adventureCompletionCriteria.setCellValueFactory(new PropertyValueFactory<>("completionCriteria"));

		TableColumn<AdventureRecord, String> adventureXP = new TableColumn<>("XP");
		adventureXP.setCellValueFactory(new PropertyValueFactory<>("experiencePointsGained"));

		adventureHeader.getColumns().addAll(adventureDesc, adventureCompletionType, adventureCompletionCriteria, adventureXP);
		this.adventuresTable.getColumns().addAll(adventureHeader);

		questsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
		{
			if (newSelection != null)
			{
				adventuresTable.getSelectionModel().clearSelection();
				adventuresTable.setItems(FXCollections.observableArrayList(newSelection.getAdventures()));

				lastSelectedQuest = questsTable.getSelectionModel().getSelectedIndex();
			}
		});

		adventuresTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->
		{
			if (newSelection != null)
			{
				questsTable.getSelectionModel().clearSelection();
			}
		});


		VBox tableContainer = new VBox();
		tableContainer.setFillWidth(true);
		tableContainer.getChildren().addAll(questsTable, adventuresTable);

		this.setCenter(tableContainer);
		this.refresh();
	}

	/**
	 * Add an adventure
	 */
	@Override
	public void add()
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add Adventure or Quest");
		alert.setContentText("Do you want to Add a new Adventure or Quest?");
		ButtonType adventureButton = new ButtonType("Adventure", ButtonData.YES);
		ButtonType questButton = new ButtonType("Quest", ButtonData.NO);
		ButtonType cancelButton = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		alert.getButtonTypes().setAll(adventureButton, questButton, cancelButton);
		alert.getDialogPane().lookupButton(adventureButton).setId("AdventureButton");
		alert.getDialogPane().lookupButton(questButton).setId("QuestButton");
		alert.getDialogPane().lookupButton(cancelButton).setId("CancelButton");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get().getText().equals("Adventure"))
		{
			AddAdventureModal.getInstance().show();
		}
		else if (result.get().getText().equals("Quest"))
		{
			AddQuestModal.getInstance().show();
		}

		//Log an action message to the alert bar
		AlertBar.getInstance().receiveMessage("ADD ADVENTURE OR QUEST");
	}

	/**
	 * Delete a quest or adventure
	 */
	@Override
	public void delete()
	{
		QuestRecord targetQuest = this.questsTable.getSelectionModel().getSelectedItem();
		AdventureRecord targetAdventure = this.adventuresTable.getSelectionModel().getSelectedItem();

		if (targetQuest == null && targetAdventure == null)
		{
			AlertBar.getInstance().receiveMessage("MUST SELECT A QUEST OR ADVENTURE TO DELETE");
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
		else if (targetAdventure != null)    //If target quest is null, then adventure must not be null
		{
			AlertBar.getInstance().receiveMessage("DELETE ADVENTURE");
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Adventure");
			alert.setContentText("Are you sure you want to delete " + targetAdventure.getAdventureDescription() + "?");
			ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonData.NO);
			alert.getButtonTypes().setAll(yesButton, noButton);
			alert.getDialogPane().lookupButton(yesButton).setId("YesButton");
			alert.getDialogPane().lookupButton(noButton).setId("NoButton");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get().getText().equals(ButtonType.YES.getText()))
			{
				CommandDeleteAdventure command = new CommandDeleteAdventure(targetAdventure.getQuestID(), targetAdventure.getAdventureID());
				ModelFacade.getSingleton().queueCommand(command);

				adventuresTable.getItems().remove(targetAdventure);

				refresh();
				AlertBar.getInstance().receiveMessage("ADVENTURE DELETED");
			}
			else if (result.get().getText().equals(ButtonType.NO.getText()))
			{
				AlertBar.getInstance().receiveMessage("DELETE ADVENTURE");
			}
			else
			{
				AlertBar.getInstance().receiveMessage("TEST");
			}

		}
	}

	/**
	 * Edit an adventure
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
			EditQuestModal.getInstance().setNumAdventures(questRecord.getAdventuresForFulfillment());
			EditQuestModal.getInstance().setQuestCompletionActionType(questRecord.getCompletionActionType());
			EditQuestModal.getInstance().setQuestCompletionActionParam(questRecord.getCompletionActionParameter());
			EditQuestModal.getInstance().setStartDate(new java.sql.Date(questRecord.getStartDate().getTime()).toLocalDate());
			EditQuestModal.getInstance().setEndDate(new java.sql.Date(questRecord.getEndDate().getTime()).toLocalDate());
			EditQuestModal.getInstance().show();
		}
		else if (this.adventuresTable.getSelectionModel().getSelectedItem() != null)
		{
			AlertBar.getInstance().receiveMessage("EDIT ADVENTURE");
			AdventureRecord adventureRecord = this.adventuresTable.getSelectionModel().getSelectedItem();
			EditAdventureModal.getInstance().setAdventureId(adventureRecord.getAdventureId());
			EditAdventureModal.getInstance().getDesc().setText(adventureRecord.getAdventureDescription());
			EditAdventureModal.getInstance().getAdventureCompletionType().setValue(adventureRecord.getCompletionType());
			EditAdventureModal.getInstance().getQuestName().setValue(getQuestNameById(adventureRecord.getQuestId()));
			EditAdventureModal.getInstance().getXpGained().getValueFactory().setValue(adventureRecord.getExperiencePointsGained());
			if (adventureRecord.getCompletionType().equals(AdventureCompletionType.REAL_LIFE) || adventureRecord.getCompletionType().equals(AdventureCompletionType.CHAT))
			{
				CriteriaStringDTO criteria = (CriteriaStringDTO) adventureRecord.getCompletionCriteria();
				EditAdventureModal.getInstance().getMapOptions().setValue(null);
				EditAdventureModal.getInstance().getTrigPosX().getValueFactory().setValue(0);
				EditAdventureModal.getInstance().getTrigPosY().getValueFactory().setValue(0);
				EditAdventureModal.getInstance().getAdventureParam().setText(criteria.getString());
				EditAdventureModal.getInstance().getRequiredKey().setText(null);
				EditAdventureModal.getInstance().getKnowledgePointsRequired().getValueFactory().setValue(0);
			}
			else if (adventureRecord.getCompletionType().equals(AdventureCompletionType.KEYSTROKE))
			{
				CriteriaStringDTO criteria = (CriteriaStringDTO) (adventureRecord.getCompletionCriteria());
				EditAdventureModal.getInstance().getMapOptions().setValue(null);
				EditAdventureModal.getInstance().getTrigPosX().getValueFactory().setValue(0);
				EditAdventureModal.getInstance().getTrigPosY().getValueFactory().setValue(0);
				EditAdventureModal.getInstance().getAdventureParam().setText(null);
				EditAdventureModal.getInstance().getRequiredKey().setText(criteria.getString());
				EditAdventureModal.getInstance().getKnowledgePointsRequired().getValueFactory().setValue(0);
			}
			else if (adventureRecord.getCompletionType().equals(AdventureCompletionType.KEYSTROKE))
			{
				@SuppressWarnings("unchecked")
				CriteriaStringDTO criteria = (CriteriaStringDTO) (adventureRecord.getCompletionCriteria());
				EditAdventureModal.getInstance().getMapOptions().setValue(null);
				EditAdventureModal.getInstance().getTrigPosX().getValueFactory().setValue(0);
				EditAdventureModal.getInstance().getTrigPosY().getValueFactory().setValue(0);
				EditAdventureModal.getInstance().getAdventureParam().setText(null);
				EditAdventureModal.getInstance().getRequiredKey().setText(criteria.toString());
				EditAdventureModal.getInstance().getRequiredKey2().setText(criteria.toString());
				EditAdventureModal.getInstance().getKnowledgePointsRequired().getValueFactory().setValue(0);
			}
			else if (adventureRecord.getCompletionType().equals(AdventureCompletionType.KNOWLEDGE_POINTS))
			{
				CriteriaIntegerDTO criteria = (CriteriaIntegerDTO) (adventureRecord.getCompletionCriteria());
				EditAdventureModal.getInstance().getMapOptions().setValue(null);
				EditAdventureModal.getInstance().getTrigPosX().getValueFactory().setValue(0);
				EditAdventureModal.getInstance().getTrigPosY().getValueFactory().setValue(0);
				EditAdventureModal.getInstance().getAdventureParam().setText(null);
				EditAdventureModal.getInstance().getRequiredKey().setText(null);
				EditAdventureModal.getInstance().getKnowledgePointsRequired().getValueFactory().setValue(criteria.getTarget());
			}
			else if (adventureRecord.getCompletionType().equals(AdventureCompletionType.MOVEMENT))
			{
				GameLocationDTO criteria = (GameLocationDTO) (adventureRecord.getCompletionCriteria());
				EditAdventureModal.getInstance().getMapOptions().setValue(criteria.getMapName());
				EditAdventureModal.getInstance().getTrigPosX().getValueFactory().setValue(criteria.getPosition().getColumn());
				EditAdventureModal.getInstance().getTrigPosY().getValueFactory().setValue(criteria.getPosition().getRow());
				EditAdventureModal.getInstance().getAdventureParam().setText(null);
				EditAdventureModal.getInstance().getRequiredKey().setText(null);
				EditAdventureModal.getInstance().getKnowledgePointsRequired().getValueFactory().setValue(0);

			}
			EditAdventureModal.getInstance().show();
		}
		else
		{
			AlertBar.getInstance().receiveMessage("SELECT ADVENTURE OR QUEST TO EDIT");
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
	 * Filter adventures
	 */
	@Override
	public void filter(String filter)
	{
		//Log an action message to the alert bar
		AlertBar.getInstance().receiveMessage("FILTER ADVENTURES AND QUESTS BY \"" + filter.toUpperCase() + "\"");
	}

	/**
	 * Refresh quests
	 */
	@Override
	public void refresh()
	{
		AlertBar.getInstance().receiveMessage("REFRESH ADVENTURES AND QUESTS VIEW");
		this.quests = new ArrayList<>();
		new ArrayList<AdventureRecord>();
		CommandGetAllQuestsAndAdventures command = new CommandGetAllQuestsAndAdventures();
		ModelFacade.getSingleton().queueCommand(command);

	}

	/**
	 * @see ui.fx.framework.ContentView#importFile()
	 */
	/**
	 * Import CSV File for Adventures and Quests
	 */
	@Override
	public void importFile()
	{
		AlertBar.getInstance().receiveMessage("IMPORT ADVENTURES AND/OR QUESTS");
		container = new FileChooserContainer(new FileChooser(), "Open Adventure/Quest Import File");
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
					optOut.setText("Wipe all existing quests and adventures?");
					optOut.selectedProperty().addListener((ov, old_val, new_val) -> wipeTable = optOut.isSelected());
					return optOut;
				}
			});
			dialog.setTitle("Select Semester Start Date");
			dialog.setHeaderText("Select the start date of the semester and then click OK to import quests and adventures.");
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
					CommandDeleteAllQuestsAdventures command = new CommandDeleteAllQuestsAdventures();
					ModelFacade.getSingleton().queueCommand(command);
				}
				// Continue with import
				LocalDate date = startDate.getValue();
				Command command = new CommandImportQuestAdventure(container.getSelectedFile(), date);
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
	 * Recieve the all quests and adventures report
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		this.adventuresTable.setItems(null);
		this.quests = ((AllQuestsAndAdventuresReport) report).getQuestInfo();
		this.questsTable.setItems(FXCollections.observableArrayList(quests));
		if (lastSelectedQuest != NO_SELECTED_QUEST)
		{
			this.questsTable.getSelectionModel().select(lastSelectedQuest);
		}
	}


	/**
	 * @return the singleton instance of the adventure content
	 */
	public static AdventureContentView getInstance()
	{
		if (instance == null)
		{
			instance = new AdventureContentView();
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
	 * @return AdventureRecord Table Object
	 */
	public TableView<AdventureRecord> getAdventureTable()
	{
		return this.adventuresTable;
	}

}
