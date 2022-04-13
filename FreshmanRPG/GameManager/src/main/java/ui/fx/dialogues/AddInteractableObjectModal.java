package ui.fx.dialogues;

import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.InteractableNullAction;
import dataENUM.InteractableItemActionType;
import datatypes.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.CommandAddInteractableObject;
import model.GameManagerMapManager;
import model.ModelFacade;

/**
 * Add interactable object modal 
 * @author Abe Loscher and Chris Boyer
 */
public class AddInteractableObjectModal extends Modal
{

	private static AddInteractableObjectModal instance;
	private TextField name;
	private Spinner<Integer> positionX;
	private Spinner<Integer> positionY;
	private ComboBox<InteractableItemActionType> interactableItemActionType;
	private ComboBox<String> maps;
	private TextField messageSent;
	private Spinner<Integer> buffPoints;
	private TextField questIDs;

	/**
	 * Constructor for add interactable object modal
	 */
	private AddInteractableObjectModal(String id, String label, int height, int width)
	{
		super(id, label, height, width);

		this.name = new TextField();
		this.positionX = new Spinner<>(0, Integer.MAX_VALUE, 0);
		this.positionY = new Spinner<>(0, Integer.MAX_VALUE, 0);

		this.interactableItemActionType = new ComboBox<>();
		ObservableList<InteractableItemActionType> actionList = FXCollections.observableArrayList();
		actionList.addAll(InteractableItemActionType.values());
		this.interactableItemActionType.setItems(actionList);

		this.interactableItemActionType.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) ->
		{

			disableFields(newValue);
		});

		this.questIDs = new TextField();

		this.maps = new ComboBox<>();
		ObservableList<String> mapNames = FXCollections.observableArrayList();
		mapNames.addAll(GameManagerMapManager.getInstance().getMaps());
		this.maps.setItems(mapNames);

		this.messageSent = new TextField();

		this.buffPoints = new Spinner<>(0, Integer.MAX_VALUE, 0);

		this.buffPoints.setDisable(true);
		this.messageSent.setDisable(true);

		VBox vbox = new VBox();
		vbox.getChildren().addAll(
				this.generateElement("Name", name),
				this.generateElement("Position X", positionX),
				this.generateElement("Position Y", positionY),
				this.generateElement("Maps", maps),
				this.generateElement("Action Type", interactableItemActionType),
				this.generateElement("Quest(s) to be Triggered", questIDs),
				this.generateElement("Message Sent", messageSent),
				this.generateElement("Buff Points", buffPoints)
		);

		modal.setCenter(vbox);
		this.setElementIds();
	}

	/**
	 * Set the ids of all elements
	 */
	private void setElementIds()
	{
		this.name.setId("Name");
		this.positionX.setId("PositionX");
		this.positionY.setId("PositionY");
		this.maps.setId("Map");
		this.interactableItemActionType.setId("InteractableItemActionType");
		this.questIDs.setId("QuestIDs");
		this.buffPoints.setId("BuffPoints");
		this.messageSent.setId("MessageSent");
	}


	/**
	 * Disables appropriate fields
	 * @param newValue new value input to combobox
	 */
	private void disableFields(InteractableItemActionType newValue)
	{
		this.buffPoints.setDisable(true);
		this.messageSent.setDisable(true);
		this.questIDs.setDisable(true);
		if (newValue.equals(InteractableItemActionType.BUFF))
		{
			buffPoints.setDisable(false);
		}
		else if (newValue.equals(InteractableItemActionType.MESSAGE))
		{
			messageSent.setDisable(false);
		}
		else if (newValue.equals(InteractableItemActionType.QUEST_TRIGGER))
		{
			questIDs.setDisable(false);
		}
		else
		{
			// Do nothing
		}

	}

	/**
	 * Gets the instance of the interactable object
	 * @return instance of interactable object
	 */
	public static AddInteractableObjectModal getInstance()
	{
		if (instance == null)
		{
			instance = new AddInteractableObjectModal("AddInteractableObject", "Add Interactable Object", 400, 400);
		}
		return instance;
	}

	/**
	 * Save the interactable object
	 */
	@Override
	public void save()
	{
		CommandAddInteractableObject cmd = null;
		if (this.interactableItemActionType.getValue().equals(InteractableItemActionType.BUFF))
		{
			cmd = new CommandAddInteractableObject(this.name.getText(),
					new Position(this.positionY.getValue(), this.positionX.getValue()),
					this.interactableItemActionType.getValue(),
					new CriteriaIntegerDTO(this.buffPoints.getValue()),
					this.maps.getValue());
		}
		else if (this.interactableItemActionType.getValue().equals(InteractableItemActionType.MESSAGE))
		{
			cmd = new CommandAddInteractableObject(this.name.getText(),
					new Position(this.positionY.getValue(), this.positionX.getValue()),
					this.interactableItemActionType.getValue(),
					new CriteriaStringDTO(this.messageSent.getText()),
					this.maps.getValue());
		}
		else if (this.interactableItemActionType.getValue().equals(InteractableItemActionType.NO_ACTION))
		{
			cmd = new CommandAddInteractableObject(this.name.getText(),
					new Position(this.positionY.getValue(), this.positionX.getValue()),
					this.interactableItemActionType.getValue(),
					new InteractableNullAction(),
					this.maps.getValue());
		}
		else if (this.interactableItemActionType.getValue().equals(InteractableItemActionType.QUEST_TRIGGER))
		{
			cmd = new CommandAddInteractableObject(this.name.getText(),
					new Position(this.positionY.getValue(), this.positionX.getValue()),
					this.interactableItemActionType.getValue(),
					new CriteriaStringDTO(this.questIDs.getText()),
					this.maps.getValue());
		}
		ModelFacade.getSingleton().queueCommand(cmd);

	}

	/**
	 * Cancels the interactable object
	 */
	@Override
	public void cancel()
	{
		// TODO Auto-generated method stub

	}

	/**
	 * Resets the interactable object
	 */
	@Override
	public void reset()
	{
		// TODO Auto-generated method stub

	}

}
