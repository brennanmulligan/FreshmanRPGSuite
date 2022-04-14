package ui.fx.dialogues;

import java.util.ArrayList;

import dataDTO.PlayerDTO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.ObjectiveRecord;
import model.CommandObjectiveCompleted;
import model.ModelFacade;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.PlayersUncompletedObjectivesReport;

/**
 * @author Joshua McMillen and Abe Lochner
 *
 */
public class ModifyPlayerStateModal extends Modal implements QualifiedObserver
{

	private static ModifyPlayerStateModal instance;
	private PlayerDTO player;
	private ArrayList<ObjectiveRecord> incompleteObjectives;
	private ArrayList<ObjectiveRecord> completedObjectives;
	private TableView<ObjectiveRecord> objectivesTable;

	/**
	 * @param id
	 * @param label
	 * @param height
	 * @param width
	 */
	@SuppressWarnings("unchecked")
	private ModifyPlayerStateModal(String id, String label, int height, int width)
	{
		super(id, label, height, width);
		incompleteObjectives = new ArrayList<>();
		completedObjectives = new ArrayList<>();
		objectivesTable = new TableView<>();
		objectivesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		objectivesTable.setId("ObjectivesTable");
		objectivesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		TableColumn<ObjectiveRecord, String> objectiveDesc = new TableColumn<>("DESC");
		objectiveDesc.setCellValueFactory(new PropertyValueFactory<>("objectiveDescription"));
		TableColumn<ObjectiveRecord, String> completionCriteria = new TableColumn<>("CRITERIA");
		completionCriteria.setCellValueFactory(new PropertyValueFactory<>("completionCriteria"));
		objectivesTable.getColumns().addAll(objectiveDesc, completionCriteria);

		HBox buttonRow = new HBox();
		buttonRow.setPadding(new Insets(8));
		buttonRow.setAlignment(Pos.CENTER);
		Button completeButton = new Button("Mark Selected Complete");
		completeButton.setOnAction((event) -> markSelectedComplete());
		buttonRow.getChildren().add(completeButton);
		VBox center = new VBox();
		center.setFillWidth(true);
		center.getChildren().addAll(objectivesTable, buttonRow);

		modal.setCenter(center);
	}


	/**
	 * @param player the player we are editing
	 */
	public void setPlayer(PlayerDTO player)
	{
		this.player = player;
	}


	/**
	 * @see ui.fx.dialogues.Modal#save()
	 */
	@Override
	public void save()
	{
		for (ObjectiveRecord objective : completedObjectives)
		{
			CommandObjectiveCompleted command = new CommandObjectiveCompleted(player.getPlayerID(), objective.getQuestID(), objective.getObjectiveID());
			ModelFacade.getSingleton().queueCommand(command);
		}
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

	private void markSelectedComplete()
	{
		for (ObjectiveRecord record : objectivesTable.getSelectionModel().getSelectedItems())
		{
			completedObjectives.add(record);
			incompleteObjectives.remove(record);
		}
		this.objectivesTable.setItems(FXCollections.observableArrayList(incompleteObjectives));
	}

	/**
	 * @return get the only one of these that should exist
	 */
	public static ModifyPlayerStateModal getInstance()
	{
		if (instance == null)
		{
			instance = new ModifyPlayerStateModal("ModifyPlayerStateModal", "Player's Unfinished Objectives", 600, 400);
		}
		return instance;
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		this.incompleteObjectives = ((PlayersUncompletedObjectivesReport) report).getAllUncompletedObjectives();
		this.objectivesTable.setItems(FXCollections.observableArrayList(incompleteObjectives));

	}

}
