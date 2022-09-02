package api.model;

import java.util.Objects;

/**
 * Login Request Object
 *
 * @author Jun
 */
public class LoginRequest
{
    private String username;
    private String password;

    /**
     * Creates a login request with username and password
     *
     * @param username - the players username
     * @param password - the players password
     */
    public LoginRequest(String username, String password)
    {
        this.username = username;
        this.password = password;
    }


    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        LoginRequest that = (LoginRequest) o;
        return Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username, password);
    }

    @Override
    public String toString()
    {
        return "LoginRequest{" +
                "name='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
