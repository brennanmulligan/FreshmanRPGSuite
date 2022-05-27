package model.reports;

import datasource.DatabaseException;
import datasource.FriendTableDataGateway;
import datasource.ServerSideTest;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * @author Joshua Wood , Evan Reese
 * Making sure that Friend Received Report is working correctly
 */
public class FriendConnectionReceivedReportTest extends ServerSideTest
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