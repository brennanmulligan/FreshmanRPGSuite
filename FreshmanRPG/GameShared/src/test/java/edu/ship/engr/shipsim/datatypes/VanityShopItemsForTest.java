package edu.ship.engr.shipsim.datatypes;

public enum VanityShopItemsForTest
{
    BEANIE_HAT(VanityItemsForTest.BEANIE_HAT.getId(), 1),
    DUCK_HAT(VanityItemsForTest.DUCK_HAT.getId(), 2),
    MERLIN_HAT(VanityItemsForTest.MERLIN_HAT.getId(), 10),
    RED_SHIRT(VanityItemsForTest.RED_SHIRT.getId(), 2),
    BLUE_SHIRT(VanityItemsForTest.BLUE_SHIRT.getId(), 2),
    GREEN_SHIRT(VanityItemsForTest.GREEN_SHIRT.getId(), 2),
    WHITE_PANTS(VanityItemsForTest.WHITE_PANTS.getId(), 4),
    FANCY_SHOES(VanityItemsForTest.FANCY_SHOES.getId(), 5),
    BLACK_SHOES(VanityItemsForTest.BLACK_SHOES.getId(), 2);

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
