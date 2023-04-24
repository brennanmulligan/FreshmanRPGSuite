package edu.ship.engr.shipsim.datatypes;

//This file will be deleted after mocks have been created.

public enum VanityAwardsForTest
{;

//    BIG_RED_HAT(1, VanityItemsForTest.BIG_RED_HAT.getId()),
//    BIG_RED_SHIRT(2, VanityItemsForTest.BIG_RED_SHIRT.getId()),
//    BIG_RED_PANTS(3, VanityItemsForTest.BIG_RED_PANTS.getId()),
//    BIG_RED_LITTLE_HAT(4, VanityItemsForTest.BIG_RED_HAT.getId()),
//    BIG_RED_LITTLE_PANTS(4, VanityItemsForTest.BIG_RED_PANTS.getId()),
//    BIG_RED_LITTLE_SHIRT(4, VanityItemsForTest.BIG_RED_SHIRT.getId()),
//    BIG_RED_LITTLE_HAIR(4, VanityItemsForTest.BIG_RED_HAIR.getId()),
//    BIG_RED_INV_HAT(17, VanityItemsForTest.BIG_RED_HAT.getId());

    private final int questId;
    private final int vanityId;

    /**
     * Represents a lookup for a vanity item given to a user
     * upon completion of a quest
     *
     * @param questId
     * @param vanityId
     */
    VanityAwardsForTest(int questId, int vanityId)
    {
        this.questId = questId;
        this.vanityId = vanityId;
    }

    /**
     * @return the quest id
     */
    public int getQuestID()
    {
        return questId;
    }

    /**
     * @return the vanity id given for that quest
     */
    public int getVanityID()
    {
        return vanityId;
    }
}
