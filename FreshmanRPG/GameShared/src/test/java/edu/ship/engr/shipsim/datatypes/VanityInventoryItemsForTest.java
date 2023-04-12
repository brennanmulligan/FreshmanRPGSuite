package edu.ship.engr.shipsim.datatypes;

/**
 * The Player to Vanity mappings that are in the test database.
 */
public enum VanityInventoryItemsForTest
{
    /**
     * Mapping which states that John owns a Hat vanity.
     */
    JOHN_OWNS_HAT(1, VanityItemsForTest.DUCK_HAT.getId(), 0),
    JOHN_OWNS_HAT2(1, VanityItemsForTest.MERLIN_HAT.getId(), 1),
    JOHN_OWNS_HAIR(1, VanityItemsForTest.BLONDE_HAIR.getId(), 1),
    JOHN_OWNS_SHIRT(1, VanityItemsForTest.RED_SHIRT.getId(), 1),
    JOHN_OWNS_PANTS(1, VanityItemsForTest.WHITE_PANTS.getId(), 1),
    JOHN_OWNS_SHOES(1, VanityItemsForTest.FANCY_SHOES.getId(), 1),
    JOHN_OWNS_BODY(1, VanityItemsForTest.SKIN_ZERO.getId(), 1),
    JOHN_OWNS_BODY1(1, VanityItemsForTest.SKIN_THIRTEEN.getId(), 0),
    JOHN_OWNS_BODY2(1, VanityItemsForTest.SKIN_GHOST.getId(), 0),
    JOHN_OWNS_BODY3(1, VanityItemsForTest.SKIN_EIGHT.getId(), 0),
    JOHN_OWNS_EYES(1, VanityItemsForTest.BLACK_EYES.getId(), 1),
    JOHN_OWNS_EYES1(1, VanityItemsForTest.RED_EYES.getId(), 0),
    JOHN_OWNS_EYES2(1, VanityItemsForTest.GREEN_EYES.getId(), 0),
    JOHN_OWNS_EYES3(1, VanityItemsForTest.PINK_EYES.getId(), 0),
    JOHN_OWNS_BIKE(1, VanityItemsForTest.BIKE_NONE.getId(), 1),

    /**
     * Mapping the items Tutor wears to each vanity type.
     */
    TUTOR_NPC_HAT(23, VanityItemsForTest.BEANIE_HAT.getId(), 1),
    TUTOR_NPC_HAIR(23, VanityItemsForTest.TUTOR_HAIR.getId(), 1),
    TUTOR_NPC_SHIRT(23, VanityItemsForTest.TUTOR_SHIRT.getId(), 1),
    TUTOR_NPC_BODY(23, VanityItemsForTest.SKIN_ZERO.getId(), 1),
    TUTOR_NPC_PANTS(23, VanityItemsForTest.BASE_PANTS.getId(), 1),
    TUTOR_NPC_SHOES(23, VanityItemsForTest.BLACK_SHOES.getId(), 1),
    TUTOR_NPC_EYES(23, VanityItemsForTest.BROWN_EYES.getId(), 1),

    /**
     * Mapping the items Big Red wears to each vanity type.
     * TODO: Use new big red apparel
     */
    BIG_RED_OWNS_HAT(24, VanityItemsForTest.BIG_RED_HAT.getId(), 1),
    BIG_RED_OWNS_HAIR(24, VanityItemsForTest.BIG_RED_HAIR.getId(), 1),
    BIG_RED_OWNS_SHIRT(24, VanityItemsForTest.BIG_RED_SHIRT.getId(), 1),
    BIG_RED_OWNS_BODY(24, VanityItemsForTest.SKIN_BIG_RED.getId(), 1),
    BIG_RED_OWNS_PANTS(24, VanityItemsForTest.BIG_RED_PANTS.getId(), 1),
    BIG_RED_OWNS_SHOES(24, VanityItemsForTest.BIG_RED_SHOES.getId(), 1),
    BIG_RED_OWNS_EYES(24, VanityItemsForTest.BIG_RED_EYES.getId(), 1),

    /**
     * Mapping the items Professor H wears to each vanity type.
     */
    PROFESSOR_H_OWNS_HAT(26, VanityItemsForTest.DUCK_HAT.getId(), 1),
    PROFESSOR_H_OWNS_HAIR(26, VanityItemsForTest.BLONDE_HAIR.getId(), 1),
    PROFESSOR_H_OWNS_SHIRT(26, VanityItemsForTest.GREEN_SHIRT.getId(), 1),
    PROFESSOR_H_OWNS_BODY(26, VanityItemsForTest.SKIN_TEN.getId(), 1),
    PROFESSOR_H_OWNS_PANTS(26, VanityItemsForTest.WHITE_PANTS.getId(), 1),
    PROFESSOR_H_OWNS_SHOES(26, VanityItemsForTest.BLACK_SHOES.getId(), 1),
    PROFESSOR_H_OWNS_EYES(26, VanityItemsForTest.YELLOW_EYES.getId(), 1),

    /**
     * Mapping the items Professor H wears to each vanity type.
     */
    IT_GUY_OWNS_HAT(28, VanityItemsForTest.BEANIE_HAT.getId(), 1),
    IT_GUY_OWNS_HAIR(28, VanityItemsForTest.BLACK_HAIR.getId(), 1),
    IT_GUY_OWNS_SHIRT(28, VanityItemsForTest.TUTOR_SHIRT.getId(), 1),
    IT_GUY_OWNS_BODY(28, VanityItemsForTest.SKIN_NINE.getId(), 1),
    PIT_GUY_OWNS_PANTS(28, VanityItemsForTest.BASE_PANTS.getId(), 1),
    IT_GUY_OWNS_SHOES(28, VanityItemsForTest.BLACK_SHOES.getId(), 1),
    IT_GUY_OWNS_EYES(28, VanityItemsForTest.GREEN_EYES.getId(), 1),

    /**
     * Mapping the items this NPC wears to each vanity type.
     */
    TEACHER_NPC_HAT(30, VanityItemsForTest.NO_HAT.getId(), 1),
    TEACHER_NPC_HAIR(30, VanityItemsForTest.GRAY_HAIR.getId(), 1),
    TEACHER_NPC_SHIRT(30, VanityItemsForTest.OFF_BY_ONE_SHIRT.getId(), 1),
    TEACHER_NPC_BODY(30, VanityItemsForTest.SKIN_SEVEN.getId(), 1),
    TEACHER_NPC_PANTS(30, VanityItemsForTest.BASE_PANTS.getId(), 1),
    TEACHER_NPC_SHOES(30, VanityItemsForTest.NO_SHOES.getId(), 1),
    TEACHER_NPC_EYES(30, VanityItemsForTest.GREEN_EYES.getId(), 1),

    /**
     * Mapping the items this NPC wears to each vanity type.
     */
    QUAD_GUY_HAT(27, VanityItemsForTest.BEANIE_HAT.getId(), 1),
    QUAD_GUY_HAIR(27, VanityItemsForTest.BLACK_HAIR.getId(), 1),
    QUAD_GUY_SHIRT(27, VanityItemsForTest.BLUE_SHIRT.getId(), 1),
    QUAD_GUY_BODY(27, VanityItemsForTest.SKIN_FIFTEEN.getId(), 1),
    QUAD_GUY_PANTS(27, VanityItemsForTest.BASE_PANTS.getId(), 1),
    QUAD_GUY_SHOES(27, VanityItemsForTest.BLACK_SHOES.getId(), 1),
    QUAD_GUY_EYES(27, VanityItemsForTest.LIGHT_BLUE_EYES.getId(), 1),


    /**
     * Mapping which states that MowreyNPC owns a Body vanity.
     */
    MOWREY_OWNS_HAT(32, VanityItemsForTest.BEANIE_HAT.getId(), 1),
    MOWREY_OWNS_HAIR(32, VanityItemsForTest.OFF_BY_ONE_HAIR.getId(), 1),
    MOWREY_OWNS_SHIRT(32, VanityItemsForTest.BLUE_SHIRT.getId(), 1),
    MOWREY_OWNS_PANTS(32, VanityItemsForTest.BASE_PANTS.getId(), 1),
    MOWREY_OWNS_BODY(32, VanityItemsForTest.SKIN_FIVE.getId(), 1),
    MOWREY_OWNS_SHOES(32, VanityItemsForTest.BLACK_SHOES.getId(), 1),
    MOWREY_OWNS_EYES(32, VanityItemsForTest.LIGHT_BLUE_EYES.getId(), 1),


    /**
     * Mapping which states that President owns a Body vanity.
     */
    PRESIDENT_OWNS_HAT(33, VanityItemsForTest.ANDY_HAT.getId(), 1),
    PRESIDENT_OWNS_HAIR(33, VanityItemsForTest.NULL_POINTER_HAIR.getId(), 1),
    PRESIDENT_OWNS_SHIRT(33, VanityItemsForTest.NULL_POINTER_SHIRT.getId(), 1),
    PRESIDENT_OWNS_PANTS(33, VanityItemsForTest.BASE_PANTS.getId(), 1),
    PRESIDENT_OWNS_SHOES(33, VanityItemsForTest.BLACK_SHOES.getId(), 1),
    PRESIDENT_OWNS_BODY(33, VanityItemsForTest.SKIN_SIX.getId(), 1),
    PRESIDENT_OWNS_EYES(33, VanityItemsForTest.ORANGE_EYES.getId(), 1),

    /**
     * Mapping which states that Josh owns a Body vanity.
     */
    JOSH_OWNS_HAT(4, VanityItemsForTest.DUCK_HAT.getId(), 1),
    JOSH_OWNS_HAIR(4, VanityItemsForTest.BLACK_HAIR.getId(), 1),
    JOSH_OWNS_SHIRT(4, VanityItemsForTest.MERLIN_HAIR.getId(), 1),
    JOSH_OWNS_PANTS(4, VanityItemsForTest.BASE_PANTS.getId(), 1),
    JOSH_OWNS_SHOES(4, VanityItemsForTest.RED_SHOES.getId(), 1),
    JOSH_OWNS_BODY(4, VanityItemsForTest.SKIN_ZERO.getId(), 1),
    JOSH_OWNS_EYES(4, VanityItemsForTest.BLACK_EYES.getId(), 1),

    /**
     * Mapping the items this NPC wears to each vanity type.
     */
    ROCK_PAPER_SCISSORS_NPC_HAT(31, VanityItemsForTest.ANDY_HAT.getId(), 1),
    ROCK_PAPER_SCISSORS_NPC_HAIR(31, VanityItemsForTest.MERLIN_HAIR.getId(), 1),
    ROCK_PAPER_SCISSORS_NPC_SHIRT(31, VanityItemsForTest.NULL_POINTER_SHIRT.getId(), 1),
    ROCK_PAPER_SCISSORS_NPC_BODY(31, VanityItemsForTest.SKIN_FIFTEEN.getId(), 1),
    ROCK_PAPER_SCISSORS_NPC_PANTS(31, VanityItemsForTest.WHITE_PANTS.getId(), 1),
    ROCK_PAPER_SCISSORS_NPC_SHOES(31, VanityItemsForTest.BLACK_SHOES.getId(), 1),
    ROCK_PAPER_SCISSORS_NPC_EYES(31, VanityItemsForTest.BROWN_EYES.getId(), 1),

    /**
     * Mapping the bike to Micycle
     */
    //MICYCLE_OWNS_BIKE(36, VanityItemsForTest.BIKE.getId(), 1),
    MICYCLE_OWNS_GRAD_HAT(36, VanityItemsForTest.GRAD_CAP_HAT.getId(), 1),

    /**
     * Mapping the items this NPC wears to each vanity type.
     */
    HISTORY_NPC_HAT(37, VanityItemsForTest.GRAD_CAP_HAT.getId(), 1),
    HISTORY_NPC_HAIR(37, VanityItemsForTest.NERD_HAIR.getId(), 1),
    HISTORY_NPC_SHIRT(37, VanityItemsForTest.NERD_SHIRT.getId(), 1),
    HISTORY_NPC_PANTS(37, VanityItemsForTest.BIG_RED_PANTS.getId(), 1),
    HISTORY_NPC_SHOES(37, VanityItemsForTest.BLACK_SHOES.getId(), 1),
    HISTORY_NPC_EYES(37, VanityItemsForTest.GREEN_EYES.getId(), 1),
    HISTORY_NPC_SKIN(37, VanityItemsForTest.SKIN_FIVE.getId(), 1),

    /**
     * Mapping the items this NPC wears to each vanity type.
     */
    HISTORY_NPC_HAT2(38, VanityItemsForTest.GRAD_CAP_HAT.getId(), 1),
    HISTORY_NPC_HAIR2(38, VanityItemsForTest.NERD_HAIR.getId(), 1),
    HISTORY_NPC_SHIRT2(38, VanityItemsForTest.NERD_SHIRT.getId(), 1),
    HISTORY_NPC_PANTS2(38, VanityItemsForTest.BIG_RED_PANTS.getId(), 1),
    HISTORY_NPC_SHOES2(38, VanityItemsForTest.BLACK_SHOES.getId(), 1),
    HISTORY_NPC_EYES2(38, VanityItemsForTest.GREEN_EYES.getId(), 1),
    HISTORY_NPC_SKIN2(38, VanityItemsForTest.SKIN_FIVE.getId(), 1),

    /**
     * Mapping the items this NPC wears to each vanity type.
     */
    HISTORY_NPC_HAT3(39, VanityItemsForTest.GRAD_CAP_HAT.getId(), 1),
    HISTORY_NPC_HAIR3(39, VanityItemsForTest.NERD_HAIR.getId(), 1),
    HISTORY_NPC_SHIRT3(39, VanityItemsForTest.NERD_SHIRT.getId(), 1),
    HISTORY_NPC_PANTS3(39, VanityItemsForTest.BIG_RED_PANTS.getId(), 1),
    HISTORY_NPC_SHOES3(39, VanityItemsForTest.BLACK_SHOES.getId(), 1),
    HISTORY_NPC_EYES3(39, VanityItemsForTest.GREEN_EYES.getId(), 1),
    HISTORY_NPC_SKIN3(39, VanityItemsForTest.SKIN_FIVE.getId(), 1);

    private final int playerID;
    private final int vanityID;
    private final int isWearing;

    VanityInventoryItemsForTest(int playerID, int vanityID, int isWearing)
    {
        this.playerID = playerID;
        this.vanityID = vanityID;
        this.isWearing = isWearing;
    }

    /**
     * Get the ID of the player from the mapping.
     *
     * @return the ID of the player.
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * Get the ID of the vanity from the mapping.
     *
     * @return the ID of the vanity.
     */
    public int getVanityID()
    {
        return vanityID;
    }

    /**
     * @return 1 if the player is wearing it, 0 is the player is not
     */
    public int getIsWearing()
    {
        return isWearing;
    }
}
