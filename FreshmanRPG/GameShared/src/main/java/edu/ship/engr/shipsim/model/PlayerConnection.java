package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.PlayerConnectionRowDataGateway;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * The behaviors associated with the PINs that are given to players when the are
 * switching servers
 *
 * @author Merlin
 */
public class PlayerConnection
{

    /**
     * The units in which we measure expiration time of a pin
     */
    public static final int EXPIRATION_TIME_UNITS = Calendar.HOUR;

    /**
     * The number of expiration time units before a pin should expire
     */
    public static final int EXPIRATION_TIME_QUANTITY = 12;
    /**
     * An error message
     */
    public static final String ERROR_PIN_NOT_EXIST = "Pin does not exist";
    /**
     * An error message
     */
    public static final String ERROR_PIN_EXPIRED = "Pin has expired";

    /**
     * Used as the default pin in testing
     */
    public static final int DEFAULT_PIN = 1111;

    private final PlayerConnectionRowDataGateway gateway;

    /**
     * @param playerID the player whose information we are using
     * @throws DatabaseException if the data source has an exception
     */
    protected PlayerConnection(int playerID) throws DatabaseException
    {
        gateway = new PlayerConnectionRowDataGateway(playerID);

    }

    /**
     * Generate the PIN this player should use for logging into his area server
     * and put it in the DB
     *
     * @return the PIN
     * @throws DatabaseException shouldn't
     */
    protected int generatePin() throws DatabaseException
    {
        int pin = (int) (Math.random() * Integer.MAX_VALUE);
        storePin(pin);
        return pin;
    }

    /**
     * Generates the default PIN for testing purposes only!
     *
     * @throws DatabaseException shouldn't
     */
    protected void generateTestPin() throws DatabaseException
    {
        this.storePin(DEFAULT_PIN);
    }

    /**
     * Get the name of the map the player was most recently on
     *
     * @return the name of the tmx file
     */
    protected String getMapName()
    {
        return gateway.getMapName();
    }

    /**
     * @return this player's current pin
     */
    protected double getPin()
    {
        return gateway.getPin();
    }

    /**
     * Get the time when this player's pin expires in GMT
     *
     * @return the expiration time
     */
    protected boolean isExpired()
    {
        GregorianCalendar now = new GregorianCalendar();
        now.setTimeZone(TimeZone.getTimeZone("GMT"));
        GregorianCalendar expirationTime;
        String changedOn = gateway.getChangedOn();
        if (changedOn == null)
        {
            return false;
        }
        expirationTime = parseTimeString(changedOn);
        expirationTime.add(Calendar.HOUR, 12);
        return !expirationTime.after(now);
    }

    /**
     * check if a pin is valid for a given player
     *
     * @param pin The pin to check against
     * @return true or false for whether the given pin is valid or not
     */
    protected boolean isPinValid(double pin)
    {
        return pin == gateway.getPin();
    }

    /**
     * Convert the time string from the data source into a Gregorian calendar.
     *
     * @param timeString The format of that time string must be
     *                   "yyyy-MM-dd HH:mm:ss"
     * @return the appropriate Gregorian Calendar
     */
    protected GregorianCalendar parseTimeString(String timeString)
    {
        GregorianCalendar result = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            result.setTime(sdf.parse(timeString));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Used only for testing!!!!! Used to set the timestamp on the player's PIN
     *
     * @param newTime the time we want
     * @throws DatabaseException if we cant set the time whan a player's
     *                           connection info was changes
     */
    protected void setChangedOn(String newTime) throws DatabaseException
    {
        gateway.setChangedOn(newTime);
    }

    /**
     * Store the map that the player is using
     *
     * @param mapFileTitle the title of the tmx file
     * @throws DatabaseException if we cannot update their state in the database
     */
    protected void setMapName(String mapFileTitle) throws DatabaseException
    {
        gateway.storeMapName(mapFileTitle);
    }

    private void storePin(int pin) throws DatabaseException
    {
        gateway.storePin(pin);
    }
}
