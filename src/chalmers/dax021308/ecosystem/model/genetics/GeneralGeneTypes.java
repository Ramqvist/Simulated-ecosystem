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
	// To add gene: genename(startindex, #bits)
	// ex JUNK(2, 4)
	;
	
	private GeneralGeneTypes(String type){
		this.type = type;
	}
	
	private String type;
	// Gen double, boolean
	
	
	/*
	 Stotting: 
	 Grouping: isgrouping, max, min, ismutable, current value group probability
	 FocusPredator:
	 
	 FocusPrey
	 
	 private UpdatedDeerGenes(int minValue, int maxValue, boolean hasGene, boolean isMutable, int currentValue)
	 private UpdatedDeerGenes(Object minValue, Object maxValue, boolean hasGene, boolean isMutable, Object currentValue)
	 STOTTING(0, 1),
	 GROUPING(1, 1),
	
	
	 */

}