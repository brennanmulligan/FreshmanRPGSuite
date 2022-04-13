package datasource;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import dataDTO.QuestionDTO;

/**
 * @author Ben Uleau, Mohammed Almaslamani, Jordan Long
 * Import questions via .csv file
 */
public class CSVQuestionGateway
{
	private String[] columnNames;
	private ArrayList<QuestionDTO> questionData;
	private LocalDate semesterStart;

	/**
	 * @param input - literal input
	 * @param semesterStart Start date of the semester
	 * @throws InvalidColumnException - column error
	 */
	public CSVQuestionGateway(String input, LocalDate semesterStart) throws InvalidColumnException
	{
		this.semesterStart = semesterStart;
		String[] lines = input.split(System.getProperty("line.separator"));
		final ArrayList<String> rows = new ArrayList<>();
		for (String line : lines)
		{
			rows.add(line);
		}

		parseColumns(rows.remove(0));
		questionData = new ArrayList<>();
		parseQuestions(rows);
	}

	/**
	 * @param path - path to the file
	 * @param semesterStart Start date of the semester
	 * @throws IOException - error reading file
	 * @throws InvalidColumnException - error with csv 
	 */
	public CSVQuestionGateway(Path path, LocalDate semesterStart) throws IOException, InvalidColumnException
	{
		this.semesterStart = semesterStart;
		BufferedReader reader = Files.newBufferedReader(path);
		final ArrayList<String> rows = new ArrayList<>();
		String line;
		while ((line = reader.readLine()) != null)
		{
			rows.add(line);
		}
		parseColumns(rows.remove(0));
		questionData = new ArrayList<>();
		parseQuestions(rows);
	}

	private void parseColumns(String header) throws InvalidColumnException
	{
		if (!Character.isAlphabetic(header.charAt(0)))
		{
			header = header.substring(1);
		}
		String[] headerNames = header.split(",");
		for (int i = 0; i < headerNames.length; i++)
		{
			headerNames[i] = headerNames[i].trim();
		}
		final ArrayList<String> columns = new ArrayList<>(Arrays.asList(headerNames));
		if (!columns.contains("quizID")
				|| (!columns.contains("question")
				|| (!columns.contains("answer")
				|| (!columns.contains("start_week")
				|| (!columns.contains("end_week"))))))
		{
			throw new InvalidColumnException(
					"must contain columns 'quizId', 'question','answer','start_week','end_week' in any order! ");
		}
		columnNames = columns.toArray(new String[columns.size()]);
	}

	/**
	 * Gets the requested column using the column id.
	 * @param i ID of the column in the array.
	 * @return Name of the column.
	 */
	public String getColumn(int i)
	{
		return this.columnNames[i];
	}

	private void parseQuestions(ArrayList<String> rows)
	{
		for (String row : rows)
		{
			final String[] values = row.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
			if (isRowValid(values))
			{
				questionData.add(parseQuestion(values));
			}
		}
	}

	private QuestionDTO parseQuestion(String[] values)
	{
		int quizID = 0;
		String question = null;
		String answer = null;
		int start_week = 0;
		int end_week = 0;
		LocalDate startDate = semesterStart;
		LocalDate endDate = semesterStart;

		for (int i = 0; i < values.length; i++)
		{
			values[i] = values[i].trim();
			switch (getColumn(i))
			{
				case "quizID":
					quizID = Integer.parseInt(values[i]);
					break;
				case "question":
					question = values[i];
					break;
				case "answer":
					answer = values[i];
					break;
				case "start_week":
					start_week = Integer.parseInt(values[i]);
					startDate = startDate.plusWeeks(start_week - 1);
					break;
				case "end_week":
					end_week = Integer.parseInt(values[i]);
					endDate = endDate.plusWeeks(end_week - 1);
					break;
			}
		}
		return new QuestionDTO(quizID, question, answer, startDate, endDate);
	}

	/**
	 * @return the list of DTOs containing question data
	 */
	public ArrayList<QuestionDTO> getQuestionData()
	{
		return questionData;
	}

	/**
	 * @return the number fo questions
	 */
	public int getNumberOfQuestions()
	{
		return questionData.size();
	}

	private boolean isRowValid(String[] values)
	{
		final Pattern pattern = Pattern.compile("^\\s*$");
		final List<String> matching = Arrays.asList(values).stream().filter(pattern.asPredicate())
				.collect(Collectors.toList());


		return matching.size() == 0;
	}


}
