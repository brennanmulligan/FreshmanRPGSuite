package datatypes;

public enum DefaultItemsForTest
{
    DefaultHat(VanityForTest.DuckHat.getId()),
    DefaultHair(VanityForTest.GrayHair.getId()),
    DefaultEyes(VanityForTest.BlackEyes.getId()),
    DefaultShirt(VanityForTest.GreenShirt.getId()),
    DefaultSkin(VanityForTest.SkinDefault.getId()),
    DefaultPants(VanityForTest.BasePants.getId());


    private int defaultID;
    DefaultItemsForTest(int defaultID)
    {
        this.defaultID = defaultID;
    }

    public int getDefaultID()
    {
        return defaultID;
    }
}
