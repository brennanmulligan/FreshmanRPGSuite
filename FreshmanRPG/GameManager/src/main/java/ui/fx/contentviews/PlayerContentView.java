package ui.fx.contentviews;

import java.util.ArrayList;
import java.util.Optional;

import dataDTO.PlayerDTO;
import datatypes.Crew;
import datatypes.Major;
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
import model.Command;
import model.CommandDeleteAllPlayers;
import model.CommandDeletePlayer;
import model.CommandGetAllPlayers;
import model.CommandGetUncompletedAdventuresForPlayer;
import model.CommandImportPlayer;
import model.ModelFacade;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.AllPlayersReport;
import ui.fx.dialogues.AddPlayerModal;
import ui.fx.dialogues.EditPlayerModal;
import ui.fx.dialogues.ModifyPlayerStateModal;
import ui.fx.framework.AlertBar;
import ui.fx.framework.ContentView;
import ui.fx.framework.FileChooserContainer;

/**
 * @author Josh McMillen, Ben Uleau Workspace content for the player content
 *         (player menu)
 */
public class PlayerContentView extends ContentView implements QualifiedObserver
{
	private static PlayerContentView instance;

	private FileChooserContainer container;

	private ArrayList<PlayerDTO> players;
	private TableView<PlayerDTO> playersTable;
	private boolean wipeTable = false;

	/**
	 * Singleton constructor
	 */
	@SuppressWarnings("unchecked")
	private PlayerContentView()
	{
		super("PlayerContentView", "Players");
		this.playersTable = new TableView<>();
		this.playersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.playersTable.setId("PlayersTable");

		TableColumn<PlayerDTO, Integer> playerID = new TableColumn<>("ID");
		playerID.setCellValueFactory(new PropertyValueFactory<>("playerId"));

		TableColumn<PlayerDTO, String> playerName = new TableColumn<>("NAME");
		playerName.setCellValueFactory(new PropertyValueFactory<>("playerName"));

		TableColumn<PlayerDTO, Crew> playerCrew = new TableColumn<>("CREW");
		playerCrew.setCellValueFactory(new PropertyValueFactory<>("crew"));

		TableColumn<PlayerDTO, Major> playerMajor = new TableColumn<>("MAJOR");
		playerMajor.setCellValueFactory(new PropertyValueFactory<>("major"));

		TableColumn<PlayerDTO, Integer> playerSection = new TableColumn<>("SECTION");
		playerSection.setCellValueFactory(new PropertyValueFactory<>("sectionId"));

		this.playersTable.getColumns().addAll(playerID, playerName, playerCrew, playerMajor, playerSection);
		this.setCenter(this.playersTable);
		this.refresh();
	}

	/**
	 * @return the singleton instance of the player content
	 */
	public static PlayerContentView getInstance()
	{
		if (instance == null)
		{
			instance = new PlayerContentView();
		}
		return instance;
	}

	/**
	 * @return PlayerDTO Table Object
	 */
	public TableView<PlayerDTO> getPlayersTable()
	{
		return this.playersTable;
	}

	/**
	 * Add players
	 */
	@Override
	public void add()
	{
		AlertBar.getInstance().receiveMessage("ADD PLAYER");
		AddPlayerModal.getInstance().show();
	}

	/**
	 * Delete a player
	 */
	@Override
	public void delete()
	{
		PlayerDTO targetedPlayer = this.playersTable.getSelectionModel().getSelectedItem();

		if (targetedPlayer == null)
		{
			AlertBar.getInstance().receiveMessage("MUST SELECT PLAYER TO DELETE");
		}
		else
		{

			AlertBar.getInstance().receiveMessage("DELETE PLAYER");
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Player");
			alert.setContentText("Are you sure you want to delete " + targetedPlayer.getPlayerName() + " ?");
			ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonData.NO);
			alert.getButtonTypes().setAll(yesButton, noButton);
			alert.getDialogPane().lookupButton(yesButton).setId("YesButton");
			alert.getDialogPane().lookupButton(noButton).setId("NoButton");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get().getText().equals(ButtonType.YES.getText()))
			{
				CommandDeletePlayer command = new CommandDeletePlayer(targetedPlayer.getPlayerID());
				ModelFacade.getSingleton().queueCommand(command);
				refresh();
				AlertBar.getInstance().receiveMessage("PLAYER DELETED");
			}
			else if (result.get().getText().equals(ButtonType.NO.getText()))
			{
				AlertBar.getInstance().receiveMessage("DELETE PLAYER");
			}
			else
			{
				AlertBar.getInstance().receiveMessage("TEST");
				System.out.println(result.get().getText());
				System.out.println(ButtonType.YES.toString());
			}

		}
	}

	/**
	 * Edit a player
	 */
	@Override
	public void edit()
	{
		AlertBar.getInstance().receiveMessage("EDIT PLAYER");
		PlayerDTO player = this.playersTable.getSelectionModel().getSelectedItem();
		if (player == null)
		{
			AlertBar.getInstance().receiveMessage("Select a player in order to edit it");
		}
		else
		{
			EditPlayerModal.getInstance().setPlayerName(player.getPlayerName());
			EditPlayerModal.getInstance().setPlayerSectionId(Integer.toString(player.getSection()));
			EditPlayerModal.getInstance().setPlayerNewPassword(player.getPlayerPassword());
			EditPlayerModal.getInstance().setPlayerConfirmPassword(player.getPlayerPassword());
			EditPlayerModal.getInstance().setPlayerMajor(player.getMajor());
			EditPlayerModal.getInstance().setPlayerCrew(player.getCrew());
			EditPlayerModal.getInstance().setOther(player.getAppearanceType(), player.getKnowledgePoints(),
					player.getExperiencePoints(), player.getPlayerID());
			EditPlayerModal.getInstance().show();
		}
	}

	/**
	 * Filter players
	 */
	@Override
	public void filter(String filter)
	{
		AlertBar.getInstance().receiveMessage("FILTER PLAYERS BY \"" + filter.toUpperCase() + "\"");
	}

	/**
	 * Refresh players
	 */
	@Override
	public void refresh()
	{
		AlertBar.getInstance().receiveMessage("REFRESH PLAYERS VIEW");
		this.players = new ArrayList<>();
		CommandGetAllPlayers command = new CommandGetAllPlayers();
		ModelFacade.getSingleton().queueCommand(command);
	}

	/**
	 * Import CSV File for Players
	 */
	@Override
	public void importFile()
	{
		AlertBar.getInstance().receiveMessage("IMPORT PLAYERS");
		container = new FileChooserContainer(new FileChooser(), "Open Player Import File");
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
					optOut.setText("Wipe all existing players?");
					optOut.selectedProperty().addListener((ov, old_val, new_val) -> wipeTable = optOut.isSelected());
					return optOut;
				}
			});

			alert.setTitle("Confirm");
			alert.setContentText("Are you sure you want to import this file?");
			alert.getDialogPane().setExpandableContent(new Group());
			alert.getDialogPane().setExpanded(true);
			ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonData.NO);
			alert.getButtonTypes().setAll(yesButton, noButton);
			alert.getDialogPane().lookupButton(yesButton).setId("YesButton");
			alert.getDialogPane().lookupButton(noButton).setId("NoButton");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == yesButton)
			{

				/**
				 * If check box to wipe table is called, clear the table before importing.
				 */
				if (wipeTable == true)
				{
					CommandDeleteAllPlayers command = new CommandDeleteAllPlayers();
					ModelFacade.getSingleton().queueCommand(command);
				}

				Command command = new CommandImportPlayer(this.container.getSelectedFile());
				ModelFacade.getSingleton().queueCommand(command);
				wipeTable=false;
			}
			else
			{
				// Cancel Import
			}
		}
	}

	/**
	 * Receive report
	 *
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		this.players = ((AllPlayersReport) report).getPlayerInfo();
		this.playersTable.setItems(FXCollections.observableArrayList(players));
	}

	/**
	 * @return the table container
	 */
	public FileChooserContainer getContainer()
	{
		return container;
	}

	/**
	 * Changes the state of an adventure for a specific player
	 *
	 * @param dto
	 *            player record
	 */
	public void changeAdventureStatusForPlayer(PlayerDTO dto)
	{
		AlertBar.getInstance().receiveMessage("MANAGE USER'S QUESTS");
		ModifyPlayerStateModal.getInstance().setPlayer(dto);
		CommandGetUncompletedAdventuresForPlayer command = new CommandGetUncompletedAdventuresForPlayer(
				dto.getPlayerID());
		ModelFacade.getSingleton().queueCommand(command);
		ModifyPlayerStateModal.getInstance().show();

	}
}
