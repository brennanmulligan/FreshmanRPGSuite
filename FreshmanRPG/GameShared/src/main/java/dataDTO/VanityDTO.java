package dataDTO;

import datatypes.VanityType;

import java.util.Objects;

import java.io.Serializable;

/**
 * Class for the vanity item data transfer object
 *
 * @author Jake, Aaron, Ktyal
 */
public class VanityDTO implements Serializable
{
    private int ID;
    private String name;
    private String description;
    private String textureName;
    private VanityType vanityType;
    private int price;

    public VanityDTO()
    {
        ID = -1;
        name = "";
        description = "";
        textureName = "";
        vanityType = VanityType.BODY;
        price = 0;
    }

    /**
     * Constructor for a VanityDTO
     * @param ID id of vanity item
     * @param name name of vanity item
     * @param description description of vanity item
     * @param textureName name of the texture for the item
     * @param vanityType type of vanity
     */
    public VanityDTO(int ID, String name, String description, String textureName, VanityType vanityType)
    {
        this(ID, name, description, textureName, vanityType, 0);
    }

    /**
     * Constructor for a VanityDTO
     * @param ID id of vanity item
     * @param name name of vanity item
     * @param description description of vanity item
     * @param textureName name of the texture for the item
     * @param vanityType type of vanity
     * @param price the price of the item
     */
    public VanityDTO(int ID, String name, String description, String textureName, VanityType vanityType, int price)
    {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.textureName = textureName;
        this.vanityType = vanityType;
        this.price = price;
    }

    /**
     * @return vanity id
     */
    public int getID()
    {
        return ID;
    }

    /**
     * @return vanity item name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return description of item
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @return name of the corresponding texture
     */
    public String getTextureName()
    {
        return textureName;
    }

    /**
     * @return type of vanity
     */
    public VanityType getVanityType()
    {
        return vanityType;
    }

    /**
     * @return the price of the item
     */
    public int getPrice()
    {
        return price;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        VanityDTO vanityDTO = (VanityDTO) o;
        return ID == vanityDTO.ID && price == vanityDTO.price && Objects.equals(name, vanityDTO.name) && Objects.equals(description, vanityDTO.description) && Objects.equals(textureName, vanityDTO.textureName) && vanityType == vanityDTO.vanityType;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(ID, name, description, textureName, vanityType, price);
    }

    /**
     * @return the name of the vanity
     */
    public String toString()
    {
        return name;
    }
}
