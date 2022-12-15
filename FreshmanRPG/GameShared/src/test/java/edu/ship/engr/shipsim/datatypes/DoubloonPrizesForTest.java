package edu.ship.engr.shipsim.datatypes;

public enum DoubloonPrizesForTest
{

    //prizes to use
    BONUSPOINTS("Bonus Points", 100, "Bonus points on your final grade"),

    PIZZAPARTY("Pizza Party", 200, "Get free pizza"),

    DUCK("Rubber Duck", 20, "Get a rubber duck"),

    SHIRT("Free T-Shirt", 150, "Get a nice T-shirt to show off on campus"),

    HWPASS("Homework pass", 300, "free pass on a lab or homework from the department");

    //variables
    private String name;
    private int cost;
    private String description;

    //constructor
    DoubloonPrizesForTest(String name, int cost, String description)
    {
        this.name = name;
        this.cost = cost;
        this.description = description;
    }

    /*
     * getter for name
     */
    public String getName()
    {
        return name;
    }

    /*
     * getter for cost
     */
    public int getCost()
    {
        return cost;
    }

    /*
     * getter for description
     */
    public String getDescription()
    {
        return description;
    }


}
