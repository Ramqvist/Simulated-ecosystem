package chalmers.dax021308.ecosystem.model.genetics;


/**
 * 
 * @author Loanne Berggren
 *
 */
public enum GeneralGeneTypes
{
	ISSTOTTING("boolean"),	// bool
	STOTTINGLENGTH("double"), // double
	STOTTINGRANGE("double"), // double  how close pred can get
	STOTTINGANGLE("double"), // double
	ISGROUPING("boolean"),  // bool isGrouping
	GROUPING_SEPARATION_FACTOR("double"), // double
	GROUPING_COHESION("double"), // double
	GROUPING_FORWARD_THRUST("double"), // double
	GROUPING_ARRAYAL_FORCE("double"), // double
	FOCUSPREDATOR("boolean"), // bool
	FOCUSPREY("boolean"), // bool
	FEMALE("boolean"),
	MALE("boolean")
	;
	
	private GeneralGeneTypes(String type){
		this.type = type;
	}
	
	private String type;

}