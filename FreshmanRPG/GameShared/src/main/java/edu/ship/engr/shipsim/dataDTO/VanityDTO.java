package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.datatypes.VanityType;

import java.io.Serializable;

/**
 * Class for the vanity item data transfer object
 *
 * @author Jake, Aaron, Ktyal
 */
public class VanityDTO implements Serializable
{

    private int vanityID;
    private String name;
    private String description;
    private String textureName;
    private VanityType vanityType;
    private int price;

    public VanityDTO()
    {
        vanityID = -1;
        name = "";
        description = "";
        textureName = "";
        vanityType = VanityType.BODY;
        price = 0;
    }

    /**
     * Constructor for a VanityDTO
     *
     * @param vanityID    id of vanity item
     * @param name        name of vanity item
     * @param description description of vanity item
     * @param textureName name of the texture for the item
     * @param vanityType  type of vanity
     * @param price       the price of the item
     */
    public VanityDTO(int vanityID, String name, String description, String textureName,
                     VanityType vanityType, int price)
    {
        this.vanityID = vanityID;
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
        return vanityID;
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

        if (vanityID != vanityDTO.vanityID)
        {
            return false;
        }
        if (price != vanityDTO.price)
        {
            return false;
        }
        if (!name.equals(vanityDTO.name))
        {
            return false;
        }
        if (!description.equals(vanityDTO.description))
        {
            return false;
        }
        if (!textureName.equals(vanityDTO.textureName))
        {
            return false;
        }
        return vanityType == vanityDTO.vanityType;
    }

    @Override
    public int hashCode()
    {
        int result = vanityID;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + textureName.hashCode();
        result = 31 * result + vanityType.hashCode();
        result = 31 * result + price;
        return result;
    }

    /**
     * @return the name of the vanity
     */
    @Override
    public String toString()
    {
        return "VanityDTO{" +
                "vanityID=" + vanityID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", textureName='" + textureName + '\'' +
                ", vanityType=" + vanityType +
                ", price=" + price +
                '}';
    }
}
