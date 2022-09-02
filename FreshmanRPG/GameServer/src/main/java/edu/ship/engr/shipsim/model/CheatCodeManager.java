package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.model.cheatCodeBehaviors.CheatCodeBehavior;

import java.util.ArrayList;

/**
 * Processes all chats and gives the correct response if a chat message is a
 * cheat code
 *
 * @author merlin
 */
public class CheatCodeManager extends TypeDetector
{

    private ArrayList<CheatCodeBehavior> cheats;

    CheatCodeManager()
    {
        findAllCheats();
    }

    private void findAllCheats()
    {
        ArrayList<Class<?>> allClasses = detectAllImplementorsInPackage(CheatCodeBehavior.class,
                "edu.ship.engr.shipsim.model.cheatCodeBehaviors");
        cheats = new ArrayList<>();
        for (Class<?> c : allClasses)
        {
            try
            {
                cheats.add((CheatCodeBehavior) c.getConstructor().newInstance());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    boolean handleChatTextForCheatBehaviors(int playerID, String chatText)
    {
        boolean wasACheat = false;
        for (CheatCodeBehavior behavior : cheats)
        {
            wasACheat |= behavior.giveCheat(playerID, chatText);
        }
        return wasACheat;
    }

    boolean hasCheatCodeBehavior(Class<? extends CheatCodeBehavior> x)
    {
        for (CheatCodeBehavior c : cheats)
        {
            if (c.getClass() == x)
            {
                return true;
            }
        }
        return false;
    }
}
