package edu.ship.engr.shipsim.model.reports;

import java.util.List;
import java.util.Objects;

public class GetAllMapNamesReport
{
    private final List<String> mapNames;

    public GetAllMapNamesReport(List<String> mapNames){
        this.mapNames = mapNames;
    }

    public List<String> getMapNames(){
        return mapNames;
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
        GetAllMapNamesReport that = (GetAllMapNamesReport) o;
        return Objects.equals(mapNames, that.mapNames);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(mapNames);
    }
}
