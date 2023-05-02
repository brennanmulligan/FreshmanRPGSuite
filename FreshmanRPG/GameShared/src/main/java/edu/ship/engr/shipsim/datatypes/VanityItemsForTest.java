package edu.ship.engr.shipsim.datatypes;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.util.ArrayList;

/**
 * The Vanity items that are in the test database.
 *
 * @author andrewjanuszko
 */
public enum VanityItemsForTest
{
    /**
     * Represents a HAT vanity object.
     */
    NO_HAT(0, "No Hat", "", "hat_none", 0,
            false, false, true, true),

    BEANIE_HAT(0, "Shippensburg University Beanie", "A little beanie.", "hat_beanie", 1,
            true, false, false, false),

    DUCK_HAT(0, "The one and only DUCK", "The Mythical Duck.", "hat_duck", 2,
            true, false, false, false),

    MERLIN_HAT(0, "Merlin's Hat", "Merlin's Hat", "hat_merlin", 10,
            true, false, false, false),

    ANDY_HAT(0, "Andy's Hat", "Andy's Hat", "hat_andy", 5,
            false, false, false, false),

    BIG_RED_HAT(0, "Big Red's Hat", "Big Red's Hat", "hat_br", 5,
            false, false, false, false),

    GRAD_CAP_HAT(0, "Graduation Cap", "A Hat for smart people", "Grad_Cap", 5,
            false, false, false, false),

    /**
     * Represents a HAIR vanity object.
     */
    //NoHair(50,1,"Bald","","hair_none",0),

    BLONDE_HAIR(1, "Blonde Hair", "A hair that the player can wear", "hair_blonde", 2,
            false, false, false, false),

    BLACK_HAIR(1, "Black Hair", "Black Hair", "hair_black", 3,
            false, false, true, true),

    DARK_BROWN_HAIR(1, "Dark Brown Hair", "Dark Brown Har", "hair_dark_brown", 4,
            false, false, false, false),

    GRAY_HAIR(1, "Gray Hair", "Gray Hair", "hair_gray", 4,
            false, false, false, false),

    LIGHT_BROWN_HAIR(1, "Light Brown Hair", "Light Brown Hair", "hair_light_brown", 5,
            false, false, false, false),

    MERLIN_HAIR(1, "Merlin's Hair", "", "hair_merlin", 10,
            false, false, false, false),

    NULL_POINTER_HAIR(1, "Null Pointer Hair", "", "hair_null_pointer", 2,
            false, false, false, false),

    LONG_NULL_POINTER_HAIR(1, "Long Null Pointer Hair", "", "hair_null_pointer_f", 2,
            false, false, false, false),

    OFF_BY_ONE_HAIR(1, "Off By One Hair", "", "hair_off_by_one", 2,
            false, false, false, false),

    OUT_OF_BOUNDS_HAIR(1, "Out Of Bounds Hair", "", "hair_out_of_bounds", 2,
            false, false, false, false),

    TUTOR_HAIR(1, "Tutor Hair", "", "hair_tutor", 2,
            false, false, false, false),

    BIG_RED_HAIR(1, "Big Red Hair", "", "hair_br", 2,
            false, false, false, false),

    NERD_HAIR(1, "Nerd Hair", "Balding??? KEKW", "Nerd Hair", 0,
            false, false, false, false),
    /**
     * Represents a SHIRT vanity object.
     */
    //NoShirt(100,2,"","","shirt_none",0),

    RED_SHIRT(2, "Red Shirt", "A shirt item that cna be worn by the user", "shirt_red", 2,
            true, false, false, false),

    BLUE_SHIRT(2, "Blue Shirt", "", "shirt_blue", 2,
            true, false, false, false),

    GREEN_SHIRT(2, "Green Shirt", "", "shirt_green", 2,
            true, false, false, false),

    MERLIN_SHIRT(2, "Merlin's Shirt", "", "shirt_merlin", 10,
            false, false, false, false),

    NULL_POINTER_SHIRT(2, "Null Pointer Shirt", "", "shirt_null_pointer", 2,
            false, false, false, false),

    OFF_BY_ONE_SHIRT(2, "Off By One Shirt", "", "shirt_off_by_one", 3,
            false, false, false, false),

    OUT_OF_BOUNDS_SHIRT(2, "Out Of Bounds Shirt", "", "shirt_out_of_bounds", 4,
            false, false, false, false),

    TUTOR_SHIRT(2, "Tutor Shirt", "", "shirt_tutor", 5,
            false, false, false, false),

    BIG_RED_SHIRT(2, "Big Red Shirt", "", "shirt_br", 5,
            false, false, false, false),

    BASE_SHIRT(2, "Default Shirt", "", "base_shirt", 0,
            false, false, true, true),

    NERD_SHIRT(2, "Nerd Shirt", "Shirt for nerds", "Nerd Shirt", 0,
            false, false, false, false),

    /**
     * Represents a PANTS vanity object.
     */
    NO_PANTS(3, "No Pants", "", "pants_none", 0,
            false, false, false, false),

    WHITE_PANTS(3, "White Pants", "A pants item that the player can wear", "pants_white", 4,
            true, false, false, false),

    BASE_PANTS(3, "Black Pants", "", "base_pants", 3,
            false, false, true, true),

    BIG_RED_PANTS(3, "Big Red Pants", "", "pants_br", 3,
            false, false, false, false),

    /**
     * Represents a SHOES vanity object.
     */

    NO_SHOES(4, "No Shoes", "", "shoes_none", 0,
            false, false, true, true),

    FANCY_SHOES(4, "Fancy Shoes", "A pair of shoes the player can wear", "shoes_fancy", 5,
            true, false, false, false),

    BLACK_SHOES(4, "Black Shoes", "", "shoes_black", 2,
            true, false, false, false),

    RED_SHOES(4, "Red Shoes", "", "shoes_red", 4,
            false, false, false, false),

    BIG_RED_SHOES(4, "Big Red Shoes", "", "shoes_br", 4,
            false, false, false, false),


    /**
     * Represents a BODY vanity object. Forgive me.
     */
    SKIN_DEFAULT(5, "Skin default", "A body item that can be worn by a user.", "skin_default", 0,
            false, false, true, true),

    SKIN_ZERO(5, "Skin zero", "A body item that can be worn by a user.", "skin_a", 0,
            false, false, true, false),

    SKIN_ONE(5, "Skin one", "A body item that can be worn by a user.", "skin_b", 0,
            false, false, true, false),

    SKIN_TWO(5, "Skin two", "A body item that can be worn by a user.", "skin_c", 0,
            false, false, true, false),

    SKIN_THREE(5, "Skin three", "A body item that can be worn by a user.", "skin_d", 0,
            false, false, true, false),

    SKIN_FOUR(5, "Skin four", "A body item that can be worn by a user.", "skin_e", 0,
            false, false, true, false),

    SKIN_FIVE(5, "Skin five", "A body item that can be worn by a user.", "skin_f", 0,
            false, false, true, false),

    SKIN_SIX(5, "Skin six", "A body item that can be worn by a user.", "skin_g", 0,
            false, false, true, false),

    SKIN_SEVEN(5, "Skin seven", "A body item that can be worn by a user.", "skin_h", 0,
            false, false, true, false),

    SKIN_EIGHT(5, "Skin eight", "A body item that can be worn by a user.", "skin_i", 0,
            false, false, true, false),

    SKIN_NINE(5, "Skin nine", "A body item that can be worn by a user.", "skin_j", 0,
            false, false, true, false),

    SKIN_TEN(5, "Skin ten", "A body item that can be worn by a user.", "skin_k", 0,
            false, false, true, false),

    SKIN_ELEVEN(5, "Skin eleven", "A body item that can be worn by a user.", "skin_l", 0,
            false, false, true, false),

    SKIN_TWELVE(5, "Skin twelve", "A body item that can be worn by a user.", "skin_m", 0,
            false, false, true, false),

    SKIN_THIRTEEN(5, "Skin thirteen", "A body item that can be worn by a user.", "skin_n", 0,
            false, false, true, false),

    SKIN_FOURTEEN(5, "Skin fourteen", "A body item that can be worn by a user.", "skin_o", 0,
            false, false, true, false),

    SKIN_FIFTEEN(5, "Skin fifteen", "A body item that can be worn by a user.", "skin_p", 0,
            false, false, true, false),

    SKIN_SIXTEEN(5, "Skin sixteen", "A body item that can be worn by a user.", "skin_q", 0,
            false, false, true, false),

    SKIN_GHOST(5, "Skin ghost", "A body item that can be worn by a user.", "skin_z_ghost", 0,
            false, false, false, false),

    SKIN_BIG_RED(5, "Skin Big Red", "A body item that can be worn by a user.", "skin_br", 0,
            false, false, false, false),



    /**
     * Represents a EYES vanity object.
     */
    BLACK_EYES(6, "Black Eyes", "", "eyes_black", 0,
            false, false, true, true),

    GREEN_EYES(6, "Green Eyes", "", "eyes_green", 0,
            false, false, true, false),

    LIGHT_BLUE_EYES(6, "Light-blue Eyes", "", "eyes_lightblue", 0,
            false, false, true, false),

    ORANGE_EYES(6, "Orange Eyes", "", "eyes_orange", 0,
            false, false, true, false),

    PINK_EYES(6, "Pink Eyes", "", "eyes_pink", 0,
            false, false, true, false),

    PURPLE_EYES(6, "Purple Eyes", "", "eyes_purple", 0,
            false, false, true, false),

    RED_EYES(6, "Red Eyes", "", "eyes_red", 0,
            false, false, true, false),

    YELLOW_EYES(6, "Yellow Eyes", "", "eyes_yellow", 0,
            false, false, true, false),

    BROWN_EYES(6, "Brown Eyes", "", "eyes_brown", 0,
            false, false, true, false),

    BIG_RED_EYES(6, "Big Red Eyes", "", "eyes_br", 0,
            false, false, false, false),

    /**
     * Represent Bike Vanity Objects
     */
    BIKE(7, "Bike", "", "bike", 0,
            false, true, false, false),

    BIKE_NONE(7, "No Bike", "", "bike_none", 0,
            false, false, true, true);



    private final int vanityType;
    private final String name;
    private final String description;
    private final String textureName;
    private final int price;
    private final boolean isInShop;
    private final boolean isDeletable;
    private final boolean isDefault;
    private final boolean isDefaultEquipped;


    VanityItemsForTest(int vanityType, String name, String description, String textureName,
                       int price, boolean isInShop, boolean isDeletable,
                       boolean isDefault, boolean isDefaultEquipped)
    {
        this.vanityType = vanityType;
        this.name = name;
        this.description = description;
        this.textureName = textureName;
        this.price = price;
        this.isInShop = isInShop;
        this.isDeletable = isDeletable;
        this.isDefault = isDefault;
        this.isDefaultEquipped = isDefaultEquipped;
    }

    public static ArrayList<VanityDTO> getAllDefaultItems()
    {
        ArrayList<VanityDTO> defaultItems = new ArrayList<>();
        for (VanityItemsForTest v : VanityItemsForTest.values())
        {
            if(v.isDefault)
            {
                defaultItems.add(toVanityDTO(v));
            }
        }
        return defaultItems;
    }

    public static VanityItemsForTest getByID(int vanityID)
    {
        for(VanityItemsForTest v : VanityItemsForTest.values())
        {
            if (v.getId() == vanityID)
            {
                return v;
            }
        }
        return null;
    }
    public static ArrayList<VanityDTO> getAllShopItems()
    {
        ArrayList<VanityDTO> shopItems = new ArrayList<>();
        for (VanityItemsForTest v : VanityItemsForTest.values())
        {
            if(v.isInShop)
            {
                shopItems.add(toVanityDTO(v));
            }
        }
        return shopItems;
    }

    public static ArrayList<VanityDTO> getAllDeletableItems()
    {
        ArrayList<VanityDTO> deletableItems = new ArrayList<>();
        for (VanityItemsForTest v : VanityItemsForTest.values())
        {
            if(v.isDeletable)
            {
                deletableItems.add(toVanityDTO(v));
            }
        }
        return deletableItems;
    }

    public static VanityDTO toVanityDTO(VanityItemsForTest v)
    {
        return new VanityDTO(v.getId(), v.getName(), v.getDescription(),
                v.getTextureName(), v.getVanityType(), v.getPrice());
    }

    public boolean isInShop()
    {
        return isInShop;
    }

    public boolean isDeletable()
    {
        return isDeletable;
    }

    public boolean isDefault()
    {
        return isDefault;
    }

    public boolean isDefaultEquipped()
    {
        return isDefaultEquipped;
    }

    /**
     * Gets the id of the item.
     *
     * @return the id of the item.
     */
    public int getId()
    {
        return this.ordinal() + 1;
    }


    /**
     * Gets the vanity type of the item as an int.
     *
     * @return the vanity type of the item as an int.
     */
    public VanityType getVanityType()
    {
        return VanityType.fromInt(vanityType);
    }

    /**
     * Gets the name of the item.
     *
     * @return the name of the item.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Gets the description of the item.
     *
     * @return the description of the item.
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Gets the texture name for the item.
     *
     * @return the texture name for the item.
     */
    public String getTextureName()
    {
        return this.textureName;
    }

    /**
     * @return the price of the vanity
     */
    public int getPrice()
    {
        return price;
    }
}