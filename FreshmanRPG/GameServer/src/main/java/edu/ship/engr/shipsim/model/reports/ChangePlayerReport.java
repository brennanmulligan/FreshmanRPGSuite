package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.model.Report;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;


@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ChangePlayerReport implements Report
{
    private final boolean successful;

    @Setter(AccessLevel.NONE)
    private String description = "";
}