package ui.fx.contentviews;

import java.util.ArrayList;
import java.util.Optional;

import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.InteractableItemActionParameter;
import dataDTO.InteractableItemDTO;
import dataENUM.InteractableItemActionType;
import datatypes.Position;
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
import model.CommandDeleteInteractableObject;
import model.CommandGetAllInteractableObjects;
import model.ModelFacade;
import model.QualifiedObservableReport;
import model.QualifiedObserver;
import model.reports.ObjectListReport;
import ui.fx.dialogues.AddInteractableObjectModal;
import ui.fx.dialogues.EditInteractableObjectModal;
import ui.fx.framework.AlertBar;
import ui.fx.framework.ContentView;
import ui.fx.framework.FileChooserContainer;

/**
 * Workspace content for the Interactable objects content (Interactable objects menu)
 * @author Benjamin Uleau,Mohammed Almaslamani
 */
public class InteractableObjectContentView extends ContentView implements QualifiedObserver
{
	private static InteractableObjectContentView instance;
	private FileChooserContainer container;
	private ArrayList<InteractableItemDTO> items;
	private boolean wipeTable = false;

	private TableView<InteractableItemDTO> itemsTable;

	/**
	 * Singleton constructor
	 */
	@SuppressWarnings("unchecked")
	private InteractableObjectContentView()
	{
		super("InteractableObjectContentView", "Interactable objects");
		//TABLE STUFF HERE
		//int id, string name, Position pos, InteractableItemActionType actionType,
		//InteractableItemActionParameter actionParam, String mapName

		this.itemsTable = new TableView<>();
		this.itemsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.itemsTable.setId("InteractableObjectsTable");

		TableColumn<InteractableItemDTO, Integer> itemID = new TableColumn<>("ID");
		itemID.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<InteractableItemDTO, String> name = new TableColumn<>("NAME");
		name.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<InteractableItemDTO, Position> position = new TableColumn<>("POSITION");
		position.setCellValueFactory(new PropertyValueFactory<>("position"));

		TableColumn<InteractableItemDTO, InteractableItemActionType> actionType = new TableColumn<>("ACTION TYPE");
		actionType.setCellValueFactory(new PropertyValueFactory<>("actionType"));

		//work in progress
		TableColumn<InteractableItemDTO, String> quests = new TableColumn<>("Quest(s) to trigger");
		quests.setCellValueFactory(new PropertyValueFactory<>("questID"));

		TableColumn<InteractableItemDTO, InteractableItemActionParameter> actionParam = new TableColumn<>("ACTION PARAMETER");
		actionParam.setCellValueFactory(new PropertyValueFactory<>("actionParam"));

		TableColumn<InteractableItemDTO, String> mapName = new TableColumn<>("MAP");
		mapName.setCellValueFactory(new PropertyValueFactory<>("mapName"));

		this.itemsTable.getColumns().addAll(itemID, name, position, actionType, actionParam, mapName);
		this.setCenter(this.itemsTable);
		this.refresh();
	}

	/**
	 * @return the singleton instance of the items content
	 */
	public static InteractableObjectContentView getInstance()
	{
		if (instance == null)
		{
			instance = new InteractableObjectContentView();
		}
		return instance;
	}

	/**
	 * @return all the interactable objects in the table
	 */
	public TableView<InteractableItemDTO> getInteractableObjectTable()
	{
		return this.itemsTable;
	}


	/**
	 * Refresh items
	 */
	@Override
	public void refresh()
	{
		this.itemsTable.setItems(null);
		AlertBar.getInstance().receiveMessage("REFRESH INTERACTABLE OBJECTS VIEW");
		items = new ArrayList<>();
		CommandGetAllInteractableObjects command = new CommandGetAllInteractableObjects();
		ModelFacade.getSingleton().queueCommand(command);
	}

	//importFile

	/**
	 * receive report
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		this.itemsTable.setItems(null);
		this.items = ((ObjectListReport) report).getObjects();
		this.itemsTable.setItems(FXCollections.observableArrayList(items));
	}

	/**
	 * Add an interactable object
	 */
	@Override
	public void add()
	{
		AlertBar.getInstance().receiveMessage("ADD INTERACTABLE OBJECT");
		AddInteractableObjectModal.getInstance().show();
	}

	/**
	 * Delete an interactable object
	 * @author Jordan Long
	 */
	@Override
	public void delete()
	{
		InteractableItemDTO targetedObject = this.itemsTable.getSelectionModel().getSelectedItem();

		if (targetedObject == null)
		{
			AlertBar.getInstance().receiveMessage("MUST SELECT ITEM TO DELETE");
		}
		else
		{
			AlertBar.getInstance().receiveMessage("DELETE INTERACTABLE OBJECT");
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Delete Interactable Object");
			alert.setContentText("Are you sure you want to delete " + targetedObject.getName() + " ?");
			ButtonType yesButton = new ButtonType("Yes", ButtonData.YES);
			ButtonType noButton = new ButtonType("No", ButtonData.NO);
			alert.getButtonTypes().setAll(yesButton, noButton);
			alert.getDialogPane().lookupButton(yesButton).setId("YesButton");
			alert.getDialogPane().lookupButton(noButton).setId("NoButton");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get().getText().equals(ButtonType.YES.getText()))
			{
				CommandDeleteInteractableObject command = new CommandDeleteInteractableObject(targetedObject.getId());
				ModelFacade.getSingleton().queueCommand(command);
				refresh();
				AlertBar.getInstance().receiveMessage("ITEM DELETED");
			}
			else if (result.get().getText().equals(ButtonType.NO.getText()))
			{
				AlertBar.getInstance().receiveMessage("DELETE ITEM CANCELLED");
			}
			else
			{
				AlertBar.getInstance().receiveMessage("TEST");
			}
		}

	}

	/**
	 * Edit an interactable object
	 */
	@Override
	public void edit()
	{
		AlertBar.getInstance().receiveMessage("EDIT INTERACTABLE OBJECT");
		InteractableItemDTO item = this.itemsTable.getSelectionModel().getSelectedItem();
		EditInteractableObjectModal.getInstance().setName(item.getName());
		EditInteractableObjectModal.getInstance().setPositionX(item.getPosition().getColumn());
		EditInteractableObjectModal.getInstance().setPositionY(item.getPosition().getRow());
		EditInteractableObjectModal.getInstance().setInteractableItemActionType(item.getActionType());
		if (item.getActionType().equals(InteractableItemActionType.MESSAGE))
		{
			CriteriaStringDTO msg = (CriteriaStringDTO.class.cast(item.getActionParam()));
			EditInteractableObjectModal.getInstance().setMessageSent(msg.getString());
			EditInteractableObjectModal.getInstance().setBuffPoints(0);
		}
		else if (item.getActionType().equals(InteractableItemActionType.BUFF))
		{
			CriteriaIntegerDTO buff = (CriteriaIntegerDTO.class.cast(item.getActionParam()));
			EditInteractableObjectModal.getInstance().setBuffPoints(buff.getTarget());
			EditInteractableObjectModal.getInstance().setMessageSent(null);
		}
		else if (item.getActionType().equals(InteractableItemActionType.QUEST_TRIGGER))
		{
			CriteriaStringDTO ids = (CriteriaStringDTO.class.cast(item.getActionParam()));
			EditInteractableObjectModal.getInstance().setQuestIDs(ids.getString());
			EditInteractableObjectModal.getInstance().setBuffPoints(0);
			EditInteractableObjectModal.getInstance().setMessageSent(null);
		}
		EditInteractableObjectModal.getInstance().setMap(item.getMapName());
		EditInteractableObjectModal.getInstance().setId(item.getId());
		EditInteractableObjectModal.getInstance().show();
	}

	/**
	 * Filter interactable objects
	 */
	@Override
	public void filter(String filter)
	{
		AlertBar.getInstance().receiveMessage("FILTER INTERACTABLE OBJECTS BY \"" + filter.toUpperCase() + "\"");
	}

	/**
	 * Import CSV file for interactable objects
	 */
	@Override
	public void importFile()
	{
		AlertBar.getInstance().receiveMessage("IMPORT INTERACTABLE OBJECTS");
		container = new FileChooserContainer(new FileChooser(), "Open Interactable Object Import File");
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
					optOut.setText("Wipe all existing objects?");
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
					System.out.println("Check box to clear table selected");
				}
				// Continue with import
				AlertBar.getInstance().receiveMessage("INTERACTABLE OBJECTS IMPORTED");

			}
			else
			{
				// Cancel import
			}
		}
	}
}
