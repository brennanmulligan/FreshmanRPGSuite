package edu.ship.engr.shipsim.datatypes;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;

import java.util.ArrayList;

public enum DefaultItemsForTest
{
    DEFAULT_BIKE(VanityItemsForTest.BIKE_NONE.getId(), 1),
    DEFAULT_HAT(VanityItemsForTest.NO_HAT.getId(), 1),
    DEFAULT_HAIR(VanityItemsForTest.BLACK_HAIR.getId(), 1),
    DEFAULT_EYES(VanityItemsForTest.BLACK_EYES.getId(), 1),
    DEFAULT_SHIRT(VanityItemsForTest.BASE_SHIRT.getId(), 1),
    DEFAULT_SKIN(VanityItemsForTest.SKIN_DEFAULT.getId(), 1),
    DEFAULT_SHOES(VanityItemsForTest.NO_SHOES.getId(), 1),
    DEFAULT_PANTS(VanityItemsForTest.BASE_PANTS.getId(), 1),

    SKIN_ZERO(VanityItemsForTest.SKIN_ZERO.getId(), 0),
    SKIN_ONE(VanityItemsForTest.SKIN_ONE.getId(), 0),
    SKIN_TWO(VanityItemsForTest.SKIN_TWO.getId(), 0),
    SKIN_THREE(VanityItemsForTest.SKIN_THREE.getId(), 0),
    SKIN_FOUR(VanityItemsForTest.SKIN_FOUR.getId(), 0),
    SKIN_FIVE(VanityItemsForTest.SKIN_FIVE.getId(), 0),
    SKIN_SIX(VanityItemsForTest.SKIN_SIX.getId(), 0),
    SKIN_SEVEN(VanityItemsForTest.SKIN_SEVEN.getId(), 0),
    SKIN_EIGHT(VanityItemsForTest.SKIN_EIGHT.getId(), 0),
    SKIN_NINE(VanityItemsForTest.SKIN_NINE.getId(), 0),
    SKIN_TEN(VanityItemsForTest.SKIN_TEN.getId(), 0),
    SKIN_ELEVEN(VanityItemsForTest.SKIN_ELEVEN.getId(), 0),
    SKIN_TWELVE(VanityItemsForTest.SKIN_TWELVE.getId(), 0),
    SKIN_THIRTEEN(VanityItemsForTest.SKIN_THIRTEEN.getId(), 0),
    SKIN_FOURTEEN(VanityItemsForTest.SKIN_FOURTEEN.getId(), 0),
    SKIN_FIFTEEN(VanityItemsForTest.SKIN_FIFTEEN.getId(), 0),
    SKIN_SIXTEEN(VanityItemsForTest.SKIN_SIXTEEN.getId(), 0),

    GREEN_EYES(VanityItemsForTest.GREEN_EYES.getId(), 0),
    LIGHT_BLUE_EYES(VanityItemsForTest.LIGHT_BLUE_EYES.getId(), 0),
    ORANGE_EYES(VanityItemsForTest.ORANGE_EYES.getId(), 0),
    PINK_EYES(VanityItemsForTest.PINK_EYES.getId(), 0),
    PURPLE_EYES(VanityItemsForTest.PURPLE_EYES.getId(), 0),
    RED_EYES(VanityItemsForTest.RED_EYES.getId(), 0),
    YELLOW_EYES(VanityItemsForTest.YELLOW_EYES.getId(), 0),
    BROWN_EYES(VanityItemsForTest.BROWN_EYES.getId(), 0);

    private final int defaultID;
    private final int defaultWearing;

    DefaultItemsForTest(int defaultID, int defaultWearing)
    {
        this.defaultID = defaultID;
        this.defaultWearing = defaultWearing;
    }

    public int getDefaultID()
    {
        return defaultID;
    }

    public int getDefaultWearing()
    {
        return defaultWearing;
    }

    public static ArrayList<VanityDTO> getDefaultItemsIsWearing()
    {
        ArrayList<VanityDTO> result = new ArrayList<>();

        for(DefaultItemsForTest a:DefaultItemsForTest.values())
        {
            if(a.defaultWearing==1)
            {
                int defaultID1 = a.getDefaultID();
                VanityItemsForTest value =
                        VanityItemsForTest.values()[defaultID1 - 1];
                VanityType type = value.getVanityType();

               result.add(new VanityDTO(defaultID1, value.getName(),
                        value.getDescription(), value.getTextureName(), type,
                       value.getPrice()));
            }
        }
        return result;
    }
}
