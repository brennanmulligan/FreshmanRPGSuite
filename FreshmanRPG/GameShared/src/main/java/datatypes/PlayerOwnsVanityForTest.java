package datatypes;

/**
 * The Player to Vanity mappings that are in the test database.
 */
public enum PlayerOwnsVanityForTest
{
    /**
     * Mapping which states that John owns a Hat vanity.
     */
    JOHN_OWNS_HAT(1, VanityForTest.DuckHat.getId(), 0),
    JOHN_OWNS_HAT2(1, VanityForTest.MerlinHat.getId(), 1),
    JOHN_OWNS_HAIR(1, VanityForTest.BlondeHair.getId(), 1),
    JOHN_OWNS_SHIRT(1, VanityForTest.RedShirt.getId(), 1),
    JOHN_OWNS_PANTS(1,VanityForTest.WhitePants.getId(), 1),
    JOHN_OWNS_SHOES(1,VanityForTest.FancyShoes.getId(), 1),
    JOHN_OWNS_BODY(1,VanityForTest.SkinZero.getId(), 1),
    JOHN_OWNS_BODY1(1,VanityForTest.SkinThirteen.getId(), 0),
    JOHN_OWNS_BODY2(1,VanityForTest.SkinGhost.getId(), 0),
    JOHN_OWNS_BODY3(1,VanityForTest.SkinEight.getId(), 0),
    JOHN_OWNS_EYES(1,VanityForTest.BlackEyes.getId(), 1),
    JOHN_OWNS_EYES1(1,VanityForTest.RedEyes.getId(), 0),
    JOHN_OWNS_EYES2(1,VanityForTest.GreenEyes.getId(), 0),
    JOHN_OWNS_EYES3(1,VanityForTest.PinkEyes.getId(), 0),

    /**
     * Mapping the items Tutor wears to each vanity type.
     */
    TUTOR_NPC_HAT(23, VanityForTest.BeanieHat.getId(),1),
    TUTOR_NPC_HAIR(23, VanityForTest.TutorHair.getId(),1),
    TUTOR_NPC_SHIRT(23, VanityForTest.TutorShirt.getId(),1),
    TUTOR_NPC_BODY(23, VanityForTest.SkinZero.getId(),1),
    TUTOR_NPC_PANTS(23, VanityForTest.BasePants.getId(),1),
    TUTOR_NPC_SHOES(23, VanityForTest.BlackShoes.getId(),1),
    TUTOR_NPC_EYES(23, VanityForTest.BrownEyes.getId(),1),

    /**
     * Mapping the items Big Red wears to each vanity type.
     * TODO: Use new big red apparel
     */
    BIG_RED_OWNS_HAT(24, VanityForTest.BR_Hat.getId(), 1),
    BIG_RED_OWNS_HAIR(24, VanityForTest.BR_Hair.getId(), 1),
    BIG_RED_OWNS_SHIRT(24, VanityForTest.BR_Shirt.getId(), 1),
    BIG_RED_OWNS_BODY(24, VanityForTest.SkinBR.getId(), 1),
    BIG_RED_OWNS_PANTS(24, VanityForTest.BR_Pants.getId(), 1),
    BIG_RED_OWNS_SHOES(24, VanityForTest.BR_Shoes.getId(), 1),
    BIG_RED_OWNS_EYES(24,VanityForTest.BR_Eyes.getId(), 1),

    /**
     * Mapping the items Professor H wears to each vanity type.
     */
    PROFESSOR_H_OWNS_HAT(26, VanityForTest.DuckHat.getId(), 1),
    PROFESSOR_H_OWNS_HAIR(26, VanityForTest.BlondeHair.getId(), 1),
    PROFESSOR_H_OWNS_SHIRT(26, VanityForTest.GreenShirt.getId(), 1),
    PROFESSOR_H_OWNS_BODY(26, VanityForTest.SkinTen.getId(), 1),
    PROFESSOR_H_OWNS_PANTS(26, VanityForTest.WhitePants.getId(), 1),
    PROFESSOR_H_OWNS_SHOES(26, VanityForTest.BlackShoes.getId(), 1),
    PROFESSOR_H_OWNS_EYES(26,VanityForTest.YellowEyes.getId(), 1),

    /**
     * Mapping the items Professor H wears to each vanity type.
     */
    IT_GUY_OWNS_HAT(28, VanityForTest.BeanieHat.getId(), 1),
    IT_GUY_OWNS_HAIR(28, VanityForTest.BlackHair.getId(), 1),
    IT_GUY_OWNS_SHIRT(28, VanityForTest.TutorShirt.getId(), 1),
    IT_GUY_OWNS_BODY(28, VanityForTest.SkinNine.getId(), 1),
    PIT_GUY_OWNS_PANTS(28, VanityForTest.BasePants.getId(), 1),
    IT_GUY_OWNS_SHOES(28, VanityForTest.BlackShoes.getId(), 1),
    IT_GUY_OWNS_EYES(28,VanityForTest.GreenEyes.getId(), 1),

    /**
     * Mapping the items this NPC wears to each vanity type.
     */
    TEACHER_NPC_HAT(30, VanityForTest.NoHat.getId(), 1),
    TEACHER_NPC_HAIR(30, VanityForTest.GrayHair.getId(), 1),
    TEACHER_NPC_SHIRT(30, VanityForTest.OBOShirt.getId(), 1),
    TEACHER_NPC_BODY(30, VanityForTest.SkinSeven.getId(), 1),
    TEACHER_NPC_PANTS(30, VanityForTest.BasePants.getId(), 1),
    TEACHER_NPC_SHOES(30, VanityForTest.NoShoes.getId(), 1),
    TEACHER_NPC_EYES(30,VanityForTest.GreenEyes.getId(), 1),

    /**
     * Mapping the items this NPC wears to each vanity type.
     */
    QUAD_GUY_HAT(27, VanityForTest.BeanieHat.getId(), 1),
    QUAD_GUY_HAIR(27, VanityForTest.BlackHair.getId(), 1),
    QUAD_GUY_SHIRT(27, VanityForTest.BlueShirt.getId(), 1),
    QUAD_GUY_BODY(27, VanityForTest.SkinFifteen.getId(), 1),
    QUAD_GUY_PANTS(27, VanityForTest.BasePants.getId(), 1),
    QUAD_GUY_SHOES(27, VanityForTest.BlackShoes.getId(), 1),
    QUAD_GUY_EYES(27,VanityForTest.LightBlueEyes.getId(), 1),


    /**
     * Mapping which states that MowreyNPC owns a Body vanity.
     */
    MOWREY_OWNS_HAT(32, VanityForTest.BeanieHat.getId(), 1),
    MOWREY_OWNS_HAIR(32, VanityForTest.OBOHair.getId(), 1),
    MOWREY_OWNS_SHIRT(32, VanityForTest.BlueShirt.getId(), 1),
    MOWREY_OWNS_PANTS(32, VanityForTest.BasePants.getId(), 1),
    MOWREY_OWNS_BODY(32, VanityForTest.SkinFive.getId(), 1),
    MOWREY_OWNS_SHOES(32, VanityForTest.BlackShoes.getId(), 1),
    MOWREY_OWNS_EYES(32,VanityForTest.LightBlueEyes.getId(), 1),


    /**
     * Mapping which states that President owns a Body vanity.
     */
    PRESIDENT_OWNS_HAT(33, VanityForTest.AndyHat.getId(), 1),
    PRESIDENT_OWNS_HAIR(33, VanityForTest.NPHair.getId(), 1),
    PRESIDENT_OWNS_SHIRT(33, VanityForTest.NPShirt.getId(), 1),
    PRESIDENT_OWNS_PANTS(33, VanityForTest.BasePants.getId(), 1),
    PRESIDENT_OWNS_SHOES(33, VanityForTest.BlackShoes.getId(), 1),
    PRESIDENT_OWNS_BODY(33, VanityForTest.SkinSix.getId(), 1),
    PRESIDENT_OWNS_EYES(33,VanityForTest.OrangeEyes.getId(), 1),

    /**
     * Mapping which states that Josh owns a Body vanity.
     */
    JOSH_OWNS_HAT(4,VanityForTest.DuckHat.getId(), 1),
    JOSH_OWNS_HAIR(4,VanityForTest.BlackHair.getId(), 1),
    JOSH_OWNS_SHIRT(4,VanityForTest.MerlinHair.getId(), 1),
    JOSH_OWNS_PANTS(4,VanityForTest.BasePants.getId(), 1),
    JOSH_OWNS_SHOES(4,VanityForTest.RedShoes.getId(), 1),
    JOSH_OWNS_BODY(4,VanityForTest.SkinZero.getId(), 1),
    JOSH_OWNS_EYES(4,VanityForTest.BlackEyes.getId(), 1),

    /**
     * Mapping the items this NPC wears to each vanity type.
     */
    RockPaperScissors_NPC_HAT(31, VanityForTest.AndyHat.getId(), 1),
    RockPaperScissors_NPC_HAIR(31, VanityForTest.MerlinHair.getId(), 1),
    RockPaperScissors_NPC_SHIRT(31, VanityForTest.NPShirt.getId(), 1),
    RockPaperScissors_NPC_BODY(31, VanityForTest.SkinFifteen.getId(), 1),
    RockPaperScissors_NPC_PANTS(31, VanityForTest.WhitePants.getId(), 1),
    RockPaperScissors_NPC_SHOES(31, VanityForTest.BlackShoes.getId(), 1),
    RockPaperScissors_NPC_EYES(31,VanityForTest.BrownEyes.getId(), 1);


    private final int playerID;
    private final int vanityID;
    private final int isWearing;

    PlayerOwnsVanityForTest(int playerID, int vanityID, int isWearing)
    {
        this.playerID = playerID;
        this.vanityID = vanityID;
        this.isWearing = isWearing;
    }

    /**
     * Get the ID of the player from the mapping.
     * @return the ID of the player.
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * Get the ID of the vanity from the mapping.
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
