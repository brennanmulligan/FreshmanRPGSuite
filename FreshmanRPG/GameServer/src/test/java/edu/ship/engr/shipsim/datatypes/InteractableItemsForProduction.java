package edu.ship.engr.shipsim.datatypes;

import edu.ship.engr.shipsim.criteria.CriteriaIntegerDTO;
import edu.ship.engr.shipsim.criteria.CriteriaStringDTO;
import edu.ship.engr.shipsim.criteria.InteractableItemActionParameter;
import edu.ship.engr.shipsim.dataENUM.InteractableItemActionType;

/**
 * The items that are in the test database
 *
 * @author Elisabeth Ostrow, Jake Moore
 */
public enum InteractableItemsForProduction
{
    /**
     * Buffer Book
     */
    BOOK(1, "Buff Book", 1, 1, InteractableItemActionType.BUFF,
            new CriteriaIntegerDTO(50), "quad.tmx"),

    /**
     * Chest with an item
     */
    CHEST(2, "Item Chest", 5, 5, InteractableItemActionType.MESSAGE,
            new CriteriaStringDTO("This is a testing message"), "quad.tmx"),

    /**
     * Test item for demo purposes
     */
    FIREPLACE(3, "Test Item", 3, 7, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("OnBoarding Quest Item Clicked"), "sortingRoom.tmx"),

    /**
     * Test item for buff
     */
    BUFF_TEST(4, "Buff Test", 7, 52, InteractableItemActionType.BUFF, new CriteriaIntegerDTO(50), "library.tmx"),

    /**
     * Test item for display message
     */
    MSG_TEST(5, "Message Test", 6, 54, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is a test message"), "library.tmx"),

    /**
     * returns a popup message
     */
    BOOKSHELF(6, "Bookshelf", 0, 5, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("You used the bookshelf. It has nothing interesting in it"), "quad.tmx"),

    /**
     * Drink a shiny potion
     */
    XP_BUFF(7, "Potion", 2, 2, InteractableItemActionType.BUFF, new CriteriaIntegerDTO(50), "sortingRoom.tmx"),

    /**
     * Find the Recipe on the Tree
     */
    TEA_RECIPE(8, "Tea Recipe", 66, 39, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("You found a tea recipe!"), "quad.tmx"),

    /**
     * Pick the flowers near spawn for the recipe
     */
    TEA_FLOWER_1(9, "Red Flower", 56, 41, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("You found a red flower. It would taste delicious in a tea.  "), "quad.tmx"),

    /**
     *
     */
    TEA_FLOWER_2(10, "Purple Flower", 56, 45, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("You found a purple flower. It would taste delicious in a tea.  "), "quad.tmx"),

    /**
     *
     */
    TEA_FLOWER_3(11, "Pink Flower", 56, 58, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("You found a pink flower. It would taste delicious in a tea.  "), "quad.tmx"),

    /**
     *
     */
    TEA_FLOWER_4(12, "Yellow Flower", 56, 62, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("You found a yellow flower. It would taste delicious in a tea.  "), "quad.tmx"),

    TRIGGER_QUEST(14, "Quest Triggering Item", 50, 37, InteractableItemActionType.QUEST_TRIGGER, new CriteriaStringDTO("105,106,107"), "quad.tmx"),

    TRIGGER_QUEST_MESSAGE(15, "Message Triggering Item", 83, 30, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("hello"), "quad.tmx"),

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
    MESSAGE_BOARD_THE_GREEN(18, "Green Message Board", 84, 12, InteractableItemActionType.BOARD, new CriteriaStringDTO("Welcome to the Green"), "quad.tmx"),

    /**
     *
     */
    MESSAGE_BOARD_REC_CENTER(19, "Rec Center Message Board", 7, 6, InteractableItemActionType.BOARD, new CriteriaStringDTO("Welcome to the Rec Center"), "recCenter.tmx"),

    /**
     *
     */
    MESSAGE_BOARD_LIBRARY(20, "Library Message Board", 5, 48, InteractableItemActionType.BOARD, new CriteriaStringDTO("Welcome to Library"), "library.tmx"),


    SECRET_TREE(22, "Secret Tree", 119, 74, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("The secret password is.... rubber ducky"), "quad.tmx"),

    SIGN_CUB(23, "Cub Sign", 28, 92, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is the CUB."), "quad.tmx"),

    SIGN_LIBRARY(24, "Library Sign", 51, 54, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is the Library."), "quad.tmx"),

    SIGN_GROVE(25, "Grove Sign", 18, 25, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is Grove Hall."), "quad.tmx"),

    SIGN_DHC(26, "DHC Sign", 99, 15, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is Dauphin Humanities Center."), "quad.tmx"),

    SIGN_FISHBOWL(27, "Fishbowl Sign", 75, 23, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is MCT."), "quad.tmx"),

    SIGN_SHIPPEN(28, "Shippen Hall Sign", 120, 49, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is Shippen Hall."), "quad.tmx"),

    SIGN_FSC_UPPER(29, "FSC Upper Sign", 65, 84, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is Franklin Science Center."), "quad.tmx"),

    SIGN_FSC_LOWER(30, "FSC Lower Sign", 99, 87, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is Franklin Science Center."), "quad.tmx"),

    SIGN_REC_CENTER(31, "Rec Center Sign", 30, 27, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is the Rec Center."), "outsideOfMowery.tmx"),
    SIGN_MOWREY(32, "Mowrey Sign", 15, 71, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is the Mowrey Tutoring Center."), "outsideOfMowrey.tmx"),
    SIGN_REISNER(33, "Reisner Sign", 67, 59, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is the Reisner Dining Hall."), "outsideOfMowrey.tmx"),
    SIGN_HEALTH_CENTER(34, "Health Center Sign", 95, 63, InteractableItemActionType.MESSAGE, new CriteriaStringDTO("This is the Etters Health Center."), "outsideOfMowrey.tmx");


    private final int itemID;
    private final String name;
    private final int row;
    private final int col;
    private final InteractableItemActionType action;
    private final InteractableItemActionParameter actionParam;
    private final String mapName;

    /**
     * Constructor
     */
    InteractableItemsForProduction(int itemID, String name, int row, int col, InteractableItemActionType action, InteractableItemActionParameter param, String mapName)
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
