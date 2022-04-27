package datatypes;

/**
 * The Vanity items that are in the test database.
 *
 * @author andrewjanuszko
 */
public enum VanityForTest
{

    /**
     * Represents a HAT vanity object.
     */
    NoHat(0,"No Hat","","hat_none",0),

    BeanieHat(0, "Shippensburg University Beanie", "A little beanie.", "hat_beanie", 1),

    DuckHat(0, "The one and only DUCK", "The Mythical Duck.", "hat_duck", 1),

    MerlinHat(0, "Merlin's Hat", "Merlin's Hat","hat_merlin", 10),

    AndyHat(0,"Andy's Hat","Andy's Hat","hat_andy",5),

    BR_Hat(0,"Big Red's Hat","Big Red's Hat","hat_br",5),

    /**
     * Represents a HAIR vanity object.
     */
    //NoHair(50,1,"Bald","","hair_none",0),

    BlondeHair(1, "Blonde Hair", "A hair that the player can wear", "hair_blonde", 2),

    BlackHair(1, "Black Hair","Black Hair","hair_black", 3),

    DarkBrownHair(1,"Dark Brown Hair","Dark Brown Har","hair_dark_brown",4),

    GrayHair(1,"Gray Hair","Gray Hair","hair_gray",4),

    LightBrownHair(1,"Light Brown Hair","Light Brown Hair","hair_light_brown",5),

    MerlinHair(1,"Merlin's Hair", "", "hair_merlin",10),

    NPHair(1,"Null Pointer Hair","","hair_null_pointer",2),

    LongNPHair(1,"Long Null Pointer Hair","","hair_null_pointer_f",2),

    OBOHair(1,"Off By One Hair","","hair_off_by_one",2),

    OOBHair(1,"Out Of Bounds Hair","","hair_out_of_bounds",2),

    TutorHair(1,"Tutor Hair","","hair_tutor",2),

    BR_Hair(1,"Big Red Hair","","hair_br",2),
    /**
     * Represents a SHIRT vanity object.
     */
    //NoShirt(100,2,"","","shirt_none",0),

    RedShirt(2, "Red Shirt", "A shirt item that cna be worn by the user", "shirt_red", 3),

    BlueShirt(2,"Blue Shirt","","shirt_blue",2),

    GreenShirt(2,"Green Shirt", "","shirt_green",2),

    MerlinShirt(2,"Merlin's Shirt","","shirt_merlin",10),

    NPShirt(2,"Null Pointer Shirt","","shirt_null_pointer",2),

    OBOShirt(2,"Off By One Shirt","","shirt_off_by_one",3),

    OOBShirt(2,"Out Of Bounds Shirt","","shirt_out_of_bounds",4),

    TutorShirt(2,"Tutor Shirt","","shirt_tutor",5),

    BR_Shirt(2,"Big Red Shirt","","shirt_br",5),

    Base_Shirt(2, "Default Shirt", "", "base_shirt", 0),

    /**
     * Represents a PANTS vanity object.
     */
    NoPants(3,"No Pants", "","pants_none",0),

    WhitePants(3, "White Pants", "A pants item that the player can wear", "pants_white", 4),

    BasePants(3,"Black Pants", "","base_pants",3),

    BR_Pants(3,"Big Red Pants", "","pants_br",3),

    /**
     * Represents a SHOES vanity object.
     */

    NoShoes(4,"No Shoes","","shoes_none",0),

    FancyShoes(4, "Fancy Shoes", "A pair of shoes the player can wear", "shoes_fancy", 5),

    BlackShoes(4,"Black Shoes","","shoes_black",2),

    RedShoes(4,"Red Shoes", "", "shoes_red",4),

    BR_Shoes(4,"Big Red Shoes", "", "shoes_br",4),


    /**
     * Represents a BODY vanity object. Forgive me.
     */
    SkinDefault(5, "Skin default", "A body item that can be worn by a user.", "skin_default", 0),
    SkinZero(5, "Skin zero", "A body item that can be worn by a user.", "skin_a", 0),
    SkinOne(5, "Skin one", "A body item that can be worn by a user.", "skin_b", 0),
    SkinTwo(5, "Skin two", "A body item that can be worn by a user.", "skin_c", 0),
    SkinThree(5, "Skin three", "A body item that can be worn by a user.", "skin_d", 0),
    SkinFour(5, "Skin four", "A body item that can be worn by a user.", "skin_e", 0),
    SkinFive(5, "Skin five", "A body item that can be worn by a user.", "skin_f", 0),
    SkinSix(5, "Skin six", "A body item that can be worn by a user.", "skin_g", 0),
    SkinSeven(5, "Skin seven", "A body item that can be worn by a user.", "skin_h", 0),
    SkinEight(5, "Skin eight", "A body item that can be worn by a user.", "skin_i", 0),
    SkinNine(5, "Skin nine", "A body item that can be worn by a user.", "skin_j", 0),
    SkinTen(5, "Skin ten", "A body item that can be worn by a user.", "skin_k", 0),
    SkinEleven(5, "Skin eleven", "A body item that can be worn by a user.", "skin_l", 0),
    SkinTwelve(5, "Skin twelve", "A body item that can be worn by a user.", "skin_m", 0),
    SkinThirteen(5, "Skin thirteen", "A body item that can be worn by a user.", "skin_n", 0),
    SkinFourteen(5, "Skin fourteen", "A body item that can be worn by a user.", "skin_o", 0),
    SkinFifteen(5, "Skin fifteen", "A body item that can be worn by a user.", "skin_p", 0),
    SkinSixteen(5, "Skin sixteen", "A body item that can be worn by a user.", "skin_q", 0),
    SkinGhost(5, "Skin ghost", "A body item that can be worn by a user.", "skin_z_ghost", 0),
    SkinBR(5, "Skin Big Red", "A body item that can be worn by a user.", "skin_br", 0),


    /**
     * Represents a EYES vanity object.
     */
    BlackEyes(6, "Black Eyes", "", "eyes_black",0),
    GreenEyes(6, "Green Eyes", "", "eyes_green",0),
    LightBlueEyes(6, "Light-blue Eyes", "", "eyes_lightblue",0),
    OrangeEyes(6, "Orange Eyes", "", "eyes_orange",0),
    PinkEyes(6, "Pink Eyes", "", "eyes_pink",0),
    PurpleEyes(6, "Purple Eyes", "", "eyes_purple",0),
    RedEyes(6, "Red Eyes", "", "eyes_red",0),
    YellowEyes(6, "Yellow Eyes", "", "eyes_yellow",0),
    BrownEyes(6, "Brown Eyes", "", "eyes_brown",0),
    BR_Eyes(6, "Big Red Eyes", "", "eyes_br",0);



    private final int vanityType;
    private final String name;
    private final String description;
    private final String textureName;
    private final int price;

    VanityForTest(int vanityType, String name, String description, String textureName, int price)
    {
        this.vanityType = vanityType;
        this.name = name;
        this.description = description;
        this.textureName = textureName;
        this.price = price;
    }

    /**
     * Gets the id of the item.
     * @return the id of the item.
     */
    public int getId()
    {
        return this.ordinal() + 1;
    }

    /**
     * Gets the vanity type of the item as an int.
     * @return the vanity type of the item as an int.
     */
    public int getVanityType()
    {
        return this.vanityType;
    }

    /**
     * Gets the name of the item.
     * @return the name of the item.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Gets the description of the item.
     * @return the description of the item.
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Gets the texture name for the item.
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