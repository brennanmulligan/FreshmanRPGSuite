package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datatypes.QuestStateEnum;
import edu.ship.engr.shipsim.datatypes.QuestsForTest;
import nl.jqno.equalsverifier.EqualsVerifier;
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
