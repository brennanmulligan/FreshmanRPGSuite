package ui.fx.dialogues;

import java.sql.Date;
import java.time.LocalDate;

import criteria.AdventureCompletionCriteria;
import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.GameLocationDTO;
import dataDTO.InteractableItemDTO;
import dataENUM.AdventureCompletionType;
import datasource.DatabaseException;
import datatypes.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import model.CommandEditAdventure;
import model.GameManagerMapManager;
import model.GameManagerQuestManager;
import model.ModelFacade;
import model.QuestRecord;
import ui.fx.contentviews.InteractableObjectContentView;

/**
 * This is the modal that contains the form which allows for the creation of adventures
 * @author Abe Loscher and Josh McMillen
 *
 */
public class EditAdventureModal extends Modal
{

	private static EditAdventureModal instance;

	private int adventureId;
	private ComboBox<String> questName;
	private TextField desc;
	private TextField adventureParam;
	private Spinner<Integer> xpGained;
	private Spinner<Integer> knowledgePointsRequired;
	private ComboBox<AdventureCompletionType> adventureCompletionType;
	private Spinner<Integer> trigPosX;
	private Spinner<Integer> trigPosY;
	private ComboBox<String> mapOptions;
	private TextField requiredKey;
	private TextField requiredKey2;
	private ComboBox<String> interactableItems;

	/**
	 * Constructor for the add adventure modal
	 * @param id id of the modal
	 * @param label label of the modal
	 * @param height height of the modal
	 * @param width width of the modal
	 */
	public EditAdventureModal(String id, String label, int height, int width)
	{
		super(id, label, height, width);

		desc = new TextField();
		adventureParam = new TextField();
		trigPosX = new Spinner<>(0, Integer.MAX_VALUE, 0);
		trigPosY = new Spinner<>(0, Integer.MAX_VALUE, 0);
		knowledgePointsRequired = new Spinner<>(0, Integer.MAX_VALUE, 0);
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

		adventureCompletionType = new ComboBox<>();
		ObservableList<AdventureCompletionType> adventureCompletionTypeList = FXCollections.observableArrayList();
		for (AdventureCompletionType a : AdventureCompletionType.values())
		{
			adventureCompletionTypeList.add(a);
		}
		adventureCompletionType.setItems(adventureCompletionTypeList);
		adventureCompletionType.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) ->
		{
			disableFields(newValue);
		});

		mapOptions = new ComboBox<>();
		ObservableList<String> mapList = FXCollections.observableArrayList();
		mapList.addAll(GameManagerMapManager.getInstance().getMaps());
		mapOptions.setItems(mapList);


		requiredKey = new TextField();
		requiredKey.setTextFormatter(new TextFormatter<String>((change) ->
		{
			String newText = change.getControlNewText();
			if (newText.length() > 1)
			{
				return null;
			}
			else
			{
				return change;
			}
		}));

		requiredKey2 = new TextField();
		requiredKey2.setTextFormatter(new TextFormatter<String>((change) ->
		{
			String newText = change.getControlNewText();
			if (newText.length() > 1)
			{
				return null;
			}
			else
			{
				return change;
			}
		}));

		ObservableList<String> interactableItemsList = FXCollections.observableArrayList();
		for (InteractableItemDTO dto : InteractableObjectContentView.getInstance().getInteractableObjectTable().getItems())
		{
			interactableItemsList.add("" + dto.getId() + ":" + dto.getName());

		}
		interactableItems = new ComboBox<>();
		interactableItems.setItems(interactableItemsList);

		VBox addAdventureBox = new VBox();
		addAdventureBox.getChildren().addAll(
				this.generateElement("Quest Name:", questName),
				this.generateElement("Description:", desc),
				this.generateElement("Experience:", xpGained),
				this.generateElement("Adventure Completion Type:", adventureCompletionType),
				this.generateElement("Parameter:", adventureParam),
				this.generateElement("Map Name:", mapOptions),
				this.generateElement("Trigger Position X:", trigPosX),
				this.generateElement("Trigger Position Y:", trigPosY),
				this.generateElement("Required # of Knowledge Points:", knowledgePointsRequired),
				this.generateElement("Required Key:", requiredKey),
				this.generateElement("Required Key 2:", requiredKey2),
				this.generateElement("Interactable item:", interactableItems)
		);

		modal.setCenter(addAdventureBox);
		this.setElementIds();
	}

	/**
	 * This method disables and reenables fields based on the type of adventure being created 
	 * @param value
	 */
	private void disableFields(AdventureCompletionType value)
	{
		if (value.equals(AdventureCompletionType.REAL_LIFE) || value.equals(AdventureCompletionType.CHAT))
		{
			this.interactableItems.setDisable(true);

			this.adventureParam.setDisable(false);
			this.trigPosX.setDisable(true);
			this.trigPosY.setDisable(true);
			this.mapOptions.setDisable(true);
			this.knowledgePointsRequired.setDisable(true);
			this.requiredKey.setDisable(true);
			this.requiredKey2.setDisable(true);
		}
		else if (value.equals(AdventureCompletionType.KEYSTROKE))
		{
			this.interactableItems.setDisable(true);

			this.adventureParam.setDisable(true);
			this.trigPosX.setDisable(true);
			this.trigPosY.setDisable(true);
			this.mapOptions.setDisable(true);
			this.knowledgePointsRequired.setDisable(true);
			this.requiredKey.setDisable(false);
			this.requiredKey2.setDisable(true);
		}
		else if (value.equals(AdventureCompletionType.KNOWLEDGE_POINTS))
		{
			this.interactableItems.setDisable(true);

			this.adventureParam.setDisable(true);
			this.trigPosX.setDisable(true);
			this.trigPosY.setDisable(true);
			this.mapOptions.setDisable(true);
			this.knowledgePointsRequired.setDisable(false);
			this.requiredKey.setDisable(true);
			this.requiredKey2.setDisable(true);
		}
		else if (value.equals(AdventureCompletionType.MOVEMENT))
		{
			this.interactableItems.setDisable(true);
			this.adventureParam.setDisable(true);
			this.trigPosX.setDisable(false);
			this.trigPosY.setDisable(false);
			this.mapOptions.setDisable(false);
			this.knowledgePointsRequired.setDisable(true);
			this.requiredKey.setDisable(true);
			this.requiredKey2.setDisable(true);
		}
		else if (value.equals(AdventureCompletionType.INTERACT))
		{
			this.interactableItems.setDisable(false);
			this.adventureParam.setDisable(true);
			this.trigPosX.setDisable(true);
			this.trigPosY.setDisable(true);
			this.mapOptions.setDisable(true);
			this.knowledgePointsRequired.setDisable(true);
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
		EditAdventureModal modal = EditAdventureModal.getInstance();
		CommandEditAdventure command = null;
		String questTitle = modal.getQuestName().getValue();
		String adventureDescription = modal.getDesc().getText();
		Integer xpAmount = modal.getXpGained().getValue();
		AdventureCompletionType completionType = modal.getAdventureCompletionType().getValue();
		AdventureCompletionCriteria criteria = null;
		QuestRecord quest = null;
		try
		{
			quest = GameManagerQuestManager.getInstance().getQuest(questTitle);
		}
		catch (DatabaseException e)
		{
			e.printStackTrace();
		}


		if (completionType.equals(AdventureCompletionType.REAL_LIFE) || completionType.equals(AdventureCompletionType.CHAT))
		{
			criteria = new CriteriaStringDTO(modal.getAdventureParam().getText());
		}
		else if (completionType.equals(AdventureCompletionType.KEYSTROKE))
		{
			criteria = new CriteriaStringDTO(modal.getRequiredKey().getText());
		}
		else if (completionType.equals(AdventureCompletionType.KNOWLEDGE_POINTS))
		{
			criteria = new CriteriaIntegerDTO(modal.getKnowledgePointsRequired().getValue());
		}
		else if (completionType.equals(AdventureCompletionType.MOVEMENT))
		{
			criteria = new GameLocationDTO(modal.getMapOptions().getValue(), new Position(modal.getTrigPosY().getValue(), modal.getTrigPosX().getValue()));
		}
		else if (completionType.equals(AdventureCompletionType.INTERACT))
		{
			String test = modal.getInteractableItems().getValue();
			String[] strings = test.split(":");
			criteria = new CriteriaIntegerDTO(Integer.parseInt(strings[0]));
		}

		command = new CommandEditAdventure(quest.getQuestID(), adventureId, adventureDescription, xpAmount, completionType, criteria);
		ModelFacade.getSingleton().queueCommand(command);
	}

	/**
	 * Set the ids of all of the elements of the modal
	 */
	private void setElementIds()
	{
		questName.setId("QuestName");
		desc.setId("Desc");
		adventureParam.setId("AdventureParam");
		xpGained.setId("XPGained");
		knowledgePointsRequired.setId("KnowledgePointsRequired");
		adventureCompletionType.setId("AdventureCompletionType");
		trigPosX.setId("TrigPosX");
		trigPosY.setId("TrigPosY");
		mapOptions.setId("MapOptions");
		requiredKey.setId("RequiredKey");
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
	}

	/**
	 * Resets the modal
	 */
	@Override
	public void reset()
	{
	}

	/**
	 * Gets the instance of the modal
	 * @return the instance
	 */
	public static EditAdventureModal getInstance()
	{
		if (instance == null)
		{
			instance = new EditAdventureModal("EditAdventureModal", "Edit Adventure", 400, 620);
		}
		return instance;
	}

	/**
	 * @param id set Adventure ID
	 */
	public void setAdventureId(int id)
	{
		this.adventureId = id;
	}

	/**
	 * Gets the id of adventure
	 * @return Adventure ID
	 */
	public int getAdventureId()
	{
		return adventureId;
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
	 * Gets the adventure param field
	 * @return adventure param textfield
	 */
	public TextField getAdventureParam()
	{
		return adventureParam;
	}

	/**
	 * Gets the knowledge points required
	 * @return knowledge points required
	 */
	public Spinner<Integer> getKnowledgePointsRequired()
	{
		return knowledgePointsRequired;
	}

	/**
	 * Gets the combobox for adventure completion type
	 * @return adventure completion type combobox
	 */
	public ComboBox<AdventureCompletionType> getAdventureCompletionType()
	{
		return adventureCompletionType;
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
	public TextInputControl getRequiredKey2()
	{
		return requiredKey2;
	}

	/**
	 * Gets the interactable items combobox
	 * @return combobox
	 */
	private ComboBoxBase<String> getInteractableItems()
	{
		return this.interactableItems;
	}
}
