package api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

/**
 * An object that stores data for a player.
 */
public class GameManagerPlayer
{
    private final String name;
    private final String password;
    private final int crew;
    private final int major;
    private final int section;

    /**
     * Create and initialize a Player
     *
     * @param name     - name
     * @param password - password
     * @param crew     - crew
     * @param major    - major
     * @param section  - section
     */
    public GameManagerPlayer(String name, String password, int crew, int major,
                             int section)
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof GameManagerPlayer))
        {
            return false;
        }
        GameManagerPlayer player = (GameManagerPlayer) o;
        return getCrew() == player.getCrew() && getMajor() == player.getMajor() && getSection() == player.getSection() && Objects.equals(getName(), player.getName()) && Objects.equals(getPassword(), player.getPassword());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getName(), getPassword(), getCrew(), getMajor(), getSection());
    }

    @Override
    public String toString()
    {
        return "Player{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", crew=" + crew +
                ", major=" + major +
                ", section=" + section +
                '}';
    }

    public String toJSON() throws JsonProcessingException
    {
        return new ObjectMapper().writeValueAsString(this);
    }
}
