package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class QuestInfoDTO
{
    private ArrayList<QuestDTO> questDTO;
    private ArrayList<String> mapNames;
    private ArrayList<QuestCompletionActionType> completionActionTypes;

    public QuestInfoDTO()
    {

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
        QuestInfoDTO that = (QuestInfoDTO) o;
        return Objects.equals(questDTO, that.questDTO) &&
                Objects.equals(mapNames, that.mapNames) &&
                Objects.equals(completionActionTypes,
                        that.completionActionTypes);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(questDTO, mapNames, completionActionTypes);
    }

    public QuestInfoDTO(ArrayList<QuestDTO> questDTO, ArrayList<String> mapNames,
                        ArrayList<QuestCompletionActionType> completionActionTypes)
    {
        this.questDTO = questDTO;
        this.mapNames = mapNames;
        this.completionActionTypes = completionActionTypes;
    }

    public ArrayList<QuestDTO> getQuestDTO()
    {
        return questDTO;
    }

    public void setQuestDTO()
    {
        this.questDTO = questDTO;

    }

    public ArrayList<String> getMapNames()
    {
        return mapNames;
    }

    public void setMapNames()
    {
        this.mapNames = mapNames;
    }

    public ArrayList<QuestCompletionActionType> getCompletionActionTypes()
    {
        return completionActionTypes;
    }

    public void setCompletionActionTypes()
    {
        this.completionActionTypes = completionActionTypes;
    }
}
