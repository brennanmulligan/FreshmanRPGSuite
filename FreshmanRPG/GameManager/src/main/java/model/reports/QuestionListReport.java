package model.reports;

import java.util.ArrayList;

import dataDTO.QuestionDTO;
import model.QualifiedObservableReport;

/**
 * Generated in response to a request for all quizbot questions.
 */
public class QuestionListReport implements QualifiedObservableReport
{

	private ArrayList<QuestionDTO> questions;

	/**
	 * @param questions ArrayList<QuestionInfo> The questions in the report.
	 */
	public QuestionListReport(ArrayList<QuestionDTO> questions)
	{
		this.questions = questions;
	}

	/**
	 * @return ArrayList<QuestionInfo> The questions.
	 */
	public ArrayList<QuestionDTO> getQuestions()
	{
		return questions;
	}

}
