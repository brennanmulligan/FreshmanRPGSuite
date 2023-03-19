package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.CrewDTO;
import edu.ship.engr.shipsim.model.Report;
import lombok.Data;

import java.util.ArrayList;

@Data
public class GetAllCrewsReport implements Report
{
    private final ArrayList<CrewDTO> crews;

    public GetAllCrewsReport(ArrayList<CrewDTO> crews)
    {
        this.crews = crews;
    }
}
