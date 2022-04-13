package datasource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import dataDTO.QuestionDTO;
import datatypes.NPCQuestionsForTest;

/**
 * A mock data source that can be shared between the row and table data gateways
 *
 * @author merlin
 *
 */
public class NPCQuestionMockDataSource
{

	private static NPCQuestionMockDataSource instance;

	private NPCQuestionMockDataSource()
	{
		dataSource = new HashMap<>();
		resetData();
	}

	/**
	 * @return the only one
	 */
	public static NPCQuestionMockDataSource getSingleton()
	{
		if (instance == null)
		{
			instance = new NPCQuestionMockDataSource();
		}
		return instance;
	}

	/**
	 * Map question ID to question information
	 */
	private static HashMap<Integer, QuestionDTO> dataSource;

	/**
	 * checks to see if a particular key exists
	 *
	 * @param questionID
	 *            the question we are looking for
	 * @return true if it is in the data source
	 */
	public boolean containsKey(int questionID)
	{

		return dataSource.containsKey(questionID);
	}

	/**
	 * @return the number of things in the data source
	 */
	public int size()
	{
		return dataSource.size();
	}

	/**
	 * @param question
	 *            the text of the question
	 * @param answer
	 *            the required answer
	 * @param startDate
	 *            the date the question should become available
	 * @param endDate
	 *            the last date the question should be available
	 * @return the question as inserted
	 */
	public QuestionDTO insert(String question, String answer, LocalDate startDate, LocalDate endDate)
	{
		int questionID = size() + 1;
		QuestionDTO info = new QuestionDTO(questionID, question, answer, startDate, endDate);
		dataSource.put(questionID, info);
		return info;
	}

	/**
	 * reload the data from the enums
	 */
	public void resetData()
	{
		dataSource = new HashMap<>();
		for (NPCQuestionsForTest p : NPCQuestionsForTest.values())
		{
			dataSource.put(p.getQuestionID(),
					new QuestionDTO(p.getQuestionID(), p.getQ(), p.getA(), p.getStartDate(), p.getEndDate()));
		}
	}

	/**
	 * @param questionID
	 *            the unique identifier
	 * @return the text of the question
	 */
	public String getQuestionStatement(int questionID)
	{
		QuestionDTO x = dataSource.get(questionID);
		return x.getQuestion();
	}

	/**
	 * @param questionID
	 *            the unique identifier
	 * @return the text of the answer
	 */
	public String getAnswer(int questionID)
	{
		return dataSource.get(questionID).getAnswer();
	}

	/**
	 * @param questionID
	 *            the unique identifier
	 * @return the first day the question should be available
	 */
	public LocalDate getStartDate(int questionID)
	{
		return dataSource.get(questionID).getStartDate();
	}

	/**
	 * @param questionID
	 *            the unique identifier
	 * @return the last day the question should be available
	 */
	public LocalDate getEndDate(int questionID)
	{
		return dataSource.get(questionID).getEndDate();
	}

	/**
	 * @return a random and valid key
	 */
	public int getRandomKey()
	{
		int position = (int) (Math.random() * dataSource.size());
		Integer key = (Integer) dataSource.keySet().toArray()[position];
		return key.intValue();
	}

	/**
	 * @param questionID
	 *            the unique identifier
	 * @param question
	 *            the new text of the question
	 */
	public void setQuestion(int questionID, String question)
	{
		QuestionDTO x = dataSource.get(questionID);
		x.setQuestion(question);
	}

	/**
	 * @param questionID
	 *            the unique identifier
	 * @param answer
	 *            the new text of the answer
	 */
	public void setAnswer(int questionID, String answer)
	{
		dataSource.get(questionID).setAnswer(answer);
	}

	/**
	 * @param questionID
	 *            the unique identifier
	 * @param startDate
	 *            the first day the question should be available
	 */
	public void setStartDate(int questionID, LocalDate startDate)
	{
		dataSource.get(questionID).setStartDate(startDate);
	}

	/**
	 * @param questionID
	 *            the unique identifier
	 * @param endDate
	 *            the last day the question should be available
	 */
	public void setEndDate(int questionID, LocalDate endDate)
	{
		dataSource.get(questionID).setEndDate(endDate);
	}

	/**
	 * @param questionID
	 *            the id of the question to be deleted from the data source
	 */
	public void remove(int questionID)
	{
		dataSource.remove(questionID);
	}

	/**
	 * @return all of the questions in the data source
	 */
	public ArrayList<QuestionDTO> values()
	{
		ArrayList<QuestionDTO> result = new ArrayList<>();
		for (QuestionDTO x : dataSource.values())
		{
			result.add(x);
		}
		return result;
	}

	/**
	 *
	 */
	public void deleteAllQuestions()
	{
		dataSource = new HashMap<>();
	}

	/**
	 * Get one from the data source. Will throw an exception if it can't be found
	 *
	 * @param questionID
	 *            the unique id of the question we want
	 * @return a DTO of the question
	 */
	public QuestionDTO getQuestionDTO(int questionID)
	{
		return dataSource.get(questionID);
	}

}
