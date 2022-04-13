package model.terminal;

import dataDTO.FriendDTO;
import datasource.DatabaseException;
import datasource.FriendTableDataGateway;
import datasource.FriendTableDataGatewayRDS;
import datatypes.FriendStatusEnum;
import model.QualifiedObservableConnector;
import model.reports.FriendConnectionReceivedReport;
import model.reports.updateFriendListReport;

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
        for (int i = 0; i < friends.length; i++)
        {
            //Try to accept request from friend
            try
            {
                gateway.accept(playerID, friends[i]);
                result.append("> " + friends[i] + " " + FriendStatusEnum.ACCEPTED + "\n");
                QualifiedObservableConnector.getSingleton().sendReport(new FriendConnectionReceivedReport(FriendTableDataGatewayRDS.getInstance().accept(playerID, friends[i]), playerID));


                //send the report to the other player
                FriendDTO objForReportOne = new FriendDTO(playerID, gateway.getSpecificIDFromName(friends[i]), FriendStatusEnum.ACCEPTED, gateway.getSpecificNameFromId(playerID), friends[i]);

                QualifiedObservableConnector.getSingleton().sendReport(new updateFriendListReport(objForReportOne));

                FriendDTO objForReportTwo = new FriendDTO(gateway.getSpecificIDFromName(friends[i]), playerID, FriendStatusEnum.ACCEPTED, friends[i], gateway.getSpecificNameFromId(playerID));
                QualifiedObservableConnector.getSingleton().sendReport(new updateFriendListReport(objForReportTwo));

            }
            catch (DatabaseException e)
            {
                //Player not found
                if (e.getSimpleDescription().startsWith("Player not found or updated"))
                {
                    result.append("> " + friends[i] + " Error: Couldn't accept request\n");
                }
                //Other Database Exception
                else
                {
                    e.printStackTrace();
                }
            }
            catch (NullPointerException e)
            {
                result.append("> " + friends[i] + " Error: Couldn't accept request\n");
            }
        }
        return result.toString();
    }

}
