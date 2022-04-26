package datatypes;

public enum VanityShopItemsForTest
{
    BeanieHat(VanityForTest.BeanieHat.getId(), 1),
    DuckHat(VanityForTest.DuckHat.getId(), 2),
    MerlinHat(VanityForTest.MerlinHat.getId(), 10),
    RedShirt(VanityForTest.RedShirt.getId(), 2),
    BlueShirt(VanityForTest.BlueShirt.getId(), 2),
    GreenShirt(VanityForTest.GreenShirt.getId(), 2),
    WhitePants(VanityForTest.WhitePants.getId(), 4),
    FancyShoes(VanityForTest.FancyShoes.getId(), 5),
    BlackShoes(VanityForTest.BlackShoes.getId(), 2);



    private final int vanityID;
    private final int price;
    VanityShopItemsForTest(int vanityID, int price)
    {
        this.vanityID = vanityID;
        this.price = price;
    }

    public int getVanityID()
    {
        return vanityID;
    }

    public int getPrice()
    {
        return price;
    }
}
