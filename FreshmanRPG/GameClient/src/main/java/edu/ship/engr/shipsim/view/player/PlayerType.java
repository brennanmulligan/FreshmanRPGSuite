package edu.ship.engr.shipsim.view.player;

/**
 * Identifies the player's appearance by the region name in the character atlas.
 */
public enum PlayerType
{

    /**
     * Male appearance, type 1
     */
    male_a("male_a"),
    /**
     * Female appearance, type 1
     */
    female_a("female_a"),
    /**
     * Knight who has a straw hat
     */
    knight_with_straw_hat("knight_with_straw_hat"),
    /**
     * Magic guy
     */
    Magi("Magi"),
    /**
     * Ninja
     */
    Ninja("Ninja"),
    /**
     * Merlin
     */
    merlin("merlin"),
    /**
     * Andy
     */
    andy("andy"),
    /**
     * Dave
     */
    dave("dave"),
    /**
     * Matt
     */
    MattKujo("MattKujo"),
    /**
     * The sorting red hat
     */
    RedHat("RedHat"),
    /**
     * out of bounds
     */
    out_of_bounds("out_of_bounds"),
    /**
     * null pointer
     */
    null_pointer_exception("null_pointer_exception"),
    /**
     * off by one
     */
    off_by_one("off_by_one"),

    /**
     * default player type
     */
    default_player("default_player"),

    /**
     * NPC tutor appearance
     */
    tutor("tutor");

    /**
     * The name of the region in the loaded {@link:TextureAtlas} used by a
     * {@link:PlayerSpriteFactory}
     */
    public final String regionName;

    private PlayerType(final String name)
    {
        this.regionName = name;
    }

}
