package datatypes;

public enum VanityAwardsForTest
{
    BigRedHat(1, VanityForTest.BR_Hat.getId()),
    BigRedShirt(2, VanityForTest.BR_Shirt.getId()),
    BigRedPants(3, VanityForTest.BR_Pants.getId()),
    BigRedAll(4, VanityForTest.BR_Hat.getId()),
    BigRedAll1(4, VanityForTest.BR_Pants.getId()),
    BigRedAll2(4, VanityForTest.BR_Shirt.getId()),
    BigRedAll3(4, VanityForTest.BR_Hair.getId());
    private final int questId;
    private final int vanityId;

    /**
     * Represents a lookup for a vanity item given to a user
     * upon completion of a quest
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
