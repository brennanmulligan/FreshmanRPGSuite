package datatypes;

public enum DefaultItemsForTest
{
    DefaultHat(VanityForTest.NoHat.getId(), 1),
    DefaultHair(VanityForTest.BlackHair.getId(), 1),
    DefaultEyes(VanityForTest.BlackEyes.getId(), 1),
    DefaultShirt(VanityForTest.Base_Shirt.getId(), 1),
    DefaultSkin(VanityForTest.SkinDefault.getId(), 1),
    DefaultShoes(VanityForTest.NoShoes.getId(), 1),
    DefaultPants(VanityForTest.BasePants.getId(), 1),

    SkinZero(VanityForTest.SkinZero.getId(), 0),
    SkinOne(VanityForTest.SkinOne.getId(), 0),
    SkinTwo(VanityForTest.SkinTwo.getId(), 0),
    SkinThree(VanityForTest.SkinThree.getId(), 0),
    SkinFour(VanityForTest.SkinFour.getId(), 0),
    SkinFive(VanityForTest.SkinFive.getId(), 0),
    SkinSix(VanityForTest.SkinSix.getId(), 0),
    SkinSeven(VanityForTest.SkinSeven.getId(), 0),
    SkinEight(VanityForTest.SkinEight.getId(), 0),
    SkinNine(VanityForTest.SkinNine.getId(), 0),
    SkinTen(VanityForTest.SkinTen.getId(), 0),
    SkinEleven(VanityForTest.SkinEleven.getId(), 0),
    SkinTwelve(VanityForTest.SkinTwelve.getId(), 0),
    SkinThirteen(VanityForTest.SkinThirteen.getId(), 0),
    SkinFourteen(VanityForTest.SkinFourteen.getId(), 0),
    SkinFifteen(VanityForTest.SkinFifteen.getId(), 0),
    SkinSixteen(VanityForTest.SkinSixteen.getId(), 0),

    GreenEyes(VanityForTest.GreenEyes.getId(), 0),
    LightBlueEyes(VanityForTest.LightBlueEyes.getId(), 0),
    OrangeEyes(VanityForTest.OrangeEyes.getId(), 0),
    PinkEyes(VanityForTest.PinkEyes.getId(), 0),
    PurpleEyes(VanityForTest.PurpleEyes.getId(), 0),
    RedEyes(VanityForTest.RedEyes.getId(), 0),
    YellowEyes(VanityForTest.YellowEyes.getId(), 0),
    BrownEyes(VanityForTest.BrownEyes.getId(), 0);


    private int defaultID;
    private int defaultWearing;
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
}
