package ui.fx.dialogues;

import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.InteractableNullAction;
import dataDTO.InteractableItemDTO;
import dataENUM.InteractableItemActionType;
import datatypes.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.CommandEditInteractableObject;
import model.GameManagerMapManager;
import model.ModelFacade;

/**
 * @author Benjamin Uleau, Chris Boyer
 * Modal for editing interactable objects
 */
public class EditInteractableObjectModal extends Modal
{
	private static EditInteractableObjectModal instance;
	private TextField name;
	private Spinner<Integer> posX;
	private Spinner<Integer> posY;
	private ComboBox<InteractableItemActionType> interactableItemActionType;
	private ComboBox<String> maps;
	private TextField messageSent;
	private Spinner<Integer> buffPoints;
	private TextField questIDs;

	private int id;

	/**
	 * Singleton constructor for the edit interactable item modal
	 * @param id - id of the modal
	 * @param label - label of the modal
	 * @param height - height of the modal
	 * @param width - width of the modal
	 */
	public EditInteractableObjectModal(String id, String label, int height, int width)
	{
		super(id, label, height, width);

		this.name = new TextField();
		this.posX = new Spinner<>(0, Integer.MAX_VALUE, 0);
		this.posY = new Spinner<>(0, Integer.MAX_VALUE, 0);

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
		this.buffPoints.setEditable(true);

		this.buffPoints.setDisable(true);
		this.messageSent.setDisable(true);

		VBox vbox = new VBox();

		vbox.getChildren().addAll(
				this.generateElement("Name", name),
				this.generateElement("Position X", posX),
				this.generateElement("Position Y", posY),
				this.generateElement("Maps", maps),
				this.generateElement("Action Type", interactableItemActionType),
				this.generateElement("Quest(s) to Trigger", questIDs),
				this.generateElement("Message Sent", messageSent),
				this.generateElement("Buff Points", buffPoints)
		);

		modal.setCenter(vbox);
		this.setElementIds();
	}

	/**
	 * Set the ids of the modal elements for styling
	 */
	private void setElementIds()
	{
		this.name.setId("Name");
		this.posX.setId("PositionX");
		this.posY.setId("PositionY");
		this.maps.setId("Map");
		this.interactableItemActionType.setId("InteractableItemActionType");
		this.questIDs.setId("Quest(s)");
		this.buffPoints.setId("BuffPoints");
		this.messageSent.setId("MessageSent");
	}

	/**
	 * Disables the appropriate fields
	 * @param newValue - new value input to combo box
	 */
	private void disableFields(InteractableItemActionType newValue)
	{
		this.questIDs.setDisable(true);
		this.buffPoints.setDisable(true);
		this.messageSent.setDisable(true);

		if (newValue.equals(InteractableItemActionType.BUFF))
		{
			buffPoints.setDisable(false);
		}
		else if (newValue.equals(InteractableItemActionType.MESSAGE)
				|| newValue.equals(InteractableItemActionType.BOARD))
		{
			messageSent.setDisable(false);
		}
		else if (newValue.equals(InteractableItemActionType.QUEST_TRIGGER))
		{
			questIDs.setDisable(false);
		}
		else
		{
			//do nothing
		}
	}

	/**
	 * Get the singleton instance of the modal
	 * @return instance of teh modal
	 */
	public static EditInteractableObjectModal getInstance()
	{
		if (instance == null)
		{
			instance = new EditInteractableObjectModal("EditInteractableObject", "Edit Interactable Object", 400, 400);
		}
		return instance;
	}

	/**
	 * Save the changes to the object. Make sure to use the correct type of interactable item action type
	 */
	@Override
	public void save()
	{
		CommandEditInteractableObject cmd = null;
		Position pos = new Position(getPositionY(), getPositionX());
		InteractableItemDTO item;
		if (this.interactableItemActionType.getValue().equals(InteractableItemActionType.BUFF))
		{
			item = new InteractableItemDTO(getItemId(), getName(), pos,
					getInteractableItemActionType(),
					new CriteriaIntegerDTO(getBuffPoints()),
					getMap());
			cmd = new CommandEditInteractableObject(item);
		}
		else if (this.interactableItemActionType.getValue().equals(InteractableItemActionType.MESSAGE)
				|| this.interactableItemActionType.getValue().equals(InteractableItemActionType.BOARD))
		{
			item = new InteractableItemDTO(getItemId(), getName(), pos,
					getInteractableItemActionType(),
					new CriteriaStringDTO(getMessageSent()),
					getMap());
			cmd = new CommandEditInteractableObject(item);
		}
		else if (this.interactableItemActionType.getValue().equals(InteractableItemActionType.NO_ACTION))
		{
			item = new InteractableItemDTO(getItemId(), getName(), pos,
					getInteractableItemActionType(),
					new InteractableNullAction(),
					getMap());
			cmd = new CommandEditInteractableObject(item);
		}
		else if (this.interactableItemActionType.getValue().equals(InteractableItemActionType.QUEST_TRIGGER))
		{
			item = new InteractableItemDTO(getItemId(), getName(), pos,
					getInteractableItemActionType(),
					new CriteriaStringDTO(getQuestIDs()),
					getMap());
			cmd = new CommandEditInteractableObject(item);
		}
		ModelFacade.getSingleton().queueCommand(cmd);
	}

	/**
	 * Cancel the changes to the object
	 */
	@Override
	public void cancel()
	{
	}

	/**
	 * Reset the modal
	 */
	@Override
	public void reset()
	{
		instance = null;
	}

	/**
	 * Set name field
	 * @param name - name
	 */
	public void setName(String name)
	{
		this.name.setText(name);
	}

	/**
	 * @return the name from the modal
	 */
	public String getName()
	{
		return name.getText();
	}

	/**
	 * Set position x field
	 * @param x - PositionX
	 */
	public void setPositionX(int x)
	{
		this.posX.getValueFactory().setValue(x);
	}

	/**
	 * @return the x position from the modal
	 */
	public int getPositionX()
	{
		return posX.getValue();
	}

	/**
	 * Set position y field
	 * @param y - PositionY
	 */
	public void setPositionY(int y)
	{
		this.posY.getValueFactory().setValue(y);
	}

	/**
	 * @return the y position from the modal
	 */
	public int getPositionY()
	{
		return posY.getValue();
	}

	/**
	 * Set action type field
	 * @param iiat - interactable item action type
	 */
	public void setInteractableItemActionType(InteractableItemActionType iiat)
	{
		this.interactableItemActionType.setValue(iiat);
	}

	/**
	 * @return the interactable item action type from the modal
	 */
	public InteractableItemActionType getInteractableItemActionType()
	{
		return interactableItemActionType.getValue();
	}

	/**
	 * Set map field
	 * @param map - map
	 */
	public void setMap(String map)
	{
		this.maps.setValue(map);
	}

	/**
	 * @return the map value from teh modal
	 */
	public String getMap()
	{
		return maps.getValue();
	}

	/**
	 * Set message sent field
	 * @param messageSent - message sent
	 */
	public void setMessageSent(String messageSent)
	{
		this.messageSent.setText(messageSent);
	}

	/**
	 * @return the message sent value from the modal
	 */
	public String getMessageSent()
	{
		return messageSent.getText();
	}

	/**
	 * Set buff points field
	 * @param points - buff points
	 */
	public void setBuffPoints(int points)
	{
		this.buffPoints.getValueFactory().setValue(points);
	}

	/**
	 * @return the buff points value from the modal
	 */
	public int getBuffPoints()
	{
		return buffPoints.getValue();
	}

	/**
	 * set the id
	 * @param id - id of the interactable item
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * @return the id of the interactable item
	 */
	public int getItemId()
	{
		return id;
	}

	/**
	 *
	 * @param questIDs
	 */
	public void setQuestIDs(String questIDs)
	{
		this.questIDs.setText(questIDs);
	}

	/**
	 *
	 * @return quests
	 */
	public String getQuestIDs()
	{
		return questIDs.getText();
	}

}
