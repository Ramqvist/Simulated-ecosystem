package chalmers.dax021308.ecosystem.model.genetics;


/**
 *
 * @author Loanne Berggren
 *
 */
public enum GeneralGeneTypes
{
	ISSTOTTING(1, "Stotting"),	// bool
	STOTTINGLENGTH(2, "Stotting length"), // double
	STOTTINGRANGE(2, "Stotting range"), // double  how close pred can get
	STOTTINGANGLE(2, "Stotting angle"), // double

	ISGROUPING(1, "Grouping"),  // bool isGrouping
	GROUPING_SEPARATION_FACTOR(2, "Separation force"), // double
	GROUPING_COHESION(2, "Cohesion"), // double
	GROUPING_FORWARD_THRUST(2, "Forward thrust"), // double
	GROUPING_ARRAYAL_FORCE(2, "Arrayal force"), // double

	FOCUSPREDATOR(1, "Focus predator"), // bool
	FOCUSPREY(1, "Focus prey"), // bool

	EATEN_EQUALS_DEATH(1, "Die when eates"),
	WEED_TRANSFORMATION_FACTOR(2, "Weed transformation factor"),
	HIGH_FACTOR(2, "High Factor"),

	//FEMALE("boolean", ""),
	//MALE("boolean", "")
	;


	private int type;
	private String name;

	private GeneralGeneTypes(int type, String name){
		this.type = type;
		this.name = name;
	}

	@Override
	public String toString(){
		return name;
	}

	public int getType(){
		return type;
	}

	public String getName(){
		return name;
	}

}