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
import model.AdventureRecord;
import model.CommandAdventureCompleted;
import model.ModelFacade;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.PlayersUncompletedAdventuresReport;

/**
 * @author Joshua McMillen and Abe Lochner
 *
 */
public class ModifyPlayerStateModal extends Modal implements QualifiedObserver
{

	private static ModifyPlayerStateModal instance;
	private PlayerDTO player;
	private ArrayList<AdventureRecord> incompleteAdventures;
	private ArrayList<AdventureRecord> completedAdventures;
	private TableView<AdventureRecord> adventuresTable;

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
		incompleteAdventures = new ArrayList<>();
		completedAdventures = new ArrayList<>();
		adventuresTable = new TableView<>();
		adventuresTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		adventuresTable.setId("AdventuresTable");
		adventuresTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		TableColumn<AdventureRecord, String> adventureDesc = new TableColumn<>("DESC");
		adventureDesc.setCellValueFactory(new PropertyValueFactory<>("adventureDescription"));
		TableColumn<AdventureRecord, String> completionCriteria = new TableColumn<>("CRITERIA");
		completionCriteria.setCellValueFactory(new PropertyValueFactory<>("completionCriteria"));
		adventuresTable.getColumns().addAll(adventureDesc, completionCriteria);

		HBox buttonRow = new HBox();
		buttonRow.setPadding(new Insets(8));
		buttonRow.setAlignment(Pos.CENTER);
		Button completeButton = new Button("Mark Selected Complete");
		completeButton.setOnAction((event) -> markSelectedComplete());
		buttonRow.getChildren().add(completeButton);
		VBox center = new VBox();
		center.setFillWidth(true);
		center.getChildren().addAll(adventuresTable, buttonRow);

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
		for (AdventureRecord adventure : completedAdventures)
		{
			CommandAdventureCompleted command = new CommandAdventureCompleted(player.getPlayerID(), adventure.getQuestID(), adventure.getAdventureID());
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
		for (AdventureRecord record : adventuresTable.getSelectionModel().getSelectedItems())
		{
			completedAdventures.add(record);
			incompleteAdventures.remove(record);
		}
		this.adventuresTable.setItems(FXCollections.observableArrayList(incompleteAdventures));
	}

	/**
	 * @return get the only one of these that should exist
	 */
	public static ModifyPlayerStateModal getInstance()
	{
		if (instance == null)
		{
			instance = new ModifyPlayerStateModal("ModifyPlayerStateModal", "Player's Unfinished Adventures", 600, 400);
		}
		return instance;
	}

	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		this.incompleteAdventures = ((PlayersUncompletedAdventuresReport) report).getAllUncompletedAdventures();
		this.adventuresTable.setItems(FXCollections.observableArrayList(incompleteAdventures));

	}

}
