package edu.ship.engr.shipsim.view.player;

import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datatypes.VanityType;

import java.util.HashMap;
import java.util.List;

/**
 * A Factory for generating renderable instances of players on a map
 */
public class PlayerSpriteFactory
{
    PlayerBodyFactory bodyFactory;
    PlayerHatFactory hatFactory;
    PlayerHairFactory hairFactory;
    PlayerShirtFactory shirtFactory;
    PlayerPantsFactory pantsFactory;
    PlayerShoesFactory shoesFactory;
    PlayerEyesFactory eyesFactory;
    PlayerBikeFactory bikeFactory;

    /**
     * A factory for creating a PlayerSprite from a factory for each piece of the sprite
     *
     * @param hatFactory  Factory for building the hat's vanity object
     * @param bodyFactory Factory for building the body's vanity object
     */
    public PlayerSpriteFactory(PlayerHatFactory hatFactory, PlayerHairFactory hairFactory,
                               PlayerBodyFactory bodyFactory, PlayerShirtFactory shirtFactory,
                               PlayerPantsFactory pantsFactory, PlayerShoesFactory shoesFactory, PlayerEyesFactory eyesFactory, PlayerBikeFactory bikeFactory)
    {
        this.bodyFactory = bodyFactory;
        this.hatFactory = hatFactory;
        this.pantsFactory = pantsFactory;
        this.hairFactory = hairFactory;
        this.shoesFactory = shoesFactory;
        this.shirtFactory = shirtFactory;
        this.eyesFactory = eyesFactory;
        this.bikeFactory = bikeFactory;
    }

    /**
     * Generates a player sprite with a PlayerSprite representation
     *
     * @param vanitiesDTOList List of all vanity DTOs that the player is wearing
     * @return PlayerSprite
     * Built from all the Vanity objects
     */
    public PlayerSprite create(List<VanityDTO> vanitiesDTOList, int playerId)
    {
        HashMap<VanityType, String> vanityTextureNames = new HashMap<>();

        if (vanitiesDTOList != null)
        {
            vanitiesDTOList.forEach(x -> vanityTextureNames.put(x.getVanityType(), x.getTextureName()));
        }
        System.out.println(playerId);
        HashMap<VanityType, Vanity> vanities = new HashMap<>();
        vanities.put(VanityType.HAT, hatFactory.create(vanityTextureNames.get(VanityType.HAT)));
        vanities.put(VanityType.BODY, bodyFactory.create(vanityTextureNames.get(VanityType.BODY)));
        vanities.put(VanityType.HAIR, hairFactory.create(vanityTextureNames.get(VanityType.HAIR)));
        vanities.put(VanityType.PANTS, pantsFactory.create(vanityTextureNames.get(VanityType.PANTS)));
        vanities.put(VanityType.SHOES, shoesFactory.create(vanityTextureNames.get(VanityType.SHOES)));
        vanities.put(VanityType.SHIRT, shirtFactory.create(vanityTextureNames.get(VanityType.SHIRT)));
        vanities.put(VanityType.EYES, eyesFactory.create(vanityTextureNames.get(VanityType.EYES)));
        vanities.put(VanityType.BIKE, bikeFactory.create(vanityTextureNames.get(VanityType.BIKE)));
        PlayerSprite player = new PlayerSprite(vanities, playerId); // shoes, pants, hair atlas' as well
        return player;
    }

    public PlayerSprite create(List<VanityDTO> vanitiesDTOList)
    {
        HashMap<VanityType, String> vanityTextureNames = new HashMap<>();

        if (vanitiesDTOList != null)
        {
            vanitiesDTOList.forEach(x -> vanityTextureNames.put(x.getVanityType(), x.getTextureName()));
        }
        HashMap<VanityType, Vanity> vanities = new HashMap<>();
        vanities.put(VanityType.HAT, hatFactory.create(vanityTextureNames.get(VanityType.HAT)));
        vanities.put(VanityType.BODY, bodyFactory.create(vanityTextureNames.get(VanityType.BODY)));
        vanities.put(VanityType.HAIR, hairFactory.create(vanityTextureNames.get(VanityType.HAIR)));
        vanities.put(VanityType.PANTS, pantsFactory.create(vanityTextureNames.get(VanityType.PANTS)));
        vanities.put(VanityType.SHOES, shoesFactory.create(vanityTextureNames.get(VanityType.SHOES)));
        vanities.put(VanityType.SHIRT, shirtFactory.create(vanityTextureNames.get(VanityType.SHIRT)));
        vanities.put(VanityType.EYES, eyesFactory.create(vanityTextureNames.get(VanityType.EYES)));
        vanities.put(VanityType.BIKE, bikeFactory.create(vanityTextureNames.get(VanityType.BIKE)));
        PlayerSprite player = new PlayerSprite(vanities); // shoes, pants, hair atlas' as well
        return player;
    }
}
