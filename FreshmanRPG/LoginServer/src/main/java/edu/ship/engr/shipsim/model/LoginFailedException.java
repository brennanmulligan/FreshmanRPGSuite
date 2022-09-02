package edu.ship.engr.shipsim.model;

/**
 * Used to report that a login attempt failed (database problem or invalid
 * credentials)
 *
 * @author merlin
 */
public class LoginFailedException extends Exception
{


    private static final long serialVersionUID = 1L;

    public LoginFailedException(String message)
    {
        super(message);
    }


}
