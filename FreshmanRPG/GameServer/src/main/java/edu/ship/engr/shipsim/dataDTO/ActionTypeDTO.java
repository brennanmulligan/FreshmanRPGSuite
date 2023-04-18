package edu.ship.engr.shipsim.dataDTO;

import java.util.Objects;

public class ActionTypeDTO
{
    private String actionName;
    private int actionID;

    public ActionTypeDTO(int actionID, String actionName)
    {
        this.actionName = actionName;
        this.actionID = actionID;
    }

    public ActionTypeDTO(String actionName)
    {
        this.actionName = actionName;
    }

    public ActionTypeDTO(int actionID)
    {
        this.actionID = actionID;
    }

    public String getActionName()
    {
        return actionName;
    }

    public int getActionID()
    {
        return actionID;
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
        ActionTypeDTO that = (ActionTypeDTO) o;
        return actionID == that.actionID &&
                Objects.equals(actionName, that.actionName);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(actionName, actionID);
    }
}