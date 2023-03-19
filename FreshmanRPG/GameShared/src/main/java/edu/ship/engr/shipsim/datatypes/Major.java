package edu.ship.engr.shipsim.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author Emily Maust
 * @author Matthew Croft
 */
public enum Major
{

    ACCOUNTING("Accounting"),
    BIOLOGY("Biology"),
    BUS_ADMIN_UNDECIDED("Business Admin-Major Undecided"),
    CIV_MECH_GENERAL("Civil & Mech Engr General"),
    COMM_JOURN("Comm, Journalism & Media"),
    CS_SE_GENERAL("Comp Sci & Soft Engr General"),
    COMPUTER_SCIENCE("Computer Science"),
    CRIMINAL_JUSTICE("Criminal Justice"),
    ED_PREK_4("ECH Elem: Pre-K - 4"),
    ENTREPRENEURSHIP("Entrepreneurship"),
    EXERCISE_SCIENCE("Exercise Science"),
    EXPLORATORY_STUDIES("Exploratory Studies"),
    EXPLORATORY_STUDIES_ED("Exploratory Studies Education"),
    FINANCE("Finance"),
    HISTORY("History"),
    HISTORY_CERT("History/Social Studies Certn"),
    MANAGEMENT("Management"),
    MARKETING("Marketing"),
    MATHEMATICS("Mathematics"),
    ED_4_8_("Mid Level/Elementary: Gr 4 - 8"),
    SUPPLY_CHAIN_MANAGEMENT("Supply Chain Management"),
    /**
     *
     */
    SOFTWARE_ENGINEERING("SWE"),

    /**
     *
     */
    COMPUTER_ENGINEERING("Comp Eng"),

    /**
     *
     */
    ELECTRICAL_ENGINEERING("EE"),
    /**
     *
     */
    CS_AND_E_GENERAL("CS&E Gen"),
    HUMAN_COMMUNICATIONS("Human Communications"),
    /**
     *
     */
    OTHER("Other");

    private final String majorName;

    Major(String majorName)
    {
        this.majorName = majorName;
    }

    /**
     * @return a unique ID for this Major
     */
    public int getID()
    {
        return ordinal();
    }

    /**
     * @param id the MajorID we are interested in
     * @return the Major with the given ID
     */
    public static Major getMajorForID(int id)
    {
        return Major.values()[id];
    }

    /**
     * @return the user friendly name of this major
     */
    public String getMajorName()
    {
        return majorName;
    }

    /**
     * @return a random element in this enum
     */
    public static Major getRandomMajor()
    {
        return values()[new Random().nextInt(values().length)];
    }

    /**
     * @return all elements in this enum
     */
    public static ArrayList<Major> getAllMajors()
    {
        Major[] majorArray = values();
        ArrayList<Major> majors = new ArrayList<Major>(Arrays.asList(majorArray));
        return majors;
    }
}
