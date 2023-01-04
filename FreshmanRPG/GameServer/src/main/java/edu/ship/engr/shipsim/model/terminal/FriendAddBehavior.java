package edu.ship.engr.shipsim.model.terminal;

import edu.ship.engr.shipsim.dataDTO.FriendDTO;
import edu.ship.engr.shipsim.datasource.DatabaseException;
import edu.ship.engr.shipsim.datasource.FriendTableDataGateway;
import edu.ship.engr.shipsim.datatypes.FriendStatusEnum;
import edu.ship.engr.shipsim.model.ReportObserverConnector;
import edu.ship.engr.shipsim.model.reports.updateFriendListReport;

/**
 * Class detailing the behavior of the 'friend add' command
 *
 * @author Zachary Semanco, Mina Kindo.
 */
class FriendAddBehavior extends FriendBehavior
{
    /**
     * Execute method for the AddBehavior
     */
    @Override
    protected String execute(int playerID, String[] friends)
    {
        StringBuilder result = new StringBuilder("> Friend Name - Status\n");
        FriendTableDataGateway gateway = getTheGateway();

        /*
         * Loop through the list of friends and try to add each one
         */
        for (String friend : friends)
        {
            //Try to add friend
            try
            {
                gateway.add(playerID, friend, FriendStatusEnum.PENDING);
                result.append("> ").append(friend).append(" ")
                        .append(FriendStatusEnum.PENDING).append("\n");

                FriendDTO objForReportOne =
                        new FriendDTO(playerID, gateway.getSpecificIDFromName(friend),
                                FriendStatusEnum.PENDING,
                                gateway.getSpecificNameFromId(playerID), friend);

                ReportObserverConnector.getSingleton()
                        .sendReport(new updateFriendListReport(objForReportOne));

                FriendDTO objForReportTwo =
                        new FriendDTO(gateway.getSpecificIDFromName(friend), playerID,
                                FriendStatusEnum.PENDING, friend,
                                gateway.getSpecificNameFromId(playerID));
                ReportObserverConnector.getSingleton()
                        .sendReport(new updateFriendListReport(objForReportTwo));
                System.out.println(
                        "Second report playerID: " + objForReportTwo.getPlayerID());
            }
            catch (DatabaseException e)
            {
                //Player not found
                if (e.getSimpleDescription().startsWith("Player not found or updated"))
                {
                    result.append("> ").append(friend)
                            .append(" Error: Couldn't send request\n");
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
                        .append(" Error: Couldn't send request\n");
            }
        }
        return result.toString();
    }
}
