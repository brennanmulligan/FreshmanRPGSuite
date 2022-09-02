package edu.ship.engr.shipsim.dataDTO;

/**
 * An object that stores data for a player.
 */
public class GameManagerPlayerDTO
{
    private String name;
    private String password;
    private int crew;
    private int major;
    private int section;

    /**
     * Create and initialize a GameManagerPlayerDTO
     *
     * @param name     - name
     * @param password - password
     * @param crew     - crew
     * @param major    - major
     * @param section  - section
     */
    public GameManagerPlayerDTO(String name, String password, int crew, int major, int section)
    {
        this.name = name;
        this.password = password;
        this.crew = crew;
        this.major = major;
        this.section = section;
    }

    /**
     * Return the name.
     *
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return the password.
     *
     * @return password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Return the crew.
     *
     * @return crew
     */
    public int getCrew()
    {
        return crew;
    }

    /**
     * Return the major.
     *
     * @return major
     */
    public int getMajor()
    {
        return major;
    }

    /**
     * Return the section.
     *
     * @return section
     */
    public int getSection()
    {
        return section;
    }

}
