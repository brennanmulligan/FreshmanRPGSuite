package edu.ship.engr.shipsim.datasource;

public class DuplicateNameException extends Exception
{
    private String name;

    private String simpleDescription;
    private Exception rootCause;
    public DuplicateNameException(String msg, Exception e, String name)
    {
        this.name = name;
        simpleDescription = msg;
        rootCause = e;
    }

    public DuplicateNameException(String msg)
    {

        simpleDescription = msg;
    }
    public String getName(String name)
    {
        return name;
    }


    /**
     * @return the original exception, if any, that occurred
     */
    public Exception getRootCause()
    {
        return rootCause;
    }

    /**
     * @return simple Description
     */
    public String getSimpleDescription()
    {
        return simpleDescription;
    }

    /**
     * @see java.lang.Throwable#toString()
     */
    public String toString()
    {
        return simpleDescription + ":" + rootCause;
    }


}
