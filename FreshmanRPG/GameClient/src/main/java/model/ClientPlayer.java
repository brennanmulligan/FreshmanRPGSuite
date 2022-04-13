package model;

import java.util.Objects;
import java.util.Observable;

import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import model.reports.ChangeMapReport;
import model.reports.ChangePlayerAppearanceReport;
import model.reports.ClientPlayerMovedReport;

/**
 * Holds the information about one player in the system
 *
 * @author merlin
 */
public class ClientPlayer
{

    protected final int id;
    protected String name;
    protected Position position;
    protected String appearanceType;
    private Crew crew;
    private Major major;
    private int section;

    /**
     * Create a player
     *
     * @param playerID the unique ID of this player
     */
    public ClientPlayer(int playerID)
    {
        this.id = playerID;
        this.position = new Position(0, 0);
    }

	@Override
	public final boolean equals(Object o)
	{
		if (this == o)
        {
            return true;
        }
		if (!(o instanceof ClientPlayer))
        {
            return false;
        }
		ClientPlayer that = (ClientPlayer) o;
		return id == that.id && section == that.section && Objects.equals(name, that.name) && Objects.equals(position, that.position) && Objects.equals(appearanceType, that.appearanceType) && crew == that.crew && major == that.major;
	}

	@Override
	public final int hashCode()
	{
		return Objects.hash(id, name, position, appearanceType, crew, major, section);
	}

	/**
     * Get the unique ID name for this player
     *
     * @return the player ID
     */
    public int getID()
    {
        return id;
    }

    /**
     * get this player's unique name
     *
     * @return the name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @return this player's appearance type
     */
    public String getAppearanceType()
    {
        return this.appearanceType;
    }

    /**
     * set this player's name
     *
     * @param playerName the new name
     */
    public void setName(String playerName)
    {
        this.name = playerName;
    }

    /**
     * Set this player's appearance. Does not send a report.
     *
     * @param appearanceType the new appearance type
     */
    public void setAppearanceTypeNoReport(String appearanceType)
    {
        this.appearanceType = appearanceType;
    }

    /**
     * Set this player's appearance. Sends a report.
     *
     * @param appearanceType - the new appearance type
     */
    public void setAppearanceTypeReport(String appearanceType)
    {
        this.appearanceType = appearanceType;
        QualifiedObservableConnector.getSingleton()
                .sendReport(new ChangePlayerAppearanceReport(this.id, appearanceType));
    }

    /**
     * Move the player's position
     *
     * @param playerPosition      The new location the player is Assuming a valid position.
     *                            Error checking else where
     * @param isThisClientsPlayer true if we are moving this client's player
     */
    public void move(Position playerPosition, boolean isThisClientsPlayer)
    {
        this.position = playerPosition;
        QualifiedObservableConnector.getSingleton()
                .sendReport(new ClientPlayerMovedReport(this.id, playerPosition, isThisClientsPlayer));
    }

    /**
     * Forcibly sets the player's position without notifying observers.
     *
     * Should only be called when the player is initialized.
     *
     * @param playerPosition The new location of the player
     */
    public void setPosition(Position playerPosition)
    {
        this.position = playerPosition;
    }

    /**
     * Get the player's position
     *
     * @return playerPosition Returns the player position. If a position is not
     * set should return null.
     */
    public Position getPosition()
    {
        return this.position;
    }

    /**
     * @param thePosition is the position of the hotspot Creates a ChangeMapReport with
     *                    the Map and location the player will teleport to.
     */
    public void teleport(Position thePosition)
    {
        TeleportHotSpot hotSpot = MapManager.getSingleton().getTeleportHotSpot(thePosition);
        QualifiedObservableConnector.getSingleton()
                .sendReport(new ChangeMapReport(id, hotSpot.getTeleportPosition(), hotSpot.getMapName()));
    }

    /**
     * @return the crew this player belongs to
     */
    public Crew getCrew()
    {
        return crew;
    }

    /**
     * @param crew the crew this player should belong to
     */
    protected void setCrew(Crew crew)
    {
        this.crew = crew;
    }

    /**
     * @return the major of the player
     */
    public Major getMajor()
    {
        return major;
    }

    /**
     * @param major of the player
     */
    public void setMajor(Major major)
    {
        this.major = major;
    }

    /**
     * @return the section number
     */
    public int getSection()
    {
        return section;
    }

    /**
     * @param section number of the player
     */
    public void setSection(int section)
    {
        this.section = section;
    }

}
