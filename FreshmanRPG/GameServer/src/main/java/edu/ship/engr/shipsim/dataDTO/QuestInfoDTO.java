package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.model.QuestRecord;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class QuestInfoDTO
{
    private ArrayList<QuestRecord> questRecords;
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
        return Objects.equals(questRecords, that.questRecords) &&
                Objects.equals(mapNames, that.mapNames) &&
                Objects.equals(completionActionTypes,
                        that.completionActionTypes);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(questRecords, mapNames, completionActionTypes);
    }

    public QuestInfoDTO(ArrayList<QuestRecord> questRecords, ArrayList<String> mapNames,
                        ArrayList<QuestCompletionActionType> completionActionTypes)
    {
        this.questRecords = questRecords;
        this.mapNames = mapNames;
        this.completionActionTypes = completionActionTypes;
    }

    public ArrayList<QuestRecord> getQuestRecords()
    {
        return questRecords;
    }

    public void setQuestRecords()
    {
        this.questRecords = questRecords;

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
