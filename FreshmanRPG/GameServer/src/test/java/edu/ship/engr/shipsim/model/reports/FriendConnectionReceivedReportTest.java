package edu.ship.engr.shipsim.model.reports;

import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.FriendTableDataGateway;
import edu.ship.engr.shipsim.testing.annotations.GameTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author Joshua Wood , Evan Reese
 * Making sure that Friend Received Report is working correctly
 */
@GameTest("GameServer")
public class FriendConnectionReceivedReportTest
{
    /**
     * Make sure the equals contract is obeyed
     */
    @Test
    public void equalsContract()
    {
        EqualsVerifier.forClass(FriendConnectionReceivedReport.class).verify();
    }

    @Test
    public void test() throws DatabaseException
    {

        FriendTableDataGateway gateway =
                FriendTableDataGateway.getSingleton();

        int senderID = gateway.accept(2, "John");
        FriendConnectionReceivedReport accepted =
                new FriendConnectionReceivedReport(senderID, 2);
        assertEquals(accepted.getSenderID(), gateway.accept(2, "John"));
        assertEquals(accepted.getReceiverID(), 2);
    }
}