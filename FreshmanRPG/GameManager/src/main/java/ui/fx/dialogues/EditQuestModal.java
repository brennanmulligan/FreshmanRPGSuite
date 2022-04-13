package ui.fx.dialogues;

import java.sql.Date;
import java.time.LocalDate;

import criteria.GameLocationDTO;
import criteria.QuestCompletionActionParameter;
import dataENUM.QuestCompletionActionType;
import datatypes.Position;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.CommandEditQuest;
import model.GameManagerMapManager;
import model.ModelFacade;

/**
 *
 * @author Abe Loscher and Josh McMillen
 *
 */
public class EditQuestModal extends Modal
{

	private static EditQuestModal instance;

	private int questID;
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
	 * @param id
	 *            the quest's unique ID
	 * @param label
	 *            a description of the quest used to label the modal
	 */
	private EditQuestModal(String id, String label)
	{
		super(id, label, 400, 620);

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
		questCompletionActionType.getSelectionModel().selectedItemProperty()
				.addListener((options, oldValue, newValue) ->
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
				});
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
		addQuestBox.getChildren().addAll(this.generateElement("Title", title),
				this.generateElement("Description:", desc), this.generateElement("Map Name:", mapOptions),
				this.generateElement("Trigger Position X:", trigPosX),
				this.generateElement("Trigger Position Y:", trigPosY), this.generateElement("EXP Gained:", xpGained),
				this.generateElement("# of Adventures for Completion:", numAdventures),
				this.generateElement("Completion Action Type:", questCompletionActionType),
				this.generateElement("Completion Action Param:", questCompletionActionParam),
				this.generateElement("Teleport Position X:", telPosX),
				this.generateElement("Teleport Position Y:", telPosY), this.generateElement("Start Date:", startDate),
				this.generateElement("End Date:", endDate));

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

	/**
	 * @see ui.fx.dialogues.Modal#save()
	 */
	@Override
	public void save()
	{
		EditQuestModal modal = EditQuestModal.getInstance();
		CommandEditQuest command = new CommandEditQuest(modal.getQuestId(), modal.getTitle().getText(),
				modal.getDesc().getText(), modal.getMapOptions().getSelectionModel().getSelectedItem(),
				new Position(modal.getTrigPosY().getValue(), modal.getTrigPosX().getValue()),
				modal.getXpGained().getValue(), modal.getNumAdventures().getValue(),
				modal.getQuestCompletionActionType().getValue(),
				new GameLocationDTO(modal.getQuestCompletionActionParam().getValue(),
						new Position(modal.getTelPosY().getValue(), modal.getTelPosX().getValue())),
				convertLocalDateToDate(modal.startDate.getValue()), convertLocalDateToDate(modal.endDate.getValue()));
		ModelFacade.getSingleton().queueCommand(command);
	}

	/**
	 * @param param
	 *            description of what happens when the quest is complete
	 */
	public void setQuestCompletionActionParam(QuestCompletionActionParameter param)
	{
		if (param != null)
		{
			GameLocationDTO localParam = (GameLocationDTO) param;
			this.questCompletionActionParam.setValue(localParam.getMapName());
			this.setTelPosX(localParam.getPosition().getColumn());
			this.setTelPosY(localParam.getPosition().getRow());
		}
	}

	/**
	 * @return description of what happens when the quest is complete
	 */
	public ComboBox<String> getQuestCompletionActionParam()
	{
		return questCompletionActionParam;
	}

	/**
	 * Convert localdate to java util date
	 *
	 * @param localDate
	 *            localdate to be converted
	 * @return java util date
	 */
	public Date convertLocalDateToDate(LocalDate localDate)
	{
		Date javaUtilDate = Date.valueOf(localDate);
		return javaUtilDate;
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

	/**
	 * @return the only one of these in the system
	 */
	public static EditQuestModal getInstance()
	{
		if (instance == null)
		{
			instance = new EditQuestModal("AddAdventureModal", "Edit Quest");
		}
		return instance;
	}

	/**
	 * @param id
	 *            the unique id of the quest being edited
	 */
	public void setQuestId(int id)
	{
		this.questID = id;
	}

	/**
	 * @return the id of the quest we are editing
	 */
	public int getQuestId()
	{
		return this.questID;
	}

	/**
	 * @param title
	 *            the title of the quest
	 */
	public void setTitle(String title)
	{
		this.title.setText(title);
	}

	/**
	 * @return the title of the quest
	 */
	public TextField getTitle()
	{
		return title;
	}

	/**
	 * @param desc
	 *            description of the quest
	 */
	public void setDesc(String desc)
	{
		this.desc.setText(desc);
	}

	/**
	 * @return description of the quest
	 */
	public TextField getDesc()
	{
		return desc;
	}

	/**
	 * @param trigPos
	 *            the position that should trigger the quest (make it active)
	 */
	public void setTrigPos(Position trigPos)
	{
		this.trigPosX.getValueFactory().setValue(trigPos.getColumn());
		this.trigPosY.getValueFactory().setValue(trigPos.getRow());
	}

	/**
	 * @return the x value of the trigger position
	 */
	public Spinner<Integer> getTrigPosX()
	{
		return trigPosX;
	}

	/**
	 * @return the y value of the trigger position
	 */
	public Spinner<Integer> getTrigPosY()
	{
		return trigPosY;
	}

	/**
	 * @param xpGained
	 *            the experience points gained on quest fulfillment
	 */
	public void setXpGained(int xpGained)
	{
		this.xpGained.getValueFactory().setValue(xpGained);
	}

	/**
	 * @return the experience points gained on quest fulfillment
	 */
	public Spinner<Integer> getXpGained()
	{
		return xpGained;
	}

	/**
	 * @param numAdventures
	 *            the number of adventures required for quest fulfillment
	 */
	public void setNumAdventures(int numAdventures)
	{
		this.numAdventures.getValueFactory().setValue(numAdventures);
	}

	/**
	 * @return the number of adventures required for quest fulfillment
	 */
	public Spinner<Integer> getNumAdventures()
	{
		return numAdventures;
	}

	/**
	 * @param telPosX
	 *            the x value of the position we should teleport to on quest
	 *            fulfillment
	 */
	public void setTelPosX(int telPosX)
	{
		this.telPosX.getValueFactory().setValue(telPosX);
	}

	/**
	 * @return the x value of the position we should teleport to on quest
	 *         fulfillment
	 */
	public Spinner<Integer> getTelPosX()
	{
		return telPosX;
	}

	/**
	 * @param telPosY
	 *            the y value of the position we should teleport to on quest
	 *            fulfillment
	 */
	public void setTelPosY(int telPosY)
	{
		this.telPosY.getValueFactory().setValue(telPosY);
	}

	/**
	 * @return the y value of the position we should teleport to on quest
	 *         fulfillment
	 */
	public Spinner<Integer> getTelPosY()
	{
		return telPosY;
	}

	/**
	 * @param mapOption
	 *            the map we should teleport to on quest fulfillment
	 */
	public void setMapOption(String mapOption)
	{
		this.mapOptions.setValue(mapOption);
	}

	/**
	 * @return the map of the position we should teleport to on quest fulfillment
	 */
	public ComboBox<String> getMapOptions()
	{
		return mapOptions;
	}

	/**
	 * @param actionType
	 *            the type of action that should be take on quest completion
	 */
	public void setQuestCompletionActionType(QuestCompletionActionType actionType)
	{
		this.questCompletionActionType.setValue(actionType);
	}

	/**
	 * @return the type of action that should be take on quest completion
	 */
	public ComboBox<QuestCompletionActionType> getQuestCompletionActionType()
	{
		return questCompletionActionType;
	}

	/**
	 * @param startDate
	 *            the date the quest becomes available
	 */
	public void setStartDate(LocalDate startDate)
	{
		this.startDate.setValue(startDate);
	}

	/**
	 * @return the date the quest becomes available
	 */
	public DatePicker getStartDate()
	{
		return startDate;
	}

	/**
	 * @param endDate
	 *            the date the quest is no longer available
	 */
	public void setEndDate(LocalDate endDate)
	{
		this.endDate.setValue(endDate);
	}

	/**
	 * @return the date the quest is no longer available
	 */
	public DatePicker getEndDate()
	{
		return endDate;
	}

}
