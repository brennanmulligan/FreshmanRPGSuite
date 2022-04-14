package datatypes;

import javax.naming.Name;

/**
 * Identifies all of the sprites within vanity.
 *
 * @author Ryan C and Adam W
 */
public enum VanityType
{
    /* The hat that goes on top of the player's head */
    HAT,
    /* The hair that goes on the players head */
    HAIR,
    /* The shirt that the player wears */
    SHIRT,
    /* The pants the player wears */
    PANTS,
    /* The shoes the player wears */
    SHOES,
    /* The whole body just in case */
    BODY,

    EYES,

    NAMEPLATE;

    public static VanityType fromInt(int vanityId)
    {
        switch(vanityId)
        {
            case 0:
                return HAT;
            case 1:
                return HAIR;
            case 2:
                return SHIRT;
            case 3:
                return PANTS;
            case 4:
                return SHOES;
            case 5:
                return BODY;
            case 6:
                return EYES;
            case 7:
                return NAMEPLATE;
            default:
                return BODY;
        }
    }
}

