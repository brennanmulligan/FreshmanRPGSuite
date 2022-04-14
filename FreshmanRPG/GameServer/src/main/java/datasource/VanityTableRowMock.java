package datasource;

import dataDTO.VanityDTO;
import datatypes.VanityType;

import java.util.ArrayList;

/**
 * A mock implementation of a vanity table row
 *
 * @author Jake Harrington, Aaron Wertman
 */

public class VanityTableRowMock
{
    int ID;
    String name;
    String description;
    String textureName;
    VanityType vanityType;

    /**
     * Constructor for a mock VanityTableRow
     * @param ID id of vanity item
     * @param name name of vanity item
     * @param description description of vanity item
     * @param textureName name of the texture for the item
     * @param vanityType type of vanity
     */
    public VanityTableRowMock(int ID, String name, String description, String textureName, VanityType vanityType)
    {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.textureName = textureName;
        this.vanityType = vanityType;
    }
}
