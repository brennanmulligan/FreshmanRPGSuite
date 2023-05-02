package edu.ship.engr.shipsim.dataDTO;

import java.util.Objects;

public class ObjectiveCompletionTypeDTO
{
    private int objCompletionId;
    private String objCompletionName;

    public ObjectiveCompletionTypeDTO(int objCompletionId, String objCompletionName)
    {
        this.objCompletionName = objCompletionName;
        this.objCompletionId = objCompletionId;
    }

    public ObjectiveCompletionTypeDTO(String objCompletionName)
    {
        this.objCompletionName = objCompletionName;
    }

    public ObjectiveCompletionTypeDTO(int actionID)
    {
        this.objCompletionId = actionID;
    }

    public String getObjCompletionName()
    {
        return objCompletionName;
    }

    public int getObjCompletionId()
    {
        return objCompletionId;
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
        ObjectiveCompletionTypeDTO that = (ObjectiveCompletionTypeDTO) o;
        return objCompletionId == that.objCompletionId &&
                Objects.equals(objCompletionName, that.objCompletionName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(objCompletionName, objCompletionId);
    }
}
