package edu.ship.engr.shipsim.dataDTO;

import java.time.LocalDate;

public class MowreyInfoDTO
{
    private int questionID;
    private String question;
    private String answer;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Create a new instance of MowreyInfo.
     *
     * @param id        question ID
     * @param question  The text of the question
     * @param answer    The correct response to the question
     * @param startDate The starting valid date for a question
     * @param endDate   The ending valid date for a question
     */
    public MowreyInfoDTO(int id, String question, String answer, LocalDate startDate, LocalDate endDate)
    {
        this.questionID = id;
        this.question = question;
        this.answer = answer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        MowreyInfoDTO other = (MowreyInfoDTO) obj;
        if (answer == null)
        {
            if (other.answer != null)
            {
                return false;
            }
        }
        else if (!answer.equals(other.answer)) //TODO Might need to change this with decision tree
        {
            return false;
        }
        if (endDate == null)
        {
            if (other.endDate != null)
            {
                return false;
            }
        }
        else if (!endDate.equals(other.endDate))
        {
            return false;
        }
        if (question == null)
        {
            if (other.question != null)
            {
                return false;
            }
        }
        else if (!question.equals(other.question))
        {
            return false;
        }
        if (questionID != other.questionID)
        {
            return false;
        }
        if (startDate == null)
        {
            if (other.startDate != null)
            {
                return false;
            }
        }
        else if (!startDate.equals(other.startDate))
        {
            return false;
        }

        return true;
    }

    /**
     * @return answer The response to the question
     */
    public String getAnswer()
    {
        return this.answer;
    }

    /**
     * @return endDate The ending valid date for a question
     */
    public LocalDate getEndDate()
    {
        return this.endDate;
    }

    /**
     * @return questionID A unique ID for this question
     */
    public int getId()
    {
        return this.questionID;
    }

    /**
     * @return question The text of the question
     */
    public String getQuestion()
    {
        return this.question;
    }

    /**
     * @return this question's unique ID
     */
    public int getQuestionID()
    {
        return questionID;
    }

    /**
     * @return startDate The starting valid date for a question
     */
    public LocalDate getStartDate()
    {
        return this.startDate;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((answer == null) ? 0 : answer.hashCode());
        result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
        result = prime * result + ((question == null) ? 0 : question.hashCode());
        result = prime * result + questionID;
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
        return result;
    }

    /**
     * @param answer2 the required answer
     */
    public void setAnswer(String answer2)
    {
        this.answer = answer2;
    }

    /**
     * @param endDate the last date this question should be available
     */
    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @param question2 the text of the question
     */
    public void setQuestion(String question2)
    {
        this.question = question2;
    }

    /**
     * @param startDate the first date this question should be available
     */
    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        String s = "MowreyInfo(question: " + this.getQuestion() + ", answer: " + this.getAnswer() + ", startDate: "
                + this.getStartDate().toString() + ", endDate: " + this.getEndDate().toString();

        return s;
    }
}
