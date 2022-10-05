package edu.ship.engr.shipsim.communication.messages;

import edu.ship.engr.shipsim.model.TypeDetector;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * This class verifies all of the classes in its package have the structure
 * required for messages. This means that they must be serializable and have
 * getters for every non-transient/final field. For a class to be serializable,
 * it must implement the Serializable interface and all of its
 * non-transient/final fields must also be serializable.
 *
 * @author Merlin
 */
@GameTest("GameShared")
public class MessageStructureVerifier extends TypeDetector
{

    /**
     * Find all of the classes in the package containing this test and test them
     * to see if they meet the requirements
     *
     * @throws ClassNotFoundException shouldn't
     */
    @Test
    public void testAllClassesInThisPackage() throws ClassNotFoundException
    {
        ArrayList<Class<?>> messageClasses = detectAllImplementorsInPackage(Message.class);
        ArrayList<Class<?>> serializableClasses = detectAllImplementorsInPackage(Serializable.class);

        Reflections commandClasses = new Reflections(this.getClass().getPackage().getName());
        Set<Class<?>> cmds = commandClasses.getSubTypesOf(Object.class);

        for (Class<?> classToCheck : cmds)
        {
            if (classToCheck != this.getClass() && (classToCheck != Message.class))
            {
                if (!messageClasses.contains(classToCheck))
                {
                    fail(classToCheck.getName() + " must implement Message");
                }
                if (!serializableClasses.contains(classToCheck))
                {
                    fail(classToCheck.getName() + " must implement Serializable");
                }

                verifyStructureForSerializability(classToCheck);
            }

        }

    }

    /**
     * Runs the entire series of checks for the structure required for
     * serializability
     *
     * @param classToCheck the class whose structure we should check
     */
    public void verifyStructureForSerializability(Class<?> classToCheck)
    {

        ArrayList<Field> storedFields = getStoredFields(classToCheck);
        for (Field fieldToCheck : storedFields)
        {
            checkIsSerializable(fieldToCheck);
            verifyGetter(fieldToCheck, classToCheck);
        }

    }

    private static void verifyGetter(Field fieldToCheck, Class<?> classToCheck)
    {
        StringBuffer capitalizedFieldName = new StringBuffer(fieldToCheck.getName());
        Class<?> type = fieldToCheck.getType();

        char firstChar = capitalizedFieldName.charAt(0);
        if ((firstChar >= 'a') && (firstChar <= 'z'))
        {
            capitalizedFieldName.setCharAt(0, (char) (firstChar - ('a' - 'A')));
        }
        try
        {
            if (type.toString().equals("boolean"))
            {
                classToCheck.getDeclaredMethod("is" + capitalizedFieldName, new Class[0]);
            }
            else
            {
                classToCheck.getDeclaredMethod("get" + capitalizedFieldName, new Class[0]);
            }
        }
        catch (NoSuchMethodException e)
        {
            fail(classToCheck.getName() + " is missing a getter for " + fieldToCheck);
        }
        catch (Exception e)
        {
            fail();
        }
    }

    /**
     * @param fieldToCheck
     */
    private static void checkIsSerializable(Field fieldToCheck)
    {
        Class<?> type = fieldToCheck.getType();
        if (!type.isPrimitive())
        {
            checkImplementsInterface(type, null);
        }
    }

    private static boolean checkImplementsInterface(Class<?> classToTest, Class<?> interfaceToCheckFor)
    {
        if (classToTest == interfaceToCheckFor)
        {
            return true;
        }
        Class<?>[] list = classToTest.getInterfaces();
        boolean found = false;
        for (Class<?> item : list)
        {
            if (checkImplementsInterface(item, interfaceToCheckFor))
            {
                found = true;
            }
        }
        if (!found)
        {
            Class<?> superclass = classToTest.getSuperclass();
            if (superclass != null)
            {
                return checkImplementsInterface(superclass, interfaceToCheckFor);
            }
            else
            {
                return false;
            }
        }

        return found;
    }

    private static ArrayList<Field> getStoredFields(Class<?> classToCheck)
    {
        ArrayList<Field> storedFields = new ArrayList<>();

        Field[] allFields = classToCheck.getDeclaredFields();
        for (int i = 0; i < allFields.length; i++)
        {
            int modifiers = allFields[i].getModifiers();
            if (!Modifier.isTransient(modifiers) && !Modifier.isFinal(allFields[i].getModifiers()))
            {
                storedFields.add(allFields[i]);
            }
        }
        return storedFields;
    }
}
