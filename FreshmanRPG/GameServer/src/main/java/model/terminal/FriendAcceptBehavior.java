package model.terminal;

import dataDTO.FriendDTO;
import datasource.DatabaseException;
import datasource.FriendTableDataGateway;
import datasource.TableDataGatewayManager;
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
        for (String friend : friends)
        {
            //Try to accept request from friend
            try
            {
                gateway.accept(playerID, friend);
                result.append("> ").append(friend).append(" ")
                        .append(FriendStatusEnum.ACCEPTED).append("\n");
                FriendTableDataGateway friendGateway =
                        (FriendTableDataGateway) TableDataGatewayManager.getSingleton()
                                .getTableGateway("Friend");
                QualifiedObservableConnector.getSingleton().sendReport(
                        new FriendConnectionReceivedReport(
                                friendGateway
                                        .accept(playerID, friend), playerID));


                //send the report to the other player
                FriendDTO objForReportOne =
                        new FriendDTO(playerID, gateway.getSpecificIDFromName(friend),
                                FriendStatusEnum.ACCEPTED,
                                gateway.getSpecificNameFromId(playerID), friend);

                QualifiedObservableConnector.getSingleton()
                        .sendReport(new updateFriendListReport(objForReportOne));

                FriendDTO objForReportTwo =
                        new FriendDTO(gateway.getSpecificIDFromName(friend), playerID,
                                FriendStatusEnum.ACCEPTED, friend,
                                gateway.getSpecificNameFromId(playerID));
                QualifiedObservableConnector.getSingleton()
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
