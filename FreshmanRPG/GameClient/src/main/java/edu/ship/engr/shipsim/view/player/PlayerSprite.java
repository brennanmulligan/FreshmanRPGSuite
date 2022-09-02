package edu.ship.engr.shipsim.view.player;

import com.badlogic.gdx.scenes.scene2d.Stage;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.ClientPlayer;
import edu.ship.engr.shipsim.model.ClientPlayerManager;
import edu.ship.engr.shipsim.view.screen.SkinPicker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Manages all the vanity objects that represent each piece of the player's sprite
 */
public class PlayerSprite
{
    // A Hashmap containing all the Vanity objects delimited by their type
    HashMap<VanityType, Vanity> vanities;
    NamePlate namePlate;
    ChatBubble chatBubble;
    float playerWidth;

    /**
     * Creates an instance where the sprite uses the vanities given
     *
     * @param vanities a hashmap containing all the vanity objects
     */
    protected PlayerSprite(HashMap<VanityType, Vanity> vanities)
    {
        this.vanities = vanities;
        namePlate = new NamePlate("", "", SkinPicker.getSkinPicker().getDefaultSkin());
    }

    /**
     * Creates an instance where the sprite uses the vanities given and gives them a name plate
     *
     * @param vanities a hashmap containing all the vanity objects
     */
    protected PlayerSprite(HashMap<VanityType, Vanity> vanities, int playerId)
    {
        ClientPlayer player = ClientPlayerManager.getSingleton().getPlayerFromID(playerId);
        this.vanities = vanities;
        playerWidth = vanities.get(VanityType.BODY).getWidth();

        namePlate = new NamePlate(player.getName(),
                player.getCrew().toString(),
                SkinPicker.getSkinPicker().getDefaultSkin());

        namePlate.setOffsetX((-namePlate.getWidth() / 2) + (playerWidth / 2));
        namePlate.setAlignment(2);

        chatBubble = new ChatBubble(player.getID());//added id as arg
        chatBubble.setOffsetX((-chatBubble.getWidth() / 2) + (playerWidth / 2));
    }

    /**
     * Constructs a headless player sprite
     */
    protected PlayerSprite()
    {
        vanities = new HashMap<VanityType, Vanity>();
        Vanity body = new Vanity();
        vanities.put(VanityType.BODY, body);
    }

    /**
     * Forcibly sets the location on screen of the sprite without animating it
     */
    //@Override
    public void setPosition(float x, float y)
    {
        vanities.forEach((w, z) -> z.setPosition(x, y));
        if (namePlate != null)
        {
            namePlate.setPosition(x + namePlate.getOffsetX(), y + namePlate.getOffSetY());
        }

        if (chatBubble != null)
        {
            chatBubble.setPosition(x + chatBubble.getOffsetX(), y + chatBubble.getOffSetY());
        }
    }

    public void addActors(Stage worldStage)
    {
        worldStage.addActor(namePlate);
        worldStage.addActor(chatBubble);
        vanities.values().stream()
                .sorted()
                .forEachOrdered((worldStage::addActor));
    }

    /**
     * Sets the location on screen that the sprite is to move to with animation
     *
     * @param x horizontal screen location of the sprite
     * @param y vertical screen location
     */
    public void move(final float x, final float y)
    {
        vanities.forEach((w, z) -> z.update(x, y));
        namePlate.update(x, y);
        chatBubble.update(x, y);
    }

    public Vanity getVanity(VanityType type)
    {
        return vanities.get(type);
    }

    /**
     * @return the current direction the sprite is facing
     */
    public Direction getFacing()
    {
        return vanities.get(VanityType.BODY).getFacing();
    }

    /**
     * get the length of time one movement takes
     *
     * @return move duration
     */
    public float getMoveDuration()
    {
        return vanities.get(VanityType.BODY).getMoveDuration();
    }

    /**
     * returns all vanities on the player
     *
     * @return the vanities the player is wearing
     */
    public ArrayList<Vanity> getVanities()
    {
        return new ArrayList<>(vanities.values());
    }

    /**
     * The x position of the Player
     *
     * @return the X position
     */
    public float getX()
    {
        return vanities.get(VanityType.BODY).getX();
    }


    /**
     * The y poistion of the player sprite
     *
     * @return the Y position
     */
    public float getY()
    {
        return vanities.get(VanityType.BODY).getY();
    }

    /**
     * Get the NamePlate
     *
     * @return returns the nameplate of the player
     */
    public NamePlate getNamePlate()
    {
        return namePlate;
    }

    public void removeActors()
    {
        getVanities().forEach(x -> x.remove());
        namePlate.remove();
        chatBubble.remove();
    }
}
