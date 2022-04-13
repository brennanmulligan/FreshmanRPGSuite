package model;

import java.io.Serializable;

import datasource.DatabaseException;
import datasource.NPCQuestionRowDataGateway;
import datasource.NPCQuestionRowDataGatewayMock;
import datasource.NPCQuestionRowDataGatewayRDS;

/**
 * NPCQuestion class that modeling the questions for the quiz bot Will return a
 * random question from the database with getRandomQuestion()
 *
 * @author Ga and Frank
 */
public class NPCQuestion implements Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private NPCQuestionRowDataGateway gateway;

	private NPCQuestion()
	{
	}

	/**
	 * @param questionID the ID of the question you want returned
	 * @return an NPCQuestion
	 */
	public static NPCQuestion getSpecificQuestion(int questionID)
	{
		NPCQuestion q = new NPCQuestion();
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			try
			{
				q.gateway = new NPCQuestionRowDataGatewayMock(questionID);
			}
			catch (DatabaseException e)
			{
				return null;
			}
		}
		else
		{
			try
			{
				q.gateway = new NPCQuestionRowDataGatewayRDS(questionID);
			}
			catch (DatabaseException e)
			{
				return null;
			}
		}
		return q;
	}

	/**
	 * @return a random question from the data source
	 * @throws DatabaseException if the data source can't complete the request
	 */
	protected static NPCQuestion getRandomQuestion() throws DatabaseException
	{
		NPCQuestion q = new NPCQuestion();
		if (OptionsManager.getSingleton().isUsingMockDataSource())
		{
			q.gateway = NPCQuestionRowDataGatewayMock.findRandomGateway();
		}
		else
		{
			q.gateway = NPCQuestionRowDataGatewayRDS.findRandomGateway();
		}
		return q;
	}

	/**
	 * @return answer to question
	 */
	public String getAnswer()
	{
		return gateway.getAnswer();
	}

	/**
	 * @return question
	 */
	protected String getQuestionStatement()
	{
		return gateway.getQuestionStatement();
	}

}
