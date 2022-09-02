package edu.ship.engr.shipsim.model;

import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;


/**
 * Finds all of the classes in a package that extend a given type
 *
 * @author Merlin
 */
public abstract class TypeDetector
{

    /**
     * Get all of the types that extend a given type in this package
     *
     * @param type the type that we are detecting
     * @return all of the types that extend the given type
     */
    protected ArrayList<Class<?>> detectAllExtendersInPackage(Class<?> type)
    {
        return detectAllExtendersInPackage(type, this.getClass().getPackage().getName());
    }

    /**
     * @param type        the type we are interested in
     * @param packageName the name of the package we should search
     * @return the types
     */
    protected ArrayList<Class<?>> detectAllExtendersInPackage(Class type, String packageName)
    {
        ArrayList<Class<?>> results = new ArrayList<>();
        Reflections commandClasses = new Reflections(packageName);
        @SuppressWarnings("unchecked") Set<Class<?>> cmds = commandClasses.getSubTypesOf(type);

        for (Class<?> cmd : cmds)
        {

            // skip over private classes that are inside another class
            if (!cmd.getName().contains("$"))
            {

                if (extendsClass(cmd, type))
                {
                    results.add(cmd);
                }
            }
        }
        Collections.sort(results, (o1, o2) -> -1 * o1.getName().compareTo(o2.getName()));
        return results;
    }


    /**
     * Get all of the types that extend a given type in this package
     *
     * @param type the type that we are detecting
     * @return all of the types that extend the given type
     */
    protected ArrayList<Class<?>> detectAllImplementorsInPackage(Class<?> type)
    {
        return detectAllImplementorsInPackage(type, this.getClass().getPackage().getName());
    }


    /**
     * Detect all of the types that implement a given type within the current
     * package
     *
     * @param type the type we are interested in
     * @return the types we find
     */
    protected ArrayList<Class<?>> detectAllImplementorsInPackage(Class<?> type, String packageName)
    {
        ArrayList<Class<?>> results = new ArrayList<>();
        Reflections commandClasses = new Reflections(packageName);
        @SuppressWarnings("unchecked") Set<Class<?>> cmds = commandClasses.getSubTypesOf((Class<Object>) type);

        for (Class<?> cmd : cmds)
        {

            // skip over private classes that are inside another class
            if (!cmd.getName().contains("$"))
            {

                if (implementsInterface(cmd, type))
                {
                    results.add(cmd);
                }
            }
        }
        return results;
    }

    /**
     * Checks to see if a class that we are trying to register extends another
     * class - either directly or as a descendent further down the inheritance
     * hierarchy
     *
     * @param classToRegister the class we are trying to register
     * @param classType       the type we want to check
     * @return true if the class being registered extends the given classType
     */
    protected boolean extendsClass(Class<?> classToRegister, Class<?> classType)
    {
        if (classToRegister.equals(classType) || classToRegister.getSuperclass().equals(classType))
        {
            return true;
        }
        Class<?> superclass = classToRegister.getSuperclass();
        if (!superclass.equals(Object.class))
        {
            return extendsClass(classToRegister.getSuperclass(), classType);
        }
        return false;
    }

    /**
     * Checks to see if a class being registered implements a given interface
     *
     * @param classToRegister the class we are registeringa
     * @param interfaceType   the interface we want to check
     * @return true if the class implements that interface
     */
    private boolean implementsInterface(Class<?> classToRegister, Class<?> interfaceType)
    {
        if (classToRegister.equals(interfaceType))
        {
            return false;
        }
        Class<?>[] list = classToRegister.getInterfaces();
        boolean found = false;
        for (Class<?> item : list)
        {
            if (recursivelyImplements(item, interfaceType))
            {
                found = true;
            }
        }
        return found;

    }

    /**
     * looks recursively to see if a class implements a given interface
     *
     * @param item          the class we are checking
     * @param interfaceType the interface we want to look for
     * @return true if the class implements the interface
     */
    private boolean recursivelyImplements(Class<?> item, Class<?> interfaceType)
    {
        if (item == interfaceType)
        {
            return true;
        }
        Class<?>[] list = item.getInterfaces();
        boolean found = false;
        for (Class<?> nextItem : list)
        {
            if (recursivelyImplements(nextItem, interfaceType))
            {
                found = true;
            }
        }

        return found;
    }

}
