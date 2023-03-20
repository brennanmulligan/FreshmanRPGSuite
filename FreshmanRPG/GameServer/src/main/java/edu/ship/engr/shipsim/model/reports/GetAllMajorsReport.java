package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.dataDTO.MajorDTO;

import edu.ship.engr.shipsim.model.Report;
import lombok.Data;

import java.util.ArrayList;

@Data
public class GetAllMajorsReport implements Report
{
    private final ArrayList<MajorDTO> majors;

    public GetAllMajorsReport(ArrayList<MajorDTO> majors)
    {
        this.majors = majors;
    }
}
