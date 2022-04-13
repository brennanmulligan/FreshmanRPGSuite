package ui.fx.dialogues;

import java.sql.Date;
import java.time.LocalDate;

import criteria.GameLocationDTO;
import dataENUM.QuestCompletionActionType;
import datatypes.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.CommandAddQuest;
import model.GameManagerMapManager;
import model.ModelFacade;

/**
 *
 * @author Abe Loscher and Josh McMillen
 *
 */
public class AddQuestModal extends Modal
{

	private static AddQuestModal instance;

	private TextField title;
	private TextField desc;
	private Spinner<Integer> trigPosX;
	private Spinner<Integer> trigPosY;
	private Spinner<Integer> xpGained;
	private Spinner<Integer> numAdventures;
	private Spinner<Integer> telPosX;
	private Spinner<Integer> telPosY;
	private ComboBox<String> mapOptions;
	private ComboBox<QuestCompletionActionType> questCompletionActionType;
	private ComboBox<String> questCompletionActionParam;
	private DatePicker startDate;
	private DatePicker endDate;


	/**
	 * Constructor
	 * @param id - the id of the quest
	 * @param label - the label of the quest content view
	 * @param height - the height of the quest content view
	 * @param width - the width of teh quest content view
	 */
	public AddQuestModal(String id, String label, int height, int width)
	{
		super(id, label, height, width);

		title = new TextField();
		desc = new TextField();

		mapOptions = new ComboBox<>();
		ObservableList<String> mapList = FXCollections.observableArrayList();
		mapList.addAll(GameManagerMapManager.getInstance().getMaps());
		mapOptions.setItems(mapList);

		trigPosX = new Spinner<>(0, Integer.MAX_VALUE, 0);
		trigPosY = new Spinner<>(0, Integer.MAX_VALUE, 0);
		xpGained = new Spinner<>(0, Integer.MAX_VALUE, 0);
		numAdventures = new Spinner<>(0, Integer.MAX_VALUE, 0);

		questCompletionActionType = new ComboBox<>();
		ObservableList<QuestCompletionActionType> actionTypeList = FXCollections.observableArrayList();
		for (QuestCompletionActionType q : QuestCompletionActionType.values())
		{
			actionTypeList.add(q);
		}
		questCompletionActionType.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) ->
				{
					if (newValue.equals(QuestCompletionActionType.NO_ACTION))
					{
						disableFields(true);
					}
					else if (newValue.equals(QuestCompletionActionType.TELEPORT))
					{
						telPosX.setDisable(false);
						telPosY.setDisable(false);
						questCompletionActionParam.setDisable(false);
					}
					else
					{
						disableFields(false);
					}
				}
		);
		questCompletionActionType.setItems(actionTypeList);
		questCompletionActionParam = new ComboBox<>();
		ObservableList<String> listOfCompletionActionParams = FXCollections.observableArrayList();
		listOfCompletionActionParams.addAll(GameManagerMapManager.getInstance().getMaps());
		questCompletionActionParam.setItems(mapList);

		telPosX = new Spinner<>(0, Integer.MAX_VALUE, 0);
		telPosY = new Spinner<>(0, Integer.MAX_VALUE, 0);
		startDate = new DatePicker(LocalDate.now());
		endDate = new DatePicker(LocalDate.now());

		VBox addQuestBox = new VBox();
		addQuestBox.getChildren().addAll(
				this.generateElement("Title", title),
				this.generateElement("Description:", desc),
				this.generateElement("Map Name:", mapOptions),
				this.generateElement("Trigger Position X:", trigPosX),
				this.generateElement("Trigger Position Y:", trigPosY),
				this.generateElement("EXP Gained:", xpGained),
				this.generateElement("# of Adventures for Completion:", numAdventures),
				this.generateElement("Completion Action Type:", questCompletionActionType),
				this.generateElement("Completion Action Parameter:", questCompletionActionParam),
				this.generateElement("Teleport Position X:", telPosX),
				this.generateElement("Teleport Position Y:", telPosY),
				this.generateElement("Start Date:", startDate),
				this.generateElement("End Date:", endDate)
		);

		modal.setCenter(addQuestBox);

		setElementIds();
	}

	/**
	 * Sets the ids of the elements in the modal
	 */
	private void setElementIds()
	{
		title.setId("QuestTitle");
		desc.setId("Desc");
		trigPosX.setId("TrigPosX");
		trigPosY.setId("TrigPosY");
		xpGained.setId("XPGained");
		numAdventures.setId("NumAdventures");
		telPosX.setId("TelPosX");
		telPosY.setId("TelPosY");
		mapOptions.setId("MapOptions");
		questCompletionActionType.setId("QuestCompletionActionType");
		questCompletionActionParam.setId("QuestCompletionActionParam");
		startDate.setId("StartDate");
		endDate.setId("EndDate");
	}

	private void disableFields(boolean shouldDisable)
	{
		telPosX.setDisable(shouldDisable);
		telPosY.setDisable(shouldDisable);
		questCompletionActionParam.setDisable(shouldDisable);
	}

	private void resetFields()
	{
		title.clear();
		desc.clear();
		trigPosX.getValueFactory().setValue(0);
		trigPosY.getValueFactory().setValue(0);
		xpGained.getValueFactory().setValue(0);
		numAdventures.getValueFactory().setValue(0);
		telPosX.getValueFactory().setValue(0);
		telPosY.getValueFactory().setValue(0);
		mapOptions.getSelectionModel().clearAndSelect(0);
		questCompletionActionType.getSelectionModel().clearAndSelect(0);
		questCompletionActionParam.setValue(null);
		startDate.setValue(null);
		endDate.setValue(null);
	}

	/**
	 * Create the command and persist it to the DB
	 */
	@Override
	public void save()
	{
		AddQuestModal modal = AddQuestModal.getInstance();
		CommandAddQuest command = new CommandAddQuest(
				modal.getTitle().getText(),
				modal.getDesc().getText(),
				modal.getMapOptions().getValue(),
				new Position(modal.getTrigPosY().getValue(), modal.getTrigPosX().getValue()),
				modal.xpGained.getValue(),
				modal.getNumAdventures().getValue(),
				modal.getQuestCompletionActionType().getValue(),
				new GameLocationDTO(
						modal.getQuestCompletionActionParam().getValue(),
						new Position(
								modal.getTelPosY().getValue(),
								modal.getTelPosX().getValue())),
				convertLocalDateToDate(modal.startDate.getValue()),
				convertLocalDateToDate(modal.endDate.getValue())
		);

		ModelFacade.getSingleton().queueCommand(command);
		resetFields();
	}

	/**
	 * @return The quest completion action parameter
	 */
	public ComboBox<String> getQuestCompletionActionParam()
	{
		return questCompletionActionParam;
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
	 * Cancel addition of a quest
	 */
	@Override
	public void cancel()
	{
		resetFields();

	}

	/**
	 * Reset the add quest modal
	 */
	@Override
	public void reset()
	{
		resetFields();

	}

	/**
	 * @return the singleton instance of the add quest modal
	 */
	public static AddQuestModal getInstance()
	{
		if (instance == null)
		{
			instance = new AddQuestModal("AddAdventureModal", "Add Quest", 400, 620);
		}
		return instance;
	}

	/**
	 * @return the title of the quest from the modal
	 */
	public TextField getTitle()
	{
		return title;
	}

	/**
	 * @return the description of the quest from the modal
	 */
	public TextField getDesc()
	{
		return desc;
	}

	/**
	 * @return the x trigger position of the quest from the modal
	 */
	public Spinner<Integer> getTrigPosX()
	{
		return trigPosX;
	}

	/**
	 * @return the y trigger position of the quest from the modal
	 */
	public Spinner<Integer> getTrigPosY()
	{
		return trigPosY;
	}

	/**
	 * @return the experience of the quest from the modal
	 */
	public Spinner<Integer> getXpGained()
	{
		return xpGained;
	}

	/**
	 * @return the number of adventures of the quest from the modal
	 */
	public Spinner<Integer> getNumAdventures()
	{
		return numAdventures;
	}

	/**
	 * @return the x teleport position of the quest from the modal
	 */
	public Spinner<Integer> getTelPosX()
	{
		return telPosX;
	}

	/**
	 * @return the y teleport position of the quest from the modal
	 */
	public Spinner<Integer> getTelPosY()
	{
		return telPosY;
	}

	/**
	 * @return the map options of the quest from the modal
	 */
	public ComboBox<String> getMapOptions()
	{
		return mapOptions;
	}

	/**
	 * @return the quest completion action type of the quest from the modal
	 */
	public ComboBox<QuestCompletionActionType> getQuestCompletionActionType()
	{
		return questCompletionActionType;
	}

	/**
	 * @return the starting date of the quest from the modal
	 */
	public DatePicker getStartDate()
	{
		return startDate;
	}

	/**
	 * @return the ending date of the quest from the modal
	 */
	public DatePicker getEndDate()
	{
		return endDate;
	}

}
