package edu.ship.engr.shipsim.model.cheatCodeBehaviors;

/**
 * Required of classes that encode the behaviors of cheat codes
 *
 * @author merlin
 */
public interface CheatCodeBehavior
{
    /**
     * If the chatMessage matches this behavior's requirements, give the bonus
     * associated with that message
     *
     * @param playerID    the player who sent the chat
     * @param chatMessage the message they sent
     * @return true if the cheat bonus was given
     */
    boolean giveCheat(int playerID, String chatMessage);

}
