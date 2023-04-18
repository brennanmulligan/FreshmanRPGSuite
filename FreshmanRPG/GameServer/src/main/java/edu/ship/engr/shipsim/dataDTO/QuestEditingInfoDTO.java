package edu.ship.engr.shipsim.dataDTO;

import edu.ship.engr.shipsim.dataENUM.QuestCompletionActionType;
import edu.ship.engr.shipsim.model.QuestRecord;

import java.util.ArrayList;
import java.util.Objects;

public class QuestEditingInfoDTO
{
    private ArrayList<QuestRecordDTO> questRecords;
    private ArrayList<String> mapNames;
    private ArrayList<ActionTypeDTO> completionActionTypes;

    public QuestEditingInfoDTO()
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
        QuestEditingInfoDTO that = (QuestEditingInfoDTO) o;
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

    public QuestEditingInfoDTO(ArrayList<QuestRecordDTO> questRecords, ArrayList<String> mapNames,
                               ArrayList<ActionTypeDTO> completionActionTypes)
    {
        this.questRecords = questRecords;
        this.mapNames = mapNames;
        this.completionActionTypes = completionActionTypes;
    }

    public ArrayList<QuestRecordDTO> getQuestRecords()
    {
        return questRecords;
    }

    public void setQuestRecords(ArrayList<QuestRecordDTO> questRecords)
    {
        this.questRecords = questRecords;
    }

    public ArrayList<String> getMapNames()
    {
        return mapNames;
    }

    public void setMapNames(ArrayList<String> mapNames)
    {
        this.mapNames = mapNames;
    }

    public ArrayList<ActionTypeDTO> getCompletionActionTypes()
    {
        return completionActionTypes;
    }

    public void setCompletionActionTypes(ArrayList<ActionTypeDTO> completionActionTypes)
    {
        this.completionActionTypes = completionActionTypes;
    }
}