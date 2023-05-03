package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.datatypes.Crew;
import edu.ship.engr.shipsim.datatypes.Major;
import edu.ship.engr.shipsim.datatypes.Position;

import java.util.ArrayList;

/**
 * Contains the information of a player.
 */
public class PlayerDTO
{
    private int playerID;
    private String playerName;
    private String password;
    private String appearanceType;
    private Position position;
    private int doubloons;
    private String mapName;
    private int experiencePoints;
    private Crew crew;
    private Major major;
    private int section;
    private int buffPool;
    private ArrayList<String> visitedMaps;
    private ArrayList<VanityDTO> vanityDTOs;

    /**
     * Default constructor.
     */
    public PlayerDTO()
    {

    }


    /**
     * Testing constructor for game manager
     *
     * @param playerID         the player's unique ID
     * @param playerName       the player's name
     * @param password   the player's password
     * @param appearanceType   - how the player will be displayed
     * @param doubloons        - the quiz score of the player
     * @param position         - where the player is standing
     * @param mapName          - the map the player is in currently
     * @param experiencePoints - the number of experience points the player has earned
     * @param crew             - the crew the player belongs to
     * @param major            - the player'a major
     * @param section        - the player's section
     * @param visitedMaps      - the player's visited maps
     */
    public PlayerDTO(int playerID, String playerName, String password, String appearanceType, int doubloons,
                     Position position, String mapName, int experiencePoints, Crew crew, Major major, int section, ArrayList<String> visitedMaps, ArrayList<VanityDTO> vanityDTOs)
    {
        this.playerID = playerID;
        this.playerName = playerName;
        this.appearanceType = appearanceType;
        this.doubloons = doubloons;
        this.position = position;
        this.mapName = mapName;
        this.experiencePoints = experiencePoints;
        this.crew = crew;
        this.major = major;
        this.section = section;
        this.password = password;
        this.visitedMaps = visitedMaps;
        this.vanityDTOs = vanityDTOs;
    }


    /**
     * Constructor used by the game manager... Attributes that aren't set by the game manager are set to default values
     *
     * @param playerID - id
     * @param password - player's password
     * @param crew     - player's crew
     * @param major    - player's major
     * @param section  - player's section
     * @param playerName     - player's playerName
     */
    protected PlayerDTO(int playerID, String password, Crew crew, Major major, int section, String playerName)
    {
        this(playerID, playerName, password, "Ninja", 0, new Position(0, 0), "sortingroom.tmx", 0, crew, major, section, new ArrayList<>(), new ArrayList<>());
    }


    /**
     * @param playerID the player's unique id
     */
    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }

    /**
     * comment
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((appearanceType == null) ? 0 : appearanceType.hashCode());
        result = prime * result + buffPool;
        result = prime * result + ((crew == null) ? 0 : crew.hashCode());
        result = prime * result + experiencePoints;
        result = prime * result + doubloons;
        result = prime * result + ((major == null) ? 0 : major.hashCode());
        result = prime * result + ((mapName == null) ? 0 : mapName.hashCode());
        result = prime * result + playerID;
        result = prime * result + ((playerName == null) ? 0 : playerName.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + section;
        result = prime * result + ((visitedMaps == null) ? 0 : visitedMaps.hashCode());
        return result;
    }

    /**
     * comment
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
        if (getClass() != obj.getClass())
        {
            return false;
        }
        PlayerDTO other = (PlayerDTO) obj;
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
        if (buffPool != other.buffPool)
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
        if (playerName == null)
        {
            if (other.playerName != null)
            {
                return false;
            }
        }
        else if (!playerName.equals(other.playerName))
        {
            return false;
        }
        if (password == null)
        {
            if (other.password != null)
            {
                return false;
            }
        }
        else if (!password.equals(other.password))
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
        if (visitedMaps == null)
        {
            if (other.visitedMaps != null)
            {
                return false;
            }
        }
        else if (!visitedMaps.equals(other.visitedMaps))
        {
            return false;
        }
        return true;
    }


    /**
     * @return the player's unique id
     */
    public int getPlayerID()
    {
        return this.playerID;
    }


    /**
     * @param appearanceType the appearance type we will use to render the player
     */
    public void setAppearanceType(String appearanceType)
    {
        this.appearanceType = appearanceType;
    }

    /**
     * @return the string describing the character type the player will be shown as
     */
    public String getAppearanceType()
    {
        return appearanceType;
    }

    /**
     * @param playerPosition the player's current position
     */
    public void setPosition(Position playerPosition)
    {
        this.position = playerPosition;
    }

    /**
     * @return the row/column the player is standing on
     */
    public Position getPosition()
    {
        return position;
    }

    /**
     * @return doubloons
     */
    public int getDoubloons()
    {
        return doubloons;
    }

    /**
     * Set the doubloons of the player.
     *
     * @param doubloons - the new value for doubloons.
     */
    public void setDoubloons(int doubloons)
    {
        this.doubloons = doubloons;
    }

    /**
     * @param mapName where the player is
     */
    public void setMapName(String mapName)
    {
        this.mapName = mapName;
    }

    /**
     * @return the name of the map the player is playing in
     */
    public String getMapName()
    {
        return mapName;
    }

    /**
     * @param experiencePoints the player's experience
     */
    public void setExperiencePoints(int experiencePoints)
    {
        this.experiencePoints = experiencePoints;
    }

    /**
     * @return the number of experience points the player has earned
     */
    public int getExperiencePoints()
    {
        return experiencePoints;
    }

    /**
     * @param crew the crew the player belongs to
     */
    public void setCrew(Crew crew)
    {
        this.crew = crew;
    }

    /**
     * @return the crew the player belongs to
     */
    public Crew getCrew()
    {
        return crew;
    }


    /**
     * @param major the player's major
     */
    public void setMajor(Major major)
    {
        this.major = major;
    }

    /**
     * @return the player's major
     */
    public Major getMajor()
    {
        return major;
    }

    /**
     * @param section the section the player is enrolled in
     */
    public void setSection(int section)
    {
        this.section = section;
    }

    /**
     * @return player's section
     */
    public int getSection()
    {
        return section;
    }

    /**
     * @param playerName the player's name
     */
    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }

    /**
     * @return the player's name
     */
    public String getPlayerName()
    {
        return playerName;
    }

    /**
     * @param password the player's password
     */
    public void setPassword(String password)
    {
        this.password = password;

    }

    /**
     * @return the player's password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @return the player's unique id
     */
    public int getPlayerId()
    {
        return this.playerID;
    }

    /**
     * set buffPool
     *
     * @param buffPool - the amount of bonus EXP to set the buffPool to.
     */
    public void setBuffPool(int buffPool)
    {
        this.buffPool = buffPool;
    }

    /**
     * get buffPool
     *
     * @return buffPool
     */
    public int getBuffPool()
    {
        return this.buffPool;
    }

    /**
     * get the visited maps
     *
     * @return visitedMaps the list of maps
     */
    public ArrayList<String> getVisitedMaps()
    {
        return this.visitedMaps;
    }

    /**
     * @return the list of vanity items this player owns
     */
    public ArrayList<VanityDTO> getVanityItems()
    {
        return vanityDTOs;
    }

    /**
     * @param ownedItems a list of the vanity items this player owns
     */
    public void setVanityItems(ArrayList<VanityDTO> ownedItems)
    {
        vanityDTOs = ownedItems;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "PlayerInfo [playerName=" + playerName + "]";
    }
}
