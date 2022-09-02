package api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

/**
 * Login Response Object
 *
 * @author Jun
 */
public class LoginResponse
{
    private int playerID;

    /**
     * creates a login response containing the playerID
     *
     * @param playerID
     */
    public LoginResponse(int playerID)
    {
        this.playerID = playerID;
    }

    public int getPlayerID()
    {
        return playerID;
    }

    public String toJSON() throws JsonProcessingException
    {
        return new ObjectMapper().writeValueAsString(this);
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
        LoginResponse that = (LoginResponse) o;
        return playerID == that.playerID;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(playerID);
    }

    @Override
    public String toString()
    {
        return "LoginResponse{" +
                "playerID=" + playerID +
                '}';
    }
}
