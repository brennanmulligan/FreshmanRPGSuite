package datatypes;

import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.InteractableItemActionParameter;
import criteria.InteractableNullAction;
import criteria.QuestListCompletionParameter;
import dataENUM.InteractableItemActionType;
import datatypes.Position;

import java.util.ArrayList;

/**
 * The items that are in the test database
 *
 * @author Elisabeth Ostrow, Jake Moore
 */
public enum InteractableItemsForTest
{
	/**
	 * Buffer Book
	 */
	BOOK(1, "Buff Book", 0, 0, InteractableItemActionType.BUFF, new CriteriaIntegerDTO(50), "theGreen.tmx"),

	/**
	 * Chest with an item
	 */
	CHEST(2, "Item Chest", 1, 1, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is a testing message"), "theGreen.tmx"),

	/**
	 * Test item for demo purposes
	 */
	FIREPLACE(3, "Test Item", 3, 7, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("OnBoarding Quest Item Clicked"), "sortingRoom.tmx"),

	/**
	 * Test item for buff
	 */
	BUFF_TEST(4, "Buff Test", 7, 52, InteractableItemActionType.BUFF, new CriteriaIntegerDTO(50), "homework.tmx"),

	/**
	 * Test item for display message
	 */
	MSG_TEST(5, "Message Test", 6, 54, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is a test message"), "homework.tmx"),

	/**
	 * returns a popup message
	 */
	BOOKSHELF(6, "Bookshelf", 0, 5, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("You used the bookshelf. It has nothing interesting in it"), "theGreen.tmx"),

	/**
	 * Drink a shiny potion
	 */
	XP_BUFF(7, "Potion", 2, 2, InteractableItemActionType.BUFF, new CriteriaIntegerDTO(50), "sortingRoom.tmx"),

	/**
	 * Find the Recipe on the Tree
	 */
	TEA_RECIPE(8, "Tea Recipe", 83, 42, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("You found a tea recipe!"), "theGreen.tmx"),

	/**
	 * Pick the flowers near spawn for the recipe
	 */
	TEA_FLOWER_1(9, "Red Flower", 86, 2, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("You found a red flower. It would taste delicious in a tea.  "), "theGreen.tmx"),

	/**
	 *
	 */
	TEA_FLOWER_2(10, "Purple Flower", 86, 4, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("You found a purple flower. It would taste delicious in a tea.  "), "theGreen.tmx"),

	/**
	 *
	 */
	TEA_FLOWER_3(11, "Pink Flower", 86, 6, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("You found a pink flower. It would taste delicious in a tea.  "), "theGreen.tmx"),

	/**
	 *
	 */
	TEA_FLOWER_4(12, "Yellow Flower", 86, 8, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("You found a yellow flower. It would taste delicious in a tea.  "), "theGreen.tmx"),

	TRIGGER_QUEST(14,"Quest Triggering Item",83,33,InteractableItemActionType.QUEST_TRIGGER,new CriteriaStringDTO("105,106,107"),"theGreen.tmx"),

	TRIGGER_QUEST_MESSAGE(15,"Message Triggering Item",83,30,InteractableItemActionType.MESSAGE, new CriteriaStringDTO("hello"),"theGreen.tmx"),

	/**
	 *
	 */
	MESSAGE_BOARD_MCT(16, "MCT Message Board", 50, 43, InteractableItemActionType.BOARD, new CriteriaStringDTO("Welcome to MCT"), "mct1.tmx"),

	/**
	 *
	 */
	MESSAGE_BOARD_SORTING_ROOM(17, "Sorting Room Message Board", 9, 11, InteractableItemActionType.BOARD, new CriteriaStringDTO("Welcome to the Sorting Room"), "sortingRoom.tmx"),

	/**
	 *
	 */
	MESSAGE_BOARD_THE_GREEN(18, "Green Message Board", 84, 12, InteractableItemActionType.BOARD, new CriteriaStringDTO("Welcome to the Green"), "theGreen.tmx"),

	/**
	 *
	 */
	MESSAGE_BOARD_QUIZNAZIUM(19, "Quiznazium Message Board", 7, 6, InteractableItemActionType.BOARD, new CriteriaStringDTO("Welcome to the Quiznazium"), "quiznazium.tmx"),

	/**
	 *
	 */
	MESSAGE_BOARD_STUDY_HALL(20, "Study Hall Message Board", 5, 48, InteractableItemActionType.BOARD, new CriteriaStringDTO("Welcome to Study Hall"), "homework.tmx"),

	/**
	 *
	 */
	MSG_BOARD_CURRENT(21, "Current Message Board", 22, 21, InteractableItemActionType.BOARD, new CriteriaStringDTO("Welcome to the Current"), "current.tmx"),

	SECRET_TREE(22, "Secret Tree", 86, 29, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("The secret password is.... rubber ducky"), "theGreen.tmx");

	private int itemID;
	private String name;
	private int row;
	private int col;
	private InteractableItemActionType action;
	private InteractableItemActionParameter actionParam;
	private String mapName;

	/**
	 * Constructor
	 *
	 * @param itemID
	 * @param name
	 * @param row
	 * @param col
	 * @param action
	 */
	InteractableItemsForTest(int itemID, String name, int row, int col, InteractableItemActionType action, InteractableItemActionParameter param, String mapName)
	{
		this.itemID = itemID;
		this.name = name;
		this.row = row;
		this.col = col;
		this.action = action;
		this.actionParam = param;
		this.mapName = mapName;
	}

	/**
	 * gets itemID
	 *
	 * @return itemID
	 */
	public int getItemID()
	{
		return this.itemID;
	}

	/**
	 * gets name
	 *
	 * @return name
	 */
	public String getName()
	{
		return this.name;
	}

	/**
	 * gets position
	 *
	 * @return position
	 */
	public Position getPosition()
	{
		return new Position(this.row, this.col);
	}

	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return ((CriteriaStringDTO) (this.actionParam)).getString();
	}

	/**
	 * @return the current exp point pool.
	 */
	public int getExperiencePointPool()
	{
		return ((CriteriaIntegerDTO) (this.actionParam)).getTarget();
	}

	/**
	 * gets action
	 *
	 * @return action
	 */
	public InteractableItemActionType getActionType()
	{
		return this.action;
	}

	/**
	 * gets action
	 *
	 * @return action
	 */
	public InteractableItemActionParameter getActionParam()
	{
		return this.actionParam;
	}

	/**
	 * gets mapName
	 *
	 * @return mapName
	 */
	public String getMapName()
	{
		return this.mapName;
	}
}
