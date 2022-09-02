package edu.ship.engr.shipsim.model.cheatCodeBehaviors;

/**
 * Used to verify that the CheatCodeManager handles behaviors correctly
 *
 * @author merlin
 */
public class MockCheatCodeBehavior implements CheatCodeBehavior
{
    /**
     * The pretend text we will react to
     */
    public static final String CHAT_TEXT = "Mock Magic";

    /**
     * Will be set to true if this behavior was told to give its cheat
     */
    public static boolean gaveCheat;

    /**
     * @see CheatCodeBehavior#giveCheat(int, java.lang.String)
     */
    @Override
    public boolean giveCheat(int playerID, String chatMessage)
    {
        gaveCheat = true;
        if (chatMessage.equals(CHAT_TEXT))
        {
            return true;
        }
        return false;
    }

}
