package api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Objects;

public class CreatePlayerResponce
{

    private int responseType;

    public CreatePlayerResponce(int response)
    {
        this.responseType = response;
    }

    public int getResponseType()
    {
        return responseType;
    }

    public void setResponseType(int responseType)
    {
        this.responseType = responseType;
    }

    public String toJSON() throws JsonProcessingException
    {
        return new ObjectMapper().writeValueAsString(this);
    }

    @Override
    public String toString()
    {
        return "CreatePlayerResponce{" +
                "responseType=" + responseType +
                '}';
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
        CreatePlayerResponce that = (CreatePlayerResponce) o;
        return responseType == that.responseType;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(responseType);
    }
}
