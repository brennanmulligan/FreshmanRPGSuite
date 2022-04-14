package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import dataDTO.ClientPlayerObjectiveStateDTO;
import dataDTO.ClientPlayerQuestStateDTO;
import dataDTO.FriendDTO;
import datasource.LevelRecord;
import datatypes.ObjectiveStateEnum;
import datatypes.Position;
import datatypes.QuestStateEnum;
import model.reports.ObjectiveNeedingNotificationReport;
import model.reports.ObjectiveStateChangeReportInClient;
import model.reports.BuffPoolChangedReport;
import model.reports.ClientTimeToLevelUpDeadlineReport;
import model.reports.CurrentFriendListReport;
import model.reports.ExperiencePointsChangeReport;
import model.reports.DoubloonChangeReport;
import model.reports.QuestNeedingNotificationReport;
import model.reports.QuestStateChangeReport;
import model.reports.QuestStateReport;
import model.reports.UpdateFriendsListReport;

/**
 * The player who is playing the game
 *
 * @author merlin
 *
 */
public class ThisClientsPlayer extends ClientPlayer implements QualifiedObserver
{
	ArrayList<ClientPlayerQuestStateDTO> questList = new ArrayList<>();
	ArrayList<FriendDTO> friendList = new ArrayList<>();

	private int experiencePoints;
	private LevelRecord record;
	private int doubloons;
	private Timer levelUpTimer = null;
	private String timeLeft;
	private Date timeToLevelUp;
	private int buffPool;

	protected ThisClientsPlayer(int playerID)
	{
		super(playerID);

		QualifiedObservableConnector.getSingleton().registerObserver(this,
				ClientTimeToLevelUpDeadlineReport.class);
	}


	/**
	 * @see model.QualifiedObserver#receiveReport(model.QualifiedObservableReport)
	 */
	@Override
	public void receiveReport(QualifiedObservableReport report)
	{
		if (report.getClass() == ClientTimeToLevelUpDeadlineReport.class)
		{
			updateDeadlineTimeToLevelUp(report);
		}
	}


	/**
	 *
	 * @return the time deadline to level up
	 */
	public String getDeadlineToLevelUp()
	{
		return timeToLevelUp.toString();
	}

	/**
	 * Updates the time to level up deadline for each player
	 * @param report time to level up deadline report
	 */
	private void updateDeadlineTimeToLevelUp(QualifiedObservableReport report)
	{
		timeToLevelUp = ((ClientTimeToLevelUpDeadlineReport) report).getTimeToDeadline();

		updateDeadlineTimeToLevelUp();

		if (levelUpTimer == null)
		{
			levelUpTimer = new Timer();
			levelUpTimer.scheduleAtFixedRate(new UpdateLevelUpDeadline(), 0, 60 * 1000);
		}
	}

	/**
	 * Updates the time to level up. If there is more than 24 hours remaining to level up,
	 * the number of days remaining is written to the GUI. If there is less than 24 hours, the number
	 * of hours remaining is written to the GUI.
	 */
	protected synchronized void updateDeadlineTimeToLevelUp()
	{
		Date now = new Date();

		long dateDifference = timeToLevelUp.getTime() - now.getTime();

		int milliSecondsPerHour = (1000 * 60 * 60);
		long hoursBetween = dateDifference / milliSecondsPerHour;

		if (hoursBetween > 24)
		{
			long daysBetween = hoursBetween / 24;
			timeLeft = daysBetween + " day(s)";
		}
		else
		{
			timeLeft = hoursBetween + " hour(s)";
		}
	}


	/**
	 * Moves this player and report if they have entered into any regions
	 *
	 * @param pos
	 *            the location to move to
	 */
	public void move(Position pos)
	{
		super.move(pos, true);
	}

	/**
	 * Returns the list of quests contained by the local player.
	 *
	 * @return the quest list
	 */
	public ArrayList<ClientPlayerQuestStateDTO> getQuests()
	{
		return questList;
	}

	/**
	 * Adds a quest to the local players quest list
	 *
	 * @param q
	 *            the quest being added
	 */
	public void addQuest(ClientPlayerQuestStateDTO q)
	{
		questList.add(q);
	}

	/**
	 * Overwrite ThisClientPlayer's quest list
	 *
	 * @param qList
	 *            current quest list
	 */
	public void overwriteQuestList(ArrayList<ClientPlayerQuestStateDTO> qList)
	{
		questList.clear();
		questList = qList;

		for (ClientPlayerQuestStateDTO q : questList)
		{
			if (q.isNeedingNotification())
			{
				QuestNeedingNotificationReport report = new QuestNeedingNotificationReport(
						ClientPlayerManager.getSingleton().getThisClientsPlayer().getID(),
						q.getQuestID(), q.getQuestDescription(), q.getQuestState());
				QualifiedObservableConnector.getSingleton().sendReport(report);
			}
			for (ClientPlayerObjectiveStateDTO objective : q.getObjectiveList())
			{
				if (objective.isNeedingNotification())
				{
					ObjectiveNeedingNotificationReport report = new ObjectiveNeedingNotificationReport(
							ClientPlayerManager.getSingleton().getThisClientsPlayer()
									.getID(), q.getQuestID(), objective.getObjectiveID(),
							objective.getObjectiveDescription(), objective.getObjectiveState(),
							objective.isNeedingNotification(), objective.getWitnessTitle());
					QualifiedObservableConnector.getSingleton().sendReport(report);
				}
			}
		}
	}

	/**
	 * Sends questList to QuestStateReport and notifies the Observers
	 */
	public void sendCurrentQuestStateReport()
	{
		QuestStateReport r = new QuestStateReport(questList);
		QualifiedObservableConnector.getSingleton().sendReport(r);
	}

	/**
	 * @return the number of experience points for ThisClientsPlayer
	 */
	public int getExperiencePoints()
	{
		return experiencePoints;
	}

	/**
	 * @param record
	 *            level record
	 * @param expPoints
	 *            experience points
	 */
	public void setLevelInfo(LevelRecord record, int expPoints)
	{
		this.record = record;
		this.experiencePoints = expPoints;

		sendExperiencePointsChangeReport();
	}

	/**
	 * Sends the report to say that experience points have changed.
	 */
	public void sendExperiencePointsChangeReport()
	{
		ExperiencePointsChangeReport r = new ExperiencePointsChangeReport(experiencePoints, record);
		QualifiedObservableConnector.getSingleton().sendReport(r);
	}

	/**
	 * Sends the report to say that the quest state has changed.
	 *
	 * @param questID
	 *            for ThisClientsPlayer's quest
	 * @param questDescription
	 *            for ThisClientsPlayer's quest description
	 * @param newState
	 *            enum for the current quest
	 */
	public void sendQuestStateChangeReport(int questID, String questDescription,
										   QuestStateEnum newState)
	{
		for (ClientPlayerQuestStateDTO q : questList)
		{
			if (q.getQuestID() == questID)
			{
				q.setState(newState);

				QuestStateChangeReport r = new QuestStateChangeReport(this.getID(),
						q.getQuestID(), questDescription, newState);
				QualifiedObservableConnector.getSingleton().sendReport(r);
			}
		}
	}

	/**
	 * @param questID
	 *            for ThisClientsPlayer's quest that the objective is in
	 * @param objectiveID
	 *            for the objective who are looking for
	 * @param objectiveDescription
	 *            for the target objective
	 * @param objectiveState
	 *            enum for the target objective
	 */
	public void sendObjectiveStateChangeReport(int questID, int objectiveID,
                                               String objectiveDescription, ObjectiveStateEnum objectiveState)
	{
		for (ClientPlayerQuestStateDTO quest : questList)
		{
			if (quest.getQuestID() == questID)
			{
				for (ClientPlayerObjectiveStateDTO objective : quest.getObjectiveList())
				{
					if (objective.getObjectiveID() == objectiveID)
					{
						objective.setObjectiveState(objectiveState);
						ObjectiveStateChangeReportInClient r = new ObjectiveStateChangeReportInClient(
								this.getID(), questID, objectiveID, objectiveDescription,
								objectiveState);
						QualifiedObservableConnector.getSingleton().sendReport(r);
					}
				}

			}
		}
	}

	/**
	 * returns the Level Record
	 *
	 * @return record the Level Record
	 */
	public LevelRecord getLevelRecord()
	{
		return record;
	}

	/**
	 * Overwrite the experience and level record in the clients player
	 *
	 * @param doubloons current doubloons
	 * @param buffPool remaining bonus points.
	 */
	public void doubloonsAndBuffPoolChanged(int doubloons, int buffPool)
	{
		if (this.buffPool != buffPool)
		{
			setBuffPool(buffPool);
			sendBuffPoolChangeReport();
		}
		setDoubloons(doubloons);
		sendDoubloonChangeReport();
	}

	/**
	 * Overwrite the experience and level record in the clients player
	 *
	 * @param experience
	 *            current experience
	 * @param rec
	 *            level report
	 */
	public void overwriteExperiencePoints(int experience, LevelRecord rec)
	{
		setLevelInfo(rec, experience);
	}

	/**
	 * @param doubloons of this player
	 */
	public void setDoubloons(int doubloons)
	{
		this.doubloons = doubloons;
	}

	/**
	 * sending the doubloon changed report
	 */
	public void sendDoubloonChangeReport()
	{
		DoubloonChangeReport r = new DoubloonChangeReport(this.doubloons);
		QualifiedObservableConnector.getSingleton().sendReport(r);
	}

	/**
	 *
	 * @return the doubloons for this player
	 */
	public int getDoubloons()
	{
		return this.doubloons;
	}


	/**
	 * @return the string for how much time is left until the level up deadline
	 */
	public String getTimeLeft()
	{
		return timeLeft;
	}


	/**
	 * @return The amount of bonus points this clients player has remaining.
	 */
	public int getBuffPool()
	{
		return buffPool;
	}


	/**
	 * @param buffPool the amount of b
	 */
	public void setBuffPool(int buffPool)
	{
		this.buffPool = buffPool;
	}

	/**
	 * sending the buff pool changed report
	 */
	public void sendBuffPoolChangeReport()
	{
		BuffPoolChangedReport r = new BuffPoolChangedReport(this.buffPool);
		QualifiedObservableConnector.getSingleton().sendReport(r);
	}

	/**
	 * @return the players friends
	 */
	public ArrayList<FriendDTO> getFriendList()
	{
		return friendList;
	}


	/**
	 * sending the friend list report
	 * @param friendList the friend list that we should send out
	 */
	public void sendCurrentFriendListReport(ArrayList<FriendDTO> friendList)
	{
		this.friendList = friendList;
		CurrentFriendListReport r = new CurrentFriendListReport(friendList);
		QualifiedObservableConnector.getSingleton().sendReport(r);

	}

	/**
	 * @param  friend  friend that we are  updating
	 */
	public void updateCurrentFriendListReport(FriendDTO friend)
	{
		//this.friendList = friendList;
		ArrayList<FriendDTO> previousFriendList = getFriendList();
		for (int i = 0; i < previousFriendList.size(); i++)
		{
			if (previousFriendList.get(i).getFriendName().equals(friend.getFriendName()))
			{
				//previousFriendList.add();
				previousFriendList.remove(i);

			}

		}

		friendList.add(friend);
		ArrayList<FriendDTO> temp = new ArrayList<>();
		temp.add(friend);
		UpdateFriendsListReport r = new UpdateFriendsListReport(temp);
		QualifiedObservableConnector.getSingleton().sendReport(r);

	}
}

/**
 * This class is a simple timer for updating the
 * @author ch5968
 *
 */
class UpdateLevelUpDeadline extends TimerTask
{

	@Override
	public void run()
	{
		ClientPlayerManager.getSingleton().getThisClientsPlayer().updateDeadlineTimeToLevelUp();
	}
}
