package edu.ship.engr.shipsim.model;

import edu.ship.engr.shipsim.dataDTO.LevelManagerDTO;
import edu.ship.engr.shipsim.dataDTO.PlayerDTO;
import edu.ship.engr.shipsim.dataDTO.VanityDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;
import edu.ship.engr.shipsim.datatypes.VanityItemsForTest;
import edu.ship.engr.shipsim.datatypes.VanityType;
import edu.ship.engr.shipsim.model.reports.DoubloonChangeReport;
import edu.ship.engr.shipsim.model.reports.ExperienceChangedReport;
import edu.ship.engr.shipsim.model.reports.NoMoreBuffReport;
import edu.ship.engr.shipsim.model.reports.PlayerAppearanceChangeReport;
import edu.ship.engr.shipsim.model.reports.PlayerMovedReport;

import java.util.ArrayList;
import java.util.Date;

/**
 * The class that hold all the data we need for a player.
 *
 * @author Merlin
 */
public class Player
{
    private PlayerLogin playerLogin;

    private int doubloons;

    private PlayerMapper playerMapper;

    private String appearanceType;

    private int playerID;

    private Position position;

    private String mapName;

    private int experiencePoints;

    private Crew crew;

    final private int LOCAL_CHAT_RADIUS = 5;

    private Major major;

    private int section;

    private int buffPool;

    private boolean playerOnline;

    private ArrayList<String> playerVisitedMaps;

    private VanityInventory vanityInventory = new VanityInventory(this.playerID);

    private String lastVisitedMap;

    Player(int playerID) throws DatabaseException
    {
        this.playerID = playerID;
    }

    /**
     * Add experience points and generates ExperienceChangedReport
     *
     * @param expPoints Player's experience points
     */
    public void addExperiencePoints(int expPoints) throws DatabaseException
    {

        this.experiencePoints = experiencePoints + expPoints;
        ExperienceChangedReport report = new ExperienceChangedReport(this.playerID, this.experiencePoints, LevelManagerDTO.getSingleton().getLevelForPoints(this.experiencePoints));

        ReportObserverConnector.getSingleton().sendReport(report);

    }

    /**
     * @return - player login information
     */
    private PlayerLogin getPlayerLogin()
    {
        return playerLogin;
    }

    /**
     * Get the appearance type for how this player should be drawn
     *
     * @return a string matching one of the enum names in the PlayerType enum
     */
    public String getAppearanceType()
    {
        return appearanceType;
    }

    /**
     * @return the crew to which this player belongs
     */
    public Crew getCrew()
    {
        return crew;
    }

    /**
     * Get the player's experience points
     *
     * @return experience points
     */
    public int getExperiencePoints()
    {
        return experiencePoints;
    }

    /**
     * @return the name of the map this player is on
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * @return the id of this player
     */
    public int getPlayerID()
    {
        return playerID;
    }

    /**
     * Get the unique player name of this player
     *
     * @return the player's name
     */
    public String getPlayerName()
    {
        return playerLogin.getPlayerName();
    }

    /**
     * Get the player's position
     *
     * @return playerPosition Returns the player position. If a position is not set
     * should return null.
     */
    public Position getPosition()
    {
        return position;
    }

    /**
     * Get the quizScore
     *
     * @return the quiz score
     */
    public int getQuizScore()
    {
        return this.doubloons;
    }

    /**
     * Increment doubloons; Checks the buffPool of the player first. If the player
     * has buffPool point, doubloons increase by 1. BuffPool point decreases
     * by 1. Edited: Truc
     */
    public void incrementDoubloons()
    {
        this.doubloons++;

        if (buffPool > 0)
        {
            this.doubloons++;
            this.buffPool--;
            if (this.buffPool == 0)
            {
                NoMoreBuffReport report = new NoMoreBuffReport(playerID);
                ReportObserverConnector.getSingleton().sendReport(report);
            }
        }

        DoubloonChangeReport report = new DoubloonChangeReport(playerID, this.doubloons, this.buffPool);
        ReportObserverConnector.getSingleton().sendReport(report);
    }

    /**
     *
     */
    public void receiveBike()
    {
        int bikeIndex = -1;
        int iterator = 0;

        do
        {
            if(vanityInventory.getInventory().get(iterator).getVanityType() == VanityType.BIKE)
            {
                bikeIndex = iterator;
            }
            iterator++;
        }
        while(bikeIndex == -1 && iterator < vanityInventory.getInventory().size());

        if(bikeIndex != -1)
        {
            vanityInventory.removeVanityItemByID(bikeIndex);
        }

        VanityDTO newBike = new VanityDTO(VanityItemsForTest.BIKE.ordinal()+1, "Bike", "", "bike", VanityType.BIKE, VanityItemsForTest.BIKE.getPrice());
        vanityInventory.addVanityItem(newBike);
        vanityInventory.addWornItem(newBike);

        Date endsAt = new Date(System.currentTimeMillis() + 60 * 1000);
        TimerManager.getSingleton().scheduleCommand(endsAt, new CommandRemovePlayerBike(playerID), playerID);

        PlayerAppearanceChangeReport report = new PlayerAppearanceChangeReport(playerID, vanityInventory.getWornItems());
        ReportObserverConnector.getSingleton().sendReport(report);
    }

    /**
     * Check the pin to make sure it is correct with regards to contents and
     * expiration
     *
     * @param pinToCheck the pin we gave the player to connect to this area server
     * @return true or false with pin validity
     * @throws DatabaseException if the data source had an exception
     */
    public boolean isPinValid(double pinToCheck) throws DatabaseException
    {
        PlayerConnection pl = new PlayerConnection(playerID);
        if (pinToCheck == 1111)
        {
            //This is a magical PIN used for testing
            return true;
        }
        return pl.isPinValid(pinToCheck) && !pl.isExpired();
    }

    /**
     * store the information into the data source
     *
     * @throws DatabaseException if the data source fails to complete the
     *                           persistance
     */
    public void persist() throws DatabaseException
    {
        playerMapper.persist();
    }

    /**
     * Set the appearance type for this player
     *
     * @param appearanceType the new appearance type
     */
    public void setAppearanceType(String appearanceType)
    {
        this.appearanceType = appearanceType;
    }

    /**
     * @param crew the crew to which this player should belong
     */
    public void setCrew(Crew crew)
    {
        this.crew = crew;
    }

    /**
     * @param playerMapper the mapper that will be used to persist this player
     */
    public void setDataMapper(PlayerMapper playerMapper)
    {
        this.playerMapper = playerMapper;
    }

    /**
     * Set experience points and generates ExperienceChangedReport
     *
     * @param expPoints Player's experience points
     */
    public void setExperiencePoints(int expPoints)
    {
        this.experiencePoints = expPoints;
    }

    /**
     * @param mapName the name of the map this player should be on
     */
    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }

    /**
     * @param playerID this player's unique ID
     */
    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }

    /**
     * Tell this player what his login information is
     *
     * @param pl his login information
     */
    public void setPlayerLogin(PlayerLogin pl)
    {
        this.playerLogin = pl;
    }

    /**
     * Set the player's position
     *
     * @param position The new location the player is Assuming a valid
     *                       position. Error checking else where
     */
    public void setPosition(Position position)
    {
        setPositionWithoutNotifying(position);

        sendReportGivingPosition();

    }

    /**
     * Move a player without notifying the other players (used when moving from one
     * map to another
     *
     * @param newPosition the position the player should move to
     */
    public void setPositionWithoutNotifying(Position newPosition)
    {
        this.position = newPosition;
    }

    /**
     * Set the doubloons
     *
     * @param doubloons the new doubloons
     */
    public void setDoubloons(int doubloons)
    {
        this.doubloons = doubloons;
    }

    /**
     * Gets the player's online status
     *
     * @param playerOnline the player's online status
     */
    public void setPlayerOnline(boolean playerOnline)
    {
        this.playerOnline = playerOnline;
    }


    /**
     * When receiving a local message check if the player is close enough to hear
     *
     * @param position position of the sender
     */
    protected boolean canReceiveLocalMessage(Position position)
    {
        Position myPosition = getPosition();

        return Math.abs(myPosition.getColumn() - position.getColumn()) <= LOCAL_CHAT_RADIUS && Math.abs(myPosition.getRow() - position.getRow()) <= LOCAL_CHAT_RADIUS;
    }

    /**
     * Report our position to anyone who is interested
     */
    public void sendReportGivingPosition()
    {
        PlayerMovedReport report = new PlayerMovedReport(playerID, this.getPlayerName(),
                position, this.mapName);

        ReportObserverConnector.getSingleton().sendReport(report);
    }

    /**
     * @return the number of doubloons of this player
     */
    public int getDoubloons()
    {
        return doubloons;
    }

    /**
     * @param price the number of points they are losing
     */
    public void removeDoubloons(int price)
    {
        doubloons -= price;
        DoubloonChangeReport report = new DoubloonChangeReport(playerID, this.doubloons, this.buffPool);
        ReportObserverConnector.getSingleton().sendReport(report);
    }

    /**
     * @return the major of the player
     */
    public Major getMajor()
    {
        return major;
    }


    /**
     * Determines if the player is currently online
     *
     * @return the players online status
     */
    public boolean isPlayerOnline()
    {
        return playerOnline;
    }

    /**
     * @param major of the player
     */
    public void setMajor(Major major)
    {
        this.major = major;
    }

    /**
     * @param section the section this player is in
     */
    public void setSection(int section)
    {
        this.section = section;
    }

    /**
     * @return the section number of this player
     */
    public int getSection()
    {
        return section;
    }

    /**
     * set value for buffPool
     *
     * @param buffPool - the value their buffPool should be set to
     */
    public void setBuffPool(int buffPool)
    {
        this.buffPool = buffPool;
    }

    /**
     * Get value of buffPool
     *
     * @return value of buffPool
     */
    public int getBuffPool()
    {
        return buffPool;
    }

    /**
     * @return list of vanity items
     */
    public ArrayList<VanityDTO> getVanityItems()
    {
        return vanityInventory.getWornItems();
    }

    /**
     * Sets player vanity items to the new list of vanity items
     *
     * @param vanityItems list of vanity items
     */
    public void setVanityItems(ArrayList<VanityDTO> vanityItems)
    {
        this.vanityInventory.setWornItems(vanityItems);
    }

    /**
     * @return a list of the vanity items this player owns
     */
    public ArrayList<VanityDTO> getAllOwnedItems()
    {
        return vanityInventory.getInventory();
    }

    /**
     * Set the player's owned clothing items
     *
     * @param ownedItems list of owned items
     */
    public void setOwnedItems(ArrayList<VanityDTO> ownedItems)
    {
        this.vanityInventory.setInventory(ownedItems);
    }

    /**
     * Adds a vanity item to the players inventory
     *
     * @param vanity the item
     */
    public void addItemToInventory(VanityDTO vanity)
    {
        vanityInventory.addVanityItem(vanity);
    }

    /**
     * Returns a DTO for the player.
     *
     * @return - PlayerInfo DTO
     */
    public PlayerDTO getPlayerDTO()
    {
        PlayerDTO playerInfo = new PlayerDTO();
        playerInfo.setAppearanceType(getAppearanceType());
        playerInfo.setExperiencePoints(getExperiencePoints());
        playerInfo.setDoubloons(getQuizScore());
        playerInfo.setPosition(getPosition());
        playerInfo.setCrew(getCrew());
        playerInfo.setMajor(getMajor());
        playerInfo.setSection(getSection());
        playerInfo.setMapName(getMapName());
        playerInfo.setPlayerID(getPlayerID());
        playerInfo.setPlayerName(getPlayerLogin().getPlayerName());
        playerInfo.setPassword(getPlayerLogin().getPlayerPassword());
        playerInfo.setVanityItems(getAllOwnedItems());
        return playerInfo;
    }

    /**
     * edits and persists the current player
     *
     * @param appearanceType   the appearance type of a player
     * @param quizScore        the players quiz score
     * @param experiencePoints amount of experience points
     * @param crew             the players crew
     * @param major            the players major
     * @param section          the section number
     * @param name             of the player
     * @param password         of the player
     * @throws DatabaseException a database exception
     */
    public void editPlayer(String appearanceType, int quizScore, int experiencePoints, Crew crew, Major major, int section, String name, String password) throws DatabaseException
    {
        this.getPlayerLogin().editPlayerLogin(name, password);
        setAppearanceType(appearanceType);
        setDoubloons(quizScore);
        setExperiencePoints(experiencePoints);
        setCrew(crew);
        setMajor(major);
        setSection(section);
        this.playerMapper.persist();
    }

    /**
     * hashCode
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + LOCAL_CHAT_RADIUS;
        result = prime * result + ((appearanceType == null) ? 0 : appearanceType.hashCode());
        result = prime * result + buffPool;
        result = prime * result + ((crew == null) ? 0 : crew.hashCode());
        result = prime * result + experiencePoints;
        result = prime * result + doubloons;
        result = prime * result + ((major == null) ? 0 : major.hashCode());
        result = prime * result + ((mapName == null) ? 0 : mapName.hashCode());
        result = prime * result + playerID;
        result = prime * result + ((playerLogin == null) ? 0 : playerLogin.hashCode());
        result = prime * result + (playerOnline ? 1231 : 1237);
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + ((playerVisitedMaps == null) ? 0 : playerVisitedMaps.hashCode());
        result = prime * result + section;
        return result;
    }

    /**
     * check if equal
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        // could have been NPC
        if (!(obj instanceof Player))
        {
            return false;
        }
        Player other = (Player) obj;
        if (appearanceType == null)
        {
            if (other.appearanceType != null)
            {
                return false;
            }
        }
        else if (!appearanceType.equals(other.appearanceType))
        {
            return false;
        }
        if (this.getClass().equals(Player.class) && buffPool != other.buffPool)
        {
            return false;
        }
        if (crew != other.crew)
        {
            return false;
        }
        if (experiencePoints != other.experiencePoints)
        {
            return false;
        }
        if (doubloons != other.doubloons)
        {
            return false;
        }
        if (major != other.major)
        {
            return false;
        }
        if (mapName == null)
        {
            if (other.mapName != null)
            {
                return false;
            }
        }
        else if (!mapName.equals(other.mapName))
        {
            return false;
        }
        if (playerID != other.playerID)
        {
            return false;
        }
        if (playerLogin == null)
        {
            if (other.playerLogin != null)
            {
                return false;
            }
        }
        else if (!playerLogin.equals(other.playerLogin))
        {
            return false;
        }
        if (playerOnline != other.playerOnline)
        {
            return false;
        }
        if (position == null)
        {
            if (other.position != null)
            {
                return false;
            }
        }
        else if (!position.equals(other.position))
        {
            return false;
        }
        if (section != other.section)
        {
            return false;
        }
        return playerVisitedMaps.equals(other.playerVisitedMaps);
    }

    /**
     * Changes the players appearance in the database.
     *
     * @param appearanceType - The appearance to change to.
     * @throws DatabaseException a database exception
     */
    public void editPlayerAppearanceType(String appearanceType) throws DatabaseException
    {
        this.playerMapper.setAppearanceType(appearanceType);
        this.playerMapper.persist();
    }

    /**
     * get the player's maps visited
     *
     * @return playerVisitedMaps list of maps
     */
    public ArrayList<String> getPlayerVisitedMaps()
    {
        return playerVisitedMaps;
    }

    /**
     * set the maps a player has visited
     *
     * @param playerVisitedMaps list of maps
     */
    public void setPlayerVisitedMaps(ArrayList<String> playerVisitedMaps)
    {
        this.playerVisitedMaps = playerVisitedMaps;
    }

    /**
     * get the last visited map
     *
     * @return lastVisitedMap
     */
    public String getLastVisitedMap()
    {
        return lastVisitedMap;
    }

    /**
     * add a newly visited map to the player
     *
     * @param mapName the title of the map you just visited.
     */
    public void addNewVisitedMap(String mapName)
    {
        String mapTitle = ServerMapManager.getSingleton().getMapTitleFromMapName(mapName);

        if (mapTitle != null)
        {
            if (!playerVisitedMaps.contains(mapTitle))
            {
                this.playerVisitedMaps.add(mapTitle);
            }
            this.lastVisitedMap = mapTitle;
        }
    }

    /**
     * Removes an item from the player
     * @param item the item to remove
     */
    public void removeVanityType(VanityType item) throws DatabaseException
    {
        for (int i = 0; i < this.vanityInventory.getInventory().size(); i++)
        {
            VanityDTO vanityItem = this.vanityInventory.getInventory().get(i);
            if (vanityItem.getVanityType() == item)
            {
                this.vanityInventory.removeVanityItem(vanityItem);
                this.vanityInventory.removeOwnedItem(vanityItem);
            }
        }

        //adds back the bike none vanity item
        if (item == VanityType.BIKE)
        {

            VanityDTO bike_none = new VanityDTO(VanityItemsForTest.BIKE_NONE.getId(), "No Bike", "", "bike_none", VanityType.BIKE, VanityItemsForTest.BIKE_NONE.getPrice());
            this.vanityInventory.addVanityItem(bike_none);
            this.vanityInventory.addWornItem(bike_none);
        }

        PlayerAppearanceChangeReport playerAppearanceChangeReport =
                new PlayerAppearanceChangeReport(this.playerID,
                        this.vanityInventory.getWornItems());
        ReportObserverConnector.getSingleton().sendReport(playerAppearanceChangeReport);
    }
}