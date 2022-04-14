package ui.fx.dialogues;

import java.sql.Date;
import java.time.LocalDate;

import criteria.ObjectiveCompletionCriteria;
import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.GameLocationDTO;
import dataDTO.InteractableItemDTO;
import dataENUM.ObjectiveCompletionType;
import datasource.DatabaseException;
import datatypes.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import model.CommandAddObjective;
import model.GameManagerMapManager;
import model.GameManagerQuestManager;
import model.ModelFacade;
import model.QuestRecord;
import ui.fx.contentviews.InteractableObjectContentView;

/**
 * This is the modal that contains the form which allows for the creation of objectives
 * @author Abe Loscher and Josh McMillen
 *
 */
public class AddObjectiveModal extends Modal
{

	private static AddObjectiveModal instance;

	private ComboBox<String> questName;
	private TextField desc;
	private TextField objectiveParam;
	private Spinner<Integer> xpGained;
	private Spinner<Integer> doubloonsRequired;
	private ComboBox<ObjectiveCompletionType> objectiveCompletionType;
	private Spinner<Integer> trigPosX;
	private Spinner<Integer> trigPosY;
	private ComboBox<String> mapOptions;
	private TextField requiredKey;
	private TextField requiredKey2;
	private ComboBox<String> interactableItems;

	/**
	 * Constructor for the add objective modal
	 * @param id id of the modal
	 * @param label label of the modal
	 * @param height height of the modal
	 * @param width width of the modal
	 */
	private AddObjectiveModal(String id, String label, int height, int width)
	{
		super(id, label, height, width);

		desc = new TextField();
		objectiveParam = new TextField();
		trigPosX = new Spinner<>(0, Integer.MAX_VALUE, 0);
		trigPosY = new Spinner<>(0, Integer.MAX_VALUE, 0);
		doubloonsRequired = new Spinner<>(0, Integer.MAX_VALUE, 0);
		xpGained = new Spinner<>(0, Integer.MAX_VALUE, 0);


		questName = new ComboBox<>();
		ObservableList<String> questList = FXCollections.observableArrayList();
		try
		{
			for (QuestRecord r : GameManagerQuestManager.getInstance().getQuests())
			{
				questList.add(r.getTitle());
			}
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}
		questName.setItems(questList);

		objectiveCompletionType = new ComboBox<>();
		ObservableList<ObjectiveCompletionType> objectiveCompletionTypeList = FXCollections.observableArrayList();
		for (ObjectiveCompletionType a : ObjectiveCompletionType.values())
		{
			objectiveCompletionTypeList.add(a);
		}
		objectiveCompletionType.setItems(objectiveCompletionTypeList);
		objectiveCompletionType.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) ->
		{
			disableFields(newValue);
		});

		mapOptions = new ComboBox<>();
		ObservableList<String> mapList = FXCollections.observableArrayList();
		mapList.addAll(GameManagerMapManager.getInstance().getMaps());
		mapOptions.setItems(mapList);

		requiredKey = new TextField();

		requiredKey2 = new TextField();

		ObservableList<String> interactableItemsList = FXCollections.observableArrayList();
		for (InteractableItemDTO dto : InteractableObjectContentView.getInstance().getInteractableObjectTable().getItems())
		{
			interactableItemsList.add("" + dto.getId() + ":" + dto.getName());

		}
		interactableItems = new ComboBox<>();
		interactableItems.setItems(interactableItemsList);

		VBox addObjectiveBox = new VBox();
		addObjectiveBox.getChildren().addAll(
				this.generateElement("Quest Name:", questName),
				this.generateElement("Description:", desc),
				this.generateElement("Experience:", xpGained),
				this.generateElement("Objective Type:", objectiveCompletionType),
				this.generateElement("Parameter:", objectiveParam),
				this.generateElement("Map Name:", mapOptions),
				this.generateElement("Trigger Position X:", trigPosX),
				this.generateElement("Trigger Position Y:", trigPosY),
				this.generateElement("Required # of Doubloons:", doubloonsRequired),
				this.generateElement("Required Key:", requiredKey),
				this.generateElement("Required Key 2:", requiredKey2),
				this.generateElement("Interactable object:", interactableItems)
		);

		modal.setCenter(addObjectiveBox);
		this.setElementIds();
	}

	/**
	 * This method disables and reenables fields based on the type of objective being created
	 * @param value
	 */
	private void disableFields(ObjectiveCompletionType value)
	{
		if (value == null)
		{
			return;
		}
		if (value.equals(ObjectiveCompletionType.REAL_LIFE) || value.equals(ObjectiveCompletionType.CHAT))
		{
			this.objectiveParam.setDisable(false);
			this.interactableItems.setDisable(true);
			this.trigPosX.setDisable(true);
			this.trigPosY.setDisable(true);
			this.mapOptions.setDisable(true);
			this.doubloonsRequired.setDisable(true);
			this.requiredKey.setDisable(true);
			this.requiredKey2.setDisable(true);
		}
		else if (value.equals(ObjectiveCompletionType.KEYSTROKE))
		{
			this.objectiveParam.setDisable(true);
			this.interactableItems.setDisable(true);
			this.trigPosX.setDisable(true);
			this.trigPosY.setDisable(true);
			this.mapOptions.setDisable(true);
			this.doubloonsRequired.setDisable(true);
			this.requiredKey.setDisable(false);
			this.requiredKey2.setDisable(true);
		}
		else if (value.equals(ObjectiveCompletionType.KEYSTROKE))
		{
			this.objectiveParam.setDisable(true);
			this.interactableItems.setDisable(true);
			this.trigPosX.setDisable(true);
			this.trigPosY.setDisable(true);
			this.mapOptions.setDisable(true);
			this.doubloonsRequired.setDisable(true);
			this.requiredKey.setDisable(false);
			this.requiredKey2.setDisable(false);
		}
		else if (value.equals(ObjectiveCompletionType.DOUBLOONS))
		{
			this.objectiveParam.setDisable(true);
			this.interactableItems.setDisable(true);
			this.trigPosX.setDisable(true);
			this.trigPosY.setDisable(true);
			this.mapOptions.setDisable(true);
			this.doubloonsRequired.setDisable(false);
			this.requiredKey.setDisable(true);
			this.requiredKey2.setDisable(true);
		}
		else if (value.equals(ObjectiveCompletionType.MOVEMENT))
		{
			this.objectiveParam.setDisable(true);
			this.interactableItems.setDisable(true);
			this.trigPosX.setDisable(false);
			this.trigPosY.setDisable(false);
			this.mapOptions.setDisable(false);
			this.doubloonsRequired.setDisable(true);
			this.requiredKey.setDisable(true);
			this.requiredKey2.setDisable(true);
		}
		else if (value.equals(ObjectiveCompletionType.INTERACT))
		{
			this.objectiveParam.setDisable(true);
			this.interactableItems.setDisable(false);
			this.trigPosX.setDisable(true);
			this.trigPosY.setDisable(true);
			this.mapOptions.setDisable(true);
			this.doubloonsRequired.setDisable(true);
			this.requiredKey.setDisable(true);
			this.requiredKey2.setDisable(true);
		}
	}

	/**
	 * Saves the modal
	 */
	@Override
	public void save()
	{
		AddObjectiveModal modal = AddObjectiveModal.getInstance();
		CommandAddObjective command = null;

		String questTitle = modal.getQuestName().getValue();
		String objectiveDescription = modal.getDesc().getText();
		Integer xpAmount = modal.getXpGained().getValue();
		ObjectiveCompletionType completionType = modal.getObjectiveCompletionType().getValue();
		ObjectiveCompletionCriteria criteria = null;
		QuestRecord quest = null;
		try
		{
			quest = GameManagerQuestManager.getInstance().getQuest(questTitle);
		}
		catch (DatabaseException e)
		{
			// TODO where do we go now
			e.printStackTrace();
		}


		if (completionType.equals(ObjectiveCompletionType.REAL_LIFE) || completionType.equals(ObjectiveCompletionType.CHAT))
		{
			criteria = new CriteriaStringDTO(modal.getObjectiveParam().getText());
		}
		else if (completionType.equals(ObjectiveCompletionType.KEYSTROKE))
		{
			criteria = new CriteriaStringDTO(modal.getRequiredKey().getText());
		}
		else if (completionType.equals(ObjectiveCompletionType.DOUBLOONS))
		{
			criteria = new CriteriaIntegerDTO(modal.getDoubloonsRequired().getValue());
		}
		else if (completionType.equals(ObjectiveCompletionType.MOVEMENT))
		{
			criteria = new GameLocationDTO(modal.getMapOptions().getValue(), new Position(modal.getTrigPosY().getValue(), modal.getTrigPosX().getValue()));
		}
		else if (completionType.equals(ObjectiveCompletionType.INTERACT))
		{
			String test = modal.getInteractableItems().getValue();
			String[] strings = test.split(":");
			criteria = new CriteriaIntegerDTO(Integer.parseInt(strings[0]));
		}

		command = new CommandAddObjective(quest.getQuestID(), objectiveDescription, xpAmount, completionType, criteria);
		ModelFacade.getSingleton().queueCommand(command);

		reset();
	}

	/**
	 * Set the ids of all of the elements of the modal
	 */
	private void setElementIds()
	{
		questName.setId("QuestName");
		desc.setId("Desc");
		objectiveParam.setId("ObjectiveParam");
		xpGained.setId("XPGained");
		doubloonsRequired.setId("DoubloonsRequired");
		objectiveCompletionType.setId("ObjectiveCompletionType");
		trigPosX.setId("TrigPosX");
		trigPosY.setId("TrigPosY");
		mapOptions.setId("MapOptions");
		requiredKey.setId("RequiredKey");
		requiredKey2.setId("RequiredKey2");
		interactableItems.setId("InteractableItem");
	}

	/**
	 * This resets all of the fields in the form
	 */
	private void resetFields()
	{
		this.desc.setText("");
		this.xpGained.decrement(this.xpGained.getValue());
		this.objectiveParam.setText("");
		this.trigPosX.decrement(this.trigPosX.getValue());
		this.trigPosY.decrement(this.trigPosY.getValue());
		this.doubloonsRequired.decrement(this.doubloonsRequired.getValue());
		this.requiredKey.setText("");
		this.requiredKey2.setText("");
		this.questName.getSelectionModel().clearSelection();
		this.objectiveCompletionType.getSelectionModel().clearSelection();
		this.mapOptions.getSelectionModel().clearSelection();
		this.interactableItems.getSelectionModel().clearSelection();
	}


	/**
	 * Convert localdate to java util date
	 * @param localDate localdate to be converted
	 * @return java util date
	 */
	public Date convertLocalDateToDate(LocalDate localDate)
	{
		Date javaUtilDate = Date.valueOf(localDate);
		return javaUtilDate;
	}

	/**
	 * Cancels the modal
	 */
	@Override
	public void cancel()
	{
		reset();
	}

	/**
	 * Resets the modal
	 */
	@Override
	public void reset()
	{
		resetFields();
	}

	/**
	 * Gets the instance of the modal
	 * @return the instance
	 */
	public static AddObjectiveModal getInstance()
	{
		if (instance == null)
		{
			instance = new AddObjectiveModal("AddObjectiveModal", "Add Objective", 400, 620);
		}
		return instance;
	}

	/**
	 * Gets the description textfield
	 * @return description textfield
	 */
	public TextField getDesc()
	{
		return desc;
	}

	/**
	 * Gets the trigger position x spinner
	 * @return trigger position x spinner
	 */
	public Spinner<Integer> getTrigPosX()
	{
		return trigPosX;
	}

	/**
	 * Gets the trigger position y spinner
	 * @return trigger position y spinner
	 */
	public Spinner<Integer> getTrigPosY()
	{
		return trigPosY;
	}

	/**
	 * Gets the xp gained spinner
	 * @return xp gained spinner
	 */
	public Spinner<Integer> getXpGained()
	{
		return xpGained;
	}

	/**
	 * Gets the map options combobox
	 * @return map options combobox
	 */
	public ComboBox<String> getMapOptions()
	{
		return mapOptions;
	}

	/**
	 * Gets the quest name combobox
	 * @return combobox
	 */
	public ComboBox<String> getQuestName()
	{
		return questName;
	}

	/**
	 * Gets the objective param field
	 * @return objective param textfield
	 */
	public TextField getObjectiveParam()
	{
		return objectiveParam;
	}

	/**
	 * Gets the doubloons required
	 * @return doubloons required
	 */
	public Spinner<Integer> getDoubloonsRequired()
	{
		return doubloonsRequired;
	}

	/**
	 * Gets the combobox for objective completion type
	 * @return objective completion type combobox
	 */
	public ComboBox<ObjectiveCompletionType> getObjectiveCompletionType()
	{
		return objectiveCompletionType;
	}

	/**
	 * Gets the textfield for required key
	 * @return required key textfield
	 */
	public TextField getRequiredKey()
	{
		return requiredKey;
	}

	/**
	 * Gets the textfield for required key
	 * @return required key textfield
	 */
	private TextInputControl getRequiredKey2()
	{
		return requiredKey2;
	}

	/**
	 * Gets the interactable items
	 * @return interactable items combobox
	 */
	private ComboBox<String> getInteractableItems()
	{
		return interactableItems;
	}
}
