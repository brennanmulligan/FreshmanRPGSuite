package model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import criteria.CriteriaIntegerDTO;
import criteria.CriteriaStringDTO;
import criteria.InteractableItemActionParameter;
import criteria.QuestListCompletionParameter;
import dataDTO.InteractableItemDTO;
import dataENUM.InteractableItemActionType;
import datasource.DatabaseException;
import datasource.InteractableItemRowDataGateway;
import datasource.InteractableItemRowDataGatewayMock;
import datasource.InteractableItemRowDataGatewayRDS;
import datasource.InteractableItemTableDataGateway;
import datasource.InteractableItemTableDataGatewayMock;
import datasource.InteractableItemTableDataGatewayRDS;
import datatypes.Position;
import model.reports.InteractableObjectBuffReport;
import model.reports.InteractableObjectTextReport;
import model.reports.InteractionDeniedReport;
import model.reports.KeyInputRecievedReport;

/**
 * InteractObjectManager checks the player position against other objects'
 * locations in the DB If the player is near the objects, report will be sent
 * out
 *
 * @author Andy, Truc, and Emmanuel
 *
 */
public class InteractObjectManager implements QualifiedObserver
{
	private static InteractObjectManager singleton;
	private static InteractableItemTableDataGateway gateway;
	private static InteractableItemRowDataGateway rowGateway;
	private static final int WIDTH = 2;
	private static final int HEIGHT = 2;

	/**
	 * Protected for singleton
	 */
	private InteractObjectManager()
	{
		// for testing purpose
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			gateway = InteractableItemTableDataGatewayMock.getInstance();
			gateway.resetData();
		}
		else
		{
			try
			{
				gateway = InteractableItemTableDataGatewayRDS.getInstance();
			}
			catch (DatabaseException e)
			{
				e.printStackTrace();
			}
		}
		QualifiedObservableConnector.getSingleton().registerObserver(this, KeyInputRecievedReport.class);
	}

	/**
	 *
	 * @return singleton instance of this manager
	 */
	public synchronized static InteractObjectManager getSingleton()
	{
		if (singleton == null)
		{
			singleton = new InteractObjectManager();
		}
		return singleton;
	}

	/**
	 * Reset the singleton for testing purpose
	 */
	public static void resetSingleton()
	{
		if (singleton != null)
		{
			QualifiedObservableConnector.getSingleton().unregisterObserver(singleton, KeyInputRecievedReport.class);
			singleton = null;
		}
	}

	/**
	 * handle a received report execute the appropriate item action if necessary
	 *
	 * edited by: Elisabeth Ostrow
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass().equals(KeyInputRecievedReport.class))
		{
			try
			{
				KeyInputRecievedReport rpt = (KeyInputRecievedReport) report;
				if (rpt.getInput().toUpperCase().equals("E"))
				{
					int playerID = rpt.getPlayerId();
					int objectID = objectInRange(playerID);
					if (objectID >= 0)
					{
						execute(playerID, objectID);
					}
					// otherwise do nothing
				}
			}
			catch (DatabaseException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Responsible for executing interaction with specific objects
	 *
	 * @param playerId
	 *            - the player who is interacting with the object
	 * @param itemId
	 *            - the itemId from the database that the player is interacting with
	 * @return whether or not execution was "successful"
	 *
	 * @author Adam Pine, Jacob Knight
	 *
	 *
	 * edited by Stephen Clabaugh and Aaron Gerber
	 */
	protected boolean execute(int playerId, int itemId)
	{
		try
		{
			if (OptionsManager.getSingleton().isUsingMockDataSource())
			{
				rowGateway = new InteractableItemRowDataGatewayMock(itemId);
			}
			else
			{
				rowGateway = new InteractableItemRowDataGatewayRDS(itemId);
			}
			InteractableItemActionType type = rowGateway.getActionType();
			InteractableItemActionParameter param = rowGateway.getActionParam();
			if (type != null)
			{
				switch (type)
				{
					case NO_ACTION:
						return true;
					case MESSAGE:
						CriteriaStringDTO txt = (CriteriaStringDTO) param;
						InteractableObjectTextReport textReport = new InteractableObjectTextReport(playerId,
								txt.getString());
						QualifiedObservableConnector.getSingleton().sendReport(textReport);
						return true;
					case BOARD:
						CriteriaStringDTO txt1 = (CriteriaStringDTO) param;
						InteractableObjectTextReport textReport1 = new InteractableObjectTextReport(playerId,
								txt1.getString());
						QualifiedObservableConnector.getSingleton().sendReport(textReport1);
						return true;
					case BUFF:
						Player p = PlayerManager.getSingleton().getPlayerFromID(playerId);
						if (p.getBuffPool() == 0)
						{
							CriteriaIntegerDTO buffPool = (CriteriaIntegerDTO) param;
							PlayerManager.getSingleton().getPlayerFromID(playerId).setBuffPool(buffPool.getTarget());
							QualifiedObservableConnector.getSingleton().sendReport(new InteractableObjectBuffReport(playerId, buffPool.getTarget()));
						}
						else
						{
							QualifiedObservableConnector.getSingleton().sendReport(new InteractionDeniedReport(playerId));
						}
						return true;
					case QUEST_TRIGGER:
						String paramString = param.toString();
						paramString = paramString.replace("Criteria String: ", "");
						List<String> arrayList = new ArrayList<String>    (Arrays.asList(paramString.split(",")));
						ArrayList<Integer> questList = new ArrayList<Integer>();

						for(String questID:arrayList)
						{
							System.out.println(questID);
							questList.add(Integer.parseInt(questID.trim()));
						}

						QuestListCompletionParameter ql = new QuestListCompletionParameter(questList); // Quests to trigger
						QuestState qs; // State of quest
						for (Integer questID : ql.getQuestIDs()) // For every quest in QuestLIstCompletionParameter
						{
							qs = QuestManager.getSingleton().getQuestStateByID(playerId, questID); // Get QuestState by playerId
								try
								{
									QuestManager.getSingleton().triggerQuest(playerId, questID);
								}
								catch (IllegalQuestChangeException | IllegalObjectiveChangeException e)
								{
									return false;
								}
						}
						return true;
					default:
						return false;
				}
			}
		}
		catch (DatabaseException | NullPointerException e)
		{
			return false;
		}
		return false;
	}

	/**
	 * Checks whether the player is in the object locations on the map or not
	 *
	 * @param playerId
	 *            the player whose location we're checking
	 * @return the object id of what is in range, -1 if nothing
	 * @throws DatabaseException
	 *             shouldn't
	 *
	 *             edited by: Elisabeth Ostrow
	 */
	public int objectInRange(int playerId) throws DatabaseException
	{
		// Gets player position from id
		Player playerObject = PlayerManager.getSingleton().getPlayerFromID(playerId);
		Position playerPosition = playerObject.getPlayerPosition();
		int x = playerPosition.getRow();
		int y = playerPosition.getColumn();

		// Creates a rectangle around player
		Rectangle playerRec = new Rectangle(x, y, WIDTH, HEIGHT);

		// Gets all objects on the same map from DB
		ArrayList<InteractableItemDTO> items = gateway.getItemsOnMap(playerObject.getMapName());

		// Checks whether the player is in the object range
		for (int i = 0; i < items.size(); i++)
		{
			Position p = items.get(i).getPosition();

			Rectangle objRec = new Rectangle(p.getRow(), p.getColumn(), WIDTH, HEIGHT);
			boolean isInObjectRange = playerRec.intersects(objRec);
			if (isInObjectRange == true)
			{
				return items.get(i).getId();
			}
		}
		return -1;
	}

}
