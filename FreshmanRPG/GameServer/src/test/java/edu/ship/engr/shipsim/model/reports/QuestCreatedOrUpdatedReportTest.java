package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestCreatedOrUpdatedReportTest
{
    /**
     * make sure it gets built correctly
     *
     * @throws DatabaseException shouldn't
     */
    @Test
    public void creation() throws DatabaseException
    {
        QuestCreatedOrUpdatedReport report = new QuestCreatedOrUpdatedReport(true);
        assertTrue(report.getSuccess());
    }
}
