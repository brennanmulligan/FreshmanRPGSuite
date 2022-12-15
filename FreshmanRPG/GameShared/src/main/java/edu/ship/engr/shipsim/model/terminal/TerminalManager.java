package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.model.TypeDetector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Chris Roadcap
 * @author Denny Fleagle
 */
public class TerminalManager extends TypeDetector
{
    static private TerminalManager singleton = null;
    private HashMap<String, Class<? extends TerminalCommand>> terminalCommandsMap;

    /**
     * Initializes the hashmap for terminal commands
     */
    private TerminalManager()
    {
        terminalCommandsMap = new HashMap<>();
        try
        {
            buildHashMap();
        }
        catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException |
               InstantiationException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * @return returns the instance of the singleton and creates one if its null
     */
    public static TerminalManager getSingleton()
    {
        if (singleton == null)
        {
            return singleton = new TerminalManager();
        }
        return singleton;
    }

    /**
     * checks if the singleton is null. if it isn't makes it null
     *
     * @return true if we reset the singleton, return false if it didn't exist
     */
    public boolean resetSingleton()
    {
        if (singleton != null)
        {
            singleton = null;
            return true;
        }
        return false;
    }

    /**
     * Builds a hashmap of all the commands by looking at all the classes that
     * extends the TerminalCommands abstract Creates instances of the commands and
     * adds them as the value and the key is the command identifier
     *
     * @throws InstantiationException if the command isn't able to be instantiated
     * @throws IllegalAccessException if the command isn't able to be instantiated
     */
    public void buildHashMap() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException
    {
        ArrayList<Class<?>> terminalTypes = this.detectAllExtendersInPackage(TerminalCommand.class);
        for (Class<?> terminalType : terminalTypes)
        {
            TerminalCommand terminalCommand = (TerminalCommand) terminalType.getConstructor().newInstance();

            terminalCommandsMap.put(terminalCommand.getTerminalIdentifier(), terminalCommand.getClass());
        }

    }

    /**
     * @param identifier of the command we want
     * @return the command object or null if the command isn't found
     */
    public Class<? extends TerminalCommand> getTerminalCommand(String identifier)
    {
        return terminalCommandsMap.get(identifier);
    }

    /**
     * @param commandName the command you want
     * @return the command object, if it exists
     */
    public TerminalCommand getTerminalCommandObject(String commandName)
    {
        try
        {
            Class<?> toReturn = TerminalManager.getSingleton()
                    .getTerminalCommand(commandName);
            //If we found the command, return a new instance of the command
            if (toReturn != null)
            {
                return (TerminalCommand) toReturn.getConstructor().newInstance();
            }
            return null;
        }
        catch (InstantiationException | IllegalAccessException | IllegalArgumentException | SecurityException |
               NoSuchMethodException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
