package model;

import dataDTO.VanityDTO;
import datatypes.Crew;
import datatypes.Major;
import datatypes.Position;
import datatypes.VanityType;
import model.reports.ChangeMapReport;
import model.reports.ChangePlayerAppearanceReport;
import model.reports.ClientPlayerMovedReport;

import java.util.List;
import java.util.Objects;

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
    protected List<VanityDTO> vanities;
    protected List<VanityDTO> ownedItems;
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
		return id == that.id && section == that.section && Objects.equals(name, that.name)
                && Objects.equals(position, that.position) && Objects.equals(vanities, that.vanities)
                && crew == that.crew && major == that.major && Objects.equals(ownedItems, that.ownedItems);
	}

	@Override
	public final int hashCode()
	{
		return Objects.hash(id, name, position, vanities, crew, major, section, ownedItems);
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
     * @return The list of all of the vanities a player has
     */
    public List<VanityDTO> getVanities()
    {
        return vanities;
    }

    /**
     * @return a list of vanity items this player owns
     */
    public List<VanityDTO> getOwnedItems()
    {
        return ownedItems;
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
     * Set the players bodyID no report
     */
    public void setVanityNoReport(List<VanityDTO> vanities)
    {
        this.vanities = vanities;
    }

    public void setVanityReport(List<VanityDTO> vanities)
    {
        this.vanities = vanities;
        QualifiedObservableConnector.getSingleton()
                .sendReport(new ChangePlayerAppearanceReport(this.id, vanities));
    }

    /**
     * Checks if the item is already owned, if not then add
     * @param item
     */
    public void addItemToInventory(VanityDTO item)
    {
        if (!ownedItems.contains(item))
        {
            ownedItems.add(item);
        }
    }

    /**
     * Sets the players owned vanity items
     * @param ownedItems a list of vanity items this player owns
     */
    public void setOwnedItems(List<VanityDTO> ownedItems)
    {
        this.ownedItems = ownedItems;
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
