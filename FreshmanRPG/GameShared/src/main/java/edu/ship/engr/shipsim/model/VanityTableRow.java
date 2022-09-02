package edu.ship.engr.shipsim.model;

/**
 * Holds information from vanity item in the database
 */
public class VanityTableRow
{
    private final int id;
    private final int vanityType;
    private final String name;
    private final String description;
    private final String textureName;
    private final int price;
    private final int isWearing;

    public VanityTableRow(int id, int vanityType, String name, String description, String textureName)
    {
        this(id, vanityType, name, description, textureName, 0);
    }

    /**
     * Creates a mock of a row of VanityTable
     *
     * @param id          the id of the item
     * @param vanityType  the type of vanity
     * @param name        vanity name
     * @param description description of the vanity
     * @param textureName the name of the texture representing this vanity item
     * @param price       the price of the vanity item
     */
    public VanityTableRow(int id, int vanityType, String name, String description, String textureName, int price)
    {
        this(id, vanityType, name, description, textureName, price, 0);
    }

    /**
     * Creates a mock of a row of VanityTable
     *
     * @param id          the id of the item
     * @param vanityType  the type of vanity
     * @param name        vanity name
     * @param description description of the vanity
     * @param textureName the name of the texture representing this vanity item
     * @param price       the price of the vanity item
     * @param isWearing   1 is the player is wearing it, 0 if not
     */
    public VanityTableRow(int id, int vanityType, String name, String description, String textureName, int price, int isWearing)
    {
        this.id = id;
        this.vanityType = vanityType;
        this.name = name;
        this.description = description;
        this.textureName = textureName;
        this.price = price;
        this.isWearing = isWearing;
    }

    /**
     * @return the ID of the vanity item
     */
    public int getId()
    {
        return id;
    }

    /**
     * @return the type of the vanity item
     */
    public int getVanityType()
    {
        return vanityType;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return the texture name
     */
    public String getTextureName()
    {
        return textureName;
    }

    /**
     * @return the price
     */
    public int getPrice()
    {
        return price;
    }

    /**
     * @return 1 if the player is wearing it, 0 if not
     */
    public int getIsWearing()
    {
        return isWearing;
    }
}
