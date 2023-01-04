package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.FriendTableDataGateway;
import edu.ship.engr.shipsim.datatypes.FriendStatusEnum;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.reports.FriendConnectionReceivedReport;
import edu.ship.engr.shipsim.model.reports.updateFriendListReport;

/**
 * Class detailing the behavior of the 'friend add' command
 *
 * @author Zachary Semanco, Mina Kindo.
 */
class FriendAcceptBehavior extends FriendBehavior
{

    @Override
    protected String execute(int playerID, String[] friends)
    {
        StringBuilder result = new StringBuilder("> Friend Name - Status\n");
        FriendTableDataGateway gateway = getTheGateway();
        /*
         * Loop through the list of friends and try to accept each request
         */
        for (String friend : friends)
        {
            //Try to accept request from friend
            try
            {
                gateway.accept(playerID, friend);
                result.append("> ").append(friend).append(" ")
                        .append(FriendStatusEnum.ACCEPTED).append("\n");
                FriendTableDataGateway friendGateway =
                        FriendTableDataGateway.getSingleton();
                ReportObserverConnector.getSingleton().sendReport(
                        new FriendConnectionReceivedReport(
                                friendGateway.accept(playerID, friend), playerID));

                //send the report to the other player
                FriendDTO objForReportOne =
                        new FriendDTO(playerID, gateway.getSpecificIDFromName(friend),
                                FriendStatusEnum.ACCEPTED,
                                gateway.getSpecificNameFromId(playerID), friend);

                ReportObserverConnector.getSingleton()
                        .sendReport(new updateFriendListReport(objForReportOne));

                FriendDTO objForReportTwo =
                        new FriendDTO(gateway.getSpecificIDFromName(friend), playerID,
                                FriendStatusEnum.ACCEPTED, friend,
                                gateway.getSpecificNameFromId(playerID));
                ReportObserverConnector.getSingleton()
                        .sendReport(new updateFriendListReport(objForReportTwo));
            }
            catch (DatabaseException e)
            {
                //Player not found
                if (e.getSimpleDescription().startsWith("Player not found or updated"))
                {
                    result.append("> ").append(friend)
                            .append(" Error: Couldn't accept request\n");
                }
                //Other Database Exception
                else
                {
                    e.printStackTrace();
                }
            }
            catch (NullPointerException e)
            {
                result.append("> ").append(friend)
                        .append(" Error: Couldn't accept request\n");
            }
        }
        return result.toString();
    }

}
