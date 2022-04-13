package datatypes;

public enum KnowledgePointPrizesForTest
{

	//prizes to use 
	BONUSPOINTS("Bonus Points", 100, "Bonus points on your final grade"),

	PIZZAPARTY("Pizza Party", 80, "get free pizza"),

	DUCK("Duck", 20, "get yourself a rubber duck programming partner"),

	SHIRT("Engineering shirt", 5, "get yourself a nice shirt to show off on campus"),

	EXAMPASS("Free A", 500, "get a free A on exam final included"),

	HWPASS("Homework pass", 100, "free pass on a lab or homework from the deaprtment");

	//variables
	private String name;
	private int cost;
	private String description;

	//constructor
	KnowledgePointPrizesForTest(String name, int cost, String description)
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
